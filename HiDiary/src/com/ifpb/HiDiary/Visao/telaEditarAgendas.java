
package com.ifpb.HiDiary.Visao;

import Excecoes.AgendaInvalidaException;
import Excecoes.AgendasVaziasException;
import com.ifpb.HiDiary.Controle.AgendaDao;
import com.ifpb.HiDiary.Controle.AgendaDaoBanco;
import com.ifpb.HiDiary.Controle.AgendaDaoBinario;
import com.ifpb.HiDiary.Controle.CompromissoDao;
import com.ifpb.HiDiary.Controle.CompromissoDaoBanco;
import com.ifpb.HiDiary.Controle.CompromissoDaoBinario;
import com.ifpb.HiDiary.Controle.UsuarioDao;
import com.ifpb.HiDiary.Controle.UsuarioDaoBinario;
import com.ifpb.HiDiary.Modelo.Agenda;
import com.ifpb.HiDiary.Modelo.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class telaEditarAgendas extends javax.swing.JFrame {
    private static AgendaDao daoAgenda;
    private CompromissoDao daoComp;
   
    public telaEditarAgendas() {
        daoAgenda = new AgendaDaoBanco();
        daoComp = new CompromissoDaoBanco();
        initComponents();
        ImageIcon icon = new ImageIcon("imagens/icone.png");
        setIconImage(icon.getImage());
        inicializaListaAgendas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonCriarAgenda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListaAgendas = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        buttonEditarAgenda = new javax.swing.JButton();
        buttonExcluir = new javax.swing.JButton();
        labelErro = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HiDiary - Editar Agendas");

        buttonCriarAgenda.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        buttonCriarAgenda.setText("CRIAR NOVA");
        buttonCriarAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCriarAgendaActionPerformed(evt);
            }
        });

        jListaAgendas.setBackground(new java.awt.Color(226, 226, 226));
        jListaAgendas.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jListaAgendas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListaAgendas.setSelectionBackground(new java.awt.Color(0, 0, 255));
        jListaAgendas.setSelectionForeground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(jListaAgendas);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        jLabel1.setText("AGENDAS");

        buttonEditarAgenda.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        buttonEditarAgenda.setText("EDITAR");
        buttonEditarAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarAgendaActionPerformed(evt);
            }
        });

        buttonExcluir.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        buttonExcluir.setText("EXCLUIR");
        buttonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\lynde\\Desktop\\HiDiary\\imagens\\agendas2.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelErro, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonCriarAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonEditarAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelErro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonCriarAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonEditarAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonExcluir)
                        .addGap(100, 100, 100))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonCriarAgenda, buttonEditarAgenda, buttonExcluir});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCriarAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCriarAgendaActionPerformed
        
        try{   
            String input = JOptionPane.showInputDialog(null, "Digite o nome da agenda").toUpperCase();
            
            daoAgenda.create(new Agenda(PaginaInicial.usuarioLogado.getEmail(),input));
            ImageIcon icon = new ImageIcon("imagens/confirm.png");
            JOptionPane.showMessageDialog(null, "Agenda criada com sucesso!", "Confirmação", JOptionPane.OK_OPTION,icon);
            inicializaListaAgendas();
            PaginaInicial.inicializarComboBox();
            PaginaInicial.inicializarTabela();
        
        }catch(ClassNotFoundException | IOException ex){
            JOptionPane.showMessageDialog(null, "Falha na conexão", "Erro", JOptionPane.ERROR_MESSAGE);
        }catch(AgendaInvalidaException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Já existe uma agenda sua com esse nome");
        }catch(NullPointerException ex){
                
        }

    }//GEN-LAST:event_buttonCriarAgendaActionPerformed

    private void buttonEditarAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarAgendaActionPerformed
        
        if(jListaAgendas.getSelectedValue()!=null){
                
            try {
                String input = JOptionPane.showInputDialog(null, "Digite o novo nome").toUpperCase();
                
                daoAgenda.update(PaginaInicial.usuarioLogado.getEmail(),jListaAgendas.getSelectedValue(), input);
                daoComp.updateAgendaComp(PaginaInicial.usuarioLogado.getEmail(), jListaAgendas.getSelectedValue(), input);
                ImageIcon icon = new ImageIcon("imagens/confirm.png");
                JOptionPane.showMessageDialog(null,"Agenda atualizada","Confirmação", JOptionPane.OK_OPTION, icon);
                inicializaListaAgendas();
                PaginaInicial.inicializarComboBox();
                PaginaInicial.inicializarTabela();                       
            }catch (ClassNotFoundException | IOException ex) {
                JOptionPane.showMessageDialog(null, "Falha na conexão");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Já existe uma agenda sua com esse nome");
            }catch(AgendaInvalidaException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }catch(NullPointerException ex){
                
            }
        }else{
            JOptionPane.showMessageDialog(null,"Selecione uma agenda para editar");
        }     
    }//GEN-LAST:event_buttonEditarAgendaActionPerformed

    private void buttonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirActionPerformed
        if(jListaAgendas.getSelectedValue() != null){
            int opcao = JOptionPane.showConfirmDialog(null, "Confirmar?");
            if(opcao==0){
                try {
                    daoComp.deletaCompAgenda(PaginaInicial.usuarioLogado.getEmail(), jListaAgendas.getSelectedValue());
                    daoAgenda.delete(PaginaInicial.usuarioLogado.getEmail(), jListaAgendas.getSelectedValue());
                        ImageIcon icon = new ImageIcon("imagens/confirm.png");
                        JOptionPane.showMessageDialog(null,"Agenda removida","Confirmação", JOptionPane.OK_OPTION, icon);
                        inicializaListaAgendas();
                        PaginaInicial.inicializarComboBox();
                        PaginaInicial.inicializarTabela();

                        
                } catch (SQLException | ClassNotFoundException | IOException ex){
                    JOptionPane.showMessageDialog(null, "Falha na conexão");
                }
 
            }   
        }else{
            JOptionPane.showMessageDialog(null,"Selecione uma agenda para remover");
        }
    }//GEN-LAST:event_buttonExcluirActionPerformed

    public static void inicializaListaAgendas(){
        
        try{
            String[] vetorVazio = new String[0];
            jListaAgendas.setListData(vetorVazio);
            String[] vetor = new String[daoAgenda.listNomesAgendas(PaginaInicial.usuarioLogado.getEmail()).size()];
            for(int i=0; i<vetor.length; i++){
                vetor[i]=daoAgenda.listNomesAgendas(PaginaInicial.usuarioLogado.getEmail()).get(i);
            }
            labelErro.setText(null);
            jListaAgendas.setListData(vetor);
        }catch(ClassNotFoundException | IOException | SQLException ex){
            labelErro.setText("Falha na conexão");
        }catch(AgendasVaziasException ex){
            labelErro.setText(ex.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(telaEditarAgendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaEditarAgendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaEditarAgendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaEditarAgendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telaEditarAgendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCriarAgenda;
    private javax.swing.JButton buttonEditarAgenda;
    private javax.swing.JButton buttonExcluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private static javax.swing.JList<String> jListaAgendas;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JLabel labelErro;
    // End of variables declaration//GEN-END:variables
}
