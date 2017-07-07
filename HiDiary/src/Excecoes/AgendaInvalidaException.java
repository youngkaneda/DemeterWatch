
package Excecoes;

public class AgendaInvalidaException extends RuntimeException{
    public AgendaInvalidaException(String mensagem){
        super(mensagem);
    }
}
