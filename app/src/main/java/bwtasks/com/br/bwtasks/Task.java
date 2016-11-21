package bwtasks.com.br.bwtasks;

/**
 * Created by gapan on 13/11/2016.
 */

public class Task {
    private Integer _id;
    private Integer hora;
    private Integer minuto;
    private String dias;
    private Boolean ativado;
    private String desc;
    private Integer dayOne;
    private Integer dayTwo;
    private Integer dayThr;

    public Integer getDayOne() {
        return dayOne;
    }

    public void setDayOne(Integer dayOne) {
        this.dayOne = dayOne;
    }

    public Integer getDayTwo() {
        return dayTwo;
    }

    public void setDayTwo(Integer dayTwo) {
        this.dayTwo = dayTwo;
    }

    public Integer getDayThr() {
        return dayThr;
    }

    public void setDayThr(Integer dayThr) {
        this.dayThr = dayThr;
    }

    public Integer getDayFou() {
        return dayFou;
    }

    public void setDayFou(Integer dayFou) {
        this.dayFou = dayFou;
    }

    public Integer getDayFiv() {
        return dayFiv;
    }

    public void setDayFiv(Integer dayFiv) {
        this.dayFiv = dayFiv;
    }

    public Integer getDaySix() {
        return daySix;
    }

    public void setDaySix(Integer daySix) {
        this.daySix = daySix;
    }

    public Integer getDaySev() {
        return daySev;
    }

    public void setDaySev(Integer daySev) {
        this.daySev = daySev;
    }

    private Integer dayFou;
    private Integer dayFiv;
    private Integer daySix;
    private Integer daySev;

    public Task(){

    }
    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getMinuto() {
        return minuto;
    }

    public void setMinuto(Integer minuto) {
        this.minuto = minuto;
    }

    public String getDias() {
        return dias;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public Boolean getAtivado() {
        return ativado;
    }

    public void setAtivado(Boolean ativado) {
        this.ativado = ativado;
    }

    public void set_id(Integer _id){
        this._id = _id;
    }

    public Integer get_id(){
        return _id;
    }

    public Task(Integer hora, Integer minuto, String dias, Boolean ativado) {
        this.hora = hora;
        this.minuto = minuto;
        this.dias = dias;
        this.ativado = ativado;

    }
    public Task(Integer hora, Integer minuto, String dias, Boolean ativado, String desc) {
        this.hora = hora;
        this.minuto = minuto;
        this.dias = dias;
        this.ativado = ativado;
        this.desc = desc;
    }


    public Task(Integer hora, Integer minuto, String dias, Boolean ativado, String desc, Integer dayOne, Integer dayTwo, Integer dayThr, Integer dayFou, Integer dayFiv, Integer daySix, Integer daySev) {
        this.hora = hora;
        this.minuto = minuto;
        this.dias = dias;
        this.ativado = ativado;
        this.desc = desc;
        this.dayOne = dayOne;
        this.dayTwo = dayTwo;
        this.dayThr = dayThr;
        this.dayFou = dayFou;
        this.dayFiv = dayFiv;
        this.daySix = daySix;
        this.daySev = daySev;
    }
}
