
package com.ifpb.HiDiary.Controle;
import com.ifpb.HiDiary.Banco.ConFactory;
import com.ifpb.HiDiary.Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    /**
     * Essa classe tem como finalidade controlar todo o DAO de todos os usuários usando banco de dados
     * @author Lyndemberg
     * @version 1.0
     */
public class UsuarioDaoBanco implements UsuarioDao{
    
    /**
     * Método construtor da classe
     * @author Lyndemberg
     * @version 1.0
     */
    public UsuarioDaoBanco(){
        
    }
    
    /**
     * Método que lê um usuário no banco
     * @param email String - o email do usuário a ser lido
     * @return O usuário caso seja encontrado, ou null se não for encontrado
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public Usuario read(String email) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario WHERE email = ?");
        stmt.setString(1, email);
        
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            Usuario usuario = new Usuario();
            
            usuario.setEmail(rs.getString("email"));
            Date dataDate = rs.getDate("nascimento");
            LocalDate nascimento = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dataDate));
            usuario.setNascimento(nascimento);
            usuario.setSexo(rs.getString("sexo"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            con.close();
            return usuario;
        }else{
            con.close();
            return null;
        }
    }

    /**
     * Método que lista todos os usuários salvos no banco
     * @return A lista de usuários
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Usuario> list() throws SQLException, ClassNotFoundException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario");
        
        ResultSet rs = stmt.executeQuery();
        List<Usuario> usuarios = new ArrayList<>();
        while(rs.next()){
            Usuario usuario = new Usuario();
            usuario.setEmail(rs.getString("email"));
            Date dataDate = rs.getDate("nascimento");
            LocalDate nascimento = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dataDate));
            usuario.setNascimento(nascimento);
            usuario.setSexo(rs.getString("sexo"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
            usuarios.add(usuario);
        }
        con.close();
        return usuarios;
    }

    /**
     * Método que insere um novo usuário no banco de dados
     * @param usuario Usuario - o novo usuário a ser inserido
     * @return boolean - true se conseguiu inserir, ou false se não conseguiu
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean create(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("INSERT INTO usuario (nome, nascimento, sexo, email, senha)"+
                                                      "VALUES (?,?,?,?,?)");
        stmt.setString(1, usuario.getNome());
        stmt.setDate(2, java.sql.Date.valueOf(usuario.getNascimento()));
        stmt.setString(3, usuario.getSexo());
        
        stmt.setString(4, usuario.getEmail());
        stmt.setString(5, usuario.getSenha());
        boolean retorno = stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }

    /**
     * Método que deleta um usuário no banco de dados
     * @param email String - o email do usuário a ser deletado
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean delete(String email) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("DELETE FROM usuario WHERE email = ?");
        stmt.setString(1, email);
        boolean retorno =  stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }

    /**
     * Método que atualiza um usuário no banco de dados
     * @param usuario Usuario - o objeto usuário já com as modificações
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @throws SQLException Se houver algum problema durante a conexão com o banco
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o banco
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean update(Usuario usuario) throws ClassNotFoundException, SQLException{
        Connection con = ConFactory.getConnection();
        PreparedStatement stmt = con.prepareStatement("UPDATE usuario SET(nome, nascimento, sexo, email, senha)"+
                                                      "= (?,?,?,?,?) WHERE email = ?");
        
        stmt.setString(1, usuario.getNome());
        stmt.setDate(2, java.sql.Date.valueOf(usuario.getNascimento()));
        stmt.setString(3, usuario.getSexo());
        
        stmt.setString(4, usuario.getEmail());
        stmt.setString(5, usuario.getSenha());
        boolean retorno = stmt.executeUpdate()>0;
        con.close();
        return retorno;
    }

}
