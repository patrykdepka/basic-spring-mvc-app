package pl.patrykdepka.basicspringmvcapp.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.event.dto.CityDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.CreateEventDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventCardDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

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
}