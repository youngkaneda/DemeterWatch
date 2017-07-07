
package com.ifpb.HiDiary.Controle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.ifpb.HiDiary.Modelo.Usuario;

public interface UsuarioDao {
    
    public Usuario read(String email) throws ClassNotFoundException, SQLException, IOException;
    public List<Usuario> list() throws SQLException, ClassNotFoundException, IOException;
    public boolean create(Usuario usuario) throws ClassNotFoundException, SQLException, IOException;
    public boolean delete(String email) throws ClassNotFoundException, SQLException, IOException;
    public boolean update(Usuario usuario) throws ClassNotFoundException, SQLException, IOException;

}
