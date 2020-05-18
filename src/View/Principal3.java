package View;

import static View.Principal3.maximized;
import static connection.ConnectionFactory.getConnection;
import java.lang.management.ManagementFactory;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import Funcoes.exportarExcel;
import Funcoes.sql;
import connection.ConnectionFactory;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Statement;
import model.bean.Agenda;
import model.bean.Chamado;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.bean.Ramais;
import model.bean.Versoes;
import model.bean.ProBean;
import model.dao.AgendaDAO;
import model.dao.ChamadoDAO;
import model.dao.EmpresaDAO;
import model.dao.FuncionarioDAO;
import model.dao.VersoesDAO;
import model.dao.RamaisDAO;
import model.dao.ProDAO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Timer;
import org.apache.commons.io.FileUtils;

//importa a lib de validação de cpf/cnpj e telefone
import Jm.JMascara;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Usuario;
import model.dao.UsuarioDAO;
import Funcoes.RoundImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;

/**
 *
 * @author William Pereira
 */
public final class Principal3 extends javax.swing.JFrame {

    //VAREAVEL DA IMAGEM
    JFileChooser jfc;
    File file;
    File file2;

    //VAREAVEL PARA IMG BOTÃO SIDEBAR
    String estadoBTN = "1";

    //VARIAVEIS PARA O CRONOMETRO
    private Timer timer;
    private int currentSegundo = 0;
    private int currentMinuto = 0;
    private int currentHora = 0;
    private int velocidade = 1000;

    static boolean state = true;

    //Define a variavel status do chamado
    String status;

    //VARIAVEL PARA USUARIOS
    public static String userLogado3;
    public static int idLogado3;

    //VARIAVEIS PARA MEXER O FRAME COM O MOUSE
    static boolean maximized = true;
    int xMouse;
    int yMouse;

    //Chama a teça chamadoView2
    Chamado_View4 chamadoView2;

    ProDAO prodao = new ProDAO();

    //Variavel para o sistema de flag da tela printscream
    public static boolean flagPrint = false;

    //Variavel para sistema de foto
    String fname = null;
    byte[] pinsertimage = null;

    //Para o PRO
    String ruta_archivo = "";
    int id = -1;

    String userLogado = "willtunner";

    //Pega usuario
    String user = LoginNew3.txt_Login.getText();

    public Principal3(String userLogado, int idLogado) throws UnknownHostException, SocketException, ParseException {
        this.chamadoView2 = new Chamado_View4();
        initComponents();

        //COLOCA O LOGIN NO LABEL
        userlogado2.setText(userLogado);
        userImageProfile(idLogado);

        txt_nome_agenda.setColumns(1);
        txt_email_agenda.setColumns(1);
        txt_telefone_agenda.setColumns(1);
        txt_endereco_agenda.setColumns(1);
        txt_observacao_agenda.setColumns(1);

        //INICIA CRONOMETRO
        iniciarContagem();//Aqui inicia a contagem
        stopTime(); // Aqui para o tempo para que o nosso cronômetro inicie parado
        //lbl_Cronometro.setVisible(false);

        //Função para pegar o time zone certo da hora
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-03:00"));

        //Função que verifica se tem algo no text input da conexão e muda o botão
        ChecaTexto();

        //adiciona os contadores do usuario Dia, Semana e Mês
        listacontador(userLogado);
        listacontadorSemana(userLogado);
        listacontadorMes(userLogado);
        listaChamadosAbertos(userLogado);

        //Função que ativa botões Tela Versão
        ativa_botao_V(false, false, false, false, false, false, false, false, false, false, true);

        //ICONE FRAME
        URL caminhoIcone = getClass().getResource("/imagem/Logo128x128.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

        //deixa o radiobutton empresa selecionado desde o inicio
        RadEmp.setSelected(true);

        //deixa o radiobutton aberto como padrão
        rad_Aberto.setSelected(true);

        //Funções para popular tabelas
        popuptabble();
        readJTable();
        readJTableChamado();
        readJTableAgenda();
        readJTableEmp();
        readJtable_User_Chamado();

        //Chama os 5 ultimos chamados
        ChamadosLastTen();

        //Popula Tabela Versão
        PreencheTblVersion();

        //Popula tabela PRO
        prodao.visualizar_PdfVO(tbl_pro);

        //NOTIFICAÇÃO DO WINDOWS
        DesktopNotify.showDesktopMessage("Bem vindo ao HelpDesk Sr:" + user,
                "Sistema feito para o aprimoramento do serviço de suporte da Logicom.", DesktopNotify.INFORMATION, 5000L);

        //Popula ComboBox Empresa
        EmpresaDAO emp = new EmpresaDAO();
        DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(emp.mostrarEmpresas());
        combo_emp.setModel(modelEstado);
        combo_emp2.setModel(modelEstado);

        //Data e Hora do sistema
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);//Pega a data no formato dd/MM/yyyy
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);//Pega a hora 

        //Mostra a data na label certa
        Lb_Data_Principal.setText(data);
        Lb_Hora_Principal.setText(hora);

        /*
         TESTE RESOLUÇÃO
         */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        System.out.println("Screen width = " + d.width);
        System.out.println("Screen height = " + d.height);
        //JOptionPane.showMessageDialog(null, "Largura :" + d.width + " Altura :" + d.height);
        //1024x768 menor resolução

        //Validação da resolução, se for menor ou igual a 1024 ele reclama
        if (d.width <= 1024 || d.height <= 600) {
            lb_chamado_dia_IMG.setLocation(100, -100);
            JOptionPane.showMessageDialog(null, "Resolução inferior á 1024 é inadequada para o sistema!");
        }

        //Insere o autocompletar no combobox empresa
        AutoCompleteDecorator.decorate(combo_emp);

        //adiciona o metodo 2  clicks na tabela chamado
        tbl_Chamado.addMouseListener(new MyMouseListener());
        tbl_Chamado2.addMouseListener(new MyMouseListener2());

        //PEGA DEFINIÇÕES DO SISTEMA
        Runtime rt = Runtime.getRuntime();
        String path = "c:/";
        File file = new File(path);
        long Livre = CapacityFormatter.toGigabytes(file.getFreeSpace());//Pega espaço livre definido no path
        long Total = CapacityFormatter.toGigabytes(file.getTotalSpace());//Pega o espaço total do path
        long Usado = CapacityFormatter.toGigabytes(file.getTotalSpace() - file.getFreeSpace());//Pega o espaço usado do path

        //Busca definições do sistema
        String osVersion = System.getProperty("os.version");//Versão do sistema
        String osName = System.getProperty("os.name");//Nome do seu sistema
        String osArch = System.getProperty("os.arch");//Arquitetura do sistema
        Locale osLang = Locale.getDefault();//Lingua do sistema

        //PEGA DEFINIÇÕES DA MEMORIA PC
        com.sun.management.OperatingSystemMXBean mxbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        //Pega informações da Memoria
        Long totalM = CapacityFormatter.toGigabytes(mxbean.getTotalPhysicalMemorySize());
        Long livreM = CapacityFormatter.toGigabytes(mxbean.getFreePhysicalMemorySize());

        System.out.println("Memoria Total : " + String.valueOf(totalM) + " Gb ");//TOTAL DE MEMORIA NO PC
        System.out.println("Memoria disponivel : " + String.valueOf(livreM) + " Gb");//TOTAL DISPONIVEL DE MEMORIA
        System.out.println("Memoria Usada : " + String.valueOf(totalM - livreM) + " Gb \n");//QUANTIDADE USADA DE MEMORIA

        System.out.println("Número de processadores logicos: " + rt.availableProcessors());

        //mostra na saida
        System.out.println("Espaço Livre: " + Livre + "Gb Livres");
        System.out.println("Espaço Total: " + Total + "Gb ");
        System.out.println("Espaço Usado: " + Usado + "Gb Usado \n");

        //Popula label Espaço HD COnfiguração
        lb_Conf_Livre.setText(String.valueOf(Livre) + " Gb");
        lb_Conf_Total.setText(String.valueOf(Total) + " Gb");
        lb_Conf_Usado.setText(String.valueOf(Usado) + " Gb");
        lb_Conf_MLivre.setText(String.valueOf(livreM) + " Gb");
        lb_Conf_MTotal.setText(String.valueOf(totalM) + " Gb");
        lb_Conf_MUsada.setText(String.valueOf(totalM - livreM) + " Gb");

        //Imprime a linguagem do sistema
        System.out.println(osLang.getDisplayLanguage()); // imprime "Português"
        System.out.println(osLang.getLanguage()); // imprime "pt"
        System.out.println("Nome do seu sistema: " + osName + " Versão do seu sistema é : " + osVersion + " Tipo é: " + osArch + " O idioma é: " + osLang.getDisplayLanguage());

        //Diz se o sistema é 64 ou 32bits
        String Arch = "";
        if (osArch.equals("amd64")) {
            Arch = "64Bit";
        } else {
            Arch = "32Bit";
        }

        //busca o ip
        InetAddress ip;
        ip = InetAddress.getLocalHost();
        lb_Ip.setText(ip.getHostAddress());//popula no label o ip do computador

        //busca o mac
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }

        lb_mac.setText(sb.toString());//popula no label o mac do computador
        lb_1.setText(osName);//Popula o nome do sistema 
        lb_2.setText(osVersion);//Popula a versão do sistema
        lb_3.setText(Arch);//Popula a arquitetura do sistema
        lb_4.setText(osLang.getDisplayLanguage());//Popula o idioma do sistema
    }

    Principal3() throws ParseException {
        this.chamadoView2 = new Chamado_View4();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * *
     * @param ArredondamentoDaImagem
     *
     */
    private void roundPhoto(String path) {
        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(
                    lbl_Image.getWidth(),
                    lbl_Image.getHeight(),
                    Image.SCALE_SMOOTH));
            lbl_Image.setIcon(new ImageIcon(RoundImage.getRoundImage(icon.getImage(), 200)));

            ImageIcon icon2 = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(
                    lb_Foto.getWidth(),
                    lb_Foto.getHeight(),
                    Image.SCALE_SMOOTH));
            lb_Foto.setIcon(new ImageIcon(RoundImage.getRoundImage(icon2.getImage(), 200)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *
     * @param Cronometro
     * @param pic
     * @return *************************************************************
     */
    private void iniciarContagem() {
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentSegundo++;

                if (currentSegundo == 60) {
                    currentMinuto++;
                    currentSegundo = 0;
                }

                if (currentMinuto == 60) {
                    currentHora++;
                    currentMinuto = 0;
                }

                String hr = currentHora <= 9 ? "0" + currentHora : currentHora + "";
                String min = currentMinuto <= 9 ? "0" + currentMinuto : currentMinuto + "";
                String seg = currentSegundo <= 9 ? "0" + currentSegundo : currentSegundo + "";

                lbl_Cronometro.setText(hr + ":" + min + ":" + seg);
            }
        };
        this.timer = new Timer(velocidade, action);
        this.timer.start();
    }

    private void stopTime() {
        timer.stop();
        currentHora = 0;
        currentMinuto = 0;
        currentSegundo = 0;
        lbl_Cronometro.setText("00:00:00");
    }

    //*************************************************************************************//
    //================  Metodo para redimensionar foto ======================//
    public ImageIcon resizeImage(String imagePath, byte[] pic) {
        ImageIcon myImage = null;
        if (imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }

        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(lb_Foto.getHeight(), lb_Foto.getWidth(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;
    }

    //metodo que converte em Gigas
    private static class CapacityFormatter {

        private static long toGigabytes(long capacity) {
            return capacity / (long) Math.pow(10, 9);
        }
    }

    //Metodo para checar o se tem algo na conexão muda o botão
    public void ChecaTexto() {
        String texto = txt_conexao.getText().trim();

        if (texto == null || texto.equals("")) {
            btnAnydesk.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
        } else {
            btnAnydesk.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
        }
    }

    //Metodo de 2 click na tabela chamado 5 ultimos
    public class MyMouseListener2 extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int index = tbl_Chamado2.getSelectedRow();

                TableModel model = tbl_Chamado2.getModel();

                String Id = model.getValueAt(index, 0).toString();
                String Titulo = model.getValueAt(index, 1).toString();
                String Conteudo = model.getValueAt(index, 2).toString();
                String Empresa = model.getValueAt(index, 4).toString();
                String Funcionario = model.getValueAt(index, 5).toString();
                String Tags = model.getValueAt(index, 6).toString();
                String Solucao = model.getValueAt(index, 3).toString();
                String Conexao = model.getValueAt(index, 8).toString();
                String User = model.getValueAt(index, 9).toString();
                String Telefone = model.getValueAt(index, 7).toString();
                String Status = model.getValueAt(index, 12).toString();
                String DataChamado = model.getValueAt(index, 10).toString();//PEGA A DATA CERTA DO CHAMADO

                //Data e Hora do sistema
                Date dataHoraAtual = new Date();
                String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

                //função para enviar o status para outro frame
                if (Status.equals("A")) {
                    chamadoView2.cmb_Status.setSelectedIndex(0);
                } else {
                    chamadoView2.cmb_Status.setSelectedIndex(1);
                }

                chamadoView2.txt_id_Chamado.setText(Id);
                chamadoView2.txt_titulo.setText(Titulo);
                chamadoView2.txt_chamado.setText(Conteudo);
                chamadoView2.lb_empresa.setText(Empresa);
                chamadoView2.lb_Funcionario.setText(Funcionario);
                chamadoView2.txt_solucao.setText(Solucao);
                chamadoView2.txt_conexao.setText(Conexao);
                chamadoView2.txt_tags.setText(Tags);
                chamadoView2.txt_usuario_log.setText(User);
                chamadoView2.lb_telefone.setText(Telefone);
                chamadoView2.lb_data_chamado.setText(DataChamado);//PRECISA AJUSTAR ESSA DATA DO CHAMADO VIEW
                chamadoView2.lb_hora.setText(hora);
                chamadoView2.setVisible(true);
                chamadoView2.pack();
                chamadoView2.setLocationRelativeTo(null);
                chamadoView2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }
    }

    //Metodo de 2 click na tabela chamado
    public class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                carregaDados();
                
                
                String refresh = "C";
                chamadoView2.refreshTabela = refresh;
            }
        }
    }

    public void carregaDados() {
        int index = tbl_Chamado.getSelectedRow();

        TableModel model = tbl_Chamado.getModel();

        String Id = model.getValueAt(index, 0).toString();
        String Titulo = model.getValueAt(index, 1).toString();
        String Conteudo = model.getValueAt(index, 2).toString();
        String Solucao = model.getValueAt(index, 3).toString();
        String Empresa = model.getValueAt(index, 4).toString();
        String Funcionario = model.getValueAt(index, 5).toString();
        String Tags = model.getValueAt(index, 6).toString();
        String Telefone = model.getValueAt(index, 7).toString();
        String Conexao = model.getValueAt(index, 8).toString();
        String User = model.getValueAt(index, 9).toString();
        String DataChamado = model.getValueAt(index, 10).toString();//PEGA A DATA CERTA DO CHAMADO
        String HoraChamado = model.getValueAt(index, 11).toString();//PEGA A HORA DO CHAMADO
        String Status = model.getValueAt(index, 12).toString();
        String Cronometro = model.getValueAt(index, 16).toString();

        if (model.getValueAt(index, 17) != null) {
            String HoraFechado = model.getValueAt(index, 17).toString();//PEGA HORA DO CHAMADO FECHADO
            chamadoView2.lbl_HoraChamadoFecha.setForeground(new Color(204, 102, 0));
            chamadoView2.lbl_HoraChamadoFecha.setText(HoraFechado);
            chamadoView2.HoraFim = HoraFechado;
        } else {
            chamadoView2.lbl_HoraChamadoFecha.setForeground(Color.red);
            chamadoView2.lbl_HoraChamadoFecha.setText("Em Aberto");
            LocalTime horaEmAberto = LocalTime.now();
            chamadoView2.HoraFim = horaEmAberto.toString();
        }

        if (model.getValueAt(index, 18) != null) {
            String DataFechado = model.getValueAt(index, 18).toString();//PEGA DATA DO CHAMADO FECHADO
            chamadoView2.lbl_DataChamadoFecha.setForeground(new Color(204, 102, 0));
            chamadoView2.lbl_DataChamadoFecha.setText(DataFechado);
            chamadoView2.dataFim = DataFechado;
        } else {
            String dataAberto = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            chamadoView2.lbl_DataChamadoFecha.setForeground(Color.red);
            chamadoView2.lbl_DataChamadoFecha.setText("Em Aberto");
            chamadoView2.dataFim = dataAberto;
        }

        //função para enviar o status para outro frame
        if (Status.equals("A")) {
            chamadoView2.cmb_Status.setSelectedIndex(0);
        } else {
            chamadoView2.cmb_Status.setSelectedIndex(1);
        }
        chamadoView2.dataIni = DataChamado;//PASSA A DATA PARA A VAREAVEL EM OUTRA TELA
        chamadoView2.txt_id_Chamado.setText(Id);
        chamadoView2.txt_titulo.setText(Titulo);
        chamadoView2.txt_chamado.setText(Conteudo);
        chamadoView2.lb_empresa.setText(Empresa);
        chamadoView2.lb_Funcionario.setText(Funcionario);
        chamadoView2.txt_solucao.setText(Solucao);
        chamadoView2.txt_conexao.setText(Conexao);
        chamadoView2.txt_tags.setText(Tags);
        chamadoView2.txt_usuario_log.setText(User);
        chamadoView2.lb_telefone.setText(Telefone);
        chamadoView2.lb_data_chamado.setText(DataChamado);
        chamadoView2.lbl_Cronometro.setText(Cronometro);

        chamadoView2.lb_hora.setText(HoraChamado);
        chamadoView2.SomaDatas();//PASSA OS DADOS DO CHAMADO ANTES DE CHAMAR A TELA 
        chamadoView2.setVisible(true);//CHAMA A TELA
        chamadoView2.pack();
        chamadoView2.setLocationRelativeTo(null);
        chamadoView2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Metodo de 2 click na Chamado do perfil/usuario
    public class MyMouseListener3 extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int index = jt_Painel_User_Chamado.getSelectedRow();

                TableModel model = jt_Painel_User_Chamado.getModel();

                String Id = model.getValueAt(index, 0).toString();
                String Titulo = model.getValueAt(index, 1).toString();
                String Conteudo = model.getValueAt(index, 2).toString();
                String Empresa = model.getValueAt(index, 4).toString();
                String Funcionario = model.getValueAt(index, 5).toString();
                String Tags = model.getValueAt(index, 6).toString();
                String Solucao = model.getValueAt(index, 3).toString();
                String Conexao = model.getValueAt(index, 8).toString();
                String User = model.getValueAt(index, 9).toString();
                String Telefone = model.getValueAt(index, 7).toString();
                String Status = model.getValueAt(index, 12).toString();
                String DataChamado = model.getValueAt(index, 10).toString();//PEGA A DATA CERTA DO CHAMADO

                //Data e Hora do sistema
                Date dataHoraAtual = new Date();
                String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

                //função para enviar o status para outro frame
                if (Status.equals("A")) {
                    chamadoView2.cmb_Status.setSelectedIndex(0);
                } else {
                    chamadoView2.cmb_Status.setSelectedIndex(1);
                }

                chamadoView2.txt_id_Chamado.setText(Id);
                chamadoView2.txt_titulo.setText(Titulo);
                chamadoView2.txt_chamado.setText(Conteudo);
                chamadoView2.lb_empresa.setText(Empresa);
                chamadoView2.lb_Funcionario.setText(Funcionario);
                chamadoView2.txt_solucao.setText(Solucao);
                chamadoView2.txt_conexao.setText(Conexao);
                chamadoView2.txt_tags.setText(Tags);
                chamadoView2.txt_usuario_log.setText(User);
                chamadoView2.lb_telefone.setText(Telefone);
                chamadoView2.lb_data_chamado.setText(DataChamado);//PRECISA AJUSTAR ESSA DATA DO CHAMADO VIEW
                chamadoView2.lb_hora.setText(hora);
                chamadoView2.setVisible(true);
                chamadoView2.pack();
                chamadoView2.setLocationRelativeTo(null);
                chamadoView2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }
    }

    //popula Tabela empresa
    public void readJTableEmp() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_empresa.getModel();
        modelo.setNumRows(0);
        tbl_empresa.setRowSorter(new TableRowSorter(modelo));
        tbl_empresa.setAutoResizeMode(tbl_empresa.AUTO_RESIZE_OFF);
        tbl_empresa.setModel(modelo);
        tbl_empresa.getColumnModel().getColumn(0).setPreferredWidth(40);//ID
        tbl_empresa.getColumnModel().getColumn(1).setPreferredWidth(170);//EMPRESA
        tbl_empresa.getColumnModel().getColumn(2).setPreferredWidth(100);//TELEFONE
        tbl_empresa.getColumnModel().getColumn(3).setPreferredWidth(120);//CNPJ
        tbl_empresa.getColumnModel().getColumn(4).setPreferredWidth(250);//OBS
        tbl_empresa.getColumnModel().getColumn(5).setPreferredWidth(100);//IP SCEF
        tbl_empresa.getColumnModel().getColumn(6).setPreferredWidth(100);//MAC SCEF
        tbl_empresa.getColumnModel().getColumn(7).setPreferredWidth(100);//CON SCEF
        tbl_empresa.getColumnModel().getColumn(8).setPreferredWidth(100);//IP NFCE
        tbl_empresa.getColumnModel().getColumn(9).setPreferredWidth(100);//MAC NFCE
        tbl_empresa.getColumnModel().getColumn(10).setPreferredWidth(100);//CON NFCE
        tbl_empresa.getColumnModel().getColumn(11).setPreferredWidth(100);//IP NFE
        tbl_empresa.getColumnModel().getColumn(12).setPreferredWidth(100);//MAC NFE
        tbl_empresa.getColumnModel().getColumn(13).setPreferredWidth(100);//CON NFE
        tbl_empresa.getColumnModel().getColumn(14).setPreferredWidth(100);//MOBILE
        tbl_empresa.getColumnModel().getColumn(15).setPreferredWidth(100);//COLETOR
        tbl_empresa.getColumnModel().getColumn(16).setPreferredWidth(100);//ESTOQUE
        tbl_empresa.getColumnModel().getColumn(17).setPreferredWidth(100);//NFCE
        tbl_empresa.getColumnModel().getColumn(18).setPreferredWidth(100);//NFE
        tbl_empresa.getColumnModel().getColumn(19).setPreferredWidth(100);//SISSEG
        tbl_empresa.getColumnModel().getColumn(20).setPreferredWidth(100);//CHECKOUT

        EmpresaDAO adao = new EmpresaDAO();

        for (Empresa e : adao.ListaEmpresa()) {
            modelo.addRow(new Object[]{
                e.getId_emp(),
                e.getNome_emp(),
                e.getTelefone(),
                e.getCnpj(),
                e.getObs(),
                e.getIp_scef(),
                e.getMac_scef(),
                e.getCon_scef(),
                e.getIp_nfce(),
                e.getMac_nfce(),
                e.getCon_nfce(),
                e.getIp_nfe(),
                e.getMac_nfe(),
                e.getCon_nfe(),
                e.getIp_mobile(),
                e.getIp_coletor(),
                e.getV_estoque(),
                e.getV_nfce(),
                e.getV_nfe(),
                e.getV_sisseg(),
                e.getV_checkout()
            });
        }
    }

    //popula Tabela agenda
    public void readJTableAgenda() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_agenda.getModel();
        tbl_agenda.setRowSorter(new TableRowSorter(modelo));//Coloca para ordenar tabela quando clica na coluna
        tbl_agenda.setAutoResizeMode(tbl_agenda.AUTO_RESIZE_OFF);//coloca a tabela para mover na horizontal

        modelo.setNumRows(0);

        tbl_agenda.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
        tbl_agenda.getColumnModel().getColumn(1).setPreferredWidth(160);//Nome
        tbl_agenda.getColumnModel().getColumn(2).setPreferredWidth(300);//Endereço
        tbl_agenda.getColumnModel().getColumn(3).setPreferredWidth(190);//Email
        tbl_agenda.getColumnModel().getColumn(4).setPreferredWidth(100);//Telefone
        tbl_agenda.getColumnModel().getColumn(5).setPreferredWidth(400);//Observação

        AgendaDAO adao = new AgendaDAO();

        for (Agenda a : adao.Listar()) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getEmail(),
                a.getEndereco(),
                a.getTelefone(),
                a.getObservacao()
            });
        }
    }

    //Função Pesquisa da Agenda
    public void PesquisaAgendaNome(String nome) {
        DefaultTableModel modelo = (DefaultTableModel) tbl_agenda.getModel();
        tbl_agenda.setRowSorter(new TableRowSorter(modelo));//Coloca para ordenar tabela quando clica na coluna
        tbl_agenda.setAutoResizeMode(tbl_agenda.AUTO_RESIZE_OFF);//coloca a tabela para mover na horizontal

        modelo.setNumRows(0);

        tbl_agenda.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
        tbl_agenda.getColumnModel().getColumn(1).setPreferredWidth(160);//Nome
        tbl_agenda.getColumnModel().getColumn(2).setPreferredWidth(300);//Endereço
        tbl_agenda.getColumnModel().getColumn(3).setPreferredWidth(190);//Email
        tbl_agenda.getColumnModel().getColumn(4).setPreferredWidth(100);//Telefone
        tbl_agenda.getColumnModel().getColumn(5).setPreferredWidth(400);//Observação

        AgendaDAO adao = new AgendaDAO();

        for (Agenda a : adao.Pesquisar(nome)) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getEmail(),
                a.getEndereco(),
                a.getTelefone(),
                a.getObservacao()
            });
        }
    }

    //Lista Chamados dia
    public String listacontador(String userLogado) {
        String ChamadoDia = "";

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();//Chama Conexão
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado WHERE usuario = '" + userLogado + "' AND dataChamado = '" + data + "'");
            rs = stmt.executeQuery();
            System.out.println("TESTE DATA ATUAL: " + stmt);
            if (rs.next()) {
                ChamadoDia = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        lb_Chamado_Dia.setText(ChamadoDia);
        return ChamadoDia;
    }

    //Lista Chamados semana
    public String listacontadorSemana(String userLogado) {
        String ChamadoSemana = "";

        //Pega 7 dias tras apartir de hoje
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -7);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataSemana = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data semana :" + dataSemana);

        try {
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE usuario = '" + userLogado + "' AND  dataChamado BETWEEN  '" + dataSemana + "' AND '" + data + "'");
            rs = stmt.executeQuery();

            //System.out.println("TESTE SEMANA ATUAL :" + stmt);
            if (rs.next()) {
                ChamadoSemana = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lb_Chamado_semana.setText(ChamadoSemana);
        return ChamadoSemana;
    }

    //Lista Chamados Mes
    /**
     * Descriçao do metodo
     *
     * @param userLogado String com o usuario logado
     * @return String com o resultado tal
     */
    public String listacontadorMes(String userLogado) {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE usuario = '" + userLogado + "' AND  dataChamado BETWEEN  '" + dataMes + "' AND '" + data + "'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lb_Chamado_mes.setText(ChamadoMes);
        return ChamadoMes;
    }

    //Lista Chamados dia
    public static String listaChamadosAbertos(String userLogado) {
        String ChamadosAbertoUser = "";

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;

            Connection con = ConnectionFactory.getConnection();//Chama Conexão
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado WHERE usuario='" + userLogado + "' and STATUS='A'");
            rs = stmt.executeQuery();

            if (rs.next()) {
                ChamadosAbertoUser = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        btn_countChamado.setText(ChamadosAbertoUser);

        if (ChamadosAbertoUser.equals("0") || ChamadosAbertoUser == null) {
            btn_countChamado.setBackground(new Color(0, 102, 102));
        } else {
            btn_countChamado.setBackground(new Color(241, 117, 9));
        }

        return ChamadosAbertoUser;
    }

    public void userImageProfile(int idLogado) {

        try {
            String imageProfile = "";
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT * FROM usuarios u WHERE id_func=" + idLogado);
            rs = stmt.executeQuery();
            if (rs.next()) {
                imageProfile = rs.getString(5);
            }

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            String path = new File(".").getCanonicalPath();
            Image image = toolkit.getImage(path + "/image/" + rs.getString(5));
            Image imagedResized = image.getScaledInstance(115, 108, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(imagedResized);
            //roundPhoto(image.toString());
            lb_Foto.setIcon(icon);

        } catch (Exception e) {
        }

    }

    //Pesuisar Chamado
    public void pesquisar_chamado(String nome_emp, String data) {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.PesquisarChamado(nome_emp, data)) {
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

    //Pesuisar Chamado
    public void pesquisar_chamadoPerfil(String nome_emp, String data) {
        DefaultTableModel modelo = (DefaultTableModel) jt_Painel_User_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.PesquisarChamado(nome_emp, data)) {
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

    public void pesquisar_chamadoPerfil(String nome_emp, String data, String dataIni, String dataFim) {
        DefaultTableModel modelo = (DefaultTableModel) jt_Painel_User_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.PesquisarChamadoPerfil(nome_emp, data, dataIni, dataFim)) {
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

    //Popula o jtable Chamado
    public void readJTableChamado() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado.getModel();
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

        for (Chamado c : cadao.ListarChamado()) {
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

    //Popula o jtable Chamado - PAINEL CONTROLE USER
    public void readJtable_User_Chamado() {
        DefaultTableModel modelo = (DefaultTableModel) jt_Painel_User_Chamado.getModel();
        modelo.setNumRows(0);

        jt_Painel_User_Chamado.setAutoResizeMode(jt_Painel_User_Chamado.AUTO_RESIZE_OFF);
        jt_Painel_User_Chamado.setRowSorter(new TableRowSorter(modelo));
        jt_Painel_User_Chamado.setModel(modelo);
        jt_Painel_User_Chamado.getColumnModel().getColumn(0).setPreferredWidth(40);//ID
        jt_Painel_User_Chamado.getColumnModel().getColumn(1).setPreferredWidth(280);//TITULO
        jt_Painel_User_Chamado.getColumnModel().getColumn(2).setPreferredWidth(200);//DESCRIÇÃO
        jt_Painel_User_Chamado.getColumnModel().getColumn(3).setPreferredWidth(280);//SOLUÇÃO
        jt_Painel_User_Chamado.getColumnModel().getColumn(4).setPreferredWidth(200);//EMPRESA
        jt_Painel_User_Chamado.getColumnModel().getColumn(5).setPreferredWidth(100);//FUNCIONARIO
        jt_Painel_User_Chamado.getColumnModel().getColumn(6).setPreferredWidth(100);//TAGS
        jt_Painel_User_Chamado.getColumnModel().getColumn(7).setPreferredWidth(150);//FONE
        jt_Painel_User_Chamado.getColumnModel().getColumn(8).setPreferredWidth(80);//CONEXÃO
        jt_Painel_User_Chamado.getColumnModel().getColumn(9).setPreferredWidth(100);//USUARIO LOGICOM
        jt_Painel_User_Chamado.getColumnModel().getColumn(10).setPreferredWidth(100);//DATA
        jt_Painel_User_Chamado.getColumnModel().getColumn(11).setPreferredWidth(90);//HORA CHAMADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(12).setPreferredWidth(50);//STATUS
        jt_Painel_User_Chamado.getColumnModel().getColumn(13).setPreferredWidth(100);//EDITADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(14).setPreferredWidth(90);//DATA EDITADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(15).setPreferredWidth(90);//HORA EDITADO
        //acrescentados
        jt_Painel_User_Chamado.getColumnModel().getColumn(16).setPreferredWidth(100);//CRONOMETRO
        jt_Painel_User_Chamado.getColumnModel().getColumn(17).setPreferredWidth(90);//HORA FECHADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(18).setPreferredWidth(90);//DATA FECHADO

        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.ListarChamadoUser(user)) {
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

    //Popula o jtable 5 ultimos chamados
    public void ChamadosLastTen() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado2.getModel();
        modelo.setNumRows(0);

        tbl_Chamado2.setAutoResizeMode(tbl_Chamado2.AUTO_RESIZE_OFF);
        tbl_Chamado2.setRowSorter(new TableRowSorter(modelo));
        tbl_Chamado2.setModel(modelo);
        tbl_Chamado2.getColumnModel().getColumn(0).setPreferredWidth(40);//ID
        tbl_Chamado2.getColumnModel().getColumn(1).setPreferredWidth(280);//TITULO
        tbl_Chamado2.getColumnModel().getColumn(2).setPreferredWidth(200);//DESCRIÇÃO
        tbl_Chamado2.getColumnModel().getColumn(3).setPreferredWidth(280);//SOLUÇÃO
        tbl_Chamado2.getColumnModel().getColumn(4).setPreferredWidth(200);//EMPRESA
        tbl_Chamado2.getColumnModel().getColumn(5).setPreferredWidth(100);//FUNCIONARIO
        tbl_Chamado2.getColumnModel().getColumn(6).setPreferredWidth(100);//TAGS
        tbl_Chamado2.getColumnModel().getColumn(7).setPreferredWidth(150);//FONE
        tbl_Chamado2.getColumnModel().getColumn(8).setPreferredWidth(80);//CONEXÃO
        tbl_Chamado2.getColumnModel().getColumn(9).setPreferredWidth(100);//USUARIO LOGICOM
        tbl_Chamado2.getColumnModel().getColumn(10).setPreferredWidth(100);//DATA
        tbl_Chamado2.getColumnModel().getColumn(11).setPreferredWidth(90);//HORA CHAMADO
        tbl_Chamado2.getColumnModel().getColumn(12).setPreferredWidth(50);//STATUS
        tbl_Chamado2.getColumnModel().getColumn(13).setPreferredWidth(100);//EDITADO
        tbl_Chamado2.getColumnModel().getColumn(14).setPreferredWidth(90);//DATA EDITADO
        tbl_Chamado2.getColumnModel().getColumn(15).setPreferredWidth(90);//HORA EDITADO
        //acrescentados
        tbl_Chamado2.getColumnModel().getColumn(16).setPreferredWidth(100);//CRONOMETRO
        tbl_Chamado2.getColumnModel().getColumn(17).setPreferredWidth(90);//HORA FECHADO
        tbl_Chamado2.getColumnModel().getColumn(18).setPreferredWidth(90);//DATA FECHADO

        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.ListarChamado()) {
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

    //Popula o jtable Empresa
    public void readJTable() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Ramais.getModel();
        modelo.setNumRows(0);
        RamaisDAO rdao = new RamaisDAO();

        for (Ramais r : rdao.Listar()) {
            modelo.addRow(new Object[]{
                r.getNome(),
                r.getRamal()
            });
        }
    }

    //Popula o jtable Empresa
    public void PreencheTblVersion() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_version.getModel();
        modelo.setNumRows(0);

        VersoesDAO versd = new VersoesDAO();
        for (Versoes v : versd.ListarVersoes()) {
            modelo.addRow(new Object[]{
                v.getId(),
                v.getNome(),
                v.getVersao(),
                v.getWinversion(),
                v.getExt(),
                v.getDataredmine(),
                v.getHoraredmine(),
                v.getTamanho(),
                v.getUsuariocadastrou(),
                v.getAtualizado()
            });
        }
    }

    //PopUp da tabela chamado (click botão direito)
    public void popuptabble() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Visualizar", new ImageIcon(getClass().getResource("/imagem/System/icon_Pro_Orange.png")));
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Visualizar", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                int index = tbl_Chamado.getSelectedRow();
                //JOptionPane.showMessageDialog(null, index);
                TableModel model = tbl_Chamado.getModel();
                String Id = model.getValueAt(index, 0).toString();
                String Titulo = model.getValueAt(index, 1).toString();
                String Conteudo = model.getValueAt(index, 2).toString();
                String Empresa = model.getValueAt(index, 4).toString();
                String Funcionario = model.getValueAt(index, 5).toString();
                String Tags = model.getValueAt(index, 6).toString();
                String Solucao = model.getValueAt(index, 3).toString();
                String Conexao = model.getValueAt(index, 8).toString();
                String User = model.getValueAt(index, 9).toString();
                String Telefone = model.getValueAt(index, 7).toString();

                //Data e Hora do sistema
                Date dataHoraAtual = new Date();
                String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

                chamadoView2.txt_id_Chamado.setText(Id);
                chamadoView2.txt_titulo.setText(Titulo);
                chamadoView2.txt_chamado.setText(Conteudo);
                chamadoView2.lb_empresa.setText(Empresa);
                chamadoView2.lb_Funcionario.setText(Funcionario);
                chamadoView2.txt_solucao.setText(Solucao);
                chamadoView2.txt_conexao.setText(Conexao);
                chamadoView2.txt_tags.setText(Tags);
                chamadoView2.txt_usuario_log.setText(User);
                chamadoView2.lb_telefone.setText(Telefone);
                chamadoView2.lb_data_chamado.setText(data);
                chamadoView2.lb_hora.setText(hora);
                chamadoView2.txt_usuario_log.setText(" ");

                /*chamadoView2.setVisible(true);
                 chamadoView2.pack();
                 chamadoView2.setLocationRelativeTo(null);*/
                //Chamado_View2 chamado = new Chamado_View2();
                //chamado.setVisible(true);
                chamadoView2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            }
        });
        popupMenu.add(menuItem);
        tbl_Chamado.setComponentPopupMenu(popupMenu);
    }

    /*
     @Metodos para o PRO - Inicio
     */
    //Função para ativar botões do PRO dependendo da situação
    public void activa_boton(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f) {
        btnguardar.setEnabled(a);
        btnmodificar.setEnabled(b);
        btneliminar.setEnabled(c);
        btnnovo.setEnabled(e);
        txt_nome_pro.setEnabled(f);
        jButton12.setText("Selecionar...");
    }

    //Função para ativar botões do versão dependendo da situação
    public void ativa_botao_V(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, boolean i, boolean j, boolean k) {
        //ajustes tela Versões
        txt_V_nome.setEnabled(a);
        txt_V_dataRed.setEnabled(b);
        txt_V_ext.setEnabled(c);
        txt_V_horaRed.setEnabled(d);
        txt_V_tamanho.setEnabled(e);
        txt_V_versao.setEnabled(f);
        combo_V_versionW.setEnabled(g);

        btn_V_salvar.setEnabled(h);
        btn_V_editar.setEnabled(i);
        btn_V_excluir.setEnabled(j);
        btn_V_novo.setEnabled(k);
    }

    //Função para fazer um botão chamar o arquivo PDF
    public void seleccionar_pdf() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf", "pdf");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(this);
        if (se == 0) {
            this.jButton12.setText("" + j.getSelectedFile().getName());
            ruta_archivo = j.getSelectedFile().getAbsolutePath();

        } else {
        }
    }

    public void guardar_pdf(int codigo, String titulo, File rota, String conteudo) {
        ProDAO pa = new ProDAO();
        ProBean po = new ProBean();
        po.setId_pro(codigo);
        po.setTitulo_pro(titulo);
        po.setConteudo_pro(conteudo);
        try {
            byte[] pdf = new byte[(int) rota.length()];
            InputStream input = new FileInputStream(rota);
            input.read(pdf);
            po.setArquivopdf(pdf);
        } catch (IOException ex) {
            po.setArquivopdf(null);
            //System.out.println("Error al agregar archivo pdf "+ex.getMessage());
        }
        pa.Salvar_Pro(po);
    }

//    public class MeuRenderer implements TableCellRenderer {
//
//        public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
//        
//        @Override
//        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//    }
    public void eliminar_pdf(int codigo) {
        ProDAO pa = new ProDAO();
        ProBean po = new ProBean();
        po.setId_pro(codigo);
        pa.Excluir_Pro(po);
    }

    public void BotaoMenusSideBar(JButton button, String estado) {
        String Botao = button.toString();

        switch (estado) {
            case "clicado":
                switch (Botao) {

                    case "":
                        break;
                }
                break;

            case "DEF":
                //faz outra coisa
                break;

            default:
            //caso não for nenhum desses casos
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GpEmp = new javax.swing.ButtonGroup();
        GpPro = new javax.swing.ButtonGroup();
        PesquisaChamadoUser = new javax.swing.ButtonGroup();
        StatusChamado = new javax.swing.ButtonGroup();
        jProgressBar1 = new javax.swing.JProgressBar();
        BackgroundFrame = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        lbl_Cronometro = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        Lb_data = new javax.swing.JLabel();
        Lb_Hora = new javax.swing.JLabel();
        Lb_Data_Principal = new javax.swing.JLabel();
        Lb_Hora_Principal = new javax.swing.JLabel();
        lb_Foto = new javax.swing.JLabel();
        userlogado2 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        btnMaximize = new javax.swing.JButton();
        btnMinimize = new javax.swing.JButton();
        Menu = new javax.swing.JPanel();
        Btn_Chamado = new javax.swing.JButton();
        Btn_Agenda = new javax.swing.JButton();
        Btn_Cadastrar = new javax.swing.JButton();
        Btn_Relatorio = new javax.swing.JButton();
        Btn_PRO = new javax.swing.JButton();
        Btn_Sites1 = new javax.swing.JButton();
        Btn_Print = new javax.swing.JButton();
        btn_Config = new javax.swing.JButton();
        btn_RelUsers = new javax.swing.JButton();
        btn_countChamado = new javax.swing.JButton();
        Body = new javax.swing.JPanel();
        ChamadosView = new javax.swing.JPanel();
        Tab_Chamado = new javax.swing.JTabbedPane();
        Chamados = new javax.swing.JPanel();
        combo_emp = new javax.swing.JComboBox<>();
        combo_func = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Solucao = new javax.swing.JEditorPane();
        jPanel7 = new javax.swing.JPanel();
        txt_tag1 = new javax.swing.JTextField();
        txt_tag2 = new javax.swing.JTextField();
        txt_tag3 = new javax.swing.JTextField();
        txt_tag5 = new javax.swing.JTextField();
        txt_tag4 = new javax.swing.JTextField();
        btn_salvar_chamado = new javax.swing.JButton();
        Btn_Limpar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        rad_Aberto = new javax.swing.JRadioButton();
        rad_Fechado = new javax.swing.JRadioButton();
        jLabel55 = new javax.swing.JLabel();
        txt_titulo_chamado = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        DescricaoChamado = new javax.swing.JScrollPane();
        txt_chamado_desc1 = new javax.swing.JEditorPane();
        jLabel14 = new javax.swing.JLabel();
        txt_conexao = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        btnAnydesk = new javax.swing.JToggleButton();
        jLabel73 = new javax.swing.JLabel();
        txt_telefone_chamado2 = new javax.swing.JTextField();
        Tabela10Chamados = new javax.swing.JScrollPane();
        tbl_Chamado2 = new javax.swing.JTable();
        PanelDiaSemanaMEs = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        lb_Chamado_Dia = new javax.swing.JLabel();
        lb_Chamado_semana = new javax.swing.JLabel();
        lb_Chamado_mes = new javax.swing.JLabel();
        lb_chamado_dia_IMG = new javax.swing.JLabel();
        lb_chamado_semana_IMG = new javax.swing.JLabel();
        lb_Chamado_mes_IMG = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        Pesq_chammado = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_Pesquisa_Chamado = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        RadEmp = new javax.swing.JRadioButton();
        rad_Desc = new javax.swing.JRadioButton();
        rad_Clien = new javax.swing.JRadioButton();
        rad_Tags = new javax.swing.JRadioButton();
        rad_ID = new javax.swing.JRadioButton();
        rad_logicom = new javax.swing.JRadioButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Chamado = new javax.swing.JTable();
        Ramais = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_Ramais = new javax.swing.JTable();
        Servidores = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        combo_emp2 = new javax.swing.JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lb_e_nomeEmpresa = new javax.swing.JLabel();
        lb_e_Tel_empresa = new javax.swing.JLabel();
        lb_e_Cnpj_emp = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lb_e_Ip_scef = new javax.swing.JLabel();
        lb_e_mac_scef = new javax.swing.JLabel();
        lb_e_Ip_Nfce = new javax.swing.JLabel();
        lb_e_Ip_Nfe = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        lb_e_Mac_nfe = new javax.swing.JLabel();
        lb_e_Mac_nfce = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        lb_e_Mobile = new javax.swing.JLabel();
        lb_e_coletor = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        VrEstoque = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        lb_e_Banco_Est = new javax.swing.JLabel();
        VrNfce = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        lb_e_Banco_Nfce = new javax.swing.JLabel();
        VrNfce1 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        lb_e_Banco_Nfe = new javax.swing.JLabel();
        VrNfce2 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        lb_e_Banco_Sisseg = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        lb_e_Banco_Checkout = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_empresa = new javax.swing.JTable();
        AgendaView = new javax.swing.JPanel();
        jp_cadAgenda = new javax.swing.JPanel();
        txt_nome_agenda = new javax.swing.JTextField();
        txt_endereco_agenda = new javax.swing.JTextField();
        txt_observacao_agenda = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btn_salvar_agenda = new javax.swing.JButton();
        btn_excluir_agenda = new javax.swing.JButton();
        btn_atualizar_agenda = new javax.swing.JButton();
        novo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_email_agenda = new javax.swing.JTextField();
        txt_telefone_agenda = new javax.swing.JTextField();
        jp_TabelaAgenda = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_agenda = new javax.swing.JTable();
        jp_Pesquisa = new javax.swing.JPanel();
        txt_pesquisa_agenda = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel57 = new javax.swing.JLabel();
        VersoesView = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_V_nome = new javax.swing.JTextField();
        btn_V_salvar = new javax.swing.JButton();
        btn_V_editar = new javax.swing.JButton();
        btn_V_excluir = new javax.swing.JButton();
        btn_V_novo = new javax.swing.JButton();
        txt_V_versao = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        combo_V_versionW = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        txt_V_ext = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txt_V_dataRed = new javax.swing.JFormattedTextField();
        jLabel34 = new javax.swing.JLabel();
        txt_V_horaRed = new javax.swing.JFormattedTextField();
        jLabel59 = new javax.swing.JLabel();
        txt_V_tamanho = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txt_pesquisa_versao = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_version = new javax.swing.JTable();
        SitesView = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        CadFunc = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btn_CadEmp = new javax.swing.JButton();
        btn_CadFunc = new javax.swing.JButton();
        btn_cadUser = new javax.swing.JButton();
        btn_CadPro = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btn_CadRamal = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        Perfil = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jt_Painel_User_Chamado = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        txt_Puser_dataIni = new javax.swing.JTextField();
        txt_Puser_dataFim = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txt_Puser_Pesquisar = new javax.swing.JTextField();
        radEmpUser = new javax.swing.JRadioButton();
        radFuncUser = new javax.swing.JRadioButton();
        radDescUser = new javax.swing.JRadioButton();
        radClienteUser = new javax.swing.JRadioButton();
        radTagsUser = new javax.swing.JRadioButton();
        btnPesquisarUser = new javax.swing.JButton();
        btnConverteExcel = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        ProView2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        PanelListaPRo = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tbl_pro = new javax.swing.JTable();
        jSeparator5 = new javax.swing.JSeparator();
        PanelCadastrarPRo = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txt_nome_pro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txt_conteudoPRo = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnnovo = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txt_pesquisar = new javax.swing.JTextField();
        radTItPro = new javax.swing.JRadioButton();
        radContPro = new javax.swing.JRadioButton();
        jButton12 = new javax.swing.JButton();
        Config = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        lb_1 = new javax.swing.JLabel();
        lb_2 = new javax.swing.JLabel();
        lb_3 = new javax.swing.JLabel();
        lb_4 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lb_Conf_Livre = new javax.swing.JLabel();
        lb_Conf_Total = new javax.swing.JLabel();
        lb_Conf_Usado = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        lb_Conf_MLivre = new javax.swing.JLabel();
        lb_Conf_MUsada = new javax.swing.JLabel();
        lb_Conf_MTotal = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        lb_Ip = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        lb_mac = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        btnBuscaFoto = new javax.swing.JButton();
        btnSalvaFoto = new javax.swing.JButton();
        lbl_Image = new javax.swing.JLabel();
        lbl_TitImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        BackgroundFrame.setBackground(new java.awt.Color(19, 29, 38));

        Header.setBackground(new java.awt.Color(19, 29, 38));
        Header.setPreferredSize(new java.awt.Dimension(149, 25));
        Header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                HeaderMouseDragged(evt);
            }
        });
        Header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                HeaderMousePressed(evt);
            }
        });

        lbl_Cronometro.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lbl_Cronometro.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Cronometro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Cronometro.setText("00:00:00");
        lbl_Cronometro.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Usuário:");

        Lb_data.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Lb_data.setForeground(new java.awt.Color(255, 255, 255));
        Lb_data.setText("Data:");

        Lb_Hora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Lb_Hora.setForeground(new java.awt.Color(255, 255, 255));
        Lb_Hora.setText("Hora:");

        Lb_Data_Principal.setBackground(new java.awt.Color(255, 255, 255));
        Lb_Data_Principal.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        Lb_Data_Principal.setForeground(new java.awt.Color(255, 255, 255));

        Lb_Hora_Principal.setBackground(new java.awt.Color(255, 255, 255));
        Lb_Hora_Principal.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        Lb_Hora_Principal.setForeground(new java.awt.Color(255, 255, 255));

        lb_Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/User.png"))); // NOI18N

        userlogado2.setBackground(new java.awt.Color(255, 102, 0));
        userlogado2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        userlogado2.setForeground(new java.awt.Color(255, 102, 0));
        userlogado2.setText("Admin");

        btnExit.setBackground(new java.awt.Color(19, 29, 38));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Exit.png"))); // NOI18N
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(true);
        btnExit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Exit (2).png"))); // NOI18N
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExitMouseExited(evt);
            }
        });
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnMaximize.setBackground(new java.awt.Color(19, 29, 38));
        btnMaximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Maximize.png"))); // NOI18N
        btnMaximize.setContentAreaFilled(false);
        btnMaximize.setOpaque(true);
        btnMaximize.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Maximize (2).png"))); // NOI18N
        btnMaximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseExited(evt);
            }
        });
        btnMaximize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaximizeActionPerformed(evt);
            }
        });

        btnMinimize.setBackground(new java.awt.Color(19, 29, 38));
        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Minimize.png"))); // NOI18N
        btnMinimize.setContentAreaFilled(false);
        btnMinimize.setFocusable(false);
        btnMinimize.setOpaque(true);
        btnMinimize.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Minimize (2).png"))); // NOI18N
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseExited(evt);
            }
        });
        btnMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(lb_Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Lb_Hora)
                    .addComponent(jLabel27)
                    .addComponent(Lb_data))
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Lb_Data_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userlogado2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Lb_Hora_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lbl_Cronometro, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addGap(153, 153, 153)
                .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        HeaderLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnExit, btnMaximize, btnMinimize});

        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lb_Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(userlogado2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Lb_Data_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Lb_data))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Lb_Hora)
                            .addComponent(Lb_Hora_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addComponent(lbl_Cronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        HeaderLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnExit, btnMaximize, btnMinimize});

        Menu.setBackground(new java.awt.Color(19, 29, 38));
        Menu.setPreferredSize(new java.awt.Dimension(60, 100));

        Btn_Chamado.setBackground(new java.awt.Color(204, 153, 0));
        Btn_Chamado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_chamado_orange.png"))); // NOI18N
        Btn_Chamado.setToolTipText("Chamados");
        Btn_Chamado.setBorderPainted(false);
        Btn_Chamado.setContentAreaFilled(false);
        Btn_Chamado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Chamado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_chamado_orange.png"))); // NOI18N
        Btn_Chamado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_ChamadoMouseClicked(evt);
            }
        });
        Btn_Chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ChamadoActionPerformed(evt);
            }
        });

        Btn_Agenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png"))); // NOI18N
        Btn_Agenda.setToolTipText("Agenda");
        Btn_Agenda.setBorderPainted(false);
        Btn_Agenda.setContentAreaFilled(false);
        Btn_Agenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Agenda.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_agenda_orange.png"))); // NOI18N
        Btn_Agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_AgendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_AgendaMouseExited(evt);
            }
        });
        Btn_Agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AgendaActionPerformed(evt);
            }
        });

        Btn_Cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png"))); // NOI18N
        Btn_Cadastrar.setToolTipText("Cadastrar");
        Btn_Cadastrar.setBorderPainted(false);
        Btn_Cadastrar.setContentAreaFilled(false);
        Btn_Cadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Cadastrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar_Orange.png"))); // NOI18N
        Btn_Cadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_CadastrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_CadastrarMouseExited(evt);
            }
        });
        Btn_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_CadastrarActionPerformed(evt);
            }
        });

        Btn_Relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png"))); // NOI18N
        Btn_Relatorio.setToolTipText("Relatorios");
        Btn_Relatorio.setBorderPainted(false);
        Btn_Relatorio.setContentAreaFilled(false);
        Btn_Relatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Relatorio.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_relatorio_Relatorio.png"))); // NOI18N
        Btn_Relatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_RelatorioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_RelatorioMouseExited(evt);
            }
        });
        Btn_Relatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_RelatorioActionPerformed(evt);
            }
        });

        Btn_PRO.setBackground(new java.awt.Color(102, 102, 102));
        Btn_PRO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png"))); // NOI18N
        Btn_PRO.setToolTipText("P.R.Os");
        Btn_PRO.setBorderPainted(false);
        Btn_PRO.setContentAreaFilled(false);
        Btn_PRO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_PRO.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Pro_Orange.png"))); // NOI18N
        Btn_PRO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_PROMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_PROMouseExited(evt);
            }
        });
        Btn_PRO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_PROActionPerformed(evt);
            }
        });

        Btn_Sites1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_sites.png"))); // NOI18N
        Btn_Sites1.setToolTipText("Sites");
        Btn_Sites1.setBorderPainted(false);
        Btn_Sites1.setContentAreaFilled(false);
        Btn_Sites1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Sites1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_sites_Orange.png"))); // NOI18N
        Btn_Sites1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_Sites1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_Sites1MouseExited(evt);
            }
        });
        Btn_Sites1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_Sites1ActionPerformed(evt);
            }
        });

        Btn_Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png"))); // NOI18N
        Btn_Print.setToolTipText("PrintScreen");
        Btn_Print.setBorderPainted(false);
        Btn_Print.setContentAreaFilled(false);
        Btn_Print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Print.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/photo-camera2.png"))); // NOI18N
        Btn_Print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_PrintMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_PrintMouseExited(evt);
            }
        });
        Btn_Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_PrintActionPerformed(evt);
            }
        });

        btn_Config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png"))); // NOI18N
        btn_Config.setToolTipText("Configuração");
        btn_Config.setContentAreaFilled(false);
        btn_Config.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Config.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/btn_config - laranja - 35x35.png"))); // NOI18N
        btn_Config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ConfigActionPerformed(evt);
            }
        });

        btn_RelUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png"))); // NOI18N
        btn_RelUsers.setToolTipText("Painel de Controle");
        btn_RelUsers.setContentAreaFilled(false);
        btn_RelUsers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_RelUsers.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_02.png"))); // NOI18N
        btn_RelUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RelUsersActionPerformed(evt);
            }
        });

        btn_countChamado.setBackground(new java.awt.Color(0, 102, 102));
        btn_countChamado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_countChamado.setText("0");
        btn_countChamado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_countChamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_countChamadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btn_Config, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Btn_Chamado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Agenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Cadastrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Relatorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_PRO, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Sites1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_Print, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_RelUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_countChamado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Btn_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Btn_Agenda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(Btn_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Btn_Relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Btn_PRO)
                .addGap(15, 15, 15)
                .addComponent(Btn_Sites1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Btn_Print, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Config, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_RelUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_countChamado, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Body.setLayout(new java.awt.CardLayout());

        ChamadosView.setPreferredSize(new java.awt.Dimension(872, 400));

        Tab_Chamado.setPreferredSize(new java.awt.Dimension(825, 771));

        Chamados.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Chamados.setOpaque(false);

        combo_emp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione Empresa" }));
        combo_emp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_empItemStateChanged(evt);
            }
        });
        combo_emp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                combo_empFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                combo_empFocusLost(evt);
            }
        });
        combo_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_empMouseClicked(evt);
            }
        });

        combo_func.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar Cliente" }));
        combo_func.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_funcItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel1.setText("Empresa:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel7.setText("Cliente:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel8.setText("Telefone:");

        jScrollPane2.setViewportView(txt_Solucao);

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_tag1.setToolTipText("Coloque aqui sua tag para uma melhor pesquisa.");
        jPanel7.add(txt_tag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));

        txt_tag2.setToolTipText("Coloque aqui sua tag para uma melhor pesquisa.");
        jPanel7.add(txt_tag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, -1));

        txt_tag3.setToolTipText("Coloque aqui sua tag para uma melhor pesquisa.");
        jPanel7.add(txt_tag3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, -1));

        txt_tag5.setToolTipText("Coloque aqui sua tag para uma melhor pesquisa.");
        jPanel7.add(txt_tag5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 80, -1));

        txt_tag4.setToolTipText("Coloque aqui sua tag para uma melhor pesquisa.");
        jPanel7.add(txt_tag4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 80, -1));

        btn_salvar_chamado.setText("Salvar");
        btn_salvar_chamado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvar_chamado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salvar_chamadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salvar_chamadoMouseExited(evt);
            }
        });
        btn_salvar_chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_chamadoActionPerformed(evt);
            }
        });
        jPanel7.add(btn_salvar_chamado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 80, -1));

        Btn_Limpar.setText("Limpar");
        Btn_Limpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Limpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_LimparMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_LimparMouseExited(evt);
            }
        });
        Btn_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_LimparActionPerformed(evt);
            }
        });
        jPanel7.add(Btn_Limpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 80, -1));
        jPanel7.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 100, 10));

        StatusChamado.add(rad_Aberto);
        rad_Aberto.setText("Aberto");
        jPanel7.add(rad_Aberto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, -1));

        StatusChamado.add(rad_Fechado);
        rad_Fechado.setText("Fechado");
        jPanel7.add(rad_Fechado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel55.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel55.setText("Status Chamado");
        jPanel7.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        txt_titulo_chamado.setText("Digite o titulo do Chamado...");
        txt_titulo_chamado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_titulo_chamadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_titulo_chamadoFocusLost(evt);
            }
        });
        txt_titulo_chamado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_titulo_chamadoMouseClicked(evt);
            }
        });
        txt_titulo_chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_titulo_chamadoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 51));
        jLabel9.setText("*");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("*");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 51));
        jLabel11.setText("*");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 51));
        jLabel12.setText("*");

        jLabel13.setText("Descrição do Chamado:");

        DescricaoChamado.setViewportView(txt_chamado_desc1);

        jLabel14.setText("Solução do problema:");

        txt_conexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_conexaoActionPerformed(evt);
            }
        });
        txt_conexao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_conexaoKeyPressed(evt);
            }
        });

        jLabel15.setText("Conexão:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/emp_02.png"))); // NOI18N
        jButton1.setToolTipText("Cadastrar Empresa");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/emp_03.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/emp_01.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tags:");

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/User_03.png"))); // NOI18N
        jButton11.setToolTipText("Cadastrar Funcionario");
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/User_02.png"))); // NOI18N
        jButton11.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/User_01.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        btnAnydesk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/anydesk bw.png"))); // NOI18N
        btnAnydesk.setToolTipText("AnyDesk");
        btnAnydesk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnydesk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnydeskActionPerformed(evt);
            }
        });

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 0, 51));
        jLabel73.setText("*");

        txt_telefone_chamado2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telefone_chamado2KeyReleased(evt);
            }
        });

        Tabela10Chamados.setAutoscrolls(true);
        Tabela10Chamados.setOpaque(false);

        tbl_Chamado2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Titulo", "Descrição", "Solução", "Empresa", "Cliente", "Tags", "Telefone", "Conexão", "Usuario Logicom", "Data Chamado", "Hora Chamado", "Status", "Editado", "Data Editado", "Hora Editado", "Cronometro", "Hora Fechado", "Data Fechado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabela10Chamados.setViewportView(tbl_Chamado2);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_Chamado_Dia.setBackground(new java.awt.Color(51, 51, 51));
        lb_Chamado_Dia.setFont(new java.awt.Font("Serif", 3, 36)); // NOI18N
        lb_Chamado_Dia.setForeground(new java.awt.Color(255, 255, 255));
        lb_Chamado_Dia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_Chamado_Dia.setText("0");
        lb_Chamado_Dia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lb_Chamado_Dia, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 47, 70, 30));

        lb_Chamado_semana.setBackground(new java.awt.Color(51, 51, 51));
        lb_Chamado_semana.setFont(new java.awt.Font("Serif", 3, 36)); // NOI18N
        lb_Chamado_semana.setForeground(new java.awt.Color(255, 255, 255));
        lb_Chamado_semana.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_Chamado_semana.setText("0");
        lb_Chamado_semana.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lb_Chamado_semana, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 157, 60, 30));

        lb_Chamado_mes.setBackground(new java.awt.Color(51, 51, 51));
        lb_Chamado_mes.setFont(new java.awt.Font("Serif", 3, 36)); // NOI18N
        lb_Chamado_mes.setForeground(new java.awt.Color(255, 255, 255));
        lb_Chamado_mes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_Chamado_mes.setText("0");
        lb_Chamado_mes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(lb_Chamado_mes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 277, 60, 30));

        lb_chamado_dia_IMG.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lb_chamado_dia_IMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Sem Título-1 copiar.png"))); // NOI18N
        lb_chamado_dia_IMG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lb_chamado_dia_IMG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_chamado_dia_IMGMouseClicked(evt);
            }
        });
        jPanel1.add(lb_chamado_dia_IMG, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 27, -1, -1));

        lb_chamado_semana_IMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Sem Título-2 copiar.png"))); // NOI18N
        lb_chamado_semana_IMG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lb_chamado_semana_IMG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_chamado_semana_IMGMouseClicked(evt);
            }
        });
        jPanel1.add(lb_chamado_semana_IMG, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 137, -1, -1));

        lb_Chamado_mes_IMG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Sem Título-3 copiar.png"))); // NOI18N
        lb_Chamado_mes_IMG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lb_Chamado_mes_IMG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_Chamado_mes_IMGMouseClicked(evt);
            }
        });
        jPanel1.add(lb_Chamado_mes_IMG, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 257, -1, 80));

        PanelDiaSemanaMEs.setViewportView(jPanel1);

        jTextField1.setText("OBS:");

        javax.swing.GroupLayout ChamadosLayout = new javax.swing.GroupLayout(Chamados);
        Chamados.setLayout(ChamadosLayout);
        ChamadosLayout.setHorizontalGroup(
            ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChamadosLayout.createSequentialGroup()
                .addComponent(Tabela10Chamados)
                .addGap(62, 62, 62))
            .addGroup(ChamadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChamadosLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(combo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(13, 13, 13)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(combo_func, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txt_telefone_chamado2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(txt_conexao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAnydesk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14)
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_titulo_chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addComponent(DescricaoChamado, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChamadosLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ChamadosLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelDiaSemanaMEs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ChamadosLayout.setVerticalGroup(
            ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChamadosLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChamadosLayout.createSequentialGroup()
                        .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel10))
                                .addGap(5, 5, 5)
                                .addComponent(combo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButton1))
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel73))
                                .addGap(6, 6, 6)
                                .addComponent(combo_func, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ChamadosLayout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel8))
                                    .addComponent(jLabel11))
                                .addGap(6, 6, 6)
                                .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_telefone_chamado2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_conexao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel15))
                            .addComponent(btnAnydesk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addComponent(txt_titulo_chamado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel13)
                        .addGap(6, 6, 6)
                        .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(DescricaoChamado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addGap(6, 6, 6)
                        .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ChamadosLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(ChamadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PanelDiaSemanaMEs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ChamadosLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tabela10Chamados, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
        );

        Tab_Chamado.addTab("Chamados", Chamados);

        Pesq_chammado.setOpaque(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Campos de busca - Chamado"));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(1233, 500));

        jLabel17.setText("Nome:");

        txt_Pesquisa_Chamado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_Pesquisa_ChamadoKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar por:"));

        GpEmp.add(RadEmp);
        RadEmp.setText("Empresa");

        GpEmp.add(rad_Desc);
        rad_Desc.setText("Descrição");

        GpEmp.add(rad_Clien);
        rad_Clien.setText("Cliente");

        GpEmp.add(rad_Tags);
        rad_Tags.setText("Tags");

        GpEmp.add(rad_ID);
        rad_ID.setText("ID");

        GpEmp.add(rad_logicom);
        rad_logicom.setText("Logicom");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(RadEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rad_Desc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rad_Clien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rad_Tags)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rad_ID)
                .addGap(18, 18, 18)
                .addComponent(rad_logicom)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(RadEmp)
                .addComponent(rad_Desc)
                .addComponent(rad_Clien)
                .addComponent(rad_Tags)
                .addComponent(rad_ID)
                .addComponent(rad_logicom))
        );

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setOpaque(false);

        tbl_Chamado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Titulo", "Descrição", "Solução", "Empresa", "Cliente", "Tags", "Telefone", "Conexão", "Logicom", "Data Chamado", "Hora Chamado", "Status", "Editado", "Data Editado", "Hora Editado", "Cronometro", "Hora Fechado", "Data Fechado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Chamado.setMaximumSize(new java.awt.Dimension(900, 0));
        tbl_Chamado.setOpaque(false);
        jScrollPane1.setViewportView(tbl_Chamado);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txt_Pesquisa_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Pesquisa_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Pesq_chammadoLayout = new javax.swing.GroupLayout(Pesq_chammado);
        Pesq_chammado.setLayout(Pesq_chammadoLayout);
        Pesq_chammadoLayout.setHorizontalGroup(
            Pesq_chammadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pesq_chammadoLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        Pesq_chammadoLayout.setVerticalGroup(
            Pesq_chammadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pesq_chammadoLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addGap(316, 316, 316))
        );

        Tab_Chamado.addTab("Pesquisa Chamados", Pesq_chammado);

        Ramais.setOpaque(false);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Listas de Ramais da empresa"));

        tbl_Ramais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Ramal"
            }
        ));
        jScrollPane3.setViewportView(tbl_Ramais);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 431, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RamaisLayout = new javax.swing.GroupLayout(Ramais);
        Ramais.setLayout(RamaisLayout);
        RamaisLayout.setHorizontalGroup(
            RamaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RamaisLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        RamaisLayout.setVerticalGroup(
            RamaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Tab_Chamado.addTab("Lista de ramais", Ramais);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Empresas"));

        combo_emp2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empresas" }));
        combo_emp2.setOpaque(false);
        combo_emp2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_emp2ItemStateChanged(evt);
            }
        });
        combo_emp2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_emp2MouseClicked(evt);
            }
        });
        combo_emp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_emp2ActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da empresa"));
        jPanel10.setPreferredSize(new java.awt.Dimension(800, 319));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Empresa:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Telefone:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Cnpj:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Observação:");

        lb_e_nomeEmpresa.setText("Economico Castanhal");

        lb_e_Tel_empresa.setText("(91) 98218-4311");

        lb_e_Cnpj_emp.setText("04.718.807/0001-15");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Ip Scef:");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Mac Scef:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Ip Nfce:");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Ip Nfe:");

        lb_e_Ip_scef.setText("192.168.0.100");

        lb_e_mac_scef.setText("B0-5A-DA-56-73-68");

        lb_e_Ip_Nfce.setText("192.168.0.180");

        lb_e_Ip_Nfe.setText("192.168.0.35");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Serviço Mobile:");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setText("Serviço Coletor:");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setText("Mac Nfe:");

        lb_e_Mac_nfe.setText("B0-5A-DA-56-73-68");

        lb_e_Mac_nfce.setText("B0-5A-DA-56-73-68");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("Mac Nfce:");

        lb_e_Mobile.setText("192.168.0.35");

        lb_e_coletor.setText("192.168.0.35");

        VrEstoque.setBackground(new java.awt.Color(255, 255, 255));
        VrEstoque.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VrEstoque.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel56.setBackground(new java.awt.Color(204, 204, 0));
        jLabel56.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Versão Estoque");
        jLabel56.setOpaque(true);
        VrEstoque.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 121, 40));

        lb_e_Banco_Est.setFont(new java.awt.Font("Serif", 3, 24)); // NOI18N
        lb_e_Banco_Est.setForeground(new java.awt.Color(51, 0, 204));
        lb_e_Banco_Est.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_e_Banco_Est.setText("80");
        VrEstoque.add(lb_e_Banco_Est, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, 40));

        VrNfce.setBackground(new java.awt.Color(255, 255, 255));
        VrNfce.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VrNfce.setPreferredSize(new java.awt.Dimension(137, 114));
        VrNfce.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel60.setBackground(new java.awt.Color(102, 0, 102));
        jLabel60.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Versão Nfce");
        jLabel60.setOpaque(true);
        VrNfce.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 121, 40));

        lb_e_Banco_Nfce.setFont(new java.awt.Font("Serif", 3, 24)); // NOI18N
        lb_e_Banco_Nfce.setForeground(new java.awt.Color(51, 0, 204));
        lb_e_Banco_Nfce.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_e_Banco_Nfce.setText("80");
        VrNfce.add(lb_e_Banco_Nfce, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, 40));

        VrNfce1.setBackground(new java.awt.Color(255, 255, 255));
        VrNfce1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VrNfce1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel62.setBackground(new java.awt.Color(102, 102, 255));
        jLabel62.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Versão Nfe");
        jLabel62.setOpaque(true);
        VrNfce1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 121, 40));

        lb_e_Banco_Nfe.setFont(new java.awt.Font("Serif", 3, 24)); // NOI18N
        lb_e_Banco_Nfe.setForeground(new java.awt.Color(51, 0, 204));
        lb_e_Banco_Nfe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_e_Banco_Nfe.setText("80");
        VrNfce1.add(lb_e_Banco_Nfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, 40));

        VrNfce2.setBackground(new java.awt.Color(255, 255, 255));
        VrNfce2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        VrNfce2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setBackground(new java.awt.Color(255, 153, 0));
        jLabel64.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Versão Sisseg");
        jLabel64.setOpaque(true);
        VrNfce2.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 121, 40));

        lb_e_Banco_Sisseg.setFont(new java.awt.Font("Serif", 3, 24)); // NOI18N
        lb_e_Banco_Sisseg.setForeground(new java.awt.Color(51, 0, 204));
        lb_e_Banco_Sisseg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_e_Banco_Sisseg.setText("20");
        VrNfce2.add(lb_e_Banco_Sisseg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, 40));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel17.setPreferredSize(new java.awt.Dimension(116, 90));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setBackground(new java.awt.Color(51, 153, 255));
        jLabel66.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("Versão CheckOut");
        jLabel66.setOpaque(true);
        jPanel17.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 120, 40));

        lb_e_Banco_Checkout.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        lb_e_Banco_Checkout.setForeground(new java.awt.Color(51, 0, 204));
        lb_e_Banco_Checkout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_e_Banco_Checkout.setText("58");
        jPanel17.add(lb_e_Banco_Checkout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, 40));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(VrEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(VrNfce, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(VrNfce1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(VrNfce2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(VrNfce, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VrEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VrNfce1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VrNfce2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_e_nomeEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(lb_e_Tel_empresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel36)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_e_Cnpj_emp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(18, 18, 18)
                        .addComponent(lb_e_Ip_scef, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_e_mac_scef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(18, 18, 18)
                        .addComponent(lb_e_Ip_Nfce, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_e_Mac_nfe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(18, 18, 18)
                        .addComponent(lb_e_Ip_Nfe, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_e_Mac_nfce, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_e_Mobile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_e_coletor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(lb_e_nomeEmpresa))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(lb_e_Tel_empresa))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel35)
                                .addComponent(lb_e_Cnpj_emp))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel36))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel43)
                                .addComponent(lb_e_Ip_Nfe))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel53)
                                .addComponent(lb_e_Mac_nfce))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel48)
                                .addComponent(lb_e_Mobile))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel49)
                                .addComponent(lb_e_coletor))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(lb_e_Ip_scef))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(lb_e_mac_scef))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(lb_e_Ip_Nfce))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(lb_e_Mac_nfe))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbl_empresa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Telefone", "Cnpj", "Obs", "IP Scef", "Mac Scef", "Conexão Scef", "IP NFCE", "Mac NFCE", "Conexão NFCE", "IP NFE", "Mac NFE", "Conexão NFE", "Mobile", "Coletor", "V. Estoque", "V. Nfce", "V. Nfe", "V. Sisseg", "V. CheckOut"
            }
        ));
        tbl_empresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_empresaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_empresa);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(combo_emp2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator3))
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_emp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ServidoresLayout = new javax.swing.GroupLayout(Servidores);
        Servidores.setLayout(ServidoresLayout);
        ServidoresLayout.setHorizontalGroup(
            ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServidoresLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ServidoresLayout.setVerticalGroup(
            ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServidoresLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        Tab_Chamado.addTab("Servidores clientes", Servidores);

        javax.swing.GroupLayout ChamadosViewLayout = new javax.swing.GroupLayout(ChamadosView);
        ChamadosView.setLayout(ChamadosViewLayout);
        ChamadosViewLayout.setHorizontalGroup(
            ChamadosViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 885, Short.MAX_VALUE)
            .addGroup(ChamadosViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ChamadosViewLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Tab_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 865, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        ChamadosViewLayout.setVerticalGroup(
            ChamadosViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 741, Short.MAX_VALUE)
            .addGroup(ChamadosViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ChamadosViewLayout.createSequentialGroup()
                    .addComponent(Tab_Chamado, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        Body.add(ChamadosView, "card2");

        AgendaView.setPreferredSize(new java.awt.Dimension(856, 700));

        jp_cadAgenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Agenda"));

        txt_nome_agenda.setText("Digite o Nome....");
        txt_nome_agenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nome_agendaFocusLost(evt);
            }
        });
        txt_nome_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nome_agendaMouseClicked(evt);
            }
        });
        txt_nome_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nome_agendaActionPerformed(evt);
            }
        });

        txt_observacao_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_observacao_agendaActionPerformed(evt);
            }
        });

        jLabel18.setText("Nome:");

        jLabel21.setText("Observação:");

        jLabel22.setText("Endereço:");

        jLabel23.setText("Telefone:");

        btn_salvar_agenda.setBackground(new java.awt.Color(0, 102, 102));
        btn_salvar_agenda.setText("Salvar");
        btn_salvar_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salvar_agendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salvar_agendaMouseExited(evt);
            }
        });
        btn_salvar_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_agendaActionPerformed(evt);
            }
        });

        btn_excluir_agenda.setText("Excluir");
        btn_excluir_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_excluir_agendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_excluir_agendaMouseExited(evt);
            }
        });
        btn_excluir_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excluir_agendaActionPerformed(evt);
            }
        });

        btn_atualizar_agenda.setText("Editar");
        btn_atualizar_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_atualizar_agendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_atualizar_agendaMouseExited(evt);
            }
        });
        btn_atualizar_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizar_agendaActionPerformed(evt);
            }
        });

        novo.setText("Novo");
        novo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                novoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                novoMouseExited(evt);
            }
        });
        novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novoActionPerformed(evt);
            }
        });

        jLabel3.setText("Email:");

        javax.swing.GroupLayout jp_cadAgendaLayout = new javax.swing.GroupLayout(jp_cadAgenda);
        jp_cadAgenda.setLayout(jp_cadAgendaLayout);
        jp_cadAgendaLayout.setHorizontalGroup(
            jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                        .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel18)
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_cadAgendaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel23)))
                        .addGap(4, 4, 4)
                        .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nome_agenda, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(txt_telefone_agenda))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_cadAgendaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(11, 11, 11)
                                .addComponent(txt_endereco_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                                .addComponent(btn_salvar_agenda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_atualizar_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                                .addComponent(btn_excluir_agenda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(novo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 65, Short.MAX_VALUE))
                    .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_observacao_agenda)))
                .addContainerGap())
        );
        jp_cadAgendaLayout.setVerticalGroup(
            jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_cadAgendaLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txt_nome_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txt_email_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_salvar_agenda)
                        .addComponent(btn_atualizar_agenda)))
                .addGap(4, 4, 4)
                .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_endereco_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_excluir_agenda)
                        .addComponent(novo))
                    .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txt_telefone_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jp_cadAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_observacao_agenda, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jLabel21)))
        );

        jp_TabelaAgenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Contatos"));

        tbl_agenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nome", "Email", "Telefone", "Endereço", "Observação"
            }
        ));
        tbl_agenda.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbl_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_agendaMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_agenda);

        javax.swing.GroupLayout jp_TabelaAgendaLayout = new javax.swing.GroupLayout(jp_TabelaAgenda);
        jp_TabelaAgenda.setLayout(jp_TabelaAgendaLayout);
        jp_TabelaAgendaLayout.setHorizontalGroup(
            jp_TabelaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_TabelaAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
                .addContainerGap())
        );
        jp_TabelaAgendaLayout.setVerticalGroup(
            jp_TabelaAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_TabelaAgendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        jp_Pesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        txt_pesquisa_agenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisa_agendaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jp_PesquisaLayout = new javax.swing.GroupLayout(jp_Pesquisa);
        jp_Pesquisa.setLayout(jp_PesquisaLayout);
        jp_PesquisaLayout.setHorizontalGroup(
            jp_PesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_PesquisaLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txt_pesquisa_agenda)
                .addContainerGap())
        );
        jp_PesquisaLayout.setVerticalGroup(
            jp_PesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_PesquisaLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(txt_pesquisa_agenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel57.setBackground(new java.awt.Color(51, 51, 51));
        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Agenda");
        jLabel57.setOpaque(true);

        javax.swing.GroupLayout AgendaViewLayout = new javax.swing.GroupLayout(AgendaView);
        AgendaView.setLayout(AgendaViewLayout);
        AgendaViewLayout.setHorizontalGroup(
            AgendaViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AgendaViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AgendaViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jp_cadAgenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_Pesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgendaViewLayout.createSequentialGroup()
                        .addGroup(AgendaViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jp_TabelaAgenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        AgendaViewLayout.setVerticalGroup(
            AgendaViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgendaViewLayout.createSequentialGroup()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_TabelaAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jp_cadAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_Pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Body.add(AgendaView, "card3");

        VersoesView.setPreferredSize(new java.awt.Dimension(1205, 659));

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Versões");
        jLabel4.setOpaque(true);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastro Versão"));

        jLabel5.setText("Nome:");

        btn_V_salvar.setText("Cadastrar");
        btn_V_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_salvarActionPerformed(evt);
            }
        });

        btn_V_editar.setText("Editar");

        btn_V_excluir.setText("Excluir");

        btn_V_novo.setText("Novo");
        btn_V_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_V_novoActionPerformed(evt);
            }
        });

        jLabel31.setText("Versão:");

        combo_V_versionW.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "32", "64", "32/64" }));

        jLabel32.setText("V. Windows:");

        jLabel33.setText("Extensão:");

        try {
            txt_V_dataRed.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel34.setText("Data Redmine:");

        try {
            txt_V_horaRed.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel59.setText("Hora Redmine:");

        jLabel58.setText("Tamanho:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txt_V_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_V_versao)
                            .addComponent(jLabel31))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_V_versionW, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel34))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txt_V_ext, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_V_dataRed, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_V_horaRed, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_V_tamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btn_V_salvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_V_editar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_V_excluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_V_novo)
                        .addGap(18, 18, 18)
                        .addComponent(txt_pesquisa_versao)))
                .addGap(131, 131, 131))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_V_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_V_horaRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_V_tamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_V_dataRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_V_ext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(combo_V_versionW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_V_versao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel59)
                                .addComponent(jLabel58)
                                .addComponent(jLabel34)
                                .addComponent(jLabel33)
                                .addComponent(jLabel32))
                            .addGap(26, 26, 26))))
                .addGap(0, 30, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_V_salvar)
                    .addComponent(btn_V_editar)
                    .addComponent(btn_V_excluir)
                    .addComponent(btn_V_novo)
                    .addComponent(txt_pesquisa_versao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbl_version.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Versão", "V. Windows", "Extensão", "Data Redmine", "Hora Redmine", "Tamanho", "Usuario", "Atualizado"
            }
        ));
        tbl_version.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_versionMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbl_version);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout VersoesViewLayout = new javax.swing.GroupLayout(VersoesView);
        VersoesView.setLayout(VersoesViewLayout);
        VersoesViewLayout.setHorizontalGroup(
            VersoesViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(VersoesViewLayout.createSequentialGroup()
                .addGroup(VersoesViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VersoesViewLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        VersoesViewLayout.setVerticalGroup(
            VersoesViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VersoesViewLayout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Body.add(VersoesView, "card5");

        SitesView.setPreferredSize(new java.awt.Dimension(1205, 659));

        jLabel61.setBackground(new java.awt.Color(51, 51, 51));
        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("Sites Uteis");
        jLabel61.setOpaque(true);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_sintegra.png"))); // NOI18N
        jButton10.setContentAreaFilled(false);
        jButton10.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_sintegra2.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_nfce_150px.png"))); // NOI18N
        jButton8.setContentAreaFilled(false);
        jButton8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_nfce2_150px.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_nfe_150px.png"))); // NOI18N
        jButton7.setContentAreaFilled(false);
        jButton7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_nfe2_150px.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_webmail1.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_webmail2.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_redmine1.png"))); // NOI18N
        jButton6.setContentAreaFilled(false);
        jButton6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_redmine2.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_xmlRs1.png"))); // NOI18N
        jButton9.setContentAreaFilled(false);
        jButton9.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_xmlRs2.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_REDMINElocal1.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_sites/btn_REDMINElocal2.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(371, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SitesViewLayout = new javax.swing.GroupLayout(SitesView);
        SitesView.setLayout(SitesViewLayout);
        SitesViewLayout.setHorizontalGroup(
            SitesViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SitesViewLayout.setVerticalGroup(
            SitesViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SitesViewLayout.createSequentialGroup()
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Body.add(SitesView, "card7");

        CadFunc.setPreferredSize(new java.awt.Dimension(1205, 659));

        jPanel18.setBackground(new java.awt.Color(204, 204, 204));

        btn_CadEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadEmp01.png"))); // NOI18N
        btn_CadEmp.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadEmp02.png"))); // NOI18N

        btn_CadFunc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/CadFunc01.png"))); // NOI18N
        btn_CadFunc.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/CadFunc02.png"))); // NOI18N

        btn_cadUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadUser01.png"))); // NOI18N
        btn_cadUser.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadUser02.png"))); // NOI18N

        btn_CadPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadPro01.png"))); // NOI18N
        btn_CadPro.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadPro02.png"))); // NOI18N

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/configCad01.png"))); // NOI18N
        btnConfig.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/configCad02.png"))); // NOI18N

        btn_CadRamal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadRamal01.png"))); // NOI18N
        btn_CadRamal.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/icones cad/cadRamal02.png"))); // NOI18N

        jLabel67.setBackground(new java.awt.Color(51, 51, 51));
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Cadastros");
        jLabel67.setOpaque(true);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(btn_CadEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_CadFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btn_cadUser, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btn_CadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_CadRamal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btn_CadEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_CadFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btn_cadUser, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btn_CadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_CadRamal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CadFuncLayout = new javax.swing.GroupLayout(CadFunc);
        CadFunc.setLayout(CadFuncLayout);
        CadFuncLayout.setHorizontalGroup(
            CadFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CadFuncLayout.setVerticalGroup(
            CadFuncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadFuncLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(316, 316, 316))
        );

        Body.add(CadFunc, "card8");

        Perfil.setBackground(new java.awt.Color(204, 204, 204));

        jLabel52.setBackground(new java.awt.Color(51, 51, 51));
        jLabel52.setFont(new java.awt.Font("MicrogrammaDBolExt", 3, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(241, 117, 9));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Painel de controle Usuario");
        jLabel52.setOpaque(true);

        jt_Painel_User_Chamado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Titulo", "Descrição", "Solução", "Empresa", "Cliente", "Tags", "Telefone", "Conexão", "Usuario Logicom", "Data Chamado", "Hora Chamado", "Status", "Editado", "Data Editado", "Hora Editado", "Cronometro", "Hora Fechado", "Data Fechado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jt_Painel_User_Chamado);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel74.setBackground(new java.awt.Color(19, 29, 38));
        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(241, 117, 9));
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Pesquisar chamado usuario");
        jLabel74.setOpaque(true);

        txt_Puser_dataIni.setText("Data inicial");
        txt_Puser_dataIni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_Puser_dataIniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Puser_dataIniFocusLost(evt);
            }
        });

        txt_Puser_dataFim.setText("Data final");
        txt_Puser_dataFim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_Puser_dataFimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Puser_dataFimFocusLost(evt);
            }
        });

        jLabel54.setText("Data periodo:");

        txt_Puser_Pesquisar.setToolTipText("Pesquisar Chamado");
        txt_Puser_Pesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Puser_PesquisarFocusLost(evt);
            }
        });
        txt_Puser_Pesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_Puser_PesquisarMouseClicked(evt);
            }
        });

        PesquisaChamadoUser.add(radEmpUser);
        radEmpUser.setSelected(true);
        radEmpUser.setText("Empresa");

        PesquisaChamadoUser.add(radFuncUser);
        radFuncUser.setText("Funcionário");

        PesquisaChamadoUser.add(radDescUser);
        radDescUser.setText("Descrição");

        PesquisaChamadoUser.add(radClienteUser);
        radClienteUser.setText("Cliente");

        PesquisaChamadoUser.add(radTagsUser);
        radTagsUser.setText("Tags");

        btnPesquisarUser.setText("Pesquisar");
        btnPesquisarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarUserActionPerformed(evt);
            }
        });

        btnConverteExcel.setText("Converter Excel");
        btnConverteExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConverteExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(btnPesquisarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConverteExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(txt_Puser_dataIni, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Puser_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(radEmpUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radFuncUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radDescUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radClienteUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radTagsUser))
                            .addComponent(jLabel54)
                            .addComponent(txt_Puser_Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE))
                        .addGap(18, 18, 18))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Puser_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel54)
                .addGap(2, 2, 2)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Puser_dataIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Puser_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radEmpUser)
                    .addComponent(radFuncUser)
                    .addComponent(radDescUser)
                    .addComponent(radClienteUser)
                    .addComponent(radTagsUser))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesquisarUser)
                    .addComponent(btnConverteExcel))
                .addGap(0, 106, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chamados do usuario", jPanel9);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 872, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel15);

        javax.swing.GroupLayout PerfilLayout = new javax.swing.GroupLayout(Perfil);
        Perfil.setLayout(PerfilLayout);
        PerfilLayout.setHorizontalGroup(
            PerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        PerfilLayout.setVerticalGroup(
            PerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerfilLayout.createSequentialGroup()
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        Body.add(Perfil, "card9");

        ProView2.setPreferredSize(new java.awt.Dimension(856, 650));

        jPanel8.setPreferredSize(new java.awt.Dimension(500, 803));

        jLabel65.setBackground(new java.awt.Color(51, 51, 51));
        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("Documentos/P.R.O");
        jLabel65.setOpaque(true);

        PanelListaPRo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("P.R.Os")));

        tbl_pro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_pro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_proMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tbl_pro);

        javax.swing.GroupLayout PanelListaPRoLayout = new javax.swing.GroupLayout(PanelListaPRo);
        PanelListaPRo.setLayout(PanelListaPRoLayout);
        PanelListaPRoLayout.setHorizontalGroup(
            PanelListaPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelListaPRoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12)
                .addContainerGap())
        );
        PanelListaPRoLayout.setVerticalGroup(
            PanelListaPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelListaPRoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelCadastrarPRo.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastrar P.R.O"));
        PanelCadastrarPRo.setPreferredSize(new java.awt.Dimension(800, 185));

        jLabel20.setText("Nome:");

        jLabel25.setText("Selecionar Arquivo:");

        txt_conteudoPRo.setColumns(20);
        txt_conteudoPRo.setRows(5);
        jScrollPane11.setViewportView(txt_conteudoPRo);

        jLabel29.setText("Selecionar Arquivo:");

        btnguardar.setBackground(new java.awt.Color(0, 102, 102));
        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_save_close_28px.png"))); // NOI18N
        btnguardar.setText("Gravar");
        btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnguardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnguardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnguardarMouseExited(evt);
            }
        });
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btnmodificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_edit_property_35px_1.png"))); // NOI18N
        btnmodificar.setText("Editar");
        btnmodificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnmodificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnmodificarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnmodificarMouseExited(evt);
            }
        });

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_delete_28px_2.png"))); // NOI18N
        btneliminar.setText("Excluir");
        btneliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btneliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btneliminarMouseExited(evt);
            }
        });
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnnovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_file_delete_25px.png"))); // NOI18N
        btnnovo.setText("Novo");
        btnnovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnnovo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnnovoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnnovoMouseExited(evt);
            }
        });
        btnnovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnovoActionPerformed(evt);
            }
        });

        jLabel30.setText("Pesquisar");

        txt_pesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisarKeyPressed(evt);
            }
        });

        GpPro.add(radTItPro);
        radTItPro.setSelected(true);
        radTItPro.setText("Titulo");

        GpPro.add(radContPro);
        radContPro.setText("Conteudo");

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_pdf_30px.png"))); // NOI18N
        jButton12.setText("Procurar Arquivos");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelCadastrarPRoLayout = new javax.swing.GroupLayout(PanelCadastrarPRo);
        PanelCadastrarPRo.setLayout(PanelCadastrarPRoLayout);
        PanelCadastrarPRoLayout.setHorizontalGroup(
            PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(24, 24, 24)
                        .addComponent(radTItPro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radContPro))
                    .addComponent(txt_pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nome_pro, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                                    .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnnovo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                                    .addComponent(jLabel25)
                                    .addGap(13, 13, 13)
                                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        PanelCadastrarPRoLayout.setVerticalGroup(
            PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel20))
                .addGap(2, 2, 2)
                .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelCadastrarPRoLayout.createSequentialGroup()
                        .addComponent(txt_nome_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnmodificar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnnovo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelCadastrarPRoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(radTItPro)
                            .addComponent(radContPro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelListaPRo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jSeparator5)
                                .addGap(12, 12, 12))
                            .addComponent(PanelCadastrarPRo, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelListaPRo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(PanelCadastrarPRo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ProView2Layout = new javax.swing.GroupLayout(ProView2);
        ProView2.setLayout(ProView2Layout);
        ProView2Layout.setHorizontalGroup(
            ProView2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
        );
        ProView2Layout.setVerticalGroup(
            ProView2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
        );

        Body.add(ProView2, "card9");

        Config.setPreferredSize(new java.awt.Dimension(1205, 659));

        jPanel13.setBackground(new java.awt.Color(153, 153, 153));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Nome sistema:");

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel68.setText("Versão do Sistema:");

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel70.setText("Arquitetura do Sistema:");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel71.setText("Idioma:");

        lb_1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_1.setForeground(new java.awt.Color(204, 0, 51));
        lb_1.setText("jLabel72");

        lb_2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_2.setForeground(new java.awt.Color(204, 0, 51));
        lb_2.setText("jLabel72");

        lb_3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_3.setForeground(new java.awt.Color(204, 0, 51));
        lb_3.setText("jLabel72");

        lb_4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_4.setForeground(new java.awt.Color(204, 0, 51));
        lb_4.setText("jLabel72");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Espaço Livre:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Espaço Total:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("Espaço Usado:");

        lb_Conf_Livre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Conf_Livre.setForeground(new java.awt.Color(204, 0, 51));
        lb_Conf_Livre.setText("livre");

        lb_Conf_Total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Conf_Total.setForeground(new java.awt.Color(204, 0, 51));
        lb_Conf_Total.setText("total");

        lb_Conf_Usado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Conf_Usado.setForeground(new java.awt.Color(204, 0, 51));
        lb_Conf_Usado.setText("usado");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setText("Memoria Livre:");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setText("Memoria Usada:");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("Memoria Total:");

        lb_Conf_MLivre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Conf_MLivre.setForeground(new java.awt.Color(204, 0, 51));
        lb_Conf_MLivre.setText("MLivre");

        lb_Conf_MUsada.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Conf_MUsada.setForeground(new java.awt.Color(204, 0, 51));
        lb_Conf_MUsada.setText("MUsada");

        lb_Conf_MTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Conf_MTotal.setForeground(new java.awt.Color(204, 0, 51));
        lb_Conf_MTotal.setText("MTotal");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setText("Ip Servidor:");

        lb_Ip.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_Ip.setForeground(new java.awt.Color(204, 0, 51));
        lb_Ip.setText("IP.....");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setText("Mac Servidor:");

        lb_mac.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_mac.setForeground(new java.awt.Color(204, 0, 51));
        lb_mac.setText("Mac...");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37)
                    .addComponent(jLabel6)
                    .addComponent(jLabel68)
                    .addComponent(jLabel39)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_2)
                            .addComponent(lb_1))
                        .addGap(193, 193, 193)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel71)
                            .addComponent(jLabel70)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_Conf_Livre)
                            .addComponent(lb_Conf_Total)
                            .addComponent(lb_Conf_Usado)
                            .addComponent(lb_Ip))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Conf_MLivre)
                    .addComponent(lb_3)
                    .addComponent(lb_4)
                    .addComponent(lb_Conf_MUsada)
                    .addComponent(lb_Conf_MTotal)
                    .addComponent(lb_mac))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel68))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(lb_1)
                        .addGap(6, 6, 6)
                        .addComponent(lb_2))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(lb_3)
                        .addGap(6, 6, 6)
                        .addComponent(lb_4))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel71)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(lb_Conf_Livre)
                    .addComponent(jLabel44)
                    .addComponent(lb_Conf_MLivre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(lb_Conf_Total)
                    .addComponent(jLabel45)
                    .addComponent(lb_Conf_MUsada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(lb_Conf_Usado)
                    .addComponent(jLabel46)
                    .addComponent(lb_Conf_MTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel51)
                        .addComponent(lb_mac))
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel47)
                        .addComponent(lb_Ip)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel63.setBackground(new java.awt.Color(51, 51, 51));
        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Configuração usuario");
        jLabel63.setOpaque(true);

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setText("Altera foto Perfil:");

        btnBuscaFoto.setText("Buscar Foto");
        btnBuscaFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaFotoActionPerformed(evt);
            }
        });

        btnSalvaFoto.setText("Salvar");
        btnSalvaFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvaFotoActionPerformed(evt);
            }
        });

        lbl_Image.setBackground(new java.awt.Color(255, 204, 0));
        lbl_Image.setOpaque(true);

        lbl_TitImage.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_TitImage.setForeground(new java.awt.Color(255, 255, 255));
        lbl_TitImage.setText("Titulo da foto.");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addComponent(btnBuscaFoto)
                    .addComponent(btnSalvaFoto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_Image, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(lbl_TitImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscaFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvaFoto))
                    .addComponent(lbl_Image, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TitImage))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ConfigLayout = new javax.swing.GroupLayout(Config);
        Config.setLayout(ConfigLayout);
        ConfigLayout.setHorizontalGroup(
            ConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ConfigLayout.setVerticalGroup(
            ConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfigLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37))
        );

        Body.add(Config, "card8");

        javax.swing.GroupLayout BackgroundFrameLayout = new javax.swing.GroupLayout(BackgroundFrame);
        BackgroundFrame.setLayout(BackgroundFrameLayout);
        BackgroundFrameLayout.setHorizontalGroup(
            BackgroundFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundFrameLayout.createSequentialGroup()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Body, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
        );
        BackgroundFrameLayout.setVerticalGroup(
            BackgroundFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundFrameLayout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BackgroundFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BackgroundFrameLayout.createSequentialGroup()
                        .addComponent(Menu, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                        .addGap(73, 73, 73))
                    .addGroup(BackgroundFrameLayout.createSequentialGroup()
                        .addComponent(Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(977, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseEntered
        btnMinimize.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnMinimizeMouseEntered

    private void btnMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseExited
        btnMinimize.setBackground(new Color(19, 29, 38));
    }//GEN-LAST:event_btnMinimizeMouseExited

    private void btnMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizeActionPerformed
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeActionPerformed

    private void btnExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseEntered
        btnExit.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btnExitMouseEntered

    private void btnExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseExited
        btnExit.setBackground(new Color(19, 29, 38));
    }//GEN-LAST:event_btnExitMouseExited

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "Deseja Fechar o Sistema?", "SAIR", dialogButton);
            if (result == 0) {
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnMaximizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseEntered
        btnMaximize.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnMaximizeMouseEntered

    private void btnMaximizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseExited
        btnMaximize.setBackground(new Color(19, 29, 38));
    }//GEN-LAST:event_btnMaximizeMouseExited

    private void btnMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaximizeActionPerformed
        if (maximized) {
            //handle fullscreen - taskbar
            Principal3.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Principal3.this.setMaximizedBounds(env.getMaximumWindowBounds());
            maximized = false;
        } else {
            setExtendedState(JFrame.NORMAL);
            maximized = true;
        }
    }//GEN-LAST:event_btnMaximizeActionPerformed

    private void HeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HeaderMouseDragged
        if (maximized) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - xMouse, y - yMouse);
        }
    }//GEN-LAST:event_HeaderMouseDragged

    private void HeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_HeaderMousePressed

    private void Btn_ChamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ChamadoActionPerformed
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado_orange.png")));//CHAMADO Laranja

        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR cinza
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        //PanelChamado.setVisible(false);
        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(ChamadosView);
        Body.repaint();
        Body.revalidate();
    }//GEN-LAST:event_Btn_ChamadoActionPerformed

    private void Btn_AgendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_AgendaMouseEntered
        //Agenda.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_AgendaMouseEntered

    private void Btn_AgendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_AgendaMouseExited
        //Agenda.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_AgendaMouseExited

    private void Btn_AgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AgendaActionPerformed
        //Organiza botões GANBIARRA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda_orange.png")));//AGENDA laranja

        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(AgendaView);
        Body.repaint();
        Body.revalidate();
        //Btn_Agenda.setIcon(new javax.swing.ImageIcon("../imagem/System/icon_Pro_Orange.png"));
        //ImageIcon press = new ImageIcon("/System/icon_Pro_Orange.png");
        //Btn_Agenda.setPressedIcon(press);
    }//GEN-LAST:event_Btn_AgendaActionPerformed

    private void Btn_CadastrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_CadastrarMouseEntered
        //Cadastrar1.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_CadastrarMouseEntered

    private void Btn_CadastrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_CadastrarMouseExited
        //Cadastrar1.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_CadastrarMouseExited

    private void Btn_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_CadastrarActionPerformed

        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar_Orange.png")));//CADASTRAR LARANJA

        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(CadFunc);
        Body.repaint();
        Body.revalidate();
    }//GEN-LAST:event_Btn_CadastrarActionPerformed

    private void Btn_RelatorioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_RelatorioMouseEntered
        //Relatorio.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_RelatorioMouseEntered

    private void Btn_RelatorioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_RelatorioMouseExited
        //Relatorio.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_RelatorioMouseExited

    private void Btn_RelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_RelatorioActionPerformed
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio_Relatorio.png")));//RELATÓRIO laranja

        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(VersoesView);
        Body.repaint();
        Body.revalidate();
    }//GEN-LAST:event_Btn_RelatorioActionPerformed

    private void Btn_PROMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_PROMouseEntered
        //Sites.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_PROMouseEntered

    private void Btn_PROMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_PROMouseExited
        //Sites.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_PROMouseExited

    private void Btn_PROActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_PROActionPerformed
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro_Orange.png")));//P.R.O laranja

        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(ProView2);
        Body.repaint();
        Body.revalidate();
        //TableCellRenderer renderer = new MeuRenderer();
        //tbl_pro.setDefaultRenderer(Object.class, renderer);
        prodao.visualizar_PdfVO(tbl_pro);
    }//GEN-LAST:event_Btn_PROActionPerformed

    private void combo_empFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_empFocusGained
        if (combo_emp.getSelectedItem().toString().equals("Empresas")) {
            JOptionPane.showMessageDialog(null, "Escolha uma empresa");
            combo_emp.setBorder(new LineBorder(Color.RED));
        } else {
            combo_emp.setBorder(new LineBorder(Color.GREEN));
        }
    }//GEN-LAST:event_combo_empFocusGained

    private void combo_empFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_empFocusLost
        if (combo_emp.getSelectedItem().equals("Empresas")) {
            JOptionPane.showMessageDialog(null, "Selecione uma empresa");
        }
    }//GEN-LAST:event_combo_empFocusLost

    private void combo_empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_empMouseClicked
        EmpresaDAO emp = new EmpresaDAO();
        DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(emp.mostrarEmpresas());
        combo_emp.setModel(modelEstado);
    }//GEN-LAST:event_combo_empMouseClicked

    private void combo_funcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_funcItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            Funcionario func = (Funcionario) combo_func.getSelectedItem();

            txt_conexao.setText(func.getConexao());
            txt_telefone_chamado2.setText(func.getTelefone());
            ChecaTexto();

            //PARTE REFERENTE AO CRONOMETRO
            lbl_Cronometro.setVisible(true);
            timer.restart();
        }
    }//GEN-LAST:event_combo_funcItemStateChanged

    private void btn_salvar_chamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_chamadoActionPerformed
        if (combo_emp.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Campo Empresa Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            combo_emp.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            combo_emp.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        if (combo_func.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Campo Funcionário Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            combo_func.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            combo_func.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        if (txt_telefone_chamado2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo Telefone Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_telefone_chamado2.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            txt_telefone_chamado2.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        if (txt_titulo_chamado.getText().equals("") || txt_titulo_chamado.getText().equals("Digite o titulo do Chamado...")) {
            txt_titulo_chamado.setBorder(new LineBorder(Color.RED));
            txt_titulo_chamado.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Titulo Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            txt_titulo_chamado.setBorder(new LineBorder(Color.lightGray));
        }

        if (txt_chamado_desc1.getText().equals("")) {
            txt_chamado_desc1.setBorder(new LineBorder(Color.RED));
            txt_chamado_desc1.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Chamado Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            txt_chamado_desc1.setBorder(new LineBorder(Color.lightGray));
        }
        if (txt_Solucao.getText().equals("")) {
            txt_Solucao.setBorder(new LineBorder(Color.RED));
            txt_Solucao.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Solução Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txt_Solucao.getText().trim().isEmpty()) {
            //txt_Solucao.setBorder(new LineBorder(Color.lightGray));
            txt_Solucao.setBorder(new LineBorder(Color.RED));
        } else {
            try {
                Chamado c = new Chamado();
                ChamadoDAO cdao = new ChamadoDAO();

                //PARTE REFERENTE AO - CRONOMETRO DO CHAMADO
                String hr = currentHora <= 9 ? "0" + currentHora : currentHora + "";
                String min = currentMinuto <= 9 ? "0" + currentMinuto : currentMinuto + "";
                String seg = currentSegundo <= 9 ? "0" + currentSegundo : currentSegundo + "";

                stopTime();
                String cronometro = hr + ":" + min + ":" + seg;//Pega o tempo do cronometro e salva na string cronometro

                SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");//Fomartar a hora
                Date data = formatador.parse(cronometro);//Adiciona o cronometro ao formatador
                Time time = new Time(data.getTime());//passa o cronometro formatado e converte para Time

                String user = userlogado2.getText();

                Empresa empresa = (Empresa) combo_emp.getSelectedItem();
                Funcionario func = (Funcionario) combo_func.getSelectedItem();

                //Para salvar o status do chamado
                if (rad_Aberto.isSelected()) {
                    status = "A";
                }
                if (rad_Fechado.isSelected()) {
                    status = "F";
                }

                empresa.getNome_emp();
                c.setFone(txt_telefone_chamado2.getText());
                c.setTitulo(txt_titulo_chamado.getText());
                c.setTexto(txt_chamado_desc1.getText());
                c.setTags(txt_tag1.getText() + " " + txt_tag2.getText() + " " + txt_tag3.getText() + " " + txt_tag4.getText() + " " + txt_tag5.getText());
                c.setSolucao(txt_Solucao.getText());
                c.setConexao(txt_conexao.getText());
                c.setStatus(status);
                c.setCronometro(time);
                try {
                    cdao.Cad_Chamado(empresa, func, c, user);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o Chamado");
                }
                readJTableChamado();

                //DEIXA INVISIVEL O CONTADOR
                lbl_Cronometro.setVisible(true);

                //adiciona o contador do usuario
                listacontador(user);
                listacontadorSemana(user);
                listacontadorMes(user);
                listaChamadosAbertos(userLogado);

                txt_telefone_chamado2.setText("");
                txt_chamado_desc1.setText("");
                txt_titulo_chamado.setText("");
                txt_Solucao.setText("");
                combo_func.removeAllItems();
                txt_tag1.setText("");
                txt_tag2.setText("");
                txt_tag3.setText("");
                txt_tag4.setText("");
                txt_tag5.setText("");
                txt_conexao.setText("");
                combo_emp.setSelectedIndex(0);
                combo_func.setSelectedIndex(0);

            } catch (ParseException ex) {
                Logger.getLogger(Principal3.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btn_salvar_chamadoActionPerformed

    private void Btn_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_LimparActionPerformed
        txt_telefone_chamado2.setText("");
        txt_chamado_desc1.setText("");
        txt_titulo_chamado.setText("");
        txt_Solucao.setText("");
        txt_tag1.setText("");
        txt_tag2.setText("");
        txt_tag3.setText("");
        txt_tag4.setText("");
        txt_tag5.setText("");
        txt_conexao.setText("");
        combo_emp.setSelectedIndex(0);
        combo_func.setSelectedIndex(0);

        currentSegundo++;

        if (currentSegundo == 60) {
            currentMinuto++;
            currentSegundo = 0;
        }

        if (currentMinuto == 60) {
            currentHora++;
            currentMinuto = 0;
        }

        //lbl_Cronometro.setVisible(false);
        stopTime();
        userImageProfile(idLogado3);
    }//GEN-LAST:event_Btn_LimparActionPerformed

    private void txt_titulo_chamadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoFocusGained
        if (txt_titulo_chamado.getText().equals("Digite o titulo do Chamado...")) {
            txt_titulo_chamado.setText("");
        }
    }//GEN-LAST:event_txt_titulo_chamadoFocusGained

    private void txt_titulo_chamadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoFocusLost
        if (txt_titulo_chamado.getText().equals("")) {
            txt_titulo_chamado.setText("Digite o titulo do Chamado...");
        }
    }//GEN-LAST:event_txt_titulo_chamadoFocusLost

    private void txt_titulo_chamadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoMouseClicked
        if (txt_titulo_chamado.getText().equals("Digite o titulo do Chamado...")) {
            txt_titulo_chamado.setText("");
        }
    }//GEN-LAST:event_txt_titulo_chamadoMouseClicked

    private void txt_titulo_chamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_titulo_chamadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Cad_emp_view2 func = new Cad_emp_view2();
        func.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void combo_emp2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_emp2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_emp2MouseClicked

    private void combo_emp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_emp2ActionPerformed
        if (combo_emp2.getSelectedItem().equals("Empresas")) {
            JOptionPane.showMessageDialog(null, "Escolha a empresa");
            readJTableEmp();
        } else {
            DefaultTableModel modelo = (DefaultTableModel) tbl_empresa.getModel();
            modelo.setNumRows(0);
            tbl_empresa.setAutoResizeMode(tbl_empresa.AUTO_RESIZE_OFF);
            tbl_empresa.setModel(modelo);
            tbl_empresa.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
            tbl_empresa.getColumnModel().getColumn(1).setPreferredWidth(170);//EMPRESA
            tbl_empresa.getColumnModel().getColumn(2).setPreferredWidth(100);//TELEFONE
            tbl_empresa.getColumnModel().getColumn(3).setPreferredWidth(120);//CNPJ
            tbl_empresa.getColumnModel().getColumn(4).setPreferredWidth(250);//OBS
            tbl_empresa.getColumnModel().getColumn(5).setPreferredWidth(100);//IP SCEF
            tbl_empresa.getColumnModel().getColumn(6).setPreferredWidth(100);//MAC SCEF
            tbl_empresa.getColumnModel().getColumn(7).setPreferredWidth(100);//CON SCEF
            tbl_empresa.getColumnModel().getColumn(8).setPreferredWidth(100);//IP NFCE
            tbl_empresa.getColumnModel().getColumn(9).setPreferredWidth(100);//MAC NFCE
            tbl_empresa.getColumnModel().getColumn(10).setPreferredWidth(100);//CON NFCE
            tbl_empresa.getColumnModel().getColumn(11).setPreferredWidth(100);//IP NFE
            tbl_empresa.getColumnModel().getColumn(12).setPreferredWidth(100);//MAC NFE
            tbl_empresa.getColumnModel().getColumn(13).setPreferredWidth(100);//CON NFE
            tbl_empresa.getColumnModel().getColumn(14).setPreferredWidth(100);//MOBILE
            tbl_empresa.getColumnModel().getColumn(15).setPreferredWidth(100);//COLETOR
            tbl_empresa.getColumnModel().getColumn(16).setPreferredWidth(100);//COLETOR
            tbl_empresa.getColumnModel().getColumn(17).setPreferredWidth(100);//COLETOR
            tbl_empresa.getColumnModel().getColumn(18).setPreferredWidth(100);//COLETOR
            tbl_empresa.getColumnModel().getColumn(19).setPreferredWidth(100);//COLETOR
            tbl_empresa.getColumnModel().getColumn(20).setPreferredWidth(100);//COLETOR

            EmpresaDAO fdao = new EmpresaDAO();
            Vector<Empresa> list = fdao.getEmp((Empresa) combo_emp2.getSelectedItem());
            modelo.setColumnIdentifiers(new Object[]{"ID", "Nome", "telefone", "Cnpj", "Observação", "Ip Scef", "Mac Scef", "Conexão Scef", "Ip Nfce", "Mac Nfce", "Conexão Nfce", "Ip Nfe", "Mac Nfe", "Conexão Nfe", "Mobile", "Coletor", "V_Estoque", "V_NFCE", "V_NFE", "V_Sisseg", "V_CheckOut"});
            Object[] row = new Object[21];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_emp();
                row[1] = list.get(i).getNome_emp();
                row[2] = list.get(i).getTelefone();
                row[3] = list.get(i).getCnpj();
                row[4] = list.get(i).getObs();
                row[5] = list.get(i).getIp_scef();
                row[6] = list.get(i).getMac_scef();
                row[7] = list.get(i).getCon_scef();
                row[8] = list.get(i).getIp_nfce();
                row[9] = list.get(i).getMac_nfce();
                row[10] = list.get(i).getCon_nfce();
                row[11] = list.get(i).getIp_nfe();
                row[12] = list.get(i).getMac_nfe();
                row[13] = list.get(i).getCon_nfe();
                row[14] = list.get(i).getIp_mobile();
                row[15] = list.get(i).getIp_coletor();
                row[16] = list.get(i).getV_estoque();
                row[17] = list.get(i).getV_nfce();
                row[18] = list.get(i).getV_nfe();
                row[19] = list.get(i).getV_sisseg();
                row[20] = list.get(i).getV_checkout();
                modelo.addRow(row);
            }
            tbl_empresa.setModel(modelo);
        }
    }//GEN-LAST:event_combo_emp2ActionPerformed

    private void tbl_empresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_empresaMouseClicked
        Empresa e = new Empresa();
        EmpresaDAO edao = new EmpresaDAO();

        int index = tbl_empresa.getSelectedRow();

        e = edao.ListaEmpresa().get(index);

        lb_e_nomeEmpresa.setText(e.getNome_emp());
        lb_e_Tel_empresa.setText(e.getTelefone());
        lb_e_Cnpj_emp.setText(e.getCnpj());
        lb_e_Ip_scef.setText(e.getIp_scef());
        lb_e_mac_scef.setText(e.getMac_nfce());
        lb_e_Ip_Nfce.setText(e.getIp_nfce());
        lb_e_Mac_nfce.setText(e.getMac_nfce());
        lb_e_Ip_Nfe.setText(e.getIp_nfe());
        lb_e_Mac_nfe.setText(e.getMac_nfe());
        lb_e_Mobile.setText(e.getIp_mobile());
        lb_e_coletor.setText(e.getIp_coletor());
        lb_e_Banco_Est.setText(e.getV_estoque());
        lb_e_Banco_Checkout.setText(e.getV_checkout());
        lb_e_Banco_Nfce.setText(e.getV_nfce());
        lb_e_Banco_Nfe.setText(e.getV_nfe());
        lb_e_Banco_Sisseg.setText(e.getV_sisseg());
    }//GEN-LAST:event_tbl_empresaMouseClicked

    private void txt_nome_agendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nome_agendaFocusLost
        if (txt_nome_agenda.getText().equals("")) {
            txt_nome_agenda.setText("Digite o Nome....");
        }
    }//GEN-LAST:event_txt_nome_agendaFocusLost

    private void txt_nome_agendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nome_agendaMouseClicked
        if (txt_nome_agenda.getText().equals("Digite o Nome....")) {
            txt_nome_agenda.setText("");
        }
    }//GEN-LAST:event_txt_nome_agendaMouseClicked

    private void txt_nome_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nome_agendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nome_agendaActionPerformed

    private void txt_observacao_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_observacao_agendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_observacao_agendaActionPerformed

    private void btn_salvar_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_agendaActionPerformed
        if (txt_nome_agenda.getText().equals("Digite o Nome....") || txt_nome_agenda.getText().equals("")) {
            txt_nome_agenda.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(null, "O campo Nome é Obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_nome_agenda.setText("");
            txt_nome_agenda.requestFocus();
            return;
        } else {
            txt_nome_agenda.setBorder(new LineBorder(Color.lightGray));
        }

        if (txt_telefone_agenda.getText().equals("(  )      -    ") || txt_telefone_agenda.getText().equals("")) {
            txt_telefone_agenda.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(null, "O campo Telefone é Obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_telefone_agenda.setText("");
            txt_telefone_agenda.requestFocus();
            return;
        } else {
            txt_telefone_agenda.setBorder(new LineBorder(Color.lightGray));
        }
        try {
            Agenda a = new Agenda();
            AgendaDAO adao = new AgendaDAO();

            a.setNome(txt_nome_agenda.getText());
            a.setTelefone(txt_telefone_agenda.getText());
            a.setEmail(txt_email_agenda.getText());
            a.setEndereco(txt_endereco_agenda.getText());
            a.setObservacao(txt_observacao_agenda.getText());
            adao.Inserir(a);

            txt_nome_agenda.setText("");
            txt_email_agenda.setText("");
            txt_endereco_agenda.setText("");
            txt_telefone_agenda.setText("");
            txt_observacao_agenda.setText("");
            readJTableAgenda();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_salvar_agendaActionPerformed

    private void btn_excluir_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excluir_agendaActionPerformed
        if (tbl_agenda.getSelectedRow() != -1) {
            Agenda a = new Agenda();
            AgendaDAO adao = new AgendaDAO();
            a.setId((int) tbl_agenda.getValueAt(tbl_agenda.getSelectedRow(), 0));

            adao.Delete(a);

            txt_nome_agenda.setText("");
            txt_telefone_agenda.setText("");
            txt_email_agenda.setText("");
            txt_endereco_agenda.setText("");
            txt_observacao_agenda.setText("");
            readJTableAgenda();
        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Deseja Excluir ?", "SAIR", dialogButton);
        }
    }//GEN-LAST:event_btn_excluir_agendaActionPerformed

    private void btn_atualizar_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizar_agendaActionPerformed
        if (tbl_agenda.getSelectedRow() != -1) {
            Agenda a = new Agenda();
            AgendaDAO adao = new AgendaDAO();

            a.setNome(txt_nome_agenda.getText());
            a.setEmail(txt_email_agenda.getText());
            a.setTelefone(txt_telefone_agenda.getText());
            a.setEndereco(txt_endereco_agenda.getText());
            a.setObservacao(txt_observacao_agenda.getText());
            adao.update(a);

            txt_nome_agenda.setText("");
            txt_email_agenda.setText("");
            txt_telefone_agenda.setText("");
            txt_endereco_agenda.setText("");
            txt_observacao_agenda.setText("");

            readJTable();
        }
    }//GEN-LAST:event_btn_atualizar_agendaActionPerformed

    private void novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novoActionPerformed
        txt_nome_agenda.setText("");
        txt_email_agenda.setText("");
        txt_endereco_agenda.setText("");
        txt_telefone_agenda.setText("");
        txt_observacao_agenda.setText("");
    }//GEN-LAST:event_novoActionPerformed

    private void txt_pesquisa_agendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisa_agendaKeyPressed
        PesquisaAgendaNome(txt_pesquisa_agenda.getText());
    }//GEN-LAST:event_txt_pesquisa_agendaKeyPressed

    private void Btn_Sites1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_Sites1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_Sites1MouseEntered

    private void Btn_Sites1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_Sites1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_Sites1MouseExited

    private void Btn_Sites1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_Sites1ActionPerformed
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites_Orange.png")));//SITES laranja

        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(SitesView);
        Body.repaint();
        Body.revalidate();
    }//GEN-LAST:event_Btn_Sites1ActionPerformed

    private void txt_Pesquisa_ChamadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Pesquisa_ChamadoKeyPressed
        //pesquisar_chamado(txt_Pesquisa_Chamado.getText());
        if (RadEmp.isSelected()) {
            String data = "nome_emp";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText().trim(), data);
        }

        if (rad_Desc.isSelected()) {
            String data = "texto";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText().trim(), data);
        }

        if (rad_Clien.isSelected()) {
            String data = "nome";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText().trim(), data);
        }

        if (rad_Tags.isSelected()) {
            String data = "tags";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText().trim(), data);
        }
        if (rad_ID.isSelected()) {
            //txt_Pesquisa_Chamado.setDocument(new soNumeros());
            String data = "id";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText().trim(), data);
        }
        if (rad_logicom.isSelected()) {
            String data = "usuario";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText().trim(), data);
        }
    }//GEN-LAST:event_txt_Pesquisa_ChamadoKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //Data
        Date dataSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Lb_Data_Principal.setText(formato.format(dataSistema));

        //Hora
        Timer timer = new Timer(1000, new hora());
        timer.start();
    }//GEN-LAST:event_formWindowOpened

    private void combo_empItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_empItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Empresa emp = (Empresa) combo_emp.getSelectedItem();
            FuncionarioDAO func = new FuncionarioDAO();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(func.mostrarFuncionario(emp.getId_emp()));
            combo_func.setModel(modelMunicipio);

            //======================================================//
            if (combo_emp.getSelectedIndex() == 0) {
                ChamadosLastTen();
                txt_telefone_chamado2.setText("");
                txt_conexao.setText("");
            } else {
                DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado2.getModel();
                modelo.setNumRows(0);
                tbl_Chamado2.setAutoResizeMode(tbl_Chamado2.AUTO_RESIZE_OFF);

                ChamadoDAO cadao = new ChamadoDAO();
                for (Chamado c : cadao.ListarChamado((Empresa) combo_emp.getSelectedItem())) {
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
                tbl_Chamado2.setModel(modelo);
            }
        }
    }//GEN-LAST:event_combo_empItemStateChanged

    private void Btn_PrintMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_PrintMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_PrintMouseEntered

    private void Btn_PrintMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_PrintMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Btn_PrintMouseExited
    //Chama tela Print
    private void Btn_PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_PrintActionPerformed
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera2.png")));//PRINTSCREEM laranja

        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA

        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        if (flagPrint == false) {//se o flag for false(que já vem false por default)

            PrintScreen3 print = new PrintScreen3();//Istancia o frame PrintScreen2
            flagPrint = true;//muda a flag para true
            print.setVisible(true);//Deixa print visivel
        }
    }//GEN-LAST:event_Btn_PrintActionPerformed

    //Botão que chama o config
    private void btn_ConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ConfigActionPerformed
        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - laranja - 35x35.png")));//CONFIGURAR laranja

        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_01.png")));//GERENCIA cinza

        Body.removeAll();
        Body.repaint();
        Body.revalidate();
        Body.add(Config);
        Body.repaint();
        Body.revalidate();

        /*Statement st;
        ResultSet rs;
        Connection con = getConnection();

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios WHERE usuario = '" + LoginNew3.txt_Login.getText() + "'");
            rs.first();
            if (rs.getString("permissao").equals("a")) {
                Body.removeAll();
                Body.repaint();
                Body.revalidate();
                Body.add(Config);
                Body.repaint();
                Body.revalidate();
            } else {
                JOptionPane.showMessageDialog(null, "Você não tem permissão para acessar!");
            }
        } catch (Exception e) {
        }*/
    }//GEN-LAST:event_btn_ConfigActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            URI link = new URI("http://192.168.0.24:8080/redmine/");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            URI link = new URI("https://webmail-seguro.com.br/logicom.com.br/");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            URI link = new URI("http://www.redmine.logicom.net.br/");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            URI link = new URI("http://www.nfe.fazenda.gov.br/portal/consultaRecaptcha.aspx?tipoConsulta=completa&tipoConteudo=XbSeqxE8pl8=");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            URI link = new URI("https://appnfc.sefa.pa.gov.br/portal/view/consultas/nfce/consultanfce.seam");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try {
            URI link = new URI("https://www.sefaz.rs.gov.br/NFE/NFE-VAL.aspx");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        try {
            URI link = new URI("http://www.sintegra.gov.br");
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Cad_Func_View func = new Cad_Func_View();
        Cad_Func_View.combo_emp_cadFunc.setSelectedItem(combo_emp.getSelectedItem());
        func.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tbl_proMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_proMouseClicked
        int column = tbl_pro.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tbl_pro.getRowHeight();
        activa_boton(false, true, true, true, true, true);
        txt_nome_pro.setEnabled(true);
        if (row < tbl_pro.getRowCount() && row >= 0 && column < tbl_pro.getColumnCount() && column >= 0) {
            id = (int) tbl_pro.getValueAt(row, 0);
            Object value = tbl_pro.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                if (boton.getText().equals("Vazio")) {
                    JOptionPane.showMessageDialog(null, "Sem arquivo Gravado!");
                } else {
                    try {

                        String name = "" + tbl_pro.getValueAt(row, 1);

                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int result = JOptionPane.showConfirmDialog(null, "Deseja abrir o documento " + name + " ?", "SAIR", dialogButton);
                        if (result == 0) {
                            ProDAO pd = new ProDAO();
                            pd.ejecutar_archivoPDF(id);
                            Desktop.getDesktop().open(new File("new.pdf"));
                        } else {
                            return;
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                }

            } else {
                String name = "" + tbl_pro.getValueAt(row, 1);
                String conteudo = "" + tbl_pro.getValueAt(row, 2);
                txt_nome_pro.setText(name);
                txt_conteudoPRo.setText(conteudo);
            }
        }
    }//GEN-LAST:event_tbl_proMouseClicked

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        /**
         * ERRO DE max_allowed_packet - CORRIGE USANDO ISSO NO BANCO show
         * variables like 'max_allowed_packet' set global
         * max_allowed_packet=33554432 TUTORIAL:
         * https://www.youtube.com/watch?v=zDaaG8hFYlk
         *
         *
         */

        String Titulo = txt_nome_pro.getText();
        String Conteudo = txt_conteudoPRo.getText();
        sql s = new sql();
        int codigo = s.auto_increment("SELECT MAX(codigopdf) FROM pdf;");
        File ruta = new File(ruta_archivo);
        if (Titulo.trim().length() != 0 && ruta_archivo.trim().length() != 0) {
            guardar_pdf(codigo, Titulo, ruta, Conteudo);
            prodao.visualizar_PdfVO(tbl_pro);
            ruta_archivo = "";
            activa_boton(true, true, true, true, true, true);
            txt_nome_pro.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Rellenar todo los campos");
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnnovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnovoActionPerformed
        activa_boton(true, true, true, true, true, true);
        txt_nome_pro.setText("");
        txt_conteudoPRo.setText("");
    }//GEN-LAST:event_btnnovoActionPerformed

    private void txt_pesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisarKeyPressed
        ProDAO tpdf = new ProDAO();
        String data = txt_pesquisar.getText();
        if (radTItPro.isSelected()) {
            String campoTabela = "titulo_pro";
            tpdf.Pesquisa_Pro(tbl_pro, data, campoTabela);
        }
        if (radContPro.isSelected()) {
            String campoTabela = "conteudo_pro";
            tpdf.Pesquisa_Pro(tbl_pro, data, campoTabela);
        }


    }//GEN-LAST:event_txt_pesquisarKeyPressed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminar_pdf(id);
        ProDAO pro = new ProDAO();
        pro.visualizar_PdfVO(tbl_pro);
        activa_boton(true, true, true, true, true, true);
        txt_nome_pro.setEnabled(false);
        ruta_archivo = "";


    }//GEN-LAST:event_btneliminarActionPerformed

    private void btn_V_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_salvarActionPerformed
        if (txt_V_nome.equals("") || txt_V_versao.equals("") || txt_V_dataRed.equals("")) {
            JOptionPane.showMessageDialog(null, "Campo Empresa Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            combo_emp.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            combo_emp.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        Versoes versb = new Versoes();
        VersoesDAO versd = new VersoesDAO();
        String user = userlogado2.getText();

        versb.setNome(txt_V_nome.getText().trim());
        versb.setVersao(txt_V_versao.getText().trim());
        versb.setWinversion((String) combo_V_versionW.getSelectedItem());
        versb.setExt(txt_V_ext.getText().trim());
        versb.setDataredmine(txt_V_dataRed.getText().trim());
        versb.setTamanho(txt_V_tamanho.getText().trim());
        versb.setHoraredmine(txt_V_horaRed.getText().trim());

        versd.Cad_Versao(versb, user);
        txt_V_nome.setText("");
        txt_V_dataRed.setText("");
        txt_V_ext.setText("");
        txt_V_horaRed.setText("");
        txt_V_tamanho.setText("");
        txt_V_versao.setText("");
        combo_V_versionW.setSelectedIndex(0);
        ativa_botao_V(false, false, false, false, false, false, false, false, false, false, true);

        PreencheTblVersion();
        try {

        } catch (Exception e) {

        }
    }//GEN-LAST:event_btn_V_salvarActionPerformed

    private void btn_V_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_V_novoActionPerformed
        txt_V_nome.setText("");
        txt_V_dataRed.setText("");
        txt_V_ext.setText("");
        txt_V_horaRed.setText("");
        txt_V_tamanho.setText("");
        txt_V_versao.setText("");
        combo_V_versionW.setSelectedIndex(0);
        ativa_botao_V(true, true, true, true, true, true, true, true, false, false, true);
    }//GEN-LAST:event_btn_V_novoActionPerformed

    private void tbl_versionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_versionMouseClicked
        Versoes v = new Versoes();
        VersoesDAO vdao = new VersoesDAO();

        int index = tbl_version.getSelectedRow();
        v = vdao.ListarVersoes().get(index);
        txt_V_nome.setText(v.getNome());
        txt_V_versao.setText(v.getVersao());
        txt_V_tamanho.setText(v.getTamanho());
        txt_V_dataRed.setText(v.getDataredmine());
        txt_V_horaRed.setText(v.getHoraredmine());
        txt_V_ext.setText(v.getExt());
        combo_V_versionW.addItem(v.getWinversion());

    }//GEN-LAST:event_tbl_versionMouseClicked

    private void combo_emp2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_emp2ItemStateChanged
        /*if (evt.getStateChange() == ItemEvent.SELECTED) {
         if (combo_emp2.getSelectedIndex() == 0) {
         readJTableEmp();
         System.out.println("Clicou!");
         } else {

         }
         }*/
    }//GEN-LAST:event_combo_emp2ItemStateChanged

    private void btn_RelUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RelUsersActionPerformed
        btn_RelUsers.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_RelUsers_02.png")));//GERENCIA laranja

        btn_Config.setIcon(new ImageIcon(getClass().getResource("/imagem/System/btn_config - cinza - 35x35.png")));//CONFIGURAR cinza
        Btn_Print.setIcon(new ImageIcon(getClass().getResource("/imagem/System/photo-camera1.png")));//PRINTSCREEM cinza
        Btn_Sites1.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_sites.png")));//SITES cinza
        Btn_PRO.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png")));//P.R.O cinza
        Btn_Relatorio.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png")));//RELATÓRIO cinza
        Btn_Cadastrar.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png")));//CADASTRAR CINZA
        Btn_Agenda.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png")));//AGENDA cinza
        Btn_Chamado.setIcon(new ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png")));//CHAMADO CINZA

        Statement st;
        ResultSet rs;
        Connection con = getConnection();

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios WHERE usuario = '" + LoginNew3.txt_Login.getText() + "'");
            rs.first();
            if (rs.getString("permissao").equals("A")) {
                /*Body.removeAll();
                Body.repaint();
                Body.revalidate();
                Body.add(Config);
                Body.repaint();
                Body.revalidate();*/
                new Gerencia2().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Você não tem permissão para acessar!");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_RelUsersActionPerformed

    private void tbl_agendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_agendaMouseClicked
        if (tbl_agenda.getSelectedRow() != -1) {
            tbl_agenda.setAutoResizeMode(tbl_agenda.AUTO_RESIZE_OFF);
            tbl_agenda.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
            tbl_agenda.getColumnModel().getColumn(1).setPreferredWidth(160);//Nome
            tbl_agenda.getColumnModel().getColumn(2).setPreferredWidth(300);//Endereço
            tbl_agenda.getColumnModel().getColumn(3).setPreferredWidth(190);//Email
            tbl_agenda.getColumnModel().getColumn(4).setPreferredWidth(100);//Telefone
            tbl_agenda.getColumnModel().getColumn(5).setPreferredWidth(400);//Observação

            txt_nome_agenda.setText(tbl_agenda.getValueAt(tbl_agenda.getSelectedRow(), 1).toString());//Nome
            txt_email_agenda.setText(tbl_agenda.getValueAt(tbl_agenda.getSelectedRow(), 2).toString());//EMAIL
            txt_telefone_agenda.setText(tbl_agenda.getValueAt(tbl_agenda.getSelectedRow(), 3).toString());//TELEFONE
            txt_endereco_agenda.setText(tbl_agenda.getValueAt(tbl_agenda.getSelectedRow(), 4).toString());//ENDEREÇO
            txt_observacao_agenda.setText(tbl_agenda.getValueAt(tbl_agenda.getSelectedRow(), 5).toString());//OBSERVAÇÃO
        }
    }//GEN-LAST:event_tbl_agendaMouseClicked

    private void txt_conexaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_conexaoKeyPressed
        ChecaTexto();//VERIFICA SE DIGITOU ALTO E ATIVA O BOTÃO DO ANYDESK

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {//ABRE O ANYDESK DANDO ENTER
            if (txt_conexao.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo anydesk vazio!");
            } else {
                EmpresaDAO edao = new EmpresaDAO();
                String any = txt_conexao.getText();
                edao.Conexao(any);
            }
        }

    }//GEN-LAST:event_txt_conexaoKeyPressed

    private void btnAnydeskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnydeskActionPerformed
        if (txt_conexao.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo anydesk vazio!");
        } else {
            EmpresaDAO edao = new EmpresaDAO();
            String any = txt_conexao.getText();
            edao.Conexao(any);
        }
    }//GEN-LAST:event_btnAnydeskActionPerformed

    private void btnBuscaFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaFotoActionPerformed
        /*jfc = new JFileChooser("C:\\");//abre o file choozer no c:\\
         if (jfc.showOpenDialog(lbl_Image) == JFileChooser.APPROVE_OPTION) {
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         Image image = toolkit.getImage(jfc.getSelectedFile().getAbsolutePath());
         Image imagedResized = image.getScaledInstance(200, 250, Image.SCALE_DEFAULT);//Coloca a altura e largura para o label de perfil do usuario
         Image imagedResized2 = image.getScaledInstance(115, 108, Image.SCALE_DEFAULT);//Coloca a altura e largura para o label de imagem do usuario inicial
         ImageIcon imageIcon = new ImageIcon(imagedResized);
         ImageIcon imageIcon2 = new ImageIcon(imagedResized2);

         lbl_Image.setIcon(imageIcon);
         lb_Foto.setIcon(imageIcon2);
         lbl_TitImage.setText(jfc.getSelectedFile().getName());

         file = new File(jfc.getSelectedFile().getPath());
         }*/

        //DEFINE IMAGEM NO FILECHOSSER
        final JLabel img = new JLabel();
        img.setPreferredSize(new Dimension(200, 200));//seta a dimensão da Label

        img.setHorizontalAlignment(JLabel.CENTER);

        JFileChooser file = new JFileChooser("C:\\");//Abre o fileChooser no C:
        file.setAccessory(img);//adciona a imagem ao file chooser como acessorio
        file.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                try {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();//super classe que pega atributos de um objeto
                    Image image = toolkit.getImage(file.getSelectedFile().getPath());//pega o caminho do arquivo selecionado
                    Image imagedResized = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);//define o tamanho da imagem 
                    ImageIcon imageIcon = new ImageIcon(imagedResized);//converte a imagem para um ImageIcon

                    if (evt.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                        img.setText("");
                        img.setIcon(imageIcon);
                    }
                } catch (Exception e) {
                    img.setText("Sem imagem");
                    img.setIcon(new ImageIcon());
                }
            }
        });

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.img", "jpg", "gif", "png");//Define os formatos que ele vai aceitar
        file.addChoosableFileFilter(filter);//Adiciona os filtros ao filechooser

        int result = file.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();

            lbl_TitImage.setText(file.getSelectedFile().getName());

            roundPhoto(path);
            //file = new File(jfc.getSelectedFile().getPath());
            file2 = new File(file.getSelectedFile().getPath());
        } else if (result == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Nenhuma foto Selecionada!");
        }
    }//GEN-LAST:event_btnBuscaFotoActionPerformed

    private void btnSalvaFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvaFotoActionPerformed
        Usuario user = new Usuario();
        UsuarioDAO userDao = new UsuarioDAO();
        JOptionPane.showMessageDialog(null, idLogado3);
        user.setImagem(lbl_TitImage.getText());
        userDao.gravaImagem(user, idLogado3);
        try {
            String path = new File(".").getCanonicalPath();
            FileUtils.copyFileToDirectory(file2, new File(path + "/image"));//FileUtils -> da biblioteca commons-io-2.4
        } catch (IOException ex) {
            Logger.getLogger(Principal3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalvaFotoActionPerformed

    private void txt_Puser_PesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_Puser_PesquisarMouseClicked
        /*if (txt_Puser_Pesquisar.getText().equals("Pesquisar Chamado....")) {
         txt_Puser_Pesquisar.setText("");
         } else {
         txt_Puser_Pesquisar.setText("Pesquisar Chamado....");
         }*/
    }//GEN-LAST:event_txt_Puser_PesquisarMouseClicked

    private void txt_Puser_PesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_PesquisarFocusLost
        /*if (txt_Puser_Pesquisar.getText().equals("")) {
         txt_Puser_Pesquisar.setText("Pesquisar Chamado....");
         } else {
         txt_Puser_Pesquisar.setText("");
         }*/

    }//GEN-LAST:event_txt_Puser_PesquisarFocusLost

    private void txt_Puser_dataFimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataFimFocusLost
        if (txt_Puser_dataFim.getText().equals("")) {
            txt_Puser_dataFim.setText("Data final");
        }
    }//GEN-LAST:event_txt_Puser_dataFimFocusLost

    private void txt_Puser_dataIniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataIniFocusLost
        if (txt_Puser_dataIni.getText().equals("")) {
            txt_Puser_dataIni.setText("Data inicial");
        }
    }//GEN-LAST:event_txt_Puser_dataIniFocusLost

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        seleccionar_pdf();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txt_telefone_chamado2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefone_chamado2KeyReleased
        txt_telefone_chamado2.setText(JMascara.GetJmascaraFone(txt_telefone_chamado2.getText()));
    }//GEN-LAST:event_txt_telefone_chamado2KeyReleased

    private void txt_conexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_conexaoActionPerformed

    }//GEN-LAST:event_txt_conexaoActionPerformed

    private void btnPesquisarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarUserActionPerformed
        String dataIni = txt_Puser_dataIni.getText().trim();
        String dataFim = txt_Puser_dataFim.getText().trim();
        String campoTexto = txt_Puser_Pesquisar.getText();

        if (dataIni.equals("Data inicial") || dataFim.equals("Data final")) {
            if (radEmpUser.isSelected()) {
                String data = "nome_emp";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radFuncUser.isSelected()) {
                String data = "usuario";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radDescUser.isSelected()) {
                String data = "texto";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radClienteUser.isSelected()) {
                String data = "nome";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radTagsUser.isSelected()) {
                String data = "tags";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
        } else {
            if (radEmpUser.isSelected()) {
                String data = "nome_emp";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }

            if (radFuncUser.isSelected()) {
                String data = "usuario";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }

            if (radDescUser.isSelected()) {
                String data = "tags";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }
            if (radClienteUser.isSelected()) {
                //txt_Pesquisa_Chamado.setDocument(new soNumeros());
                String data = "nome";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }
            if (radTagsUser.isSelected()) {
                String data = "tags";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }
        }
    }//GEN-LAST:event_btnPesquisarUserActionPerformed

    private void txt_Puser_dataIniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataIniFocusGained
        if (txt_Puser_dataIni.getText().equals("Data inicial")) {
            txt_Puser_dataIni.setText("");
        }
    }//GEN-LAST:event_txt_Puser_dataIniFocusGained

    private void txt_Puser_dataFimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataFimFocusGained
        if (txt_Puser_dataFim.getText().equals("Data final")) {
            txt_Puser_dataFim.setText("");
        }
    }//GEN-LAST:event_txt_Puser_dataFimFocusGained

    private void btnConverteExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConverteExcelActionPerformed
        exportarExcel excel = new exportarExcel();
        excel.crearArchivo(jt_Painel_User_Chamado);
    }//GEN-LAST:event_btnConverteExcelActionPerformed

    private void btn_salvar_chamadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvar_chamadoMouseEntered
        btn_salvar_chamado.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_salvar_chamadoMouseEntered

    private void btn_salvar_chamadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvar_chamadoMouseExited
        btn_salvar_chamado.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btn_salvar_chamadoMouseExited

    private void Btn_LimparMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_LimparMouseEntered
        Btn_Limpar.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_Btn_LimparMouseEntered

    private void Btn_LimparMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_LimparMouseExited
        Btn_Limpar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_Btn_LimparMouseExited

    private void Btn_ChamadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_ChamadoMouseClicked

    }//GEN-LAST:event_Btn_ChamadoMouseClicked

    private void btnguardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseEntered
        btnguardar.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btnguardarMouseEntered

    private void btneliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneliminarMouseEntered
        btneliminar.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btneliminarMouseEntered

    private void btneliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneliminarMouseExited
        btneliminar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btneliminarMouseExited

    private void btnmodificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmodificarMouseEntered
        btnmodificar.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btnmodificarMouseEntered

    private void btnmodificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnmodificarMouseExited
        btnmodificar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnmodificarMouseExited

    private void btnnovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnnovoMouseEntered
        btnnovo.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btnnovoMouseEntered

    private void btnnovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnnovoMouseExited
        btnnovo.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnnovoMouseExited

    private void btnguardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseExited
        btnguardar.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnguardarMouseExited

    private void btn_salvar_agendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvar_agendaMouseEntered
        btn_salvar_agenda.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_salvar_agendaMouseEntered

    private void btn_salvar_agendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvar_agendaMouseExited
        btn_salvar_agenda.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btn_salvar_agendaMouseExited

    private void btn_atualizar_agendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_atualizar_agendaMouseEntered
        btn_atualizar_agenda.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_atualizar_agendaMouseEntered

    private void btn_atualizar_agendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_atualizar_agendaMouseExited
        btn_atualizar_agenda.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btn_atualizar_agendaMouseExited

    private void btn_excluir_agendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_excluir_agendaMouseEntered
        btn_excluir_agenda.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_excluir_agendaMouseEntered

    private void btn_excluir_agendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_excluir_agendaMouseExited
        btn_excluir_agenda.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btn_excluir_agendaMouseExited

    private void novoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_novoMouseEntered
        novo.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_novoMouseEntered

    private void novoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_novoMouseExited
        novo.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_novoMouseExited

    private void lb_chamado_dia_IMGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_chamado_dia_IMGMouseClicked
        //DEFINE LISTA CHAMADOS DIA/SEMANA/MES
        new Lista_ChamadosDia(userLogado3).setVisible(true);
    }//GEN-LAST:event_lb_chamado_dia_IMGMouseClicked

    private void lb_chamado_semana_IMGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_chamado_semana_IMGMouseClicked
        //DEFINE LISTA CHAMADOS DIA/SEMANA/MES
        new Lista_ChamadosSemana(userLogado3).setVisible(true);
    }//GEN-LAST:event_lb_chamado_semana_IMGMouseClicked

    private void lb_Chamado_mes_IMGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_Chamado_mes_IMGMouseClicked
        //DEFINE LISTA CHAMADOS DIA/SEMANA/MES
        new Lista_ChamadosMes(userLogado3).setVisible(true);
    }//GEN-LAST:event_lb_Chamado_mes_IMGMouseClicked

    private void btn_countChamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_countChamadoActionPerformed
        new ListaChamadosAbertos(userLogado3).setVisible(true);
    }//GEN-LAST:event_btn_countChamadoActionPerformed

    class JTableMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JOptionPane.showMessageDialog(null, "Visualizar", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            java.util.logging.Logger.getLogger(Principal3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal3().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Principal3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AgendaView;
    private javax.swing.JPanel BackgroundFrame;
    public static javax.swing.JPanel Body;
    private javax.swing.JButton Btn_Agenda;
    private javax.swing.JButton Btn_Cadastrar;
    private javax.swing.JButton Btn_Chamado;
    private javax.swing.JButton Btn_Limpar;
    private javax.swing.JButton Btn_PRO;
    private javax.swing.JButton Btn_Print;
    private javax.swing.JButton Btn_Relatorio;
    private javax.swing.JButton Btn_Sites1;
    private javax.swing.JPanel CadFunc;
    private javax.swing.JPanel Chamados;
    private javax.swing.JPanel ChamadosView;
    public static javax.swing.JPanel Config;
    private javax.swing.JScrollPane DescricaoChamado;
    private javax.swing.ButtonGroup GpEmp;
    private javax.swing.ButtonGroup GpPro;
    private javax.swing.JPanel Header;
    private javax.swing.JLabel Lb_Data_Principal;
    private javax.swing.JLabel Lb_Hora;
    private javax.swing.JLabel Lb_Hora_Principal;
    private javax.swing.JLabel Lb_data;
    private javax.swing.JPanel Menu;
    private javax.swing.JPanel PanelCadastrarPRo;
    private javax.swing.JScrollPane PanelDiaSemanaMEs;
    private javax.swing.JPanel PanelListaPRo;
    private javax.swing.JPanel Perfil;
    private javax.swing.JPanel Pesq_chammado;
    private javax.swing.ButtonGroup PesquisaChamadoUser;
    private javax.swing.JPanel ProView2;
    private javax.swing.JRadioButton RadEmp;
    private javax.swing.JPanel Ramais;
    private javax.swing.JPanel Servidores;
    private javax.swing.JPanel SitesView;
    private javax.swing.ButtonGroup StatusChamado;
    public static javax.swing.JTabbedPane Tab_Chamado;
    private javax.swing.JScrollPane Tabela10Chamados;
    private javax.swing.JPanel VersoesView;
    private javax.swing.JPanel VrEstoque;
    private javax.swing.JPanel VrNfce;
    private javax.swing.JPanel VrNfce1;
    private javax.swing.JPanel VrNfce2;
    private javax.swing.JToggleButton btnAnydesk;
    private javax.swing.JButton btnBuscaFoto;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnConverteExcel;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnMaximize;
    private javax.swing.JButton btnMinimize;
    private javax.swing.JButton btnPesquisarUser;
    private javax.swing.JButton btnSalvaFoto;
    private javax.swing.JButton btn_CadEmp;
    private javax.swing.JButton btn_CadFunc;
    private javax.swing.JButton btn_CadPro;
    private javax.swing.JButton btn_CadRamal;
    private javax.swing.JButton btn_Config;
    private javax.swing.JButton btn_RelUsers;
    private javax.swing.JButton btn_V_editar;
    private javax.swing.JButton btn_V_excluir;
    private javax.swing.JButton btn_V_novo;
    private javax.swing.JButton btn_V_salvar;
    private javax.swing.JButton btn_atualizar_agenda;
    private javax.swing.JButton btn_cadUser;
    private static javax.swing.JButton btn_countChamado;
    private javax.swing.JButton btn_excluir_agenda;
    private javax.swing.JButton btn_salvar_agenda;
    private javax.swing.JButton btn_salvar_chamado;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnnovo;
    private javax.swing.JComboBox combo_V_versionW;
    public static javax.swing.JComboBox<Object> combo_emp;
    private javax.swing.JComboBox combo_emp2;
    public static javax.swing.JComboBox<Object> combo_func;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel jp_Pesquisa;
    private javax.swing.JPanel jp_TabelaAgenda;
    private javax.swing.JPanel jp_cadAgenda;
    private javax.swing.JTable jt_Painel_User_Chamado;
    private javax.swing.JLabel lb_1;
    private javax.swing.JLabel lb_2;
    private javax.swing.JLabel lb_3;
    private javax.swing.JLabel lb_4;
    private javax.swing.JLabel lb_Chamado_Dia;
    private javax.swing.JLabel lb_Chamado_mes;
    private javax.swing.JLabel lb_Chamado_mes_IMG;
    private javax.swing.JLabel lb_Chamado_semana;
    private javax.swing.JLabel lb_Conf_Livre;
    private javax.swing.JLabel lb_Conf_MLivre;
    private javax.swing.JLabel lb_Conf_MTotal;
    private javax.swing.JLabel lb_Conf_MUsada;
    private javax.swing.JLabel lb_Conf_Total;
    private javax.swing.JLabel lb_Conf_Usado;
    private javax.swing.JLabel lb_Foto;
    private javax.swing.JLabel lb_Ip;
    private javax.swing.JLabel lb_chamado_dia_IMG;
    private javax.swing.JLabel lb_chamado_semana_IMG;
    private javax.swing.JLabel lb_e_Banco_Checkout;
    private javax.swing.JLabel lb_e_Banco_Est;
    private javax.swing.JLabel lb_e_Banco_Nfce;
    private javax.swing.JLabel lb_e_Banco_Nfe;
    private javax.swing.JLabel lb_e_Banco_Sisseg;
    private javax.swing.JLabel lb_e_Cnpj_emp;
    private javax.swing.JLabel lb_e_Ip_Nfce;
    private javax.swing.JLabel lb_e_Ip_Nfe;
    private javax.swing.JLabel lb_e_Ip_scef;
    private javax.swing.JLabel lb_e_Mac_nfce;
    private javax.swing.JLabel lb_e_Mac_nfe;
    private javax.swing.JLabel lb_e_Mobile;
    private javax.swing.JLabel lb_e_Tel_empresa;
    private javax.swing.JLabel lb_e_coletor;
    private javax.swing.JLabel lb_e_mac_scef;
    private javax.swing.JLabel lb_e_nomeEmpresa;
    private javax.swing.JLabel lb_mac;
    private javax.swing.JLabel lbl_Cronometro;
    private javax.swing.JLabel lbl_Image;
    private javax.swing.JLabel lbl_TitImage;
    private javax.swing.JButton novo;
    private javax.swing.JRadioButton radClienteUser;
    private javax.swing.JRadioButton radContPro;
    private javax.swing.JRadioButton radDescUser;
    private javax.swing.JRadioButton radEmpUser;
    private javax.swing.JRadioButton radFuncUser;
    private javax.swing.JRadioButton radTItPro;
    private javax.swing.JRadioButton radTagsUser;
    private javax.swing.JRadioButton rad_Aberto;
    private javax.swing.JRadioButton rad_Clien;
    private javax.swing.JRadioButton rad_Desc;
    private javax.swing.JRadioButton rad_Fechado;
    private javax.swing.JRadioButton rad_ID;
    private javax.swing.JRadioButton rad_Tags;
    private javax.swing.JRadioButton rad_logicom;
    private javax.swing.JTable tbl_Chamado;
    private javax.swing.JTable tbl_Chamado2;
    private javax.swing.JTable tbl_Ramais;
    private javax.swing.JTable tbl_agenda;
    private javax.swing.JTable tbl_empresa;
    private javax.swing.JTable tbl_pro;
    private javax.swing.JTable tbl_version;
    private javax.swing.JTextField txt_Pesquisa_Chamado;
    private javax.swing.JTextField txt_Puser_Pesquisar;
    private javax.swing.JTextField txt_Puser_dataFim;
    private javax.swing.JTextField txt_Puser_dataIni;
    public static javax.swing.JEditorPane txt_Solucao;
    private javax.swing.JFormattedTextField txt_V_dataRed;
    private javax.swing.JTextField txt_V_ext;
    private javax.swing.JFormattedTextField txt_V_horaRed;
    private javax.swing.JTextField txt_V_nome;
    private javax.swing.JTextField txt_V_tamanho;
    private javax.swing.JTextField txt_V_versao;
    private javax.swing.JEditorPane txt_chamado_desc1;
    private javax.swing.JTextField txt_conexao;
    private javax.swing.JTextArea txt_conteudoPRo;
    private javax.swing.JTextField txt_email_agenda;
    private javax.swing.JTextField txt_endereco_agenda;
    private javax.swing.JTextField txt_nome_agenda;
    private javax.swing.JTextField txt_nome_pro;
    private javax.swing.JTextField txt_observacao_agenda;
    private javax.swing.JTextField txt_pesquisa_agenda;
    private javax.swing.JTextField txt_pesquisa_versao;
    private javax.swing.JTextField txt_pesquisar;
    private javax.swing.JTextField txt_tag1;
    private javax.swing.JTextField txt_tag2;
    private javax.swing.JTextField txt_tag3;
    private javax.swing.JTextField txt_tag4;
    private javax.swing.JTextField txt_tag5;
    private javax.swing.JTextField txt_telefone_agenda;
    private javax.swing.JTextField txt_telefone_chamado2;
    private javax.swing.JTextField txt_titulo_chamado;
    public static javax.swing.JLabel userlogado2;
    // End of variables declaration//GEN-END:variables

    public class hora implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            Lb_Hora_Principal.setText(String.format("%1$tH:%1$tM:%1$tS", now));
        }
    }
}
