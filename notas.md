# Diferenzas entre getCurrentSession() e openSession()

## getCurrentSession()

- **Xestión automática**: A sesión está vinculada ao contexto actual (normalmente unha transacción)
- **Ciclo de vida**: A sesión féchase automáticamente cando remata a transacción
- **Non precisas pechar**: Non chamar a `session.close()`, faise automaticamente
- **Ámbito**: Unha sesión por transacción/thread (depende da configuración)
- **Configuración**: Require configurar o `current_session_context_class` no ficheiro de configuración de Hibernate **>> NON!! <<**

```java
// Non precisa fechar explícitamente
Session session = sessionFactory.getCurrentSession();
session.beginTransaction();
// ... operacións ...
session.getTransaction().commit(); // Péchase automáticamente
```

## openSession()

- **Xestión manual**: Controlamos completamente o ciclo de vida da sesión
- **Ciclo de vida**: Débese fechar a sesión explícitamente
- **Fechamento obrigatorio**: Sempre chamar a `session.close()`
- **Ámbito**: Múltiplas sesións abertas simultaneamente
- **Flexibilidade**: Útil cando precisar control total sobre as sesións

```java
// Débese fechar explícitamente
Session session = sessionFactory.openSession();
try {
    session.beginTransaction();
    // ... operacións ...
    session.getTransaction().commit();
} finally {
    session.close(); // IMPORTANTE: pechado manual
}
```

## Cando usar cada unha?

- **getCurrentSession()**: Recomendado para aplicacións web e con Spring/Java EE, onde as transaccións están ben definidas. **<<<-- OK!!**
- **openSession()**: Útil cando se precisa múltiplas sesións concorrentes ou control fino sobre o ciclo de vida.

## Exemplo de uso de transacción con commit e rollback

```java
Transaction transaction = null;
try {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    transaction = session.beginTransaction();
    Serializable id = session.save(departamento);
    transaction.commit();
} catch (HibernateException e) {
    if (transaction != null) {
        transaction.rollback();
    }
    throw e;
}
```

### Observacións IMPORTANTES do código anterior
**A ter en conta en todos os proxectos con Hibernate**

- **Non usar try-with-resources con getCurrentSession()**: A sesión féchase automáticamente ao rematar a transacción **<<< --- ok**
- **commit() dentro do try**: Debe estar dentro do bloque try para que poida facerse rollback se falla **<< -- ok**
- **Engadir rollback()**: Se hai unha excepción, debes facer rollback da transacción para descartar os cambios **<< -- ok**
- **Inicializar transaction a null**: Para poder comprobar se existe antes de facer rollback **<< -- ok**

---

# Diferenzas entre merge, save e persist

## save()

- **API**: Específico de Hibernate (non está en JPA)
- **Retorna**: O identificador (ID) do obxecto gardado
- **Comportamento**: Insire sempre un novo rexistro na base de datos
- **Estado**: O obxecto pasa a estado persistent
- **ID**: Se o obxecto xa ten ID, pode dar erro ou comportamento inesperado

## persist()

- **API**: Estándar JPA (tamén dispoñible en Hibernate)
- **Retorna**: void (non retorna nada)
- **Comportamento**: Insire un novo rexistro, pero o INSERT pode retrasarse ata o flush
- **Estado**: O obxecto pasa a estado persistent
- **ID**: Se o obxecto xa ten ID asignado, lanza excepción
- **Recomendado**: Para aplicacións JPA puras

## merge()

- **API**: Estándar JPA
- **Retorna**: Unha copia managed do obxecto
- **Comportamento**:
  - Se o obxecto non existe → INSERT
  - Se o obxecto existe → UPDATE
- **Estado**: O obxecto orixinal segue detached, retorna unha nova instancia persistent
- **Uso**: Ideal para obxectos detached ou cando non sabes se existe

---

## Que significa "managed" (xestionado)?

En Hibernate/JPA, os obxectos poden estar en diferentes estados:

- **Transient (transitorio)**: Obxecto novo, non gardado na BD, non xestionado por Hibernate
- **Persistent/Managed (xestionado)**: Obxecto asociado cunha sesión/EntityManager activa. Hibernate vixía os seus cambios
- **Detached (desconectado)**: Obxecto que estivo managed pero xa non está asociado á sesión

### Exemplo de managed

```java
// Obxecto DETACHED (non está xestionado)
Departamento dept = new Departamento();
dept.setId(1L);
dept.setNombre("Vendas");

EntityManager em = entityManagerFactory.createEntityManager();
em.getTransaction().begin();

// merge() retorna unha copia MANAGED
Departamento deptManaged = em.merge(dept);

// Agora hai DÚAS instancias:
System.out.println(dept == deptManaged); // false - son obxectos diferentes

// Os cambios en deptManaged SÍ se gardan automáticamente <<< OK !!
deptManaged.setNombre("Marketing"); // Hibernate detecta o cambio

// Os cambios en dept NON se gardan
dept.setNombre("Compras"); // Hibernate ignora isto

em.getTransaction().commit(); // Só se garda "Marketing"
```