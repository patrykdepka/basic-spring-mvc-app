package pl.patrykdepka.basicspringmvcapp.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventCardDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventCardDTO> findFirst10UpcomingEvents();

    EventDTO findEvent(Long id);

    Page<EventCardDTO> findAllUpcomingEvents(LocalDateTime currentDateTime, Pageable pageable);

    Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable pageable);
}