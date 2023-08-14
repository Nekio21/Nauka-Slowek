package umk.mat.jakuburb.encje;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

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
    private String password;

    @Column(name = "image", columnDefinition="org.hibernate.type.BinaryType")
    private byte[] image;


    public User(){

    }

    public User(String login, String password, byte[] image) {
        this.login = login;
        this.password = password;
        this.image = image;
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
}
