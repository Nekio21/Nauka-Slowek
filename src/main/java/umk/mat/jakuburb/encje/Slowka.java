package umk.mat.jakuburb.encje;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Slowka")
public class Slowka {

    @Id
    @SequenceGenerator(name="sekwencja", sequenceName = "slowka_idslowko_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencja")
    @Column(name = "idslowko")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idzestawu")
    private ZestawySlowek zestawySlowek;

    @Column(name = "texta")
    private String textA;

    @Column(name = "textb")
    private String textB;

    @Column(name = "dobreodpowiedzi")
    private Integer dobreOdpowiedzi;

    @Column(name = "zleodpowiedzi")
    private Integer zleOdpowiedzi;

    @Column(name = "punkty")
    private Integer punkty;

    @Column(name = "datastworzenia")
    private LocalDateTime dataStworzenia;

    @Column(name = "ostatniagra")
    private LocalDateTime ostatniaGra;

    @OneToMany(mappedBy = "slowka", fetch = FetchType.LAZY)
    private List<SlowkaGra> slowkaGraList = new ArrayList<>();

    @Column(name = "ostatniaodpowiedzdobra")
    private Boolean ostatniaOdpowiedzDobra;

    public Slowka(){

    }

    public Slowka(ZestawySlowek zestawySlowek, String textA, String textB, Integer dobreOdpowiedzi, Integer zleOdpowiedzi, Integer punkty, LocalDateTime dataStworzenia, LocalDateTime ostatniaGra, List<SlowkaGra> slowkaGraList, Boolean ostatniaOdpowiedzDobra) {
        this.zestawySlowek = zestawySlowek;
        this.textA = textA;
        this.textB = textB;
        this.dobreOdpowiedzi = dobreOdpowiedzi;
        this.zleOdpowiedzi = zleOdpowiedzi;
        this.punkty = punkty;
        this.dataStworzenia = dataStworzenia;
        this.ostatniaGra = ostatniaGra;
        this.slowkaGraList = slowkaGraList;
        this.ostatniaOdpowiedzDobra = ostatniaOdpowiedzDobra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZestawySlowek getIdZestawu() {
        return zestawySlowek;
    }

    public void setIdZestawu(ZestawySlowek zestawySlowek) {
        this.zestawySlowek = zestawySlowek;
    }

    public String getTextA() {
        return textA;
    }

    public void setTextA(String textA) {
        this.textA = textA;
    }

    public String getTextB() {
        return textB;
    }

    public void setTextB(String textB) {
        this.textB = textB;
    }

    public Integer getDobreOdpowiedzi() {
        return dobreOdpowiedzi;
    }

    public void setDobreOdpowiedzi(Integer dobreOdpowiedzi) {
        this.dobreOdpowiedzi = dobreOdpowiedzi;
    }

    public Integer getZleOdpowiedzi() {
        return zleOdpowiedzi;
    }

    public void setZleOdpowiedzi(Integer zleOdpowiedzi) {
        this.zleOdpowiedzi = zleOdpowiedzi;
    }

    public Integer getPunkty() {
        return punkty;
    }

    public void setPunkty(Integer punkty) {
        this.punkty = punkty;
    }

    public LocalDateTime getDataStworzenia() {
        return dataStworzenia;
    }

    public void setDataStworzenia(LocalDateTime dataStworzenia) {
        this.dataStworzenia = dataStworzenia;
    }

    public LocalDateTime getOstatniaGra() {
        return ostatniaGra;
    }

    public void setOstatniaGra(LocalDateTime ostatniaGra) {
        this.ostatniaGra = ostatniaGra;
    }

    public ZestawySlowek getZestawySlowek() {
        return zestawySlowek;
    }

    public void setZestawySlowek(ZestawySlowek zestawySlowek) {
        this.zestawySlowek = zestawySlowek;
    }

    public List<SlowkaGra> getSlowkaGraList() {
        return slowkaGraList;
    }

    public void setSlowkaGraList(List<SlowkaGra> slowkaGraList) {
        this.slowkaGraList = slowkaGraList;
    }

    public Boolean getOstatniaOdpowiedzDobra() {
        return ostatniaOdpowiedzDobra;
    }

    public void setOstatniaOdpowiedzDobra(Boolean ostatniaOdpowiedzDobra) {
        this.ostatniaOdpowiedzDobra = ostatniaOdpowiedzDobra;
    }

    @Override
    public String toString() {
        return "Slowka{" +
                "id=" + id +
                ", textA='" + textA + '\'' +
                ", textB='" + textB + '\'' +
                ", dobreOdpowiedzi=" + dobreOdpowiedzi +
                ", zleOdpowiedzi=" + zleOdpowiedzi +
                ", punkty=" + punkty +
                ", dataStworzenia=" + dataStworzenia +
                ", ostatniaGra=" + ostatniaGra +
                '}';
    }
}
