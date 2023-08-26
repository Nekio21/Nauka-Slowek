package umk.mat.jakuburb.encje;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "HistoriaZestawu")
public class HistoriaZestawu {

    @Id
    @SequenceGenerator(name="sekwencja", sequenceName = "historiazestawu_idhz_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencja")
    @Column(name = "idhz")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idgry")
    private Gra gra;

    @ManyToOne
    @JoinColumn(name = "idzes")
    private ZestawySlowek zestawySlowek;

    @Column(name = "procentznajomosci")
    private Integer procentZnajmosci;

    @Column(name = "datagry")
    private LocalDateTime dataGry;

    public HistoriaZestawu() {
    }

    public HistoriaZestawu(Gra gra, Integer procentZnajmosci, LocalDateTime dataGry) {
        this.gra = gra;
        this.procentZnajmosci = procentZnajmosci;
        this.dataGry = dataGry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gra getGra() {
        return gra;
    }

    public void setGra(Gra gra) {
        this.gra = gra;
    }

    public Integer getProcentZnajmosci() {
        return procentZnajmosci;
    }

    public void setProcentZnajmosci(Integer procentZnajmosci) {
        this.procentZnajmosci = procentZnajmosci;
    }

    public LocalDateTime getDataGry() {
        return dataGry;
    }

    public void setDataGry(LocalDateTime dataGry) {
        this.dataGry = dataGry;
    }

    public ZestawySlowek getZestawySlowek() {
        return zestawySlowek;
    }

    public void setZestawySlowek(ZestawySlowek zestawySlowek) {
        this.zestawySlowek = zestawySlowek;
    }
}
