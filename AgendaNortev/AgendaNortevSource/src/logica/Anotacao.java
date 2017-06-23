package logica;

import javax.persistence.*;

@Entity
@Table ( name = "Anotacao" )
public class Anotacao {
    
       
        
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private int id;
    private String titulo;
    private String conteudo;

    public Anotacao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Anotacao(String titulo, String conteudo) {
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    

}
