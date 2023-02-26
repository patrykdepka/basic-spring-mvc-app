package pl.patrykdepka.basicspringmvcapp.event.mapper;

import pl.patrykdepka.basicspringmvcapp.event.Event;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventDTO;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class EventDTOMapper {

    public static EventDTO mapToEventDTO(Event event) {
        return new EventDTO.EventDTOBuilder()
                .withId(event.getId())
                .withName(event.getName())
                .withImageType(event.getEventImage().getFileType())
                .withImageData(Base64.getEncoder().encodeToString(event.getEventImage().getFileData()))
                .withEventType(event.getEventType())
                .withDate(event.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .withHour(event.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .withLanguage(event.getLanguage())
                .withAdmission(event.getAdmission())
                .withCity(event.getCity())
                .withLocation(event.getLocation())
                .withAddress(event.getAddress())
                .withOrganizerId(event.getOrganizer().getId())
                .withOrganizerImageType(event.getOrganizer().getProfileImage().getFileType())
                .withOrganizerImageData(Base64.getEncoder().encodeToString(event.getOrganizer().getProfileImage().getFileData()))
                .withOrganizerName(event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName())
                .withDescription(event.getDescription())
                .build();
    }
}