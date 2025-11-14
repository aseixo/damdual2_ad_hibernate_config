package edu.damdual.hibernate.xestionprodutos.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;
    @Column(name = "descricion")
    private String descricion;
    @Column(name="prezo")
    private BigDecimal prezo;
    @Column(name = "cantidade_stock")
    private int cantidadeStock;
    @Column(name = "data_creacion")
    private Date dataCreacion;
    @Column(name = "data_actualizacion")
    private Date dataActualizacion;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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

    public BigDecimal getPrezo() {
        return prezo;
    }

    public void setPrezo(BigDecimal prezo) {
        this.prezo = prezo;
    }

    public int getCantidadeStock() {
        return cantidadeStock;
    }

    public void setCantidadeStock(int cantidadeStock) {
        this.cantidadeStock = cantidadeStock;
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

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricion='" + descricion + '\'' +
                ", prezo=" + prezo +
                ", cantidadeStock=" + cantidadeStock +
                ", dataCreacion=" + dataCreacion +
                ", dataActualizacion=" + dataActualizacion +
                ", departamento=" + departamento +
                '}';
    }
}