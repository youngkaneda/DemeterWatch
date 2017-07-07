
package com.ifpb.HiDiary.Controle;

import Excecoes.AgendaInvalidaException;
import Excecoes.AgendasVaziasException;
import com.ifpb.HiDiary.Banco.ConFactory;
import com.ifpb.HiDiary.Modelo.Agenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    /**
     * Essa classe tem como finalidade controlar todo o DAO das agendas de todos os usuários usando banco de dados
     * @author Lyndemberg
     * @version 1.0
     */
public class AgendaDaoBanco implements AgendaDao{
    /**
     * Método construtor da classe
     * @author Lyndemberg
     * @version 1.0
     */
    public AgendaDaoBanco(){
        
    }
    /**
     * Método que insere uma nova agenda no banco de dados
     * @param nova Agenda - é a nova agenda a ser inserida
     * @return boolean - true se for possível inserir a agenda, ou false se não conseguir
     * @throws SQLException Se o usuário dono dessa agenda já tiver uma agenda com o mesmo nome
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean create(Agenda nova) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("INSERT INTO agenda (emailUsuario, nome)"+
                                                      "VALUES (?,?)");        
        stmt.setString(1, nova.getEmailUsuario());
        stmt.setString(2, nova.getNome());
        boolean retorno = stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }
    /**
     * Método que lê uma nova agenda no banco de dados
     * @param emailUsuario String - representa o email do usuário dono da agenda buscada
     * @param nome String - representa o nome da agenda buscada
     * @return A agenda que foi buscada, ou null caso não encontre
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public Agenda read(String emailUsuario, String nome) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM agenda WHERE emailUsuario=? AND nome=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, nome);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            Agenda agenda = new Agenda(emailUsuario,nome);
            con.close();
            return agenda;
        }else{
            con.close();
            return null;
        }
    }
    /**
     * Método que lista todas as agendas salvas no banco de dados
     * @return A lista de agendas de todos os usuários
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Agenda> list() throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM agenda");
        
        ResultSet rs = stmt.executeQuery();
        List<Agenda> agendas = new ArrayList<>();
        while(rs.next()){
            Agenda agenda = new Agenda(rs.getString("emailUsuario"),rs.getString("nome"));
            agendas.add(agenda);    
        }
        con.close();
        return agendas;
    }
    /**
     * Método que lista todas as agendas de um determinado usuário salvas no banco de dados
     * @return A lista de agendas de um determinado usuário
     * @param emailUsuario String - o email do usuário
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Agenda> list(String emailUsuario) throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM agenda WHERE emailUsuario=?");
        stmt.setString(1, emailUsuario);
        ResultSet rs = stmt.executeQuery();
        List<Agenda> agendasUsuario = new ArrayList<>();
        while(rs.next()){
            Agenda agenda = new Agenda(rs.getString("emailUsuario"),rs.getString("nome"));
            agendasUsuario.add(agenda);
        }
        con.close();
        if(agendasUsuario.isEmpty()) throw new AgendasVaziasException("Você ainda não tem agendas");
        return agendasUsuario; 
    }
    
    /**
     * Método que lista somente os nomes de todas as agendas de um determinado usuário
     * @return A lista de String contendo os nomes das agendas do usuário
     * @param emailUsuario String - o email do usuário
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<String> listNomesAgendas(String emailUsuario) throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT nome FROM agenda WHERE emailUsuario=?");
        stmt.setString(1, emailUsuario);
        ResultSet rs = stmt.executeQuery();
        List<String> nomesAgendasUsuario= new ArrayList<>();
        while(rs.next()){
            nomesAgendasUsuario.add(rs.getString("nome"));
        }
        con.close();
        if(nomesAgendasUsuario.isEmpty()) throw new AgendasVaziasException("Você ainda não tem agendas");
        return nomesAgendasUsuario;
    }

    /**
     * Método que deleta uma determinada agenda de um usuário
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @param emailUsuario String - o email do usuário
     * @param nome String - o nome da agenda a ser deletada
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean delete(String emailUsuario, String nome) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("DELETE FROM agenda WHERE emailUsuario=? AND nome=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, nome);
        boolean retorno =  stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }

    /**
     * Método que atualiza o nome de uma agenda
     * @return boolean - true se conseguiu atualizar, ou false se não conseguiu
     * @param emailUsuario String - o email do usuário
     * @param nomeAntigo String - o nome antigo da agenda
     * @param nomeAtual String - o nome atual(novo) da agenda
     * @throws AgendaInvalidaException Se o usuário já tiver uma agenda com o mesmo nome
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean update(String emailUsuario, String nomeAntigo, String nomeAtual) throws ClassNotFoundException, SQLException{
        if(nomeAntigo.equals(nomeAtual)) throw new AgendaInvalidaException("A agenda já está com esse nome");
        if(nomeAtual==null) throw new AgendaInvalidaException("O nome da agenda não pode ser vazio");
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM agenda WHERE emailUsuario=? AND nome=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, nomeAtual);
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            con.close();
            throw new AgendaInvalidaException("Você já tem uma agenda com esse nome");
        }else{
            PreparedStatement stmt2 = con.prepareStatement("UPDATE agenda SET(nome) = (?) WHERE emailUsuario=? AND nome=?");
            stmt2.setString(1, nomeAtual);
            stmt2.setString(2, emailUsuario);
            stmt2.setString(3, nomeAntigo);
            boolean retorno = stmt2.executeUpdate()>0;
            con.close();
            return retorno;
        }
    }
    
}
