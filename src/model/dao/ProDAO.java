/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import Tabla.imgTabla;
import connection.ConnectionFactory;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.bean.ProBean;

/**
 *
 * @author William
 */
public class ProDAO {

    ProDAO dao = null;

    public void Salvar_Pro(ProBean vo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO pro (titulo_pro, conteudo_pro, pdf) VALUES(?, ?, ?)");
            ps.setString(1, vo.getTitulo_pro());
            ps.setString(2, vo.getConteudo_pro());
            ps.setBytes(3, vo.getArquivopdf());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "P.R.O :" + vo.getTitulo_pro() + " salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    //mostra os pros na tabela
    public void visualizar_PdfVO(JTable tabla) {
        tabla.setDefaultRenderer(Object.class, new imgTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dt.addColumn("ID");
        dt.addColumn("Titulo");
        dt.addColumn("Conteudo");
        dt.addColumn("PDF");

        ImageIcon icono = null;
        if (get_Image("/imagem/32pdf.png") != null) {
            icono = new ImageIcon(get_Image("/imagem/32pdf.png"));
        }

        dao = new ProDAO();
        ProBean vo = new ProBean();
        ArrayList<ProBean> list = dao.Listar_PdfVO();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[4];
                vo = list.get(i);
                fila[0] = vo.getId_pro();
                fila[1] = vo.getTitulo_pro();
                fila[2] = vo.getConteudo_pro();
                if (vo.getArquivopdf() != null) {
                    fila[3] = new JButton(icono);
                } else {
                    fila[3] = new JButton("Vazio");
                }
                dt.addRow(fila);
            }
            tabla.setModel(dt);
            tabla.setRowHeight(32);
        }
    }

    public ArrayList<ProBean> Listar_PdfVO() {
        ArrayList<ProBean> list = new ArrayList<ProBean>();
        Connection conec = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM pro;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProBean vo = new ProBean();
                vo.setId_pro(rs.getInt(1));
                vo.setTitulo_pro(rs.getString(2));
                vo.setConteudo_pro(rs.getString(3));
                vo.setArquivopdf(rs.getBytes(4));
                list.add(vo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                conec.close();
            } catch (Exception ex) {
            }
        }
        return list;
    }

    public Image get_Image(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta));
            Image mainIcon = imageIcon.getImage();
            return mainIcon;
        } catch (Exception e) {
        }
        return null;
    }

    //Permite mostrar PDF contenido en la base de datos
    public void ejecutar_archivoPDF(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        byte[] b = null;

        try {
            ps = con.prepareStatement("SELECT pdf FROM pro WHERE id_pro = ?;");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                b = rs.getBytes(1);
            }
            InputStream bos = new ByteArrayInputStream(b);

            int tamanoInput = bos.available();
            byte[] datosPDF = new byte[tamanoInput];
            bos.read(datosPDF, 0, tamanoInput);

            OutputStream out = new FileOutputStream("new.pdf");
            out.write(datosPDF);

            out.close();
            bos.close();
            ps.close();
            rs.close();
            con.close();

        } catch (IOException | NumberFormatException | SQLException ex) {
            System.out.println("Error al abrir archivo PDF " + ex.getMessage());
        }
    }

    public void Excluir_Pro(ProBean vo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps = null;

        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Deseja Excluir ?", "SAIR", dialogButton);
            ps = con.prepareStatement("DELETE FROM pro WHERE id_pro = ?;");
            ps.setInt(1, vo.getId_pro());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    public void Pesquisa_Pro(JTable tabela, String titulo, String campoTabela) {
        tabela.setDefaultRenderer(Object.class, new imgTabla());
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dt.addColumn("ID");
        dt.addColumn("Titulo");
        dt.addColumn("Conteudo");
        dt.addColumn("P.R.O");

        ImageIcon icone = null;
        if (get_Image("/imagem/32pdf.png") != null) {
            icone = new ImageIcon(get_Image("/imagem/32pdf.png"));
        }

        //dao = new PdfDAO();
        ProBean vo = new ProBean();

        ArrayList<ProBean> list = Pesquisar_Pdf(titulo, campoTabela);

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Object fila[] = new Object[4];
                vo = list.get(i);
                fila[0] = vo.getId_pro();
                fila[1] = vo.getTitulo_pro();
                fila[2] = vo.getConteudo_pro();
                if (vo.getArquivopdf() != null) {
                    fila[3] = new JButton(icone);
                } else {
                    fila[3] = new JButton("Vazio");
                }

                dt.addRow(fila);
            }
            tabela.setModel(dt);
            tabela.setRowHeight(32);
        }
    }

    public ArrayList<ProBean> Pesquisar_Pdf(String titulo, String campoTabela) {
        ArrayList<ProBean> list = new ArrayList<>();
        Connection conec = ConnectionFactory.getConnection();
        String sql = "SELECT * FROM pro  WHERE "+campoTabela+" LIKE '%" + titulo + "%'";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProBean vo = new ProBean();
                vo.setId_pro(rs.getInt(1));
                vo.setTitulo_pro(rs.getString(2));
                vo.setConteudo_pro(rs.getString(3));
                vo.setArquivopdf(rs.getBytes(4));
                list.add(vo);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
                conec.close();
            } catch (Exception ex) {
            }
        }
        return list;
    }
}
