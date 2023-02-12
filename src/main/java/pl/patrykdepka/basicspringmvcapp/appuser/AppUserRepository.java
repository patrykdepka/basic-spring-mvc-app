package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AppUserRepository extends PagingAndSortingRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT a FROM AppUser a")
    Page<AppUser> findAllUsers(Pageable pageable);
}
