package br.edu.ifsp.arq.dmos5.ifitness.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "atividade")
public class Atividade implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private Usuario usuario;
    private String categoriaAtividade;
    private Double distancia;
    private Double duracao;
    private String dataAtividade;

    public Atividade(Usuario usuario, String categoriaAtividade, Double distancia, Double duracao, String dataAtividade) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.categoriaAtividade = categoriaAtividade;
        this.distancia = distancia;
        this.duracao = duracao;
        this.dataAtividade = dataAtividade;
    }

    @Ignore
    public Atividade(){
        this(null,"",null,null, "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCategoriaAtividade() {
        return categoriaAtividade;
    }

    public void setCategoriaAtividade(String categoriaAtividade) {
        this.categoriaAtividade = categoriaAtividade;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getDuracao() {
        return duracao;
    }

    public void setDuracao(Double duracao) {
        this.duracao = duracao;
    }

    public String getDataAtividade() {
        return dataAtividade;
    }

    public void setDataAtividade(String dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividade atividade = (Atividade) o;
        return id.equals(atividade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "categoriaAtividade='" + categoriaAtividade + '\'' +
                ", distancia=" + distancia +
                ", duracao=" + duracao +
                ", dataAtividade='" + dataAtividade + '\'' +
                '}';
    }
}
