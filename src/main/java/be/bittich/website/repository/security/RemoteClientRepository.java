package be.bittich.website.repository.security;

import be.bittich.website.domain.security.RemoteClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nbittich on 19/11/2015.
 */
@Repository
public interface RemoteClientRepository extends JpaRepository<RemoteClient, String>{

    List<RemoteClient> findByBannedTrue();

    List<RemoteClient> findByBannedFalse();
}
