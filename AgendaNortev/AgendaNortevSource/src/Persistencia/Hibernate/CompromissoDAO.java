package Persistencia.Hibernate;

import java.util.ArrayList;
import java.util.List;
import logica.Compromisso;

public class CompromissoDAO extends Dao<Compromisso>{
    
    public CompromissoDAO(){
        super(Compromisso.class);
    }
    
    //STUB method
    public List<Compromisso> listarNotificacoes(){
        //Deve executar a query:
            //select * from Compromisso where notificacao = TRUE AND (fuiNotificadoVespera = FALSE OR fuiNotificadoDia = FALSE );
        
        List<Compromisso> lista = new ArrayList<Compromisso>();
        return lista;
    }
    
}
