package pl.patrykdepka.basicspringmvcapp.event.mapper;

import org.springframework.data.domain.Page;
import pl.patrykdepka.basicspringmvcapp.event.Event;
import pl.patrykdepka.basicspringmvcapp.event.dto.EventCardDTO;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EventCardDTOMapper {

    public static List<EventCardDTO> mapToEventCardDTOs(List<Event> events) {
        return events
                .stream()
                .map(EventCardDTOMapper::mapToEventCardDTO)
                .collect(Collectors.toList());
    }

    public static Page<EventCardDTO> mapToEventCardDTOs(Page<Event> events) {
        return events.map(EventCardDTOMapper::mapToEventCardDTO);
    }

    private static EventCardDTO mapToEventCardDTO(Event event) {
        return new EventCardDTO.EventCardDTOBuilder()
                .withId(event.getId())
                .withDate(event.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM")))
                .withDayOfWeek(event.getDateTime().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()))
                .withName(event.getName())
                .withCity(event.getCity())
                .withEventType(event.getEventType())
                .withAdmission(event.getAdmission())
                .build();
    }
}