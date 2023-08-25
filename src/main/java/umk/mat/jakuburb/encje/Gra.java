package umk.mat.jakuburb.encje;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gra")
public class Gra {

    @Id
    @SequenceGenerator(name="sekwencja", sequenceName = "gra_idgra_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencja")
    @Column(name = "idgra")
    private Integer idGra;

    @ManyToOne
    @JoinColumn(name = "idKalendarz")
    private Kalendarz kalendarz;

    @Column(name = "rozpoczeciegry")
    private LocalDateTime dataGry;

    @OneToMany(mappedBy = "gra", cascade =  CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<SlowkaGra> slowkaGraList = new ArrayList<>();

    public Gra() {
    }

    public Gra(Integer idGra, Kalendarz kalendarz, LocalDateTime dataGry) {
        this.idGra = idGra;
        this.kalendarz = kalendarz;
        this.dataGry = dataGry;
    }

    public Integer getIdGra() {
        return idGra;
    }

    public void setIdGra(Integer idGra) {
        this.idGra = idGra;
    }

    public Kalendarz getKalendarz() {
        return kalendarz;
    }

    public void setKalendarz(Kalendarz kalendarz) {
        this.kalendarz = kalendarz;
    }

    public LocalDateTime getDataGry() {
        return dataGry;
    }

    public void setDataGry(LocalDateTime dataGry) {
        this.dataGry = dataGry;
    }

    public List<SlowkaGra> getSlowkaGraList() {
        return slowkaGraList;
    }

    public void setSlowkaGraList(List<SlowkaGra> slowkaGraList) {
        this.slowkaGraList = slowkaGraList;
    }
}
