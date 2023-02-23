package pl.patrykdepka.basicspringmvcapp.event;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;

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