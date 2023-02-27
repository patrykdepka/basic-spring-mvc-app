package pl.patrykdepka.basicspringmvcapp.event.mapper;

import pl.patrykdepka.basicspringmvcapp.event.Event;
import pl.patrykdepka.basicspringmvcapp.event.dto.EditEventDTO;

import java.util.Base64;

public class EditEventDTOMapper {

    public static EditEventDTO mapToEditEventDTO(Event event) {
        EditEventDTO editEventDTO = new EditEventDTO();
        editEventDTO.setId(event.getId());
        editEventDTO.setName(event.getName());
        editEventDTO.setImageType(event.getEventImage().getFileType());
        editEventDTO.setImageData(Base64.getEncoder().encodeToString(event.getEventImage().getFileData()));
        editEventDTO.setEventType(event.getEventType());
        editEventDTO.setDateTime(event.getDateTime().toString());
        editEventDTO.setLanguage(event.getLanguage());
        editEventDTO.setAdmission(event.getAdmission());
        editEventDTO.setCity(event.getCity());
        editEventDTO.setLocation(event.getLocation());
        editEventDTO.setAddress(event.getAddress());
        editEventDTO.setDescription(event.getDescription());
        return editEventDTO;
    }
}
