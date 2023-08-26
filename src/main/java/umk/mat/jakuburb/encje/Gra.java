package umk.mat.jakuburb.encje;

import jakarta.persistence.*;
import umk.mat.jakuburb.usefullClass.GameModes;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "gamemode")
    private GameModes gameMode;

    @Column(name = "rozpoczeciegry")
    private LocalDateTime dataGry;

    @OneToMany(mappedBy = "gra", cascade =  CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<SlowkaGra> slowkaGraList = new ArrayList<>();

    public Gra() {
    }

    public Gra(Kalendarz kalendarz, GameModes gameMode, LocalDateTime dataGry, List<SlowkaGra> slowkaGraList) {
        this.kalendarz = kalendarz;
        this.gameMode = gameMode;
        this.dataGry = dataGry;
        this.slowkaGraList = slowkaGraList;
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

    public GameModes getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameModes gameMode) {
        this.gameMode = gameMode;
    }
}
