package pl.patrykdepka.basicspringmvcapp.event;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.eventimage.EventImage;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_event_image",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_image_id", referencedColumnName = "id")
    )
    private EventImage eventImage;
    private String eventType;
    private LocalDateTime dateTime;
    private String language;
    private String admission;
    private String city;
    private String location;
    private String address;
    @OneToOne
    @JoinColumn(name = "organizer_id")
    private AppUser organizer;
    private String description;
}