
package com.ifpb.HiDiary.Controle;
import com.ifpb.HiDiary.Modelo.Compromisso;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public interface CompromissoDao {
    
    public boolean create(Compromisso novo) throws ClassNotFoundException, SQLException, IOException;
    public Compromisso read(String emailUsuario, String agenda, LocalDate data, LocalTime hora) throws ClassNotFoundException, SQLException, IOException;
    public List<Compromisso> list(String emailUsuario) throws SQLException, ClassNotFoundException, IOException;
    public List<Compromisso> list() throws SQLException, ClassNotFoundException, IOException;
    public boolean delete(Compromisso comp) throws ClassNotFoundException, SQLException, IOException;
    public List<Compromisso> compromissosIntervalo(String emailUsuario, LocalDate inicio, LocalDate fim) throws ClassNotFoundException, SQLException, IOException;
    public List<Compromisso> compromissos30dias(String emailUsuario) throws SQLException, ClassNotFoundException, IOException;
    public List<Compromisso> compromissos30dias(String emailUsuario, String nomeAgenda) throws SQLException, ClassNotFoundException, IOException;
    public boolean deletaCompAgenda(String emailUsuario, String nomeAgenda) throws SQLException, ClassNotFoundException , IOException;
    public List<Compromisso> compAgenda(String emailUsuario, String nomeAgenda) throws SQLException, ClassNotFoundException , IOException;
    public boolean updateAgendaComp(String emailUsuario, String nomeAntigo, String nomeAtual) throws SQLException, ClassNotFoundException , IOException;
}
