package pl.patrykdepka.basicspringmvcapp.event;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.event.dto.CityDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.CreateEventDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventCardDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventDTO;
import pl.patrykdepka.basicspringmvcapp.event.enumeration.AdmissionType;
import pl.patrykdepka.basicspringmvcapp.event.enumeration.EventType;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EventCardDTOMapper;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EventDTOMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<CityDTO> findAllCities() {
        List<String> cities = eventRepository.findAllCities();
        List<CityDTO> cityDTOs = new ArrayList<>();
        for (String city : cities) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setNameWithoutPlCharacters(getCityNameWithoutPlCharacters(city));
            cityDTO.setDisplayName(city);
            cityDTOs.add(cityDTO);
        }
        return cityDTOs;
    }

    public Page<EventCardDTO> findUpcomingEventsByCity(String city, LocalDateTime currentDateTime, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findUpcomingEventsByCity(city, currentDateTime, pageable));
    }

    public Page<EventCardDTO> findAllPastEvents(LocalDateTime currentDateTime, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findAllPastEvents(currentDateTime, pageable));
    }

    public Page<EventCardDTO> findPastEventsByCity(String city, LocalDateTime currentDateTime, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findPastEventsByCity(city, currentDateTime, pageable));
    }

    public EventDTO findOrganizerEvent(AppUser user, Long id) {
        return eventRepository
                .findById(id)
                .map(event -> {
                    if (!user.equals(event.getOrganizer())) {
                        throw new AccessDeniedException("Access denied");
                    }
                    return EventDTOMapper.mapToEventDTO(event);
                }).orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    public EventDTO createEvent(AppUser user, CreateEventDTO newEvent) {
        Event event = new Event();
        event.setName(newEvent.getName());
        event.setEventType(EventType.valueOf(newEvent.getEventType().toUpperCase()).getDisplayName());
        event.setDateTime(LocalDateTime.parse(newEvent.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        event.setLanguage(newEvent.getLanguage());
        event.setAdmission(AdmissionType.valueOf(newEvent.getAdmission().toUpperCase()).getDisplayName());
        event.setCity(newEvent.getCity());
        event.setLocation(newEvent.getLocation());
        event.setAddress(newEvent.getAddress());
        event.setOrganizer(user);
        event.setDescription(newEvent.getDescription());
        return EventDTOMapper.mapToEventDTO(eventRepository.save(event));
    }

    public Page<EventCardDTO> findOrganizerEvents(AppUser user, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findOrganizerEvents(user, pageable));
    }

    public Page<EventCardDTO> findOrganizerEventsByCity(AppUser user, String city, Pageable pageable) {
        return EventCardDTOMapper.mapToEventCardDTOs(eventRepository.findOrganizerEventsByCity(user, city, pageable));
    }

    private String getCityNameWithoutPlCharacters(String city) {
        city = city.toLowerCase();
        city = city.replace("\\s", "-");
        city = StringUtils.stripAccents(city);
        return city;
    }
}