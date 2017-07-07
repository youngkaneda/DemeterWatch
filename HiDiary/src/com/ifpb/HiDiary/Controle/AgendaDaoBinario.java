
package com.ifpb.HiDiary.Controle;
import Excecoes.AgendaInvalidaException;
import Excecoes.AgendasVaziasException;
import com.ifpb.HiDiary.Modelo.Agenda;
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
     * Essa classe tem como finalidade controlar todo o DAO das agendas de todos os usuários usando arquivo
     * @author Lyndemberg
     * @version 1.0
     */
public class AgendaDaoBinario implements AgendaDao{
    
    private File arquivo;
    
    /**
     * Método construtor da classe que inicializa o arquivo caso ele não exista ainda
     * @author Lyndemberg
     * @version 1.0
     */
    public AgendaDaoBinario(){
        arquivo = new File("agendas.bin");
        
        if(!arquivo.exists()){
            try{
                arquivo.createNewFile();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Falha na conexão com o arquivo","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Método que insere uma nova agenda no arquivo
     * @param nova Agenda - é a nova agenda a ser inserida
     * @return boolean - true se for conseguiu inserir a agenda, ou false se não conseguir
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @throws AgendaInvalidaException Se o usuário já tiver uma agenda com o mesmo nome
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean create(Agenda nova) throws ClassNotFoundException, IOException{
        List<Agenda> agendas = list();
        for(int i=0; i<agendas.size(); i++){
            if(agendas.get(i).getEmailUsuario().equals(nova.getEmailUsuario()) && agendas.get(i).getNome().equals(nova.getNome())){
                throw new AgendaInvalidaException("Você já tem uma agenda com esse nome");
            }
        }
        agendas.add(nova);
        atualizarArquivo(agendas);
        return true;
    }

    /**
     * Método que lê uma agenda salva no arquivo
     * @param emailUsuario String - o email do usuário dono da agenda buscada
     * @param nome String - o nome da agenda a ser buscada
     * @return Retorna a agenda encontrada ou null se não encontrar
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public Agenda read(String emailUsuario, String nome) throws ClassNotFoundException, IOException {
        List<Agenda> agendas = list();
        for(int i=0; i<agendas.size(); i++){
            if(agendas.get(i).getEmailUsuario().equals(emailUsuario) && agendas.get(i).getNome().equals(nome)){
                return agendas.get(i);
            }
        }
        return null;
    }

    /**
     * Método que lê todas as agendas de um usuário que estão salvas no arquivo
     * @param emailUsuario String - o email do usuário dono das agendas
     * @return Retorna a lista de agendas do usuário
     * @throws IOException  Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @throws AgendasVaziasException Se o usuário não tiver nenhuma agenda
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Agenda> list(String emailUsuario) throws ClassNotFoundException, IOException{
        List<Agenda> agendas = list();
        List<Agenda> agendasUsuario = new ArrayList<>();
        if(agendas != null){
            for(int i=0; i<agendas.size(); i++){
                if(agendas.get(i).getEmailUsuario().equals(emailUsuario)){
                    agendasUsuario.add(agendas.get(i));
                }
            }
        
        }
        if(agendasUsuario.isEmpty()) throw new AgendasVaziasException("Você ainda não tem agendas");
        return agendasUsuario;
    }

    /**
     * Método que deleta uma agenda específica de um usuário
     * @param emailUsuario String - o email do usuário
     * @param nome String - o nome da agenda
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @throws IOException  Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean delete(String emailUsuario, String nome) throws ClassNotFoundException, IOException {
        List<Agenda> agendas = list();
        
        for(int i=0; i<agendas.size(); i++){
            if(agendas.get(i).getEmailUsuario().equals(emailUsuario) && agendas.get(i).getNome().equals(nome)){
                agendas.remove(agendas.get(i));
                atualizarArquivo(agendas);
                return true;
            }
        }
        return false;
    }

    /**
     * Método que atualiza o nome de uma agenda
     * @return boolean - true se conseguiu atualizar, ou false se não conseguiu
     * @param emailUsuario String - o email do usuário
     * @param nomeAntigo String - o nome antigo da agenda
     * @param nomeAtual String - o nome atual(novo) da agenda
     * @throws AgendaInvalidaException Se o usuário já tiver uma agenda com o mesmo nome
     * @throws IOException Se tiver problema de conexão com o arquivo 
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean update(String emailUsuario, String nomeAntigo, String nomeAtual) throws ClassNotFoundException, IOException,AgendaInvalidaException {
        List<Agenda> agendas = list();
        List<Agenda> agendasUsuario = list(emailUsuario);
        if(nomeAntigo.equals(nomeAtual)) throw new AgendaInvalidaException("A agenda já está com esse nome");
        if(nomeAtual==null) throw new AgendaInvalidaException("O nome da agenda não pode ser vazio");
        for(int k=0; k<agendasUsuario.size(); k++){
            if(agendasUsuario.get(k).getNome().equals(nomeAtual)){
                throw new AgendaInvalidaException("Você já tem uma agenda com esse nome");
            }
        }    
        for(int i=0; i<agendas.size(); i++){
            if(agendas.get(i).getEmailUsuario().equals(emailUsuario) && agendas.get(i).getNome().equals(nomeAntigo)){
                agendas.get(i).setNome(nomeAtual);
                atualizarArquivo(agendas);
                return true;
            }        
        }
              return false;
        }
    
        
        
        
    /**
     * Método que lista todas as agendas salvas no arquivo
     * @return A lista de agendas
     * @throws IOException Se houver problema na conexão com o arquivo 
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Agenda> list() throws ClassNotFoundException, IOException {
        List<Agenda> agenda = new ArrayList<>();
        
        if(arquivo.length()>0){
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(arquivo));
            return (List<Agenda>) input.readObject();
        }else{
            return new ArrayList<Agenda>();
        }
    }
    
    /**
     * Método que atualiza o arquivo cada vez que houver uma modificação na lista de agendas
     * @param agendas Lista de agendas atualizada
     * @throws IOException Se houver problema na conexão com o arquivo 
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    private void atualizarArquivo(List<Agenda> agendas) throws FileNotFoundException, IOException{
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(arquivo));
        output.writeObject(agendas);
        output.close();
    }

    /**
     * Método que lista todos os nomes de agendas de um usuário específico
     * @param emailUsuario String - o email do usuário
     * @return A lista de String contendo os nomes de todas as agendas do usuário
     * @throws IOException Se houver problema na conexão com o arquivo 
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @throws AgendasVaziasException Se o usuário não tiver nenhuma agenda
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<String> listNomesAgendas(String emailUsuario) throws  IOException, ClassNotFoundException{
        List<Agenda> agendas = list(emailUsuario);
        if(agendas.isEmpty()) throw new AgendasVaziasException("Você ainda não tem agendas");
        List<String> nomesAgendas = new ArrayList<>();
        
        for(int i=0; i<agendas.size(); i++){
            nomesAgendas.add(agendas.get(i).getNome());
        }
            
        return nomesAgendas;
    }
    
}
    

