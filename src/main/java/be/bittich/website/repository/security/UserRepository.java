package be.bittich.website.repository.security;

import be.bittich.website.domain.security.Action;
import be.bittich.website.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nordine on 08-11-15.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
