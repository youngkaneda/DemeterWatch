/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.gpes.jdt.samples;

/**
 *
 * @author juan
 */
@FunctionalInterface    
public interface I {

    public void semRetorno();

    public default void methodDefault(String nome) {
        System.out.println("oi" + nome);
    }
}
