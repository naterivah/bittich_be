package be.bittich.website.repository;

import be.bittich.website.domain.personal.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nordine on 07-11-15.
 */

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Long> {
}
