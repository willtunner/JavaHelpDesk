package model.bean;

import java.sql.Time;
import java.util.Date;
import javax.swing.JOptionPane;

public class Chamado {
    private int id;
    private String nome_emp;
    private String nome;
    private String fone;
    private String titulo;
    private String texto;
    private String tags;
    private String solucao;
    private String conexao;
    private String user;
    private Date data;
    private String hora;
    private String status;
    private String editado;
    private String dataeditado;
    private String horaeditado;
    private Time cronometro;
    private Time HoraFechaChamado;
    private Date DataChamadoFechado;
    private String total;
    
    private String abertos;
    private String fechados;

    
    
    public Date getDataChamadoFechado() {
        return DataChamadoFechado;
    }

    public void setDataChamadoFechado(Date DataChamadoFechado) {
        this.DataChamadoFechado = DataChamadoFechado;
    }

    public Time getCronometro() {
        return cronometro;
    }

    public void setCronometro(Time cronometro) {
        this.cronometro = cronometro;
    }

    public Time getHoraFechaChamado() {
        return HoraFechaChamado;
    }

    public void setHoraFechaChamado(Time HoraFechaChamado) {
        this.HoraFechaChamado = HoraFechaChamado;
    }

    public String getHoraeditado() {
        return horaeditado;
    }

    public void setHoraeditado(String horaeditado) {
        this.horaeditado = horaeditado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEditado() {
        return editado;
    }

    public void setEditado(String editado) {
        this.editado = editado;
    }

    public String getDataeditado() {
        return dataeditado;
    }

    public void setDataeditado(String dataeditado) {
        this.dataeditado = dataeditado;
    }
    
    public Chamado(){
        
    }

    @Override
    public String toString() {
        return "Chamado{" + "cronometro=" + cronometro + '}';
    }
    
    public Chamado (String user){
        this.user = user;
    }
    
    public Chamado(int id,String nome_emp, String nome,  String fone,  String titulo,  String texto,  String tags, String solucao, String user, String data,String hora){
       //this.id_emp = id_emp; 
        this.id =id;
        this.nome_emp=nome_emp;
        this.fone =fone;
        this.titulo =titulo;
        this.texto =texto;
        this.tags =tags;
        this.solucao =solucao;
        this.user =user;
        this.hora =hora;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    

    public String getConexao() {
        return conexao;
    }

    public void setConexao(String conexao) {
        this.conexao = conexao;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_emp() {
        return nome_emp;
    }

    public void setNome_emp(String nome_emp) {
        this.nome_emp = nome_emp;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

     
    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void getId_func(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String setNome(Chamado c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(String Id) {
        JOptionPane.showMessageDialog(null, "Erro no campo id"+ Id);
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAbertos() {
        return abertos;
    }

    public void setAbertos(String abertos) {
        this.abertos = abertos;
    }

    public String getFechados() {
        return fechados;
    }

    public void setFechados(String fechados) {
        this.fechados = fechados;
    }

    
}


























