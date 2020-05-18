/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import connection.ConnectionFactory;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Suporte-07
 */
public class ImagemPerfil {
    
    private connection.ConnectionFactory cc =  new ConnectionFactory();
    private Connection cn = cc.getConexion();
    private PreparedStatement ps;
    
    private String UPDATE = "UPDATE imagen SET nombre = ? WHERE id = 1";
    public String ruta_guardar = "./imagenes/";
    
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    
    public boolean isUpdate(ImagemPerfil f){
        try {
            String sql = f.UPDATE;
            
            ps = cn.prepareStatement(sql);
            ps.setString(1, f.getNombre());
            ps.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ImagemPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public String getNombreImagen(){
        try {
            String nombre = "";
            
            String SQL = "SELECT nombre FROM imagen WHERE id = 1";
            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            
            if(rs.next()){
                nombre = rs.getString("nombre");
            }
            return nombre;
        } catch (SQLException ex) {
            Logger.getLogger(ImagemPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nombre;
    }
    
    public Icon ajustarImagen(String rutaImagen, JLabel lblImagen){
        Image img = new ImageIcon(rutaImagen).getImage();
        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
        
        return img2;
    }
    
    public void copyImagen(String origen, String destino){
        Path DE = Paths.get(origen);
        Path A = Paths.get(destino);
        
        CopyOption[] opciones = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        };
        
        try {
            Files.copy(DE, A, opciones);
        } catch (IOException ex) {
            Logger.getLogger(ImagemPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
