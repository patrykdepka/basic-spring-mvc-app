package pl.patrykdepka.basicspringmvcapp.event.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pl.patrykdepka.basicspringmvcapp.core.Image;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditEventDTO {
    private Long id;
    @NotNull(message = "{form.field.name.error.notNull.message}")
    @NotEmpty(message = "{form.field.name.error.notEmpty.message}")
    private String name;
    private String imageType;
    private String imageData;
    @Image(width = 480, height = 270)
    private MultipartFile eventImage;
    @NotNull(message = "{form.field.eventType.error.notNull.message}")
    @NotEmpty(message = "{form.field.eventType.error.notEmpty.message}")
    private String eventType;
    @NotNull(message = "{form.field.dateTime.error.notNull.message}")
    @NotEmpty(message = "{form.field.dateTime.error.notEmpty.message}")
    private String dateTime;
    @NotNull(message = "{form.field.language.error.notNull.message}")
    @NotEmpty(message = "{form.field.language.error.notEmpty.message}")
    private String language;
    @NotNull(message = "{form.field.admission.error.notNull.message}")
    @NotEmpty(message = "{form.field.admission.error.notEmpty.message}")
    private String admission;
    @NotNull(message = "{form.field.city.error.notNull.message}")
    @NotEmpty(message = "{form.field.city.error.notEmpty.message}")
    private String city;
    @NotNull(message = "{form.field.location.error.notNull.message}")
    @NotEmpty(message = "{form.field.location.error.notEmpty.message}")
    private String location;
    @NotNull(message = "{form.field.address.error.notNull.message}")
    @NotEmpty(message = "{form.field.address.error.notEmpty.message}")
    private String address;
    @NotNull(message = "{form.field.description.error.notNull.message}")
    @NotEmpty(message = "{form.field.description.error.notEmpty.message}")
    private String description;
}
