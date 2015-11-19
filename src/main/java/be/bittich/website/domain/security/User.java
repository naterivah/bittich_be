package be.bittich.website.domain.security;

import be.bittich.website.domain.AbstractDomain;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nordine on 08-11-15.
 */

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractDomain{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String username;
    @NotNull
    @Email
    private String email;
    @NotBlank
    private String password;

    private int failedPassword;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "roles_user")
    @Column(name = "roles")
    @Singular
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @Singular
    private Set<RemoteClient> remoteClients;


    public void addRemoteClient(RemoteClient remoteClient){
        remoteClients.add(remoteClient);
    }
    public void incrementFailedLogin(){
        this.failedPassword += 1;
    }
}
