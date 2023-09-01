package umk.mat.jakuburb.encje;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Zestawy")
public class ZestawySlowek {
    @Id
    @SequenceGenerator(name="sekwencjaa", sequenceName = "zestawy_idzestawy_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencjaa")
    @Column(name = "idzestawy")
    private Long id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "punkty")
    private Integer punkty;

    @Column(name = "procentObecnejZnajomosci")
    private Integer procentObecnejZnajomosci;

    @Column(name = "ostatniagra")
    private LocalDateTime ostatniaGra;

    @Column(name = "datastworzenia")
    private LocalDateTime dataStworzenia;

    @ManyToMany(mappedBy = "zestawySlowek", fetch = FetchType.EAGER)
    private List<User> uzytkownicy = new ArrayList<>();

    @OneToMany(mappedBy = "zestawySlowek", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Slowka> slowka = new ArrayList<>();

    @OneToMany(mappedBy = "zestawySlowek", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<HistoriaZestawu> historiaZestawuList = new ArrayList<>();

    public ZestawySlowek(){

    }

    public ZestawySlowek(String name, Integer punkty, Integer procentObecnejZnajomosci, LocalDateTime ostatniaGra, LocalDateTime dataStworzenia, List<User> uzytkownicy, List<Slowka> slowka, List<HistoriaZestawu> historiaZestawuList) {
        this.name = name;
        this.punkty = punkty;
        this.procentObecnejZnajomosci = procentObecnejZnajomosci;
        this.ostatniaGra = ostatniaGra;
        this.dataStworzenia = dataStworzenia;
        this.uzytkownicy = uzytkownicy;
        this.slowka = slowka;
        this.historiaZestawuList = historiaZestawuList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPunkty() {
        return punkty;
    }

    public void setPunkty(Integer punkty) {
        this.punkty = punkty;
    }

    public LocalDateTime getOstatniaGra() {
        return ostatniaGra;
    }

    public void setOstatniaGra(LocalDateTime ostatniaGra) {
        this.ostatniaGra = ostatniaGra;
    }

    public LocalDateTime getDataStworzenia() {
        return dataStworzenia;
    }

    public void setDataStworzenia(LocalDateTime dataStworzenia) {
        this.dataStworzenia = dataStworzenia;
    }

    public List<User> getUzytkownicy() {
        return uzytkownicy;
    }

    public void setUzytkownicy(List<User> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }

    public List<Slowka> getSlowka() {
        return slowka;
    }

    public void setSlowka(List<Slowka> slowka) {
        this.slowka = slowka;
    }

    public Integer getProcentObecnejZnajomosci() {
        return procentObecnejZnajomosci;
    }

    public void setProcentObecnejZnajomosci(Integer procentObecnejZnajomosci) {
        this.procentObecnejZnajomosci = procentObecnejZnajomosci;
    }

    public List<HistoriaZestawu> getHistoriaZestawuList() {
        return historiaZestawuList;
    }

    public void setHistoriaZestawuList(List<HistoriaZestawu> historiaZestawuList) {
        this.historiaZestawuList = historiaZestawuList;
    }

    @Override
    public String toString() {
        return "ZestawySlowek{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", punkty=" + punkty +
                ", procentObecnejZnajomosci=" + procentObecnejZnajomosci +
                ", ostatniaGra=" + ostatniaGra +
                ", dataStworzenia=" + dataStworzenia +
                ", slowka=" + slowka +
                '}';
    }
}
