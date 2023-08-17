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

    @Column(name = "ostatniagra")
    private LocalDateTime ostatniaGra;

    @Column(name = "datastworzenia")
    private LocalDateTime dataStworzenia;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "zestawySlowek")
    private List<User> uzytkownicy = new ArrayList<>();

    public ZestawySlowek(){

    }

    public ZestawySlowek(String name, int punkty, LocalDateTime ostatniaGra, LocalDateTime dataStworzenia) {
        this.name = name;
        this.punkty = punkty;
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

    @Override
    public String toString() {
        return "ZestawySlowek{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", punkty=" + punkty +
                ", ostatniaGra=" + ostatniaGra +
                ", dataStworzenia=" + dataStworzenia +
                '}';
    }
}
