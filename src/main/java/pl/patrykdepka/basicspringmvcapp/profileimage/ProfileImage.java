package pl.patrykdepka.basicspringmvcapp.profileimage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private byte[] fileData;

    public static class ProfileImageBuilder {
        private Long id;
        private String fileName;
        private String fileType;
        private byte[] fileData;

        public ProfileImageBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProfileImageBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ProfileImageBuilder withFileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public ProfileImageBuilder withFileData(byte[] fileData) {
            this.fileData = fileData;
            return this;
        }

        public ProfileImage build() {
            ProfileImage profileImage = new ProfileImage();
            profileImage.setId(id);
            profileImage.setFileName(fileName);
            profileImage.setFileType(fileType);
            profileImage.setFileData(fileData);
            return profileImage;
        }
    }
}
