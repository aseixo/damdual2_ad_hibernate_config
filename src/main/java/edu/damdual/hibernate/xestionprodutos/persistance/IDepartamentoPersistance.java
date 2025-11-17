package edu.damdual.hibernate.xestionprodutos.persistance;

import edu.damdual.hibernate.xestionprodutos.model.Departamento;

import java.util.List;

public interface IDepartamentoPersistance {

    public Long save(Departamento departamento);
    public void merge(Departamento departamento);
    public void persists(Departamento departamento);
    public Departamento getById(int id);
    public List<Departamento> getAll();
    public void update(Departamento departamento);

    public void delete(Departamento departamento);
    public int deleteById(int id);
    public List<Departamento> todos();
    public List<Departamento> seleccionarPorId(int id);

}
