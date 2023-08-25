package umk.mat.jakuburb.encje;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import umk.mat.jakuburb.controllers.helpers.DayValue;

import java.time.LocalDateTime;

@Entity
@Table(name = "kalendarz")
public class Kalendarz {

    @Id
    @SequenceGenerator(name="sekwencja", sequenceName = "kalendarz_idkalendarz_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencja")
    @Column(name = "idKalendarz")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @Column(name = "dataKalendarz")
    private LocalDateTime localDateTime;

    @Column(name = "dayValue")
    @Enumerated(EnumType.STRING)
    private DayValue dayValue;

    public Kalendarz() {
    }

    public Kalendarz(User user, LocalDateTime localDateTime, DayValue dayValue) {
        this.user = user;
        this.localDateTime = localDateTime;
        this.dayValue = dayValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public DayValue getDayValue() {
        return dayValue;
    }

    public void setDayValue(DayValue dayValue) {
        this.dayValue = dayValue;
    }
}
