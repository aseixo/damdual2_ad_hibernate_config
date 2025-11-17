package edu.damdual.hibernate.xestionprodutos.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "departamentos")

@NamedNativeQueries( {
        @NamedNativeQuery(name = "Departamento.todos",
        query = "select * from departamentos d order by d.nome asc",
        resultClass= Departamento.class)
})
@NamedQueries({
        @NamedQuery(name = "Departamento.porId",
        query = "SELECT d FROM Departamento d where d.id = :id")
})
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name= "nome")
    private String nome;
    @Column(name = "descricion")
    private String descricion;
    @Column(name = "data_creacion")
    private Date dataCreacion;
    @Column(name = "data_actualizacion")
    private Date dataActualizacion;

    @OneToMany(mappedBy = "departamento")
    List<Produto> produtos;

    public Departamento() {
    }

    public Departamento(int id, String nome, String descricion, Date dataCreacion, Date dataActualizacion) {
        this.id = id;
        this.nome = nome;
        this.descricion = descricion;
        this.dataCreacion = dataCreacion;
        this.dataActualizacion = dataActualizacion;
    }

    public Departamento(int id, String nome, String descricion, Date dataCreacion, Date dataActualizacion, List<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.descricion = descricion;
        this.dataCreacion = dataCreacion;
        this.dataActualizacion = dataActualizacion;
        this.produtos = produtos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public Date getDataCreacion() {
        return dataCreacion;
    }

    public void setDataCreacion(Date dataCreacion) {
        this.dataCreacion = dataCreacion;
    }

    public Date getDataActualizacion() {
        return dataActualizacion;
    }

    public void setDataActualizacion(Date dataActualizacion) {
        this.dataActualizacion = dataActualizacion;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricion='" + descricion + '\'' +
                ", dataCreacion=" + dataCreacion +
                ", dataActualizacion=" + dataActualizacion +
                '}';
    }
}