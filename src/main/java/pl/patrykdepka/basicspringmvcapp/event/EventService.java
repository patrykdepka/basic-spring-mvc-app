package pl.patrykdepka.basicspringmvcapp.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.event.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    List<EventCardDTO> findFirst10UpcomingEvents();

    EventDTO findEvent(Long id);

    Page<EventCardDTO> findAllUpcomingEvents(LocalDateTime currentDateTime, Pageable pageable);

    List<CityDTO> findAllCities();

    Page<EventCardDTO> findUpcomingEventsByCity(String city, LocalDateTime currentDateTime, Pageable pageable);

    Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable pageable);

    Page<EventCardDTO> findPastEventsByCity(String city, LocalDateTime currentDateTime, Pageable pageable);

    EventDTO findOrganizerEvent(AppUser user, Long id);

    EventDTO createEvent(AppUser user, CreateEventDTO newEvent);

    Page<EventCardDTO> findOrganizerEvents(AppUser user, Pageable pageable);

    Page<EventCardDTO> findOrganizerEventsByCity(AppUser user, String city, Pageable pageable);

    EditEventDTO findEventToEdit(AppUser user, Long id);

    void updateEvent(AppUser user, EditEventDTO editEventDTO);

    void addUserToEventParticipantsList(AppUser user, Long id);

    boolean checkIfCurrentUserIsParticipant(Optional<AppUser> user, EventDTO event);
}