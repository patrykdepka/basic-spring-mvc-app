package pl.patrykdepka.basicspringmvcapp.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.basicspringmvcapp.core.CurrentUserFacade;
import pl.patrykdepka.basicspringmvcapp.event.dto.CityDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.CreateEventDTO;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventDTO;
import pl.patrykdepka.basicspringmvcapp.event.enumeration.AdmissionType;
import pl.patrykdepka.basicspringmvcapp.event.enumeration.EventType;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class EventController {
    private final EventService eventService;
    private final CurrentUserFacade currentUserFacade;

    public EventController(EventService eventService, CurrentUserFacade currentUserFacade) {
        this.eventService = eventService;
        this.currentUserFacade = currentUserFacade;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("events", eventService.findFirst10UpcomingEvents());
        return "index";
    }

    @GetMapping("/events/{id}")
    public String getEvent(@PathVariable Long id, Model model) {
        EventDTO event = eventService.findEvent(id);
        model.addAttribute("event", event);
        return "event";
    }

    @GetMapping("/events")
    public String getAllUpcomingEvents(@RequestParam(name = "page", required = false) Integer pageNumber,
                                       Model model) {
        model.addAttribute("pathName", "/events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("ASC"), "dateTime"));
        model.addAttribute("events", eventService.findAllUpcomingEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/events/cities/{city}")
    public String getUpcomingEventsByCity(@PathVariable String city,
                                          @RequestParam(name = "page", required = false) Integer pageNumber,
                                          Model model) {
        model.addAttribute("pathName", "/events");
        List<CityDTO> cities = eventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("ASC"), "dateTime"));
        model.addAttribute("events", eventService.findUpcomingEventsByCity(city, LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/archive/events")
    public String getAllPastEvents(@RequestParam(name = "page", required = false) Integer pageNumber,
                                   Model model) {
        model.addAttribute("pathName", "/archive/events");
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("DESC"), "dateTime"));
        model.addAttribute("events", eventService.findAllPastEvents(LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/archive/events/cities/{city}")
    public String getPastEventsByCity(@PathVariable String city,
                                      @RequestParam(name = "page", required = false) Integer pageNumber,
                                      Model model) {
        model.addAttribute("pathName", "/archive/events");
        List<CityDTO> cities = eventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("DESC"), "dateTime"));
        model.addAttribute("events", eventService.findPastEventsByCity(city, LocalDateTime.now(), pageRequest));
        return "events";
    }

    @GetMapping("/organizer-panel/events/{id}")
    public String getOrganizerEvent(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.findOrganizerEvent(currentUserFacade.getCurrentUser(), id));
        return "event";
    }

    @GetMapping("/organizer-panel/create_event")
    public String showCreateEventForm(Model model) {
        model.addAttribute("createEventDTO", new CreateEventDTO());
        model.addAttribute("eventTypeList", Arrays.asList(EventType.values()));
        model.addAttribute("admissionTypeList", Arrays.asList(AdmissionType.values()));
        return "organizer/forms/create-event-form";
    }

    @PostMapping("/organizer-panel/create_event")
    public String createEvent(@Valid @ModelAttribute("createEventDTO") CreateEventDTO createEventDTO,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("eventTypeList", Arrays.asList(EventType.values()));
            model.addAttribute("admissionTypeList", Arrays.asList(AdmissionType.values()));
            return "organizer/forms/create-event-form";
        } else {
            EventDTO createdEvent = eventService.createEvent(currentUserFacade.getCurrentUser(), createEventDTO);
            return "redirect:/organizer-panel/events/" + createdEvent.getId();
        }
    }

    @GetMapping("/organizer-panel/events")
    public String findOrganizerEvents(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        model.addAttribute("cities", eventService.findAllCities());
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("DESC"), "dateTime"));
        model.addAttribute("events", eventService.findOrganizerEvents(currentUserFacade.getCurrentUser(), pageRequest));
        return "organizer/events";
    }

    @GetMapping("/organizer-panel/events/cities/{city}")
    public String findOrganizerEventsByCity(@PathVariable String city,
                                            @RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        List<CityDTO> cities = eventService.findAllCities();
        model.addAttribute("cities", cities);
        city = getCity(cities, city);
        model.addAttribute("cityName", city);
        int page = pageNumber != null ? pageNumber : 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("DESC"), "dateTime"));
        model.addAttribute("events", eventService.findOrganizerEventsByCity(currentUserFacade.getCurrentUser(), city, pageRequest));
        return "organizer/events";
    }

    private String getCity(List<CityDTO> cities, String city) {
        for (CityDTO cityDTO : cities) {
            if (cityDTO.getNameWithoutPlCharacters().equals(city)) {
                return cityDTO.getDisplayName();
            }
        }

        throw new CityNotFoundException("City with name " + city + " not found");
    }
}