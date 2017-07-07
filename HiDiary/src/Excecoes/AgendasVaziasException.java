/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excecoes;

/**
 *
 * @author Lyndemberg
 */
public class AgendasVaziasException extends RuntimeException{
    public AgendasVaziasException(String mensagem){
        super(mensagem);
    }
}
