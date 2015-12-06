package be.bittich.website.domain.security;

import be.bittich.website.domain.AbstractDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by nbittich on 19/11/2015.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteClient extends AbstractDomain {

    @Id
    private String ip;
    private boolean banned;

}
