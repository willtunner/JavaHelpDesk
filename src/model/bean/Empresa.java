package model.bean;

/**
 *
 * @author Suporte-07
 */
public class Empresa {

    private int id_emp;
    private String nome_emp;
    private String telefone;
    private String cnpj;
    private String obs;
    private String ip_scef;
    private String mac_scef;
    private String con_scef;
    private String ip_nfce;
    private String mac_nfce;
    private String con_nfce;
    private String ip_nfe;
    private String mac_nfe;
    private String con_nfe;
    private String ip_mobile;
    private String ip_coletor;
    private String v_estoque;
    private String v_nfce;
    private String v_nfe;
    private String v_sisseg;
    private String v_checkout;

    public Empresa() {
    }

    public Empresa(int id_emp, String nome_emp, String telefone, String cnpj, String obs, String ip_scef, String mac_scef, String con_scef, String ip_nfce, String mac_nfce, String con_nfce, String ip_nfe, String mac_nfe, String con_nfe, String ip_mobile, String ip_coletor, String v_estoque, String v_nfce, String v_nfe, String v_sisseg, String v_checkout) {
        this.id_emp = id_emp;
        this.nome_emp = nome_emp;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.obs = obs;
        this.ip_scef = ip_scef;
        this.mac_scef = mac_scef;
        this.con_scef = con_scef;
        this.ip_nfce = ip_nfce;
        this.mac_nfce = mac_nfce;
        this.con_nfce = con_nfce;
        this.ip_nfe = ip_nfe;
        this.mac_nfe = mac_nfe;
        this.con_nfe = con_nfe;
        this.ip_mobile = ip_mobile;
        this.ip_coletor = ip_coletor;
        this.v_estoque = v_estoque;
        this.v_nfce = v_nfce;
        this.v_nfe = v_nfe;
        this.v_sisseg = v_sisseg;
        this.v_checkout = v_checkout;
    }
    
    public String getV_checkout() {
        return v_checkout;
    }

    public void setV_checkout(String v_checkout) {
        this.v_checkout = v_checkout;
    }
    

    public String getV_estoque() {
        return v_estoque;
    }

    public void setV_estoque(String v_estoque) {
        this.v_estoque = v_estoque;
    }

    public String getV_nfce() {
        return v_nfce;
    }

    public void setV_nfce(String v_nfce) {
        this.v_nfce = v_nfce;
    }

    public String getV_nfe() {
        return v_nfe;
    }

    public void setV_nfe(String v_nfe) {
        this.v_nfe = v_nfe;
    }

    public String getV_sisseg() {
        return v_sisseg;
    }

    public void setV_sisseg(String v_sisseg) {
        this.v_sisseg = v_sisseg;
    }

    public String getNome_emp() {
        return nome_emp;
    }

    public void setNome_emp(String nome_emp) {
        this.nome_emp = nome_emp;
    }

    public String getIp_scef() {
        return ip_scef;
    }

    public void setIp_scef(String ip_scef) {
        this.ip_scef = ip_scef;
    }

    public String getMac_scef() {
        return mac_scef;
    }

    public void setMac_scef(String mac_scef) {
        this.mac_scef = mac_scef;
    }

    public String getCon_scef() {
        return con_scef;
    }

    public void setCon_scef(String con_scef) {
        this.con_scef = con_scef;
    }

    public String getIp_nfce() {
        return ip_nfce;
    }

    public void setIp_nfce(String ip_nfce) {
        this.ip_nfce = ip_nfce;
    }

    public String getMac_nfce() {
        return mac_nfce;
    }

    public void setMac_nfce(String mac_nfce) {
        this.mac_nfce = mac_nfce;
    }

    public String getCon_nfce() {
        return con_nfce;
    }

    public void setCon_nfce(String con_nfce) {
        this.con_nfce = con_nfce;
    }

    public String getIp_nfe() {
        return ip_nfe;
    }

    public void setIp_nfe(String ip_nfe) {
        this.ip_nfe = ip_nfe;
    }

    public String getMac_nfe() {
        return mac_nfe;
    }

    public void setMac_nfe(String mac_nfe) {
        this.mac_nfe = mac_nfe;
    }

    public String getCon_nfe() {
        return con_nfe;
    }

    public void setCon_nfe(String con_nfe) {
        this.con_nfe = con_nfe;
    }

    public String getnome_emp(String nome_emp) {
        return nome_emp;
    }

    public void setnome_emp(String nome_emp) {
        this.nome_emp = nome_emp;
    }

    public String getIp_mobile() {
        return ip_mobile;
    }

    public void setIp_mobile(String ip_mobile) {
        this.ip_mobile = ip_mobile;
    }

    public String getIp_coletor() {
        return ip_coletor;
    }

    public void setIp_coletor(String ip_coletor) {
        this.ip_coletor = ip_coletor;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return getNome_emp();
    }
}
