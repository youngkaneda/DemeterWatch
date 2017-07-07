
package com.ifpb.HiDiary.Visao;

import Excecoes.PreencheCamposException;

import com.ifpb.HiDiary.Controle.UsuarioDao;
import com.ifpb.HiDiary.Controle.UsuarioDaoBanco;
import com.ifpb.HiDiary.Controle.UsuarioDaoBinario;
import com.ifpb.HiDiary.Modelo.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class TelaCadastra extends javax.swing.JFrame {
    private UsuarioDao dao;
    public TelaCadastra() {
        dao = new UsuarioDaoBanco();
        initComponents();
        ImageIcon icon = new ImageIcon("imagens/icone.png");
        setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoSexo = new javax.swing.ButtonGroup();
        imgCadastro = new javax.swing.JLabel();
        labelCadastro = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        labelNascimento = new javax.swing.JLabel();
        labelSexo = new javax.swing.JLabel();
        labelEmail = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        campoSenha = new javax.swing.JPasswordField();
        buttonCadastrar = new javax.swing.JButton();
        campoNascimento = new com.toedter.calendar.JDateChooser();
        radioMasculino = new javax.swing.JRadioButton();
        radioFeminino = new javax.swing.JRadioButton();
        campoEmail = new javax.swing.JTextField();

        grupoSexo.add(radioMasculino);
        grupoSexo.add(radioFeminino);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HiDiary - Cadastrar Usuário");

        imgCadastro.setIcon(new javax.swing.ImageIcon("C:\\Users\\lynde\\Desktop\\HiDiary\\imagens\\cadastro.png")); // NOI18N

        labelCadastro.setFont(new java.awt.Font("Arial", 1, 50)); // NOI18N
        labelCadastro.setText("CADASTRO");

        labelNome.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNome.setText("Nome");

        campoNome.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        labelNascimento.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelNascimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNascimento.setText("Nascimento");

        labelSexo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelSexo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSexo.setText("Sexo");

        labelEmail.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEmail.setText("Email");

        labelSenha.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSenha.setText("Senha");

        campoSenha.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        buttonCadastrar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        buttonCadastrar.setText("CADASTRAR");
        buttonCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCadastrarActionPerformed(evt);
            }
        });

        campoNascimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        campoNascimento.setPreferredSize(new java.awt.Dimension(87, 50));

        radioMasculino.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        radioMasculino.setSelected(true);
        radioMasculino.setText("Masculino");
        radioMasculino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMasculinoActionPerformed(evt);
            }
        });

        radioFeminino.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        radioFeminino.setText("Feminino");
        radioFeminino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFemininoActionPerformed(evt);
            }
        });

        campoEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelNascimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(campoNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelNome)
                            .addGap(10, 10, 10)
                            .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelEmail)
                                    .addComponent(labelSenha, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoSenha)
                                    .addComponent(campoEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelSexo)
                                .addGap(18, 18, 18)
                                .addComponent(radioMasculino, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(radioFeminino))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imgCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelCadastro)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labelEmail, labelNascimento, labelNome, labelSenha, labelSexo});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(labelCadastro)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imgCadastro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoNome, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(labelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(labelNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSexo)
                    .addComponent(radioMasculino)
                    .addComponent(radioFeminino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSenha))
                .addGap(29, 29, 29)
                .addComponent(buttonCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {labelEmail, labelNascimento, labelNome, labelSenha, labelSexo});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {campoNascimento, campoNome, campoSenha});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCadastrarActionPerformed
        Usuario novo = new Usuario();
        try {
         
        novo.setNome(campoNome.getText());
        Date nascimentoDate = campoNascimento.getDate();
        LocalDate nascimento = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(nascimentoDate));
        novo.setNascimento(nascimento);
        String sexo = null;
        if(radioMasculino.isSelected()){
            sexo = "M";
        }else if(radioFeminino.isSelected()){
            sexo = "F";
        }
        novo.setSexo(sexo);
        novo.setEmail(campoEmail.getText());
        novo.setSenha(new String(campoSenha.getPassword()));

            if(dao.create(novo)){
                ImageIcon icon = new ImageIcon("imagens/confirm.png");
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "Confirmação", JOptionPane.OK_OPTION,icon);
                //emailConfirmacao(novo.getEmail());
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Já existe um usuário cadastrado com esse email", "Erro ao cadastrar",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha na conexão", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Já existe um usuário com esse email", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
        }catch(PreencheCamposException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Data Inválida");
        }catch(DateTimeException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
 
    }//GEN-LAST:event_buttonCadastrarActionPerformed

    private void emailConfirmacao(String emailUsuario){
        SimpleEmail email = new SimpleEmail();
        email.setSSLOnConnect(true);
        email.setHostName("smtp.gmail.com" );
        email.setSslSmtpPort("465");
        email.setAuthenticator( new DefaultAuthenticator( "projetohidiary@gmail.com" ,  "hidiary123" ) );
        
        try {
            email.setFrom( "projetohidiary@gmail.com", "HiDiary");            
            email.setSubject( "Cadastro no HiDiary" );
            email.setMsg( "Seu cadastro foi realizado com sucesso na agenda virtual HiDiary. Esperamos que você "
                    + "usufrua ao máximo dos nossos serviços" );
            email.addTo(emailUsuario);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
    
    
    private void radioMasculinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMasculinoActionPerformed
        
    }//GEN-LAST:event_radioMasculinoActionPerformed

    private void radioFemininoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFemininoActionPerformed
        
    }//GEN-LAST:event_radioFemininoActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCadastrar;
    private javax.swing.JTextField campoEmail;
    private com.toedter.calendar.JDateChooser campoNascimento;
    private javax.swing.JTextField campoNome;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.ButtonGroup grupoSexo;
    private javax.swing.JLabel imgCadastro;
    private javax.swing.JLabel labelCadastro;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelNascimento;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelSexo;
    private javax.swing.JRadioButton radioFeminino;
    private javax.swing.JRadioButton radioMasculino;
    // End of variables declaration//GEN-END:variables
}
