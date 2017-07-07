
package com.ifpb.HiDiary.Modelo;
import Excecoes.AgendaInvalidaException;

import Excecoes.PreencheCamposException;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.mail.EmailException;


public class Usuario implements Serializable{
    private String nome;
    private LocalDate nascimento;
    private String sexo;
    private String email;
    private String senha;

    public Usuario(String nome, LocalDate nascimento, String sexo, String email, String senha) {
        if(nome.equals("")) throw new PreencheCamposException("O nome não pode ser vazio");
        if(nascimento.isAfter(LocalDate.now())) throw new DateTimeException("O nascimento não pode ser depois de hoje");
        this.nome = nome;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.email = email;
        this.senha = senha;
    }
    public Usuario(){
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.equals("")) throw new PreencheCamposException("O nome não pode ser vazio");
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        if(nascimento.isAfter(LocalDate.now())) throw new DateTimeException("O nascimento não pode ser depois de hoje");
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.equals("")) throw new PreencheCamposException("O email não pode ser vazio");
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if(senha.equals("")) throw new PreencheCamposException("A senha não pode ser vazio");
        this.senha = senha;
    }
    
    public boolean autenticar(String email, String senha){
        return this.email.equals(email) && this.senha.equals(senha);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.nome);
        hash = 71 * hash + Objects.hashCode(this.nascimento);
        hash = 71 * hash + Objects.hashCode(this.sexo);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.senha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.nascimento, other.nascimento)) {
            return false;
        }
        return true;
    }

}
