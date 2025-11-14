package edu.damdual.hibernate.xestionprodutos.app;

import edu.damdual.hibernate.xestionprodutos.model.Departamento;
import edu.damdual.hibernate.xestionprodutos.persistance.IDepartamentoPersistance;
import edu.damdual.hibernate.xestionprodutos.persistance.impl.DepartamentoPersistanceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String ... args) {

        IDepartamentoPersistance departamentoPersistance = new DepartamentoPersistanceImpl();
        Departamento departamento = new Departamento();
        departamento.setNome("hibernate utils bla bla ");
        //departamento.setId(45);
        departamento.setDataCreacion(Date.valueOf(LocalDate.now()));
        departamento.setDataActualizacion(Date.valueOf(LocalDate.now()));
        Long id = departamentoPersistance.save(departamento);

        Departamento departamento2 = new Departamento();
        departamento2.setNome("complementos de baño e duche");
        departamento2.setDescricion("artigos como toalleiros, torneiras, luces, etc");
        departamento2.setDataCreacion(Date.valueOf(LocalDate.now()));
        departamento2.setDataActualizacion(Date.valueOf(LocalDate.now()));
        //departamentoPersistance.merge(departamento2);

        departamento2.setDescricion("cambio de descrición");
        departamentoPersistance.update(departamento2);

        departamentoPersistance.delete(departamento);
        Departamento d = departamentoPersistance.getById(2);
        System.out.println(d.getNome());
        //List<Departamento> departamentoList = departamentoPersistance.getAll();
        //departamentoList.stream().forEach(System.out::println);
    }
}
