package pl.foodflow.infrastructure.security.token;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

//    public VerificationToken(String token, User user) {
//        this.token = token;
//        this.user = user;
//        this.expirationTime = TokenExpirationTime.getExpirationTime();
//    }


}
