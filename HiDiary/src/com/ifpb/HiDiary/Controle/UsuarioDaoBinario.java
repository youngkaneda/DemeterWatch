
package com.ifpb.HiDiary.Controle;

import com.ifpb.HiDiary.Modelo.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

    /**
     * Essa classe tem como finalidade controlar todo o DAO de usuários no arquivo
     * @author Lyndemberg
     * @version 1.0
     */
public class UsuarioDaoBinario implements UsuarioDao{
    
    private File arquivo;
    
    /**
     * Método construtor da classe que inicializa o arquivo caso ele não exista ainda
     * @author Lyndemberg
     * @version 1.0
     */
    public UsuarioDaoBinario(){
        arquivo = new File("usuarios.bin");
        
        if(!arquivo.exists()){
            try{
                arquivo.createNewFile();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Falha na conexão com o arquivo","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    /**
     * Método que lê um determinado usuário
     * @param email String - o email do usuário a ser lido
     * @return Retorna o usuário, ou null caso ele não exista
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public Usuario read(String email) throws ClassNotFoundException, IOException {
        List<Usuario> usuarios = list();
        for(int i=0; i<usuarios.size(); i++){
            if(usuarios.get(i).getEmail().equals(email)){
                return usuarios.get(i);
            }
        }
        return null;
    }
    
    /**
     * Método que lista todos os usuários
     * @return Retorna a lista de usuários salvos no arquivo
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Usuario> list() throws ClassNotFoundException, IOException {
        List<Usuario> usuario = null;
        
        if(arquivo.length()>0){
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(arquivo));
            return (List<Usuario>) input.readObject();
        }else{
            return new ArrayList<Usuario>();
        }
    }

    /**
     * Método que insere um novo usuário no arquivo
     * @param usuario Usuario - o novo usuário a ser inserido
     * @return boolean - true se conseguiu inserir, ou false se não conseguiu
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean create(Usuario usuario) throws ClassNotFoundException, IOException {
        List<Usuario> usuarios = list();
        
        for(int i=0; i<usuarios.size(); i++){
            if(usuarios.get(i).getEmail().equals(usuario.getEmail())){
                return false;
            }
        }
        usuarios.add(usuario);
        atualizarArquivo(usuarios);
        return true;
    }

    /**
     * Método que deleta um usuário do arquivo
     * @param email String - o email do usuário a ser deletado
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean delete(String email) throws ClassNotFoundException, IOException {
        List<Usuario> usuarios = list();
        
        for(int i=0; i<usuarios.size(); i++){
            if(usuarios.get(i).getEmail().equals(email)){
                usuarios.remove(usuarios.get(i));
                atualizarArquivo(usuarios);
                return true;
            }
        }
        return false;
    }

    /**
     * Método que atualiza um usuário no arquivo
     * @param usuario Usuario - o objeto do usuário com as novas mudanças 
     * @return boolean - true se conseguiu atualizar, ou false se não conseguiu
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean update(Usuario usuario) throws ClassNotFoundException, IOException {
        List<Usuario> usuarios = list();
        
        for(int i=0; i<usuarios.size(); i++){
            if(usuarios.get(i).getEmail().equals(usuario.getEmail())){
                usuarios.set(i, usuario);
                atualizarArquivo(usuarios);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Método que atualiza o arquivo cada vez que houver uma modificação na lista de usuários
     * @param usuarios Lista de usuários atualizada
     * @throws IOException Se houver problema na conexão com o arquivo 
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    private void atualizarArquivo(List<Usuario> usuarios) throws FileNotFoundException, IOException{
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(arquivo));
        output.writeObject(usuarios);
        output.close();
    }
}
