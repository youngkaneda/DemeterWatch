
package com.ifpb.HiDiary.Banco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConFactory {

    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        
        String url = "jdbc:postgresql://localhost:5432/HiDiary";
        String usuario = "postgres";
        String senha = "lbn15258158";
        
        return DriverManager.getConnection(url,usuario,senha);
    }
}
