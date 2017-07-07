
package com.ifpb.HiDiary.Controle;
import Excecoes.CompromissosException;
import com.ifpb.HiDiary.Banco.ConFactory;
import com.ifpb.HiDiary.Modelo.Compromisso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

    /**
     * Essa classe tem como finalidade controlar todo o DAO de compromissos no banco de dados
     * @author Lyndemberg
     * @version 1.0
     */
public class CompromissoDaoBanco implements CompromissoDao{
    
    /**
     * Método construtor da classe
     * @author Lyndemberg
     * @version 1.0
     */
    public CompromissoDaoBanco(){
        
    }
    
    /**
     * Método que insere um novo compromisso no banco de dados
     * @param novo Compromisso - o novo compromisso a ser inserido
     * @return boolean - true se conseguiu inserir, ou false se não conseguiu
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean create(Compromisso novo) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("INSERT INTO compromisso (emailUsuario, nomeAgenda, data, hora, descricao, local)"+
                                                      "VALUES (?,?,?,?,?,?)");
        stmt.setString(1, novo.getEmailUsuario());
        stmt.setString(2, novo.getNomeAgenda());
        stmt.setDate(3, java.sql.Date.valueOf(novo.getData()));
        stmt.setTime(4, java.sql.Time.valueOf(novo.getHora()));
        stmt.setString(5, novo.getDescricao());
        stmt.setString(6, novo.getLocal());
        boolean retorno = stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }
    
    /**
     * Método que lê um compromisso no banco de dados
     * @param emailUsuario String - o email do usuário dono do compromisso
     * @param agenda String - a agenda que o compromisso está inserido
     * @param data LocalDate - a data do compromisso
     * @param hora LocalTime - a hora do compromisso
     * @return Retorna o compromisso caso seja encontrado, ou null se não for encontrado
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public Compromisso read(String emailUsuario, String agenda, LocalDate data, LocalTime hora) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * compromisso WHERE emailUsuario=? AND nomeAgenda=? AND data=? AND hora=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, agenda);
        stmt.setDate(3, java.sql.Date.valueOf(data));
        stmt.setTime(4, java.sql.Time.valueOf(hora));
        
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            con.close();
            return comp;
        }else{
            con.close();
            return null;
        }
    }

    /**
     * Método que lista todos os compromissos de um usuário
     * @param emailUsuario String - o email do usuário dono do compromissos
     * @return Retorna a lista de compromissos do usuário
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> list(String emailUsuario) throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM compromisso WHERE emailUsuario=?");
        stmt.setString(1, emailUsuario);
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compUsuario = new ArrayList<>();
        while(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            compUsuario.add(comp);
        }
        con.close();
        return compUsuario;
    }

    /**
     * Método que lista todos os compromissos salvos no banco de dados
     * @return Retorna a lista de compromissos
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> list() throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM compromissos");
        
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compromissos = new ArrayList<>();
        while(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            compromissos.add(comp);
        }
        con.close();
        return compromissos;
    }

    /**
     * Método que deleta um compromisso salvo no banco de dados
     * @param comp Compromisso - o compromisso a ser deletado
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean delete(Compromisso comp) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("DELETE FROM compromisso WHERE emailUsuario = ? AND data=? AND hora=?");
        stmt.setString(1, comp.getEmailUsuario());
        stmt.setDate(2, java.sql.Date.valueOf(comp.getData()));
        stmt.setTime(3, java.sql.Time.valueOf(comp.getHora()));
        boolean retorno =  stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }

    /**
     * Método que lista os compromissos que um usuário tem dentro de um intervalo de datas
     * @param emailUsuario String - o email do usuário
     * @param inicio LocalDate - a data de início do intervalo
     * @param fim LocalDate - a data de fim do intervalo
     * @return A lista de compromissos que estão dentro do intervalo de datas
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @throws CompromissosException Se não tiver nenhum compromisso dentro do intervalo de datas 
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compromissosIntervalo(String emailUsuario, LocalDate inicio, LocalDate fim) throws ClassNotFoundException, SQLException{
        if(inicio.isAfter(fim)) throw new DateTimeException("Intervalo inválido. A data de fim é menor que de início");
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM compromisso WHERE emailUsuario=? AND data>=? AND data<=?");
        stmt.setString(1, emailUsuario);
        stmt.setDate(2, java.sql.Date.valueOf(inicio));
        stmt.setDate(3, java.sql.Date.valueOf(fim));
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compIntervalo = new ArrayList<>();
        while(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            compIntervalo.add(comp);
        }
        con.close();
        if(!compIntervalo.isEmpty()){
            return compIntervalo;
        }else{
            throw new CompromissosException("Você não tem compromissos nesse intervalo");
        }
    }

    /**
     * Método que lista os compromissos que um usuário tem para os próximos 30 dias (a partir do dia atual)
     * @param emailUsuario String - o email do usuário dono dos compromissos
     * @return A lista de compromissos que estão dentro do intervalo de 30 dias
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @throws CompromissosException Se não tiver nenhum compromisso para os próximos 30 dias
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compromissos30dias(String emailUsuario) throws SQLException, ClassNotFoundException{
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(30);
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM compromisso WHERE emailUsuario=? AND data>=? AND data<=?");
        stmt.setString(1, emailUsuario);
        stmt.setDate(2, java.sql.Date.valueOf(inicio));
        stmt.setDate(3, java.sql.Date.valueOf(fim));
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compUsuario = new ArrayList<>();
        while(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            compUsuario.add(comp);
        }
        con.close();
        if(!compUsuario.isEmpty()){
            return compUsuario;
        }else{
            throw new CompromissosException("Sem compromissos para os próximos 30 dias");
        }
    }

    /**
     * Método que lista os compromissos que um usuário tem dentro de uma agenda para os próximos 30 dias(a partir do dia atual)
     * @param emailUsuario String - o email do usuário dono dos compromissos
     * @param nomeAgenda String - o nome da agenda do usuário
     * @return A lista de compromissos que estão dentro do intervalo de 30 dias na agenda
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @throws CompromissosException Se não tiver nenhum compromisso para os próximos 30 dias na agenda
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compromissos30dias(String emailUsuario, String nomeAgenda) throws SQLException, ClassNotFoundException{
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now().plusDays(30);
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM compromisso WHERE emailUsuario=? AND nomeAgenda=? AND data>=? AND data<=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, nomeAgenda);
        stmt.setDate(3, java.sql.Date.valueOf(inicio));
        stmt.setDate(4, java.sql.Date.valueOf(fim));
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compAgenda30dias = new ArrayList<>();
        while(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            compAgenda30dias.add(comp);
        }
        con.close();
        if(!compAgenda30dias.isEmpty()){
            return compAgenda30dias;
        }else{
            throw new CompromissosException("Essa agenda não tem compromissos para os próximos 30 dias");
        }
    }

    /**
     * Método que deleta todos os compromissos de uma agenda específica de um usuário
     * @param emailUsuario String - o email do usuário
     * @param nomeAgenda String - o nome da agenda do usuário
     * @return boolean - true se conseguiu deletar os compromissos, ou false se não conseguiu
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean deletaCompAgenda(String emailUsuario, String nomeAgenda) throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("DELETE FROM compromisso WHERE emailUsuario=? AND nomeAgenda=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, nomeAgenda);
        boolean retorno =  stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }

    /**
     * Método que lista os compromissos de uma agenda de um usuário
     * @param emailUsuario String - o email do usuário
     * @param nomeAgenda String - o nome da agenda do usuário
     * @return A lista de compromissos que estão dentro da agenda do usuário
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compAgenda(String emailUsuario, String nomeAgenda) throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM compromisso WHERE emailUsuario=? AND nomeAgenda=?");
        stmt.setString(1, emailUsuario);
        stmt.setString(2, nomeAgenda);
        ResultSet rs = stmt.executeQuery();
        List<Compromisso> compUsuarioAgenda = new ArrayList<>();
        while(rs.next()){
            Compromisso comp = new Compromisso();
            comp.setEmailUsuario(rs.getString("emailUsuario"));
            comp.setNomeAgenda(rs.getString("nomeAgenda"));
            comp.setData(rs.getDate("data").toLocalDate());
            comp.setHora(rs.getTime("hora").toLocalTime());
            comp.setDescricao(rs.getString("descricao"));
            comp.setLocal(rs.getString("local"));
            compUsuarioAgenda.add(comp);
        }
        con.close();
        return compUsuarioAgenda;
    }

    /**
     * Método que atualiza o nome da agenda de compromissos que antes estavam em outra agenda
     * @param emailUsuario String - representa o email do usuário dono dos compromissos
     * @param nomeAntigo String - representa o nome da agenda que antes estava nos compromissos
     * @param nomeAtual String - representa o novo nome da agenda a ser colocado nos compromissos
     * @return boolean - true se conseguiu atualizar o nome, ou false se não conseguiu
     * @throws SQLException Se tiver problemas na conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean updateAgendaComp(String emailUsuario, String nomeAntigo, String nomeAtual) throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("UPDATE compromisso SET nomeAgenda = ? WHERE emailUsuario=? AND nomeAgenda=?");
        stmt.setString(1, nomeAtual);
        stmt.setString(2, emailUsuario);
        stmt.setString(3, nomeAntigo );
        boolean retorno = stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }
    
}
