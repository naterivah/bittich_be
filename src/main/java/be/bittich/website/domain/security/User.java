package be.bittich.website.domain.security;

import be.bittich.website.domain.AbstractDomain;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Set;

/**
 * Created by Nordine on 08-11-15.
 */

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractDomain implements Principal {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @Singular
    private Set<Action> actions;

    @Override
    @Transient
    public String getName() {
        return email;
    }
}
