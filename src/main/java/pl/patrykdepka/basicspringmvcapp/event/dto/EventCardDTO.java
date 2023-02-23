package pl.patrykdepka.basicspringmvcapp.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCardDTO {
    private Long id;
    private String date;
    private String dayOfWeek;
    private String name;
    private String city;
    private String eventType;
    private String admission;

    public static class EventCardDTOBuilder {
        private Long id;
        private String date;
        private String dayOfWeek;
        private String name;
        private String city;
        private String eventType;
        private String admission;

        public EventCardDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EventCardDTOBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public EventCardDTOBuilder withDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public EventCardDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EventCardDTOBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public EventCardDTOBuilder withEventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public EventCardDTOBuilder withAdmission(String admission) {
            this.admission = admission;
            return this;
        }

        public EventCardDTO build() {
            EventCardDTO eventBoxDTO = new EventCardDTO();
            eventBoxDTO.setId(id);
            eventBoxDTO.setDate(date);
            eventBoxDTO.setDayOfWeek(dayOfWeek);
            eventBoxDTO.setName(name);
            eventBoxDTO.setCity(city);
            eventBoxDTO.setEventType(eventType);
            eventBoxDTO.setAdmission(admission);
            return eventBoxDTO;
        }
    }
}