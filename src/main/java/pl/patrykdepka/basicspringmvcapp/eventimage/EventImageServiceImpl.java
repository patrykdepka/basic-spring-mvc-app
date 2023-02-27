package pl.patrykdepka.basicspringmvcapp.eventimage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.basicspringmvcapp.event.Event;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class EventImageServiceImpl implements EventImageService {
    private final EventImageRepository eventImageRepository;

    public EventImageServiceImpl(EventImageRepository eventImageRepository) {
        this.eventImageRepository = eventImageRepository;
    }

    public EventImage createEventImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            return createCustomEventImage(image);
        }
        return createDefaultEventImage();
    }

    public Optional<EventImage> updateEventImage(Event event, MultipartFile newEventImage) {
        EventImage currentEventImage = event.getEventImage();
        try (InputStream is = newEventImage.getInputStream()) {
            if (currentEventImage.getFileData() != is.readAllBytes()) {
                currentEventImage.setFileName(newEventImage.getOriginalFilename());
                currentEventImage.setFileType(newEventImage.getContentType());
                currentEventImage.setFileData(newEventImage.getBytes());
                return Optional.of(currentEventImage);
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new DefaultEventImageNotFoundException("File not found");
        }
    }

    private EventImage createDefaultEventImage() {
        ClassPathResource resource = new ClassPathResource("static/images/default_event_image.png");
        try (InputStream defaultEventImage = resource.getInputStream()) {
            EventImage eventImage = new EventImage();
            eventImage.setFileName(resource.getFilename());
            eventImage.setFileType("image/png");
            eventImage.setFileData(defaultEventImage.readAllBytes());
            return eventImageRepository.save(eventImage);
        } catch (IOException e) {
            throw new DefaultEventImageNotFoundException("File " + resource.getPath() + " not found");
        }
    }

    private EventImage createCustomEventImage(MultipartFile image) {
        try (InputStream is = image.getInputStream()) {
            EventImage eventImage = new EventImage();
            eventImage.setFileName(image.getOriginalFilename());
            eventImage.setFileType(image.getContentType());
            eventImage.setFileData(is.readAllBytes());
            return eventImageRepository.save(eventImage);
        } catch (IOException e) {
            throw new DefaultEventImageNotFoundException("File not found");
        }
    }
}
