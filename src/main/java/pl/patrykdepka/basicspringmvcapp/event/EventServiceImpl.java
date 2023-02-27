package pl.patrykdepka.basicspringmvcapp.event;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.event.dto.*;
import pl.patrykdepka.basicspringmvcapp.event.enumeration.AdmissionType;
import pl.patrykdepka.basicspringmvcapp.event.enumeration.EventType;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EditEventDTOMapper;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EventCardDTOMapper;
import pl.patrykdepka.basicspringmvcapp.event.mapper.EventDTOMapper;
import pl.patrykdepka.basicspringmvcapp.eventimage.EventImageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventImageService eventImageService;

    public EventServiceImpl(EventRepository eventRepository, EventImageService eventImageService) {
        this.eventRepository = eventRepository;
        this.eventImageService = eventImageService;
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
        event.setEventImage(eventImageService.createEventImage(newEvent.getEventImage()));
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

    public EditEventDTO findEventToEdit(AppUser user, Long id) {
        return eventRepository
                .findById(id)
                .map(event -> {
                    if (!user.equals(event.getOrganizer())) {
                        throw new AccessDeniedException("Access denied");
                    }
                    return EditEventDTOMapper.mapToEditEventDTO(event);
                }).orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    @Transactional
    public void updateEvent(AppUser user, EditEventDTO editEventDTO) {
        eventRepository
                .findById(editEventDTO.getId())
                .ifPresentOrElse(
                        event -> {
                            if (!user.equals(event.getOrganizer())) {
                                throw new AccessDeniedException("Access denied");
                            }
                            setEventFields(editEventDTO, event);
                        },
                        () -> {
                            throw new EventNotFoundException("Event with ID " + editEventDTO.getId() + " not found");
                        }
                );
    }

    @Transactional
    public void addUserToEventParticipantsList(AppUser user, Long id) {
        eventRepository
                .findById(id)
                .map(event -> event.addParticipant(user))
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    public boolean checkIfCurrentUserIsParticipant(Optional<AppUser> user, EventDTO event) {
        if (user.isPresent()) {
            return event.checkIfCurrentUserIsParticipant(user.get());
        }

        return false;
    }

    private String getCityNameWithoutPlCharacters(String city) {
        city = city.toLowerCase();
        city = city.replace("\\s", "-");
        city = StringUtils.stripAccents(city);
        return city;
    }

    private void setEventFields(EditEventDTO source, Event target) {
        if (source.getName() != null && !source.getName().equals(target.getName())) {
            target.setName(source.getName());
        }
        if (!source.getEventImage().isEmpty()) {
            eventImageService.updateEventImage(target, source.getEventImage()).ifPresent(target::setEventImage);
        }
        if (source.getDateTime() != null && !source.getDateTime().equals(target.getDateTime().toString())) {
            target.setDateTime(LocalDateTime.parse(source.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        if (source.getEventType() != null && !EventType.valueOf(source.getEventType().toUpperCase()).getDisplayName().equals(target.getEventType())) {
            target.setEventType(EventType.valueOf(source.getEventType().toUpperCase()).getDisplayName());
        }
        if (source.getLanguage() != null && !source.getLanguage().equals(target.getLanguage())) {
            target.setLanguage(source.getLanguage());
        }
        if (source.getAdmission() != null && !AdmissionType.valueOf(source.getAdmission().toUpperCase()).getDisplayName().equals(target.getAdmission())) {
            target.setAdmission(AdmissionType.valueOf(source.getAdmission().toUpperCase()).getDisplayName());
        }
        if (source.getCity() != null && !source.getCity().equals(target.getCity())) {
            target.setCity(source.getCity());
        }
        if (source.getLocation() != null && !source.getLocation().equals(target.getLocation())) {
            target.setLocation(source.getLocation());
        }
        if (source.getAddress() != null && !source.getAddress().equals(target.getAddress())) {
            target.setAddress(source.getAddress());
        }
        if (source.getDescription() != null && !source.getDescription().equals(target.getDescription())) {
            target.setDescription(source.getDescription());
        }
    }
}