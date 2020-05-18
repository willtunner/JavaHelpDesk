/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author Suporte-07
 */
public class Versoes {
    private int id;
    private String nome;
    private String versao;
    private String winversion;
    private String ext;
    private String dataredmine;
    private String horaredmine;
    private String tamanho;
    private String usuariocadastrou;
    private String atualizado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getWinversion() {
        return winversion;
    }

    public void setWinversion(String winversion) {
        this.winversion = winversion;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDataredmine() {
        return dataredmine;
    }

    public void setDataredmine(String dataredmine) {
        this.dataredmine = dataredmine;
    }

    public String getHoraredmine() {
        return horaredmine;
    }

    public void setHoraredmine(String horaredmine) {
        this.horaredmine = horaredmine;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getUsuariocadastrou() {
        return usuariocadastrou;
    }

    public void setUsuariocadastrou(String usuariocadastrou) {
        this.usuariocadastrou = usuariocadastrou;
    }

    public String getAtualizado() {
        return atualizado;
    }

    public void setAtualizado(String atualizado) {
        this.atualizado = atualizado;
    }

    @Override
    public String toString() {
        return "Versoes{" + "nome=" + nome + ", versao=" + versao + ", usuariocadastrou=" + usuariocadastrou + '}';
    }
    
    
    
}
