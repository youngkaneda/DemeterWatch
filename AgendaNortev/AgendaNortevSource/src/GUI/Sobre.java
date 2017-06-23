package GUI;

public class Sobre extends javax.swing.JFrame {


    public Sobre() {
        super("Sobre...");
        initComponents();
        setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("<html> <head>   \t </head> <body>  \t  \t<span style=\"font-weight: bold; font-size: 14pt;\">Agenda Utilit&aacute;ria</span><br /> \t \t \t&nbsp;&nbsp; Software desenvolvido com o intuito de ajudar nossos clientes com v&aacute;rias atividadades<br /> \t \tdurante sua rotina. Este programa possui uma agenda telefônica eletrônica onde poderá ser<br /> salva informa&ccedil;&otilde;es como nome, telefone, endereço etc de seus amigos e conhecidos. Você  <br /> também poderá desfrutar de um sistema de lembretes, onde você cadastra seus compromissos.<br /> Poderá armazenar senhas e seriais de seus usuários / softwares. Guarde telefones úteis,<br/> Supermercado, Médicos etc... E ainda poderá fazer algumas anotações!! \t \t \t<br /><br /> \t \t \t<span style=\"font-weight: bold; font-size: 14pt;\">Desenvolvimento</span><br /> \t \t \t&nbsp;&nbsp;&nbsp; Software desenvolvido por <span style=\"font-style: italic;\">Marco T&uacute;lio Jacovine Noronha</span> &lt;<span style=\"color: rgb(0, 0, 205);\">mtnoronha7@gmail.com</span>>,<br /> \t \tcomo ferramenta auxiliar da <span style=\"font-style: italic;\">Nortev Solutions</span> &lt;<span style=\"color: rgb(0, 0, 205); text-decoration: underline;\"><a href=\"http://www.nortev.net63.net/\">http://www.nortev.net63.net/</a></span>>.<br /> \t \t \t<br /> \t \t \t<span style=\"font-weight: bold; font-size: 14pt;\">Licen&ccedil;a</span><br /> \t \t \t&nbsp;&nbsp;&nbsp; Este software &eacute; freeware e está livre para&nbsp; distribuí&ccedil;ão. Este programa não cont&eacute;m nenhum tipo<br /> \tde garantia. O risco e os problemas causados pelo seu uso são por conta do usuário. Projeto Open </div> <div>   Source dispon&#237;vel em: &lt;<u><font color=\"rgb(0, 0, 205)\"><a href=\"https://github.com/mtnoronha/AgendaNortev\"><span>https://github.com/mtnoronha/AgendaNortev</span></a></font></u>&gt;.<br /> \t \t \t<br /> \t \t \t \t   </body> </html>");

        jButton1.setText("Fechar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
