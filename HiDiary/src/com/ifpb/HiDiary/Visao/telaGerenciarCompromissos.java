
package com.ifpb.HiDiary.Visao;

import Excecoes.CompromissosException;
import com.ifpb.HiDiary.Controle.CompromissoDao;
import com.ifpb.HiDiary.Controle.CompromissoDaoBanco;
import com.ifpb.HiDiary.Controle.CompromissoDaoBinario;
import com.ifpb.HiDiary.Controle.UsuarioDao;
import com.ifpb.HiDiary.Controle.UsuarioDaoBinario;
import com.ifpb.HiDiary.Modelo.Agenda;
import com.ifpb.HiDiary.Modelo.Compromisso;
import com.ifpb.HiDiary.Modelo.Usuario;
import static com.ifpb.HiDiary.Visao.PaginaInicial.jTable30days;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class telaGerenciarCompromissos extends javax.swing.JFrame {
    private static List<Compromisso> listaIntervalo;
    private static  CompromissoDao daoComp;
    public telaGerenciarCompromissos() {
        daoComp = new CompromissoDaoBanco();
        initComponents();
        ImageIcon icon = new ImageIcon("imagens/icone.png");
        setIconImage(icon.getImage());
        dataInicio.setDate(java.sql.Date.valueOf(LocalDate.now()));
        dataFim.setDate(java.sql.Date.valueOf(LocalDate.now()));
        inicializaJTable();
    }

    public static void inicializaJTable(){
        try{
            labelErro.setText(null);
            buttonEditar.setVisible(false);
            buttonExcluir.setVisible(false);
            Date inicioDate = dataInicio.getDate();
            LocalDate inicio = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(inicioDate));
            Date fimDate = dataFim.getDate();
            LocalDate fim = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(fimDate));
            listaIntervalo = daoComp.compromissosIntervalo(PaginaInicial.usuarioLogado.getEmail(), inicio, fim);
            
                String[] titulos = {"Data","Hora","Descrição","Local"};
                String[][] matriz = new String[listaIntervalo.size()][4];
                for(int i=0; i<listaIntervalo.size(); i++){
                    Compromisso comp = listaIntervalo.get(i);

                    DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    matriz[i][0] = dtfData.format(comp.getData());

                    DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");
                    matriz[i][1] = dtfHora.format(comp.getHora());

                    matriz[i][2] = comp.getDescricao();
                    matriz[i][3] = comp.getLocal();  
                }
                DefaultTableModel modelo = new DefaultTableModel(matriz, titulos);
                jTableCompromissos.setModel(modelo);
                ListSelectionModel modoSelecao = jTableCompromissos.getSelectionModel();
                modoSelecao.setSelectionMode(modoSelecao.SINGLE_SELECTION);
                jTableCompromissos.setSelectionModel(modoSelecao);
                buttonEditar.setVisible(true);
                buttonExcluir.setVisible(true);
                
            
             
        }catch(ClassNotFoundException | IOException | SQLException ex ){
            JOptionPane.showMessageDialog(null, "Falha na conexão");
        }catch(NullPointerException ex){
            buttonEditar.setVisible(false);
            buttonExcluir.setVisible(false);
            labelErro.setText("Insira datas válidas no intervalo");
            String[] titulos = {"Data","Hora","Descrição","Local"};
            String[][] matriz = new String[0][4];
            DefaultTableModel modelo = new DefaultTableModel(matriz, titulos);
            jTableCompromissos.setModel(modelo);
        }catch(DateTimeException ex){
            buttonEditar.setVisible(false);
            buttonExcluir.setVisible(false);
            labelErro.setText(ex.getMessage());
        }catch(CompromissosException ex){
            buttonEditar.setVisible(false);
            buttonExcluir.setVisible(false);
            String[] titulos = {"Data","Hora","Descrição","Local"};
            String[][] matriz = new String[0][4];
            labelErro.setText(ex.getMessage());
            DefaultTableModel modelo = new DefaultTableModel(matriz, titulos);
            jTableCompromissos.setModel(modelo);
        }
        
                
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataInicio = new com.toedter.calendar.JDateChooser();
        dataFim = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCompromissos = new javax.swing.JTable();
        buttonAtualizar = new javax.swing.JButton();
        buttonEditar = new javax.swing.JButton();
        buttonExcluir = new javax.swing.JButton();
        labelErro = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HiDiary - Gerenciar Compromissos");

        jTableCompromissos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTableCompromissos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Hora", "Descrição", "Local"
            }
        ));
        jScrollPane1.setViewportView(jTableCompromissos);

        buttonAtualizar.setText("ATUALIZAR");
        buttonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAtualizarActionPerformed(evt);
            }
        });

        buttonEditar.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        buttonEditar.setText("EDITAR");
        buttonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarActionPerformed(evt);
            }
        });

        buttonExcluir.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        buttonExcluir.setText("EXCLUIR");
        buttonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirActionPerformed(evt);
            }
        });

        labelErro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        labelErro.setForeground(new java.awt.Color(255, 0, 0));
        labelErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("INÍCIO");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("FIM");

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\lynde\\Desktop\\HiDiary\\imagens\\GERENCIAR.png")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel4.setText("COMPROMISSOS");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("DIGITE UM INTERVALO DE DATAS ABAIXO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(buttonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(146, 146, 146))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(buttonAtualizar)
                                .addGap(221, 221, 221))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 4, Short.MAX_VALUE))
                            .addComponent(labelErro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonEditar, buttonExcluir});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dataFim, dataInicio});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4)))
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dataFim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAtualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelErro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonEditar, buttonExcluir});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dataFim, dataInicio});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAtualizarActionPerformed
        inicializaJTable();
    }//GEN-LAST:event_buttonAtualizarActionPerformed

    private void buttonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarActionPerformed

        if(jTableCompromissos.getSelectedRow()!=-1){
           
            try {
                telaEditaCompromisso tela = new telaEditaCompromisso(listaIntervalo.get(jTableCompromissos.getSelectedRow()));
                tela.setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Falha na conexão");
            }
            
        }else{
            JOptionPane.showMessageDialog(null,"Nenhum compromisso selecionado");
        }
        
    }//GEN-LAST:event_buttonEditarActionPerformed

    private void buttonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirActionPerformed
        if(jTableCompromissos.getSelectedRow()!=-1){
            int janela = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Remover compromisso",JOptionPane.YES_NO_OPTION);    
                if(janela==JOptionPane.OK_OPTION){
                    try {
                        daoComp.delete(listaIntervalo.get(jTableCompromissos.getSelectedRow()));
                        ImageIcon icon = new ImageIcon("imagens/confirm.png");
                        JOptionPane.showMessageDialog(null, "Compromisso removido com sucesso!", "Confirmação", JOptionPane.OK_OPTION,icon);
                        inicializaJTable();
                        PaginaInicial.inicializarTabela();
                    } catch (ClassNotFoundException | IOException | SQLException ex){
                        JOptionPane.showMessageDialog(null, "Falha na conexão");
                    }
                
                }
        }else{
                    JOptionPane.showMessageDialog(null,"Nenhum compromisso selecionado");
        }
    }//GEN-LAST:event_buttonExcluirActionPerformed

    
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
            java.util.logging.Logger.getLogger(telaGerenciarCompromissos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaGerenciarCompromissos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaGerenciarCompromissos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaGerenciarCompromissos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telaGerenciarCompromissos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAtualizar;
    private static javax.swing.JButton buttonEditar;
    private static javax.swing.JButton buttonExcluir;
    private static com.toedter.calendar.JDateChooser dataFim;
    private static com.toedter.calendar.JDateChooser dataInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTableCompromissos;
    private static javax.swing.JLabel labelErro;
    // End of variables declaration//GEN-END:variables
}
