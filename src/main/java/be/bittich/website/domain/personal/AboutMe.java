package be.bittich.website.domain.personal;

import be.bittich.website.domain.AbstractDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public class AboutMe extends AbstractDomain {

    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private String aboutMe;
}
