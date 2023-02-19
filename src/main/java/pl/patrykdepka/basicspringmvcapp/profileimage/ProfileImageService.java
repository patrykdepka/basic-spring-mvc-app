package pl.patrykdepka.basicspringmvcapp.profileimage;

import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;

import java.util.Optional;

public interface ProfileImageService {

    ProfileImage createDefaultProfileImage();

    Optional<ProfileImage> updateProfileImage(AppUser user, MultipartFile newProfileImage);
}