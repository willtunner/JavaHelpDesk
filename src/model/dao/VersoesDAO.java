/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import ds.desktop.notify.DesktopNotify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Ramais;
import model.bean.Versoes;

/**
 *
 * @author Suporte-07
 */
public class VersoesDAO {

    public void Cad_Versao(Versoes v, String user) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //stmt.setString(1, e.getNome_emp());
        try {
            stmt = con.prepareStatement("INSERT INTO versoes (`nome`, `versao`, `winversion`, `ext`, `dataredmine`, `horaredmine`, `tamanho`, `usuariocadastrou`, `atualizado`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, v.getNome());
            stmt.setString(2, v.getVersao());
            stmt.setString(3, v.getWinversion());
            stmt.setString(4, v.getExt());
            stmt.setString(5, v.getDataredmine());
            stmt.setString(6, v.getHoraredmine());
            stmt.setString(7, v.getTamanho());
            stmt.setString(8, user);
            stmt.setString(9, data);
            stmt.executeUpdate();
            
            DesktopNotify.showDesktopMessage("Versão Salva com Sucesso",
                    "Aplicativo: " + v.getNome()
                    + " - Versão: " + v.getVersao(),DesktopNotify.SUCCESS, 5000L);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Versões" + ex);
        }
    }
    
    public List<Versoes> ListarVersoes() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Versoes> versionList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM versoes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Versoes v = new Versoes();
                v.setId(rs.getInt("id"));
                v.setNome(rs.getString("nome"));
                v.setVersao(rs.getString("versao"));
                v.setWinversion(rs.getString("winversion"));
                v.setExt(rs.getString("ext"));
                v.setDataredmine(rs.getString("dataredmine"));
                v.setHoraredmine(rs.getString("horaredmine"));
                v.setTamanho(rs.getString("tamanho"));
                v.setUsuariocadastrou(rs.getString("usuariocadastrou"));
                v.setAtualizado(rs.getString("atualizado"));
                versionList.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return versionList;
    }
}
