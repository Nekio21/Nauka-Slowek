package umk.mat.jakuburb.encje;

import jakarta.persistence.*;

@Entity
@Table(name = "slowkagra")
public class SlowkaGra {

    @Id
    @SequenceGenerator(name="sekwencja", sequenceName = "slowkagra_idSlowkaGra_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencja")
    @Column(name = "idSlowkaGra")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idGry")
    private Gra gra;

    @ManyToOne
    @JoinColumn(name = "idSlowka")
    private Slowka slowka;

    private Boolean dobrzeCzyZle;

    public SlowkaGra() {
    }

    public SlowkaGra(Gra gra, Slowka slowka, Boolean dobrzeCzyZle) {
        this.gra = gra;
        this.slowka = slowka;
        this.dobrzeCzyZle = dobrzeCzyZle;
    }

    public Gra getGra() {
        return gra;
    }

    public void setGra(Gra gra) {
        this.gra = gra;
    }

    public Slowka getSlowka() {
        return slowka;
    }

    public void setSlowka(Slowka slowka) {
        this.slowka = slowka;
    }

    public Boolean getDobrzeCzyZle() {
        return dobrzeCzyZle;
    }

    public void setDobrzeCzyZle(Boolean dobrzeCzyZle) {
        this.dobrzeCzyZle = dobrzeCzyZle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
