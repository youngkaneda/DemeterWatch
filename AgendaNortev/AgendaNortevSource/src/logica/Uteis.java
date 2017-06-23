package logica;

import javax.persistence.*;

@Entity
@Table (name = "Uteis")
public class Uteis {

    @Id
    @GeneratedValue ( strategy= GenerationType.AUTO )
    private int id;
    private String descricao;
    private String tel;

    public Uteis() {
    }
   

    public Uteis(String descricao, String tel) {
        this.descricao = descricao;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
