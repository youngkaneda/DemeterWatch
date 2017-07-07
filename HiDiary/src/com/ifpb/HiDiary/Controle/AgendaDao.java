
package com.ifpb.HiDiary.Controle;

import Excecoes.AgendasVaziasException;
import com.ifpb.HiDiary.Modelo.Agenda;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.ifpb.HiDiary.Modelo.Usuario;

public interface AgendaDao {
    
    public boolean create(Agenda nova) throws ClassNotFoundException, SQLException, IOException;
    public Agenda read(String emailUsuario, String nome) throws ClassNotFoundException, SQLException, IOException;
    public List<Agenda> list() throws SQLException, ClassNotFoundException, IOException;
    public List<Agenda> list(String emailUsuario) throws SQLException, ClassNotFoundException, IOException;
    public List<String> listNomesAgendas(String emailUsuario) throws SQLException, ClassNotFoundException, IOException;
    public boolean delete(String emailUsuario, String nome) throws ClassNotFoundException, SQLException, IOException;
    public boolean update(String emailUsuario, String nomeAntigo, String nomeAtual) throws ClassNotFoundException, SQLException, IOException;

}
