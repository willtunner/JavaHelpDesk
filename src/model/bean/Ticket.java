/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author WillTunner
 */
public class Ticket {

    private int idticket;
    private String titulo;
    private String descricao;
    private String status;
    private String desenvolvedor;
    private String empresa;
    private String cliente;
    private String telefone;
    private Date dataticket;
    private Time horaticket;
    private String idchamado;
    private String conexaochamado;
    private String tags;
    
    private String andamento;
    private String pendente;
    private String resolvido;
    
    
    public Ticket(){
        
    }
    
    public Ticket(int idticket,String titulo, String descricao, String status, String desenvolvedor, String empresa, String cliente, String telefone, Date dataticket, Time horaticket, String idchamado, String tags ){
        this.idticket = idticket;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.desenvolvedor = desenvolvedor;
        this.empresa = empresa;
        this.cliente = cliente;
        this.telefone = telefone;
        this.dataticket = dataticket;
        this.horaticket = horaticket;
        this.idchamado = idchamado;
        this.tags = tags;
    }
    
    
    public int getIdticket() {
        return idticket;
    }

    public void setIdticket(int idticket) {
        this.idticket = idticket;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(String desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataticket() {
        return dataticket;
    }

    public void setDataticket(Date dataticket) {
        this.dataticket = dataticket;
    }

    public Time getHoraticket() {
        return horaticket;
    }

    public void setHoraticket(Time horaticket) {
        this.horaticket = horaticket;
    }

    @Override
    public String toString() {
        return "Ticket{" + "idticket=" + idticket + ", titulo=" + titulo + ", descricao=" + descricao + ", status=" + status + ", desenvolvedor=" + desenvolvedor + ", empresa=" + empresa + ", cliente=" + cliente + ", telefone=" + telefone + ", dataticket=" + dataticket + ", horaticket=" + horaticket + '}';
    }

    public String getIdchamado() {
        return idchamado;
    }

    public void setIdchamado(String idchamado) {
        this.idchamado = idchamado;
    }

    public String getConexaochamado() {
        return conexaochamado;
    }

    public void setConexaochamado(String conexaochamado) {
        this.conexaochamado = conexaochamado;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAndamento() {
        return andamento;
    }

    public void setAndamento(String andamento) {
        this.andamento = andamento;
    }

    public String getPendente() {
        return pendente;
    }

    public void setPendente(String pendente) {
        this.pendente = pendente;
    }

    public String getResolvido() {
        return resolvido;
    }

    public void setResolvido(String resolvido) {
        this.resolvido = resolvido;
    }

    
}
