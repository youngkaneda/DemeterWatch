package logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table ( name="Compromisso" )
public class Compromisso implements Serializable {
    
    
    @Id
    @GeneratedValue ( strategy=GenerationType.AUTO )
    private int id;
    
    @Temporal ( TemporalType.DATE )
    private Date data;
    
    private String hora;
    private String local;
    private String detalhes;
    private boolean notificacao;
    private boolean fuiNotificadoVespera;
    private boolean fuiNotificadoDia;

    public Compromisso() {
    }


    public Compromisso(Date data, String hora, String local, String detalhes, boolean not) {
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.detalhes = detalhes;
        notificacao = not;
        fuiNotificadoVespera = false;
        fuiNotificadoDia = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public boolean isFuiNotificadoDia() {
        return fuiNotificadoDia;
    }

    public void setFuiNotificadoDia(boolean fuiNotificadoDia) {
        this.fuiNotificadoDia = fuiNotificadoDia;
    }

    public boolean isFuiNotificadoVespera() {
        return fuiNotificadoVespera;
    }

    public void setFuiNotificadoVespera(boolean fuiNotificadoVespera) {
        this.fuiNotificadoVespera = fuiNotificadoVespera;
    }

    public boolean isNotificacao() {
        return notificacao;
    }

    public void setNotificacao(boolean notificacao) {
        this.notificacao = notificacao;
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }    
}
