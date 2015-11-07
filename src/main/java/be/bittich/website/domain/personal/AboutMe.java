package be.bittich.website.domain.personal;

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
import javax.persistence.Lob;

/**
 * Created by Nordine on 07-11-15.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AboutMe extends AbstractDomain {

    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private String aboutMe;
}
