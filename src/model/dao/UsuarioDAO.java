package model.dao;

import View.LoginNew3;
import View.Principal3;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Chamado;
import model.bean.Funcionario;
import model.bean.Usuario;

/**
 *
 * @author Samuelson
 */
public class UsuarioDAO {

    public boolean checkLogin(String login, String senha) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean check = false;

        try {

            stmt = con.prepareStatement("SELECT * FROM usuario WHERE usuario = ? and senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);

            rs = stmt.executeQuery();

            if (rs.next()) {

                check = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return check;

    }

    public void gravaImagem(Usuario u, int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String msgEditar = "<html>"
                + "Imagem( "
                + "<b>"
                + u.getImagem()
                + "</b>"
                + ") salva com sucesso!";

        try {
            stmt = con.prepareStatement("UPDATE usuarios SET `image`=? WHERE  `id_func`=?");
            stmt.setString(1, u.getImagem());
            stmt.setInt(2, id);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "id: " + id + " foto: " + u.getImagem());

        } catch (Exception e) {
        }
    }

    public void checaLogin() {
        ConnectionFactory conec = new ConnectionFactory();
        Connection con = ConnectionFactory.getConnection();

        String user = LoginNew3.txt_Login.getText();
        Statement st = null;
        ResultSet rs = null;

        try {
            con = conec.getConexion();
            st = con.createStatement();
            rs = st.executeQuery("select * from usuarios where ='" + user + "'");
            rs.first();

            if (rs.getString("permissao").equals("a")) {
                Principal3.Body.removeAll();
                Principal3.Body.repaint();
                Principal3.Body.revalidate();
                Principal3.Body.add(Principal3.Config);
                Principal3.Body.repaint();
                Principal3.Body.revalidate();
            } else {
                JOptionPane.showMessageDialog(null, "Você não tem acesso!");
            }
        } catch (Exception e) {
        }
    }

    public void SalvarUsuario(Usuario u) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO usuarios (`usuario`, `senha`, `permissao`) VALUES (?, ?, ?)");
            stmt.setString(1, u.getUsuario());
            stmt.setString(2, u.getSenha());
            stmt.setString(3, u.getPermissao());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salva com sucesso");
        } catch (Exception e) {
        }
    }

    public List<Usuario> ListarUsuario() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> UsuarioList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios ORDER BY id_func asc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_func"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPermissao(rs.getString("permissao"));
                UsuarioList.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return UsuarioList;

    }

    public void DeleteUsuario(Usuario u) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM usuarios WHERE  `id_func`= ?");
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario - " + u.getUsuario() + " Excluido com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir" + e);
        }
    }

    public void update(Usuario u) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String msgEditar = "<html>"
                + "Funcionario( "
                + "<b>"
                + u.getUsuario()
                + "</b>"
                + ") editado com sucesso!";
        try {
            stmt = con.prepareStatement("UPDATE usuarios SET `usuario`= ?, `senha`= ?, `permissao`= ? WHERE  `id_func`=?");
            stmt.setString(1, u.getUsuario());
            stmt.setString(2, u.getSenha());
            stmt.setString(3, u.getPermissao());
            stmt.setInt(4, u.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, stmt);
            JOptionPane.showMessageDialog(null, msgEditar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<Usuario> PesquisarUsuario(String usuario) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE usuario LIKE '%"+usuario+"%'");
            //System.out.println(stmt);
            rs = stmt.executeQuery();
            
            
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id_func"));
                user.setUsuario(rs.getString("usuario"));
                user.setSenha(rs.getString("senha"));
                user.setPermissao(rs.getString("permissao"));
                usuarios.add(user);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return usuarios;
    }
}
