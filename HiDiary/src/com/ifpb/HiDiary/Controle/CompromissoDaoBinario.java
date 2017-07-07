
package com.ifpb.HiDiary.Controle;
import Excecoes.CompromissosException;
import com.ifpb.HiDiary.Modelo.Compromisso;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

    /**
     * Essa classe tem como finalidade controlar todo o DAO de compromissos no arquivo
     * @author Lyndemberg
     * @version 1.0
     */
public class CompromissoDaoBinario implements CompromissoDao {
    
    private File arquivo;
    
    /**
     * Método construtor da classe que inicializa o arquivo caso ele não exista ainda
     * @author Lyndemberg
     * @version 1.0
     */
    public CompromissoDaoBinario(){
        arquivo = new File("compromissos.bin");
        
        if(!arquivo.exists()){
            try{
                arquivo.createNewFile();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Falha na conexão com o arquivo","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    /**
     * Método que insere um novo compromisso no arquivo
     * @param novo Compromisso - o novo compromisso a ser inserido
     * @return boolean - true se conseguiu inserir, ou false se não conseguiu
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean create(Compromisso novo) throws ClassNotFoundException, IOException {
        List<Compromisso> compromissos = list();
        
        if(!compromissos.isEmpty()){
            for(int i=0; i<compromissos.size(); i++){
                if(compromissos.get(i).getEmailUsuario().equals(novo.getEmailUsuario())
                        && compromissos.get(i).getData().equals(novo.getData())
                        && compromissos.get(i).getHora().equals(novo.getHora())){
                    return false;
                }
            }
            
       
        }
        compromissos.add(novo);
        atualizarArquivo(compromissos);
        return true;
    }

    /**
     * Método que insere um novo compromisso no arquivo
     * @param emailUsuario String - representa o email do usuário que é dono do compromisso
     * @param agenda String - representa o nome da agenda que o compromisso está inserido
     * @param hora LocalTime - representa a hora do compromisso
     * @param data LocalDate - representa a data do compromisso
     * @return Retorna o compromisso caso seja encontrado, ou null se não for encontrado
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public Compromisso read(String emailUsuario, String agenda, LocalDate data, LocalTime hora) throws ClassNotFoundException, IOException {
        List<Compromisso> compromissos = list();
        
        for(int i=0; i<compromissos.size(); i++){
            if(compromissos.get(i).getEmailUsuario().equals(emailUsuario) && 
                    compromissos.get(i).getNomeAgenda().equals(agenda) &&
                        compromissos.get(i).getData().equals(data) &&
                            compromissos.get(i).getHora().equals(hora)){
               return compromissos.get(i);
            }
        }
        return null;
    }

    /**
     * Método que lista todos os compromissos de um usuário
     * @param emailUsuario String - representa o email do usuário que é dono dos compromissos
     * @return Retorna a lista de compromissos do usuário
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> list(String emailUsuario) throws  ClassNotFoundException, IOException{
        List<Compromisso> compromissos = list();
        List<Compromisso> compromissosUsuario = new ArrayList<>();
        if(compromissos.size()>0){
            for(int i=0; i<compromissos.size();i++){
                if(compromissos.get(i).getEmailUsuario().equals(emailUsuario)){
                    compromissosUsuario.add(compromissos.get(i));
                }
            }  
        }
        if(!compromissosUsuario.isEmpty()){
            return compromissosUsuario;
        }else{
            return new ArrayList<Compromisso>();
        }
    }
    
    
    /**
     * Método que lista todos os compromissos salvos no arquivo
     * @return Retorna a lista de compromissos
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> list() throws ClassNotFoundException, IOException {
        List<Compromisso> compromissos = null;
        
        if(arquivo.length()>0){
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(arquivo));
            return (List<Compromisso>) input.readObject();
        }else{
            return new ArrayList<Compromisso>();
        }
    }

    /**
     * Método que deleta um compromisso do arquivo
     * @param comp Compromisso - o compromisso a ser deletado
     * @return boolean - true se conseguiu deletar, ou false se não conseguiu
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean delete(Compromisso comp) throws ClassNotFoundException, IOException {
        List<Compromisso> compromissos = list();
        if(!compromissos.isEmpty()){
            for(int i=0; i<compromissos.size(); i++){
            if(compromissos.get(i).equals(comp)){
                compromissos.remove(compromissos.get(i));
                atualizarArquivo(compromissos);
                return true;
            }
        }
        }
        return false;
    }

    /**
     * Método que lista os compromissos que um usuário tem para os próximos 30 dias (a partir do dia atual)
     * @param emailUsuario String - o email do usuário dono dos compromissos
     * @return A lista de compromissos que estão dentro do intervalo de 30 dias
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @throws CompromissosException Se não tiver nenhum compromisso para os próximos 30 dias
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compromissos30dias(String emailUsuario) throws ClassNotFoundException, IOException{
        List<Compromisso> compUsuario = list(emailUsuario);
        List<Compromisso> comp30dias = new ArrayList<>();
        if(!compUsuario.isEmpty()){
            for(int i=0; i<compUsuario.size(); i++){
            if(compUsuario.get(i).getData().compareTo(LocalDate.now())>=0
                    && compUsuario.get(i).getData().compareTo(LocalDate.now().plusDays(30))<=0){
                comp30dias.add(compUsuario.get(i));
            }
            }   
        }
        if(!comp30dias.isEmpty()){
            return comp30dias;
        }else{
            throw new CompromissosException("Sem compromissos para os próximos 30 dias");
        } 
    }

    /**
     * Método que lista os compromissos que um usuário tem dentro de uma agenda para os próximos 30 dias(a partir do dia atual)
     * @param emailUsuario String - o email do usuário dono dos compromissos
     * @param nomeAgenda String - o nome da agenda do usuário
     * @return A lista de compromissos que estão dentro do intervalo de 30 dias na agenda
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @throws CompromissosException Se não tiver nenhum compromisso para os próximos 30 dias na agenda
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compromissos30dias(String emailUsuario, String nomeAgenda) throws  ClassNotFoundException, IOException, CompromissosException{
        List<Compromisso> compAgenda = compAgenda(emailUsuario, nomeAgenda);
        List<Compromisso> compAgenda30dias = new ArrayList<>();
       if(!compAgenda.isEmpty()){
            for(int i=0; i<compAgenda.size(); i++){
            if(compAgenda.get(i).getData().compareTo(LocalDate.now())>=0
                    && compAgenda.get(i).getData().compareTo(LocalDate.now().plusDays(30))<=0){
                compAgenda30dias.add(compAgenda.get(i));
            }
        }
       }
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
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean deletaCompAgenda(String emailUsuario, String nomeAgenda) throws ClassNotFoundException, IOException {
        List<Compromisso> compromissos = list();
        if(!compromissos.isEmpty()){
            for(int i=0; i<compromissos.size(); i++){
            if(compromissos.get(i).getEmailUsuario().equals(emailUsuario) && compromissos.get(i).getNomeAgenda().equals(nomeAgenda)){
                compromissos.remove(compromissos.get(i));
                atualizarArquivo(compromissos);
                return true;
            }
            }
        }
        return false;
    }

    /**
     * Método que lista os compromissos que um usuário tem dentro de um intervalo de datas
     * @param emailUsuario String - o email do usuário
     * @param inicio LocalDate - a data de início do intervalo
     * @param fim LocalDate - a data de fim do intervalo
     * @return A lista de compromissos que estão dentro do intervalo de datas
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @throws CompromissosException Se não tiver nenhum compromisso dentro do intervalo de datas 
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compromissosIntervalo(String emailUsuario, LocalDate inicio, LocalDate fim) throws ClassNotFoundException, IOException, CompromissosException{
        if(inicio.isAfter(fim)) throw new DateTimeException("Intervalo inválido. A data de fim é menor que de início");
        List<Compromisso> compromissosUsuario = list(emailUsuario);
        List<Compromisso> compromissosIntervalo = new ArrayList<>();
        if(!compromissosUsuario.isEmpty()){
            for(int i=0; i<compromissosUsuario.size(); i++){
                if(compromissosUsuario.get(i).getData().compareTo(inicio)>=0
                            && compromissosUsuario.get(i).getData().compareTo(fim)<=0){
                        compromissosIntervalo.add(compromissosUsuario.get(i));
                }
            }
        }
        if(!compromissosIntervalo.isEmpty()){
            return compromissosIntervalo;
        }else{
            throw new CompromissosException("Você não tem compromissos nesse intervalo");
        }
    }

    /**
     * Método que lista os compromissos de uma agenda de um usuário
     * @param emailUsuario String - o email do usuário
     * @param nomeAgenda String - o nome da agenda do usuário
     * @return A lista de compromissos que estão dentro da agenda do usuário
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public List<Compromisso> compAgenda(String emailUsuario, String nomeAgenda) throws ClassNotFoundException, IOException{
        List<Compromisso> compromissosUsuario = list(emailUsuario);
        List<Compromisso> compAgenda = new ArrayList<>();
        if(!compromissosUsuario.isEmpty()){
            for(int i=0; i<compromissosUsuario.size(); i++){
            if(compromissosUsuario.get(i).getNomeAgenda().equals(nomeAgenda)){
                compAgenda.add(compromissosUsuario.get(i));
            }
            }
        }
        if(!compAgenda.isEmpty()){
            return compAgenda;
        }else{
            return new ArrayList<Compromisso>();
        }
    }
    
    /**
     * Método que atualiza o arquivo cada vez que houver uma modificação na lista de compromissos
     * @param compromissos Lista de compromissos atualizada
     * @throws IOException Se houver problema na conexão com o arquivo 
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    private void atualizarArquivo(List<Compromisso> compromissos) throws FileNotFoundException, IOException{
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(arquivo));
        output.writeObject(compromissos);
        output.close();
    }

    /**
     * Método que atualiza o nome da agenda de compromissos que antes estavam em outra agenda
     * @param emailUsuario String - representa o email do usuário dono dos compromissos
     * @param nomeAntigo String - representa o nome da agenda que antes estava nos compromissos
     * @param nomeAtual String - representa o novo nome da agenda a ser colocado nos compromissos
     * @return boolean - true se conseguiu atualizar o nome, ou false se não conseguiu
     * @throws IOException Se tiver problemas na conexão com o arquivo
     * @throws ClassNotFoundException Se houver algum problema durante a conexão com o arquivo
     * @author Lyndemberg
     * @version 1.0
     */
    @Override
    public boolean updateAgendaComp(String emailUsuario, String nomeAntigo, String nomeAtual) throws ClassNotFoundException, IOException {
        List<Compromisso> compromissos = list();
        if(!compromissos.isEmpty()){
            for(int i=0; i<compromissos.size(); i++){
                if(compromissos.get(i).getEmailUsuario().equals(emailUsuario) && 
                        compromissos.get(i).getNomeAgenda().equals(nomeAntigo)){
                            compromissos.get(i).setNomeAgenda(nomeAtual);
                    atualizarArquivo(compromissos);
                    return true;
                }
            }
        }
        return false;
    }
    
    
}
