
package com.ifpb.HiDiary.Visao;

import Excecoes.AgendasVaziasException;
import Excecoes.CompromissosException;
import com.ifpb.HiDiary.Controle.AgendaDao;
import com.ifpb.HiDiary.Controle.AgendaDaoBanco;
import com.ifpb.HiDiary.Controle.AgendaDaoBinario;
import com.ifpb.HiDiary.Controle.CompromissoDao;
import com.ifpb.HiDiary.Controle.CompromissoDaoBanco;
import com.ifpb.HiDiary.Controle.CompromissoDaoBinario;
import com.ifpb.HiDiary.Controle.UsuarioDao;
import com.ifpb.HiDiary.Controle.UsuarioDaoBinario;
import com.ifpb.HiDiary.Modelo.Agenda;
import java.util.ArrayList;
import java.util.List;
import com.ifpb.HiDiary.Modelo.Usuario;
import com.ifpb.HiDiary.Modelo.Compromisso;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
public class PaginaInicial extends javax.swing.JFrame {
    public static Usuario usuarioLogado;
    private static CompromissoDao daoComp;
    private static AgendaDao daoAgenda;
    public PaginaInicial(Usuario u){
        usuarioLogado = u;
        daoComp = new CompromissoDaoBanco();
        daoAgenda = new AgendaDaoBanco();
        
        initComponents();
        ImageIcon icon = new ImageIcon("imagens/icone.png");
        setIconImage(icon.getImage());
        inicializarComboBox();
        inicializarTabela();
        
        
        
    }
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonNovoCompromisso = new javax.swing.JButton();
        buttonEditarAgendas = new javax.swing.JButton();
        buttonGerenciar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable30days = new javax.swing.JTable();
        cbAgenda30days = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        buttonAtualizar = new javax.swing.JButton();
        labelErro = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HiDiary - Página Inicial");
        setBackground(new java.awt.Color(255, 255, 255));

        buttonNovoCompromisso.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonNovoCompromisso.setText("<html>\n<center>Novo\n<br/>Compromisso</center>\n\n</html>\n"); // NOI18N
        buttonNovoCompromisso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonNovoCompromisso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNovoCompromissoActionPerformed(evt);
            }
        });

        buttonEditarAgendas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonEditarAgendas.setText("Editar Agendas");
        buttonEditarAgendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarAgendasActionPerformed(evt);
            }
        });

        buttonGerenciar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonGerenciar.setText("<html>\n<center>Gerenciar<br/> compromissos\n</center>\n</html>\n");
        buttonGerenciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGerenciarActionPerformed(evt);
            }
        });

        jTable30days.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTable30days.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Hora", "Descrição", "Local"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable30days.setAutoscrolls(false);
        jTable30days.setRowHeight(25);
        jTable30days.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable30days);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));

        buttonAtualizar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        buttonAtualizar.setText("ATUALIZAR");
        buttonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAtualizarActionPerformed(evt);
            }
        });

        labelErro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelErro.setForeground(new java.awt.Color(255, 0, 0));

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\lynde\\Desktop\\HiDiary\\imagens\\inicial.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonNovoCompromisso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonEditarAgendas)
                            .addComponent(buttonGerenciar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelErro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(cbAgenda30days, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonAtualizar)
                        .addGap(155, 155, 155)))
                .addGap(18, 18, 18)
                .addComponent(jLabel1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(131, 131, 131))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonEditarAgendas, buttonGerenciar, buttonNovoCompromisso});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAgenda30days, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAtualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(buttonNovoCompromisso)
                                    .addGap(44, 44, 44)
                                    .addComponent(buttonEditarAgendas))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(buttonGerenciar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelErro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {buttonEditarAgendas, buttonGerenciar, buttonNovoCompromisso});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonNovoCompromissoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNovoCompromissoActionPerformed
        try {
            List<Agenda> agendas = daoAgenda.list(PaginaInicial.usuarioLogado.getEmail());
            telaCadastraCompromisso compNovo = new telaCadastraCompromisso();
            compNovo.setVisible(true);
           
        } catch (SQLException | ClassNotFoundException | IOException ex ){
            JOptionPane.showConfirmDialog(null, "Falha na conexão");
        }catch(AgendasVaziasException ex ){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
           
      

    }//GEN-LAST:event_buttonNovoCompromissoActionPerformed

    private void buttonEditarAgendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarAgendasActionPerformed
        telaEditarAgendas editarAgendas = new telaEditarAgendas();
        editarAgendas.setVisible(true);
    }//GEN-LAST:event_buttonEditarAgendasActionPerformed

    private void buttonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAtualizarActionPerformed
        inicializarTabela();
    }//GEN-LAST:event_buttonAtualizarActionPerformed

    private void buttonGerenciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGerenciarActionPerformed
        telaGerenciarCompromissos tela = new telaGerenciarCompromissos();
        tela.setVisible(true);
    }//GEN-LAST:event_buttonGerenciarActionPerformed

    
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
            java.util.logging.Logger.getLogger(PaginaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaInicial(usuarioLogado).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton buttonAtualizar;
    private javax.swing.JButton buttonEditarAgendas;
    private javax.swing.JButton buttonGerenciar;
    private javax.swing.JButton buttonNovoCompromisso;
    static javax.swing.JComboBox<String> cbAgenda30days;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    static javax.swing.JTable jTable30days;
    private static javax.swing.JLabel labelErro;
    // End of variables declaration//GEN-END:variables

    public static void inicializarComboBox(){
        
        
        try {
            labelErro.setText(null);
            cbAgenda30days.removeAllItems();
            cbAgenda30days.addItem("Todas");
            List<String> agendas = daoAgenda.listNomesAgendas(PaginaInicial.usuarioLogado.getEmail());
            
            for(int i=0; i<agendas.size(); i++){
                cbAgenda30days.addItem(agendas.get(i));
            }
        }catch (SQLException | IOException | ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Falha na conexão");
        }catch(AgendasVaziasException ex){
            labelErro.setText(ex.getMessage());
        }
        
        
      
    }

    public static void inicializarTabela(){
                
        try{
            
            List<Compromisso> compromissos30days;
            
            if(cbAgenda30days.getSelectedItem().toString().equals("Todas")){
                compromissos30days = daoComp.compromissos30dias(usuarioLogado.getEmail());
            }else{
                compromissos30days = daoComp.compromissos30dias(usuarioLogado.getEmail(), cbAgenda30days.getSelectedItem().toString());
            }
            
            if(!compromissos30days.isEmpty()){
               labelErro.setText(null);
               jTable30days.setEnabled(true);
                String[] titulos = {"Data","Hora","Descrição","Local"};
                String[][] matriz = new String[compromissos30days.size()][4];

                for(int i=0; i<compromissos30days.size(); i++){
                    Compromisso comp = compromissos30days.get(i);

                    DateTimeFormatter dtfData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    matriz[i][0] = dtfData.format(comp.getData());

                    DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");
                    matriz[i][1] = dtfHora.format(comp.getHora());
                    //matriz[i][1] = comp.getHora().toString();

                    //matriz[i][1] = ""+comp.getHora().getHour()+""+":"+comp.getHora().getMinute()+"";
                    matriz[i][2] = comp.getDescricao();
                    matriz[i][3] = comp.getLocal();  
                }
                ListSelectionModel modoSelecao = jTable30days.getSelectionModel();
                modoSelecao.setSelectionMode(modoSelecao.SINGLE_SELECTION);
                jTable30days.setSelectionModel(modoSelecao);
                DefaultTableModel modelo = new DefaultTableModel(matriz, titulos);
                jTable30days.setModel(modelo); 
            }
                
            
            }catch(ClassNotFoundException | IOException | SQLException ex){
                JOptionPane.showMessageDialog(null, "Falha na conexão");
            }catch(CompromissosException ex){
                labelErro.setText(ex.getMessage());
                String[] titulos = {"Data","Hora","Descrição","Local"};
                String[][] matriz = new String[0][4];
                 DefaultTableModel modelo = new DefaultTableModel(matriz, titulos);
                jTable30days.setModel(modelo); 
                jTable30days.setEnabled(false);
            }
}
            
    
}
