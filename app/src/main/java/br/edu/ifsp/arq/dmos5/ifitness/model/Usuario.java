package br.edu.ifsp.arq.dmos5.ifitness.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "usuario")
public class Usuario implements Serializable{

    @NonNull
    @PrimaryKey
    private String id;
    private String nome;
    private String dataNascimento;
    private String genero;
    private String telefone;
    private Double peso;
    private String email;
    private String senha;
    private String imagem;
    private int pontuacao;
    private List<String> emblemas;

    public Usuario(String nome, String dataNascimento, String genero, String telefone, Double peso, String email, String senha, String imagem, int pontuacao, List<String> emblemas) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.peso = peso;
        this.email = email;
        this.senha = senha;
        this.imagem = imagem;
        this.pontuacao = pontuacao;
        this.emblemas = emblemas;
    }

    @Ignore
    public Usuario(){
        this("","","","", 0.0, "", "", "", 0, null);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public List<String> getEmblemas() {
        return emblemas;
    }

    public void setEmblemas(List<String> emblemas) {
        this.emblemas = emblemas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

