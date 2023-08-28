package umk.mat.jakuburb.encje;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @SequenceGenerator(name="sekwencja", sequenceName = "users_iduser_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sekwencja")
    @Column(name = "iduser")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "haslo")
    @ColumnTransformer(read = "pgp_sym_decrypt(haslo, 'tajnekey')", write = "pgp_sym_encrypt(?, 'tajnekey')")
    private String password;

    @Column(name = "image", columnDefinition="org.hibernate.type.BinaryType")
    private byte[] image;

    @Column(name="logowaniedate")
    private LocalDateTime logowanieDate;

    @ManyToMany
    @JoinTable(
            name = "UserZestawy",
            joinColumns = @JoinColumn(name = "idowner"),
            inverseJoinColumns = @JoinColumn(name = "idzes")
    )
    private List<ZestawySlowek> zestawySlowek = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY)
    private List<Kalendarz> kalendarzList = new ArrayList<>();

    public User(){

    }

    public User(String login, String password, byte[] image, LocalDateTime logowanieDate, List<ZestawySlowek> zestawySlowek, List<Kalendarz> kalendarzList) {
        this.login = login;
        this.password = password;
        this.image = image;
        this.logowanieDate = logowanieDate;
        this.zestawySlowek = zestawySlowek;
        this.kalendarzList = kalendarzList;
    }


    public User(String login, String password, byte[] image, LocalDateTime logowanieDate) {
        this.login = login;
        this.password = password;
        this.image = image;
        this.logowanieDate = logowanieDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<ZestawySlowek> getZestawySlowek() {
        return zestawySlowek;
    }

    public void setZestawySlowek(List<ZestawySlowek> zestawySlowek) {
        this.zestawySlowek = zestawySlowek;
    }

    public List<Kalendarz> getKalendarzList() {
        return kalendarzList;
    }

    public void setKalendarzList(List<Kalendarz> kalendarzList) {
        this.kalendarzList = kalendarzList;
    }

    public LocalDateTime getLogowanieDate() {
        return logowanieDate;
    }

    public void setLogowanieDate(LocalDateTime logowanieDate) {
        this.logowanieDate = logowanieDate;
    }
}
