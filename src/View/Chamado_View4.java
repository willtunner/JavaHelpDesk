/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static View.ListaChamadosAbertos.tbl_Chamado;
import static View.Principal3.userlogado2;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Chamado;
import model.dao.ChamadoDAO;

/**
 *
 * @author Suporte-07
 */
public class Chamado_View4 extends javax.swing.JFrame {

    String status;

    public String dataIni;
    public String dataFim;
    public String HoraFim;
    
    public String refreshTabela;

    ChamadoDAO cdao = new ChamadoDAO();

    Date dataHoraAtual = new Date();
    String horaAtual = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

    public Chamado_View4() throws ParseException {
        initComponents();
        txt_chamado.setLineWrap(true);
        txt_solucao.setLineWrap(true);
        
        //refreshTabela
        

        //ICONE FRAME
        URL caminhoIcone = getClass().getResource("/imagem/Logo128x128.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

        //Ajusta a hora para ficar igual o do computador
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-03:00"));

        txt_id_Chamado.setEditable(false);
        //txt_conexao.setEditable(false);
        //txt_titulo.setEditable(false);
        txt_chamado.setEditable(false);
        txt_solucao.setEditable(false);
        txt_tags.setEditable(false);
        btn_editar.setEnabled(true);
        btn_salvar.setEnabled(false);
        cmb_Status.setEnabled(false);
        cmb_Status.setEditable(false);
        //this.setTitle("");

        //EVENTO AO FECHAR 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                txt_id_Chamado.setEditable(false);
                //txt_conexao.setEditable(false);
                //txt_titulo.setEditable(false);
                txt_chamado.setEditable(false);
                txt_solucao.setEditable(false);
                txt_tags.setEditable(false);
                btn_editar.setEnabled(true);
                btn_salvar.setEnabled(false);
                cmb_Status.setEnabled(false);
                dispose();
                super.windowClosing(e);
            }
        });
    }

    public void SomaDatas() {
        try {
            /*TESTE PARA CALCULAR DATAS*/
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            if (dataIni != null && dataFim != null) {
                Date dataini = formato.parse(dataIni);
                Date datafim = formato.parse(dataFim);

                Calendar calendario = Calendar.getInstance();
                Calendar calendario2 = Calendar.getInstance();
                calendario.setTime(dataini);
                calendario2.setTime(datafim);

                int anoIni = calendario.get(Calendar.YEAR);
                int mesIni = calendario.get(Calendar.MONTH);
                int diaIni = calendario.get(Calendar.DAY_OF_MONTH);

                int anoFim = calendario2.get(Calendar.YEAR);
                int mesFim = calendario2.get(Calendar.MONTH);
                int diaFim = calendario2.get(Calendar.DAY_OF_MONTH);

                LocalDate dataHoraEspecifica
                        = LocalDate.of(anoIni, mesIni + 1, diaIni);

                LocalDate dataHoraAtual
                        = LocalDate.of(anoFim, mesFim + 1, diaFim);

                Period periodo = Period.between(dataHoraEspecifica, dataHoraAtual);
                //JOptionPane.showMessageDialog(null, periodo.getYears() + " Anos " + periodo.getMonths() + " Meses " + periodo.getDays() + " Dias");

                lb_DiasChamados.setText(periodo.getYears() + " Anos " + periodo.getMonths() + " Meses " + periodo.getDays() + " Dias");
                
                JOptionPane.showMessageDialog(this, refreshTabela);
                System.out.println(refreshTabela);
            }

        } catch (ParseException ex) {
            Logger.getLogger(Chamado_View4.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txt_id_Chamado = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cmb_Status = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lb_empresa = new javax.swing.JLabel();
        lb_Funcionario = new javax.swing.JLabel();
        lb_data_chamado = new javax.swing.JLabel();
        lb_hora = new javax.swing.JLabel();
        lbl_HoraChamadoFecha = new javax.swing.JLabel();
        lbl_DataChamadoFecha = new javax.swing.JLabel();
        txt_usuario_log = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_titulo = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_conexao = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lb_telefone = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_Cronometro = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_chamado = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_solucao = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        txt_tags = new javax.swing.JTextField();
        btn_editar = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lb_DiasChamados = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(" :: Tela de Chamado ::");
        setBackground(new java.awt.Color(0, 102, 102));
        setPreferredSize(new java.awt.Dimension(1200, 629));

        jPanel1.setBackground(new java.awt.Color(19, 29, 38));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("MicrogrammaDBolExt", 0, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("CHAMADO:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Data do chamado:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Hora do chamado:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Empresa:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Data chamado Fechado:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Hora chamado Fechado:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Status:");

        cmb_Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aberto", "Fechado" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Atendente:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cliente:");

        lb_empresa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_empresa.setForeground(new java.awt.Color(241, 117, 9));
        lb_empresa.setText("Empresa....");

        lb_Funcionario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_Funcionario.setForeground(new java.awt.Color(241, 117, 9));
        lb_Funcionario.setText("Cliente....");

        lb_data_chamado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_data_chamado.setForeground(new java.awt.Color(241, 117, 9));
        lb_data_chamado.setText("01/01/2020");

        lb_hora.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_hora.setForeground(new java.awt.Color(241, 117, 9));
        lb_hora.setText("00:00:00");

        lbl_HoraChamadoFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_HoraChamadoFecha.setForeground(new java.awt.Color(241, 117, 9));
        lbl_HoraChamadoFecha.setText("00:00:00");

        lbl_DataChamadoFecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_DataChamadoFecha.setForeground(new java.awt.Color(241, 117, 9));
        lbl_DataChamadoFecha.setText("01/01/2020");

        txt_usuario_log.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_usuario_log.setForeground(new java.awt.Color(241, 117, 9));
        txt_usuario_log.setText("Willtunner");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Titulo:");

        txt_titulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_titulo.setForeground(new java.awt.Color(241, 117, 9));
        txt_titulo.setText("Titulo.........");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Conexão:");

        txt_conexao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_conexao.setForeground(new java.awt.Color(241, 117, 9));
        txt_conexao.setText("123456789");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Telefone:");

        lb_telefone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lb_telefone.setForeground(new java.awt.Color(241, 117, 9));
        lb_telefone.setText("91-9821846311");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Cronometro:");

        lbl_Cronometro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_Cronometro.setForeground(new java.awt.Color(241, 117, 9));
        lbl_Cronometro.setText("00:00:00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_id_Chamado))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_conexao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_usuario_log, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel3)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lb_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel12)
                                .addGap(6, 6, 6)
                                .addComponent(lb_data_chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lb_Funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel15)
                                .addGap(6, 6, 6)
                                .addComponent(lb_hora, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel16))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_DataChamadoFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(jLabel21)
                                        .addGap(4, 4, 4)
                                        .addComponent(cmb_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_HoraChamadoFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel18)
                                        .addGap(10, 10, 10)
                                        .addComponent(lbl_Cronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_empresa)
                                    .addComponent(jLabel12)
                                    .addComponent(lb_data_chamado))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_Funcionario)
                                    .addComponent(jLabel15)
                                    .addComponent(lb_hora)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(cmb_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel16))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_DataChamadoFecha)
                                        .addGap(9, 9, 9)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_HoraChamadoFecha)
                                            .addComponent(jLabel18)
                                            .addComponent(lbl_Cronometro))))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(lb_telefone))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_titulo)
                                .addComponent(jLabel17)
                                .addComponent(txt_conexao)
                                .addComponent(jLabel11)
                                .addComponent(txt_usuario_log)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txt_id_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(231, 235, 238));
        jPanel2.setPreferredSize(new java.awt.Dimension(1089, 592));

        jPanel3.setBackground(new java.awt.Color(231, 235, 238));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição do Chamado:"));

        txt_chamado.setColumns(20);
        txt_chamado.setRows(5);
        txt_chamado.setWrapStyleWord(true);
        txt_chamado.setPreferredSize(new java.awt.Dimension(164, 90));
        jScrollPane1.setViewportView(txt_chamado);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel4.setBackground(new java.awt.Color(231, 235, 238));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Resolução do Chamado:"));

        txt_solucao.setColumns(20);
        txt_solucao.setRows(5);
        txt_solucao.setWrapStyleWord(true);
        txt_solucao.setPreferredSize(new java.awt.Dimension(164, 90));
        jScrollPane2.setViewportView(txt_solucao);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
        );

        jPanel5.setBackground(new java.awt.Color(231, 235, 238));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tags:"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_tags, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_tags, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        btn_editar.setBackground(new java.awt.Color(241, 117, 9));
        btn_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_edit_property_35px_1.png"))); // NOI18N
        btn_editar.setText("Editar");
        btn_editar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editar.setOpaque(false);
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });

        btn_salvar.setBackground(new java.awt.Color(241, 117, 9));
        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_save_35px.png"))); // NOI18N
        btn_salvar.setText("Salvar");
        btn_salvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        jLabel1.setText("Duração do chamado:");

        lb_DiasChamados.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_DiasChamados, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(lb_DiasChamados)))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        String userlogado = userlogado2.getText();
        String userChamado = txt_usuario_log.getText();

        SomaDatas();

        if (userlogado == null ? userChamado == null : userlogado.equals(userChamado)) {
            JOptionPane.showMessageDialog(null, "Mesmo usuario : " + userlogado + " - " + userChamado);
            txt_id_Chamado.setEnabled(true);
            //txt_conexao.setEditable(true);
            //txt_titulo.setEditable(true);
            txt_chamado.setEditable(true);
            txt_solucao.setEditable(true);
            txt_tags.setEditable(true);
            btn_salvar.setEnabled(true);
            cmb_Status.setEnabled(true);
            btn_editar.setEnabled(false);
        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "Usuario logado diferente do chamado! \nDeseja Editar o Chamado?", "Editar Chamado", dialogButton);
            if (result == 0) {
                txt_id_Chamado.setEnabled(true);
                //txt_conexao.setEditable(true);
                //txt_titulo.setEditable(true);
                txt_chamado.setEditable(true);
                txt_solucao.setEditable(true);
                txt_tags.setEditable(true);
                btn_salvar.setEnabled(true);
                cmb_Status.setEnabled(true);
                btn_editar.setEnabled(false);
            } else {
                txt_id_Chamado.setEnabled(false);
                //txt_conexao.setEditable(false);
                //txt_titulo.setEditable(false);
                txt_chamado.setEditable(false);
                txt_solucao.setEditable(false);
                txt_tags.setEditable(false);
                btn_salvar.setEnabled(false);
                cmb_Status.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        int MesCont = 1;
        try {
            String userlogado = userlogado2.getText();

            /* TESTE DATA */
            String dataRetorno = lb_data_chamado.getText(); //PEGA A DATA QUE CHEGA NO LABEL E TRANSFORMA EM VAREAVEL

            DateTimeFormatter formatData
                    = DateTimeFormatter.ofPattern("yyyy-MM-dd");//DEFINIR O PADRÃO DA DATA
            TemporalAccessor parse3 = formatData.parse(dataRetorno);//PEGA A DATA EM STRING PASSA PARA O PADRÃO E TRANSFORMA EM TEMPORAL

            LocalDate from3 = LocalDate.from(parse3);//PEGA O TEMPORAL E PASSA PARA LOCALDATE
            Date date1 = Date.from(from3.atStartOfDay(ZoneId.systemDefault()).toInstant());//CONVERTE DE LOCALDATE PARA DATE

            GregorianCalendar calendar = new GregorianCalendar();//CRIA UM GregorianCalendar
            calendar.setTime(date1);//PASSA A DATA CONVERTIDA EM DATE PARA ELE

            int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);//PEGA O DIA
            int Mes = calendar.get(GregorianCalendar.MONTH);//PEGA O MES
            int ano = calendar.get(GregorianCalendar.YEAR);//PEGA O ANO

            //PEGA O DIA/MESANO ATUAL PELO LocalDate
            LocalDate today = LocalDate.now();
            int day = today.getDayOfMonth();
            int month = today.getMonthValue();  // Returns 1-12 as values.
            int year = today.getYear();

            int Mes2 = Mes + MesCont;//GAMBIARRA PARA AJUSTAR O MES

            LocalDate DateAntigo = LocalDate.of(ano, Mes2, dia);
            LocalDate localDateNovo = LocalDate.of(year, month, day);//FALTA PEGAR A DANTA ANTIGA

            System.out.println("Convertendo String Data para DateTime");
            JOptionPane.showMessageDialog(null, "Ano: " + ano + " Mês: " + Mes2 + " dia: " + dia);
            JOptionPane.showMessageDialog(null, "Data antiga: " + DateAntigo + " - " + " Nova Data: " + localDateNovo);

            /* FIM TESTE DATA */
            Chamado c = new Chamado();
            ChamadoDAO cdao = new ChamadoDAO();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date DataCerto = new java.sql.Date(format.parse(lb_data_chamado.getText()).getTime());//CONVERTE STRING EM DATA

            c.setId(Integer.parseInt(txt_id_Chamado.getText()));
            c.setNome_emp(lb_empresa.getText());
            c.setNome(lb_Funcionario.getText());
            c.setFone(lb_telefone.getText());
            c.setTitulo(txt_titulo.getText());
            c.setTexto(txt_chamado.getText());
            c.setTags(txt_tags.getText());
            c.setSolucao(txt_solucao.getText());
            c.setConexao(txt_conexao.getText());
            c.setUser(txt_usuario_log.getText());
            c.setData(DataCerto);
            c.setHora(lb_hora.getText());

            String Status = (String) cmb_Status.getSelectedItem();
            if (Status.equals("Aberto")) {
                status = "A";
            } else {
                status = "F";
            }
            c.setStatus(status);
            c.setEditado(userlogado);
            c.setHoraeditado(horaAtual);

            c.setId(Integer.parseInt(txt_id_Chamado.getText()));

            cdao.EditarChamado(c);

            Principal3.listaChamadosAbertos(userlogado);

            //TESTES PARA ATUALIZAR TABELAS Chamado dia/semana/mes/abertos
            ListaChamadosAberto(userlogado);

        } catch (ParseException ex) {
            Logger.getLogger(Chamado_View2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    public void ListaChamadosAberto(String user) {
        
        //CRIAR UM IF
        /*SE FOR P 
            DefaultTableModel modelo = (DefaultTableModel) Principal3.tbl_Chamado.getModel();
        /SE FOR D
            DefaultTableModel modelo = (DefaultTableModel) Lista_ChamadosDia.tbl_Chamado.getModel();
        /SE FOR S
            DefaultTableModel modelo = (DefaultTableModel) Lista_ChamadosSemana.tbl_Chamado.getModel();
        /SE FOR M
            DefaultTableModel modelo = (DefaultTableModel) Lista_ChamadosMes.tbl_Chamado.getModel();
        /SE FOR A
            DefaultTableModel modelo = (DefaultTableModel) ListaChamadosAbertos.tbl_Chamado.getModel();
        /SE FOR L - ULTIMOS 10
            DefaultTableModel modelo = (DefaultTableModel) Principal3.Tabela10Chamados.getModel();
        
        
        
        */
        
        DefaultTableModel modelo = (DefaultTableModel) ListaChamadosAbertos.tbl_Chamado.getModel();
        modelo.setNumRows(0);

        tbl_Chamado.setAutoResizeMode(tbl_Chamado.AUTO_RESIZE_OFF);
        tbl_Chamado.setRowSorter(new TableRowSorter(modelo));
        tbl_Chamado.setModel(modelo);
        tbl_Chamado.getColumnModel().getColumn(0).setPreferredWidth(40);//ID
        tbl_Chamado.getColumnModel().getColumn(1).setPreferredWidth(280);//TITULO
        tbl_Chamado.getColumnModel().getColumn(2).setPreferredWidth(200);//DESCRIÇÃO
        tbl_Chamado.getColumnModel().getColumn(3).setPreferredWidth(280);//SOLUÇÃO
        tbl_Chamado.getColumnModel().getColumn(4).setPreferredWidth(200);//EMPRESA
        tbl_Chamado.getColumnModel().getColumn(5).setPreferredWidth(100);//FUNCIONARIO
        tbl_Chamado.getColumnModel().getColumn(6).setPreferredWidth(100);//TAGS
        tbl_Chamado.getColumnModel().getColumn(7).setPreferredWidth(150);//FONE
        tbl_Chamado.getColumnModel().getColumn(8).setPreferredWidth(80);//CONEXÃO
        tbl_Chamado.getColumnModel().getColumn(9).setPreferredWidth(100);//USUARIO LOGICOM
        tbl_Chamado.getColumnModel().getColumn(10).setPreferredWidth(100);//DATA
        tbl_Chamado.getColumnModel().getColumn(11).setPreferredWidth(90);//HORA CHAMADO
        tbl_Chamado.getColumnModel().getColumn(12).setPreferredWidth(50);//STATUS
        tbl_Chamado.getColumnModel().getColumn(13).setPreferredWidth(100);//EDITADO
        tbl_Chamado.getColumnModel().getColumn(14).setPreferredWidth(90);//DATA EDITADO
        tbl_Chamado.getColumnModel().getColumn(15).setPreferredWidth(90);//HORA EDITADO
        //acrescentados
        tbl_Chamado.getColumnModel().getColumn(16).setPreferredWidth(100);//CRONOMETRO
        tbl_Chamado.getColumnModel().getColumn(17).setPreferredWidth(90);//HORA FECHADO
        tbl_Chamado.getColumnModel().getColumn(18).setPreferredWidth(90);//DATA FECHADO

        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.ListarChamadoAberto(user)) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getTitulo(),
                c.getTexto(),
                c.getSolucao(),
                c.getNome_emp(),
                c.getNome(),
                c.getTags(),
                c.getFone(),
                c.getConexao(),
                c.getUser(),
                c.getData(),
                c.getHora(),
                c.getStatus(),
                c.getEditado(),
                c.getDataeditado(),
                c.getHoraeditado(),
                c.getCronometro(),
                c.getHoraFechaChamado(),
                c.getDataChamadoFechado()
            });
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
            java.util.logging.Logger.getLogger(Chamado_View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chamado_View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chamado_View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chamado_View4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Chamado_View4().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Chamado_View4.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_salvar;
    public javax.swing.JComboBox cmb_Status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lb_DiasChamados;
    public javax.swing.JLabel lb_Funcionario;
    public javax.swing.JLabel lb_data_chamado;
    public javax.swing.JLabel lb_empresa;
    public javax.swing.JLabel lb_hora;
    public javax.swing.JLabel lb_telefone;
    public javax.swing.JLabel lbl_Cronometro;
    public javax.swing.JLabel lbl_DataChamadoFecha;
    public javax.swing.JLabel lbl_HoraChamadoFecha;
    public javax.swing.JTextArea txt_chamado;
    public javax.swing.JLabel txt_conexao;
    public javax.swing.JTextField txt_id_Chamado;
    public javax.swing.JTextArea txt_solucao;
    public javax.swing.JTextField txt_tags;
    public javax.swing.JLabel txt_titulo;
    public javax.swing.JLabel txt_usuario_log;
    // End of variables declaration//GEN-END:variables
}
