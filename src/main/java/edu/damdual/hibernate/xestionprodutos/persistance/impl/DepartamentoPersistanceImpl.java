package edu.damdual.hibernate.xestionprodutos.persistance.impl;

import edu.damdual.hibernate.xestionprodutos.model.Departamento;
import edu.damdual.hibernate.xestionprodutos.persistance.IDepartamentoPersistance;
import edu.damdual.hibernate.xestionprodutos.util.HibernateUtil;
import edu.damdual.hibernate.xestionprodutos.util.HibernateUtil2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class DepartamentoPersistanceImpl implements IDepartamentoPersistance {
    @Override
    public Long save(Departamento departamento) {
        //Esquema para implementación con Excepcións a nivel de Hibernate
        Transaction transaction = null;
        try (Session session = HibernateUtil2.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Serializable id = session.save(departamento);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null)
                transaction.rollback();
            throw e;
        };
        return new Long(departamento.getId());
    }

    @Override
    public void merge(Departamento departamento) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(departamento);
        transaction.commit();
    }

    @Override
    public void persists(Departamento departamento) {
        Session session = HibernateUtil2.getSessionFactory().getCurrentSession();
        Transaction transaction =session.beginTransaction();
        session.persist(departamento);
    }

    @Override
    public Departamento getById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Departamento departamento = session.get(Departamento.class, id);
        transaction.commit();
        return departamento;
    }

    @Override
    public List<Departamento> getAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Departamento";
        Query<Departamento> queryAll = session.createQuery(hql);
        List<Departamento> departamentoList = queryAll.list();
        transaction.commit();
        return departamentoList;
    }

    @Override
    public void update(Departamento departamento) {
        Session session = HibernateUtil2.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(departamento);
        transaction.commit();

    }

    @Override
    public void delete(Departamento departamento) {
        Session session = HibernateUtil2.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(departamento);
        transaction.commit();
    }

    @Override
    public int deleteById(int id) {
        int res = -1;
        Session session = HibernateUtil2.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Departamento departamento = this.getById(id);
        if (departamento!=null){
            session.delete(departamento);
            res = departamento.getId();
        }
        transaction.commit();
        return  id;
    }

}
