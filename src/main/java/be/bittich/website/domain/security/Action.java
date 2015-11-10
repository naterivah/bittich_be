package be.bittich.website.domain.security;

import be.bittich.website.domain.AbstractDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.security.auth.Subject;
import java.beans.Transient;
import java.security.Principal;

/**
 * Created by Nordine on 08-11-15.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action extends AbstractDomain {

    public static final Action ADMIN_ACTION= Action.builder()
            .action("*")
            .domain("*")
            .method(Method.ALL)
            .build();



    public enum Method{ GET, PUT, POST, DELETE, OPTIONS, ALL}

    @Id
    @GeneratedValue
    private Long id;
    private String domain;
    private String action;
    private Method method;

}
