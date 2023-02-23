package pl.patrykdepka.basicspringmvcapp.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventCardDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventDTO;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EventCardDTOMapper;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EventDTOMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventCardDTO> findFirst10UpcomingEvents() {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findFirst10EventsByOrderByDateTimeAsc());
    }

    public EventDTO findEvent(Long id) {
        return eventRepository
                .findById(id)
                .map(EventDTOMapper::mapToEventDTO)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    public Page<EventCardDTO> findAllUpcomingEvents(LocalDateTime currentDateTime, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findAllUpcomingEvents(currentDateTime, pageable));
    }

    public Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findAllPastEvents(currentDateTime, pageable));
    }
}