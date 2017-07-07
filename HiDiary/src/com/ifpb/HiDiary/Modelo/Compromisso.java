
package com.ifpb.HiDiary.Modelo;
import Excecoes.PreencheCamposException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


public class Compromisso implements Serializable{
    private String emailUsuario;
    private String nomeAgenda;
    private LocalDate data;
    private LocalTime hora;
    private String descricao;
    private String local;

   
    public Compromisso(String emailUsuario, String nomeAgenda, LocalDate data, LocalTime hora, String descricao, String local) {
        if(data.isBefore(LocalDate.now())) throw new DateTimeException("A data é anterior a hoje");
        if(descricao.equals("")) throw new PreencheCamposException("A descrição não pode ser vazia");
        if(descricao.equals("")) throw new PreencheCamposException("O local não pode ser vazio");
        this.emailUsuario = emailUsuario;
        this.nomeAgenda = nomeAgenda;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.local = local;
    }
   
    public Compromisso(){
        
    }

    
    public String getEmailUsuario() {
        return emailUsuario;
    }

    
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

   
    public String getNomeAgenda() {
        return nomeAgenda;
    }

    
    public void setNomeAgenda(String nomeAgenda) {
        this.nomeAgenda = nomeAgenda;
    }

    
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        if(data.isBefore(LocalDate.now())) throw new DateTimeException("A data é anterior a hoje");
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if(descricao.equals("")) throw new PreencheCamposException("A descrição não pode ser vazia");
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        if(local.equals("")) throw new PreencheCamposException("O local não pode ser vazia");
        this.local = local;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.emailUsuario);
        hash = 97 * hash + Objects.hashCode(this.nomeAgenda);
        hash = 97 * hash + Objects.hashCode(this.data);
        hash = 97 * hash + Objects.hashCode(this.hora);
        hash = 97 * hash + Objects.hashCode(this.descricao);
        hash = 97 * hash + Objects.hashCode(this.local);
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
        final Compromisso other = (Compromisso) obj;
        if (!Objects.equals(this.emailUsuario, other.emailUsuario)) {
            return false;
        }
        if (!Objects.equals(this.nomeAgenda, other.nomeAgenda)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.local, other.local)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        return true;
    }

}
