package umk.mat.jakuburb.encje;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Zestawy")
public class ZestawySlowek {

    //TODO: Zestaw ma tylko jednego uzytkownika, ABLO ZOSTAW TAK JAK JEST OBECNIE I ZRBO TRYB CAOUPE
    //TODO: ROZDZIEL STATYKE OD ZESTAWU I OD SLOWKA
    //TODO: MOZLIWOSC PRYWATNE LUB PUBLICZNO
    //TODO: DAC HASLO LUB NIE
    //TODO: MOZLIWOSC EDYCJI LUB NIE

    //TODO: STworzyc tabele historia lub statystyka zestawu gdzie beda pola
    //TODO: poprzedni wynik punktowy, atualny wynik punktowy, data gry, procent,
    //TODO: Starsza historia jest kumulowa do sredniej i tamte rekorydy sa usuwane
    //TODO: i jeszcze wiele do jednego tabela stytyka slowka danego polaczona z zestawem
    //TODO: ALBO NIE
    //TODO: to do bedzie jednak tabela GRY_USERA, dla jednego rekordu bedzie
    //TODO: dobre odpowiedzi, zle odpowiedzi, data gry rozpoczecia, data gry zakonczenia, klucz do slowkaStatytyki
    //TODO: id_gry, id_slowka, (moze ) id_zestawu, porpawnie czy zle
    //TODO: albo nie wiem

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

    @ManyToMany(mappedBy = "zestawySlowek")
    private List<User> uzytkownicy = new ArrayList<>();

    @OneToMany(mappedBy = "zestawySlowek", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Slowka> slowka = new ArrayList<>();

    public ZestawySlowek(){

    }

    public ZestawySlowek(String name, int punkty, int procentObecnejZnajomosci,  LocalDateTime ostatniaGra, LocalDateTime dataStworzenia) {
        this.name = name;
        this.punkty = punkty;
        this.procentObecnejZnajomosci = procentObecnejZnajomosci;
        this.ostatniaGra = ostatniaGra;
        this.dataStworzenia = dataStworzenia;
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
