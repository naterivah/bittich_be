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
    @Length(min = 6, max = 15)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "roles_user")
    @Column(name = "roles")
    @Singular
    private Set<Role> roles;
}
