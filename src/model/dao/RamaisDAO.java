/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Ramais;

/**
 *
 * @author William
 */
public class RamaisDAO {
    public void Inserir(Ramais r) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO ramais (`Nome`, `Ramal`) VALUES (?, ?)");
            stmt.setString(1, r.getNome());
            stmt.setString(2, r.getRamal());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Ramal - "+r.getRamal()+" : Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    
        public List<Ramais> Listar() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ramais> ramalist = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Ramais");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ramais ramais = new Ramais();
                ramais.setNome(rs.getString("Nome"));
                ramais.setRamal(rs.getString("Ramal"));
                ramalist.add(ramais);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return ramalist;

    }

}
