package pl.patrykdepka.basicspringmvcapp.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {
    private Long id;
    private String name;
    private String imageType;
    private String imageData;
    private String eventType;
    private String date;
    private String hour;
    private String language;
    private String admission;
    private String city;
    private String location;
    private String address;
    private Long organizerId;
    private String organizerImageType;
    private String organizerImageData;
    private String organizerName;
    private String description;

    public static class EventDTOBuilder {
        private Long id;
        private String name;
        private String imageType;
        private String imageData;
        private String eventType;
        private String date;
        private String hour;
        private String language;
        private String admission;
        private String city;
        private String location;
        private String address;
        private Long organizerId;
        private String organizerImageType;
        private String organizerImageData;
        private String organizerName;
        private String description;

        public EventDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EventDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EventDTOBuilder withImageType(String imageType) {
            this.imageType = imageType;
            return this;
        }

        public EventDTOBuilder withImageData(String imageData) {
            this.imageData = imageData;
            return this;
        }

        public EventDTOBuilder withEventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public EventDTOBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public EventDTOBuilder withHour(String hour) {
            this.hour = hour;
            return this;
        }

        public EventDTOBuilder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public EventDTOBuilder withAdmission(String admission) {
            this.admission = admission;
            return this;
        }

        public EventDTOBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public EventDTOBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public EventDTOBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public EventDTOBuilder withOrganizerId(Long organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public EventDTOBuilder withOrganizerImageType(String organizerImageType) {
            this.organizerImageType = organizerImageType;
            return this;
        }

        public EventDTOBuilder withOrganizerImageData(String organizerImageData) {
            this.organizerImageData = organizerImageData;
            return this;
        }

        public EventDTOBuilder withOrganizerName(String organizerName) {
            this.organizerName = organizerName;
            return this;
        }

        public EventDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventDTO build() {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(id);
            eventDTO.setName(name);
            eventDTO.setImageType(imageType);
            eventDTO.setImageData(imageData);
            eventDTO.setEventType(eventType);
            eventDTO.setDate(date);
            eventDTO.setHour(hour);
            eventDTO.setLanguage(language);
            eventDTO.setAdmission(admission);
            eventDTO.setCity(city);
            eventDTO.setLocation(location);
            eventDTO.setAddress(address);
            eventDTO.setOrganizerId(organizerId);
            eventDTO.setOrganizerImageType(organizerImageType);
            eventDTO.setOrganizerImageData(organizerImageData);
            eventDTO.setOrganizerName(organizerName);
            eventDTO.setDescription(description);
            return eventDTO;
        }
    }
}