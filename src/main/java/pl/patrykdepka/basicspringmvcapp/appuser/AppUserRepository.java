package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT a FROM AppUser a")
    List<AppUser> findAllUsers();
}
