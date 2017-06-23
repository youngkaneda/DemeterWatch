package logica;

import javax.persistence.*;

@Entity
@Table ( name = "Senha")
public class SerialSenha {
    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;
    
    @Column (name = "usuario")
    private String usuarioSoft;
    @Column (name = "senha")
    private String senhaSerial;
    
    private String comentario;

    public SerialSenha() {
    }

    public SerialSenha(String usuarioSoft, String senhaSerial, String comentario) {
        this.usuarioSoft = usuarioSoft;
        this.senhaSerial = senhaSerial;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getSenhaSerial() {
        return senhaSerial;
    }

    public void setSenhaSerial(String senhaSerial) {
        this.senhaSerial = senhaSerial;
    }

    public String getUsuarioSoft() {
        return usuarioSoft;
    }

    public void setUsuarioSoft(String usuarioSoft) {
        this.usuarioSoft = usuarioSoft;
    }

    

}
