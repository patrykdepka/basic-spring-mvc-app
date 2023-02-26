package pl.patrykdepka.basicspringmvcapp.eventimage;

import org.springframework.web.multipart.MultipartFile;

public interface EventImageService {

    EventImage createEventImage(MultipartFile image);
}
