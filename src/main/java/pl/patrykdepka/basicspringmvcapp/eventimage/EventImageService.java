package pl.patrykdepka.basicspringmvcapp.eventimage;

import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.basicspringmvcapp.event.Event;

import java.util.Optional;

public interface EventImageService {

    EventImage createEventImage(MultipartFile image);

    Optional<EventImage> updateEventImage(Event event, MultipartFile newEventImage);
}
