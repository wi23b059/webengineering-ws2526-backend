package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Salutation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserUpdateRequestDtoTest {

    @Test
    void settersAndGettersWorkCorrectly() {
        UserUpdateRequestDto dto = new UserUpdateRequestDto();

        dto.setSalutation(Salutation.MR);
        dto.setFirstName("Max");
        dto.setLastName("Mustermann");
        dto.setCountryCode("AT");
        dto.setAddress("Musterstraße 1");
        dto.setCity("Wien");
        dto.setZip("1010");
        dto.setPassword("Abc12345");

        assertThat(dto.getSalutation()).isEqualTo(Salutation.MR);
        assertThat(dto.getFirstName()).isEqualTo("Max");
        assertThat(dto.getLastName()).isEqualTo("Mustermann");
        assertThat(dto.getCountryCode()).isEqualTo("AT");
        assertThat(dto.getAddress()).isEqualTo("Musterstraße 1");
        assertThat(dto.getCity()).isEqualTo("Wien");
        assertThat(dto.getZip()).isEqualTo("1010");
        assertThat(dto.getPassword()).isEqualTo("Abc12345");
    }

    @Test
    void allArgsConstructorSetsFieldsCorrectly() {
        UserUpdateRequestDto dto = new UserUpdateRequestDto(
                Salutation.MR,
                "Anna",
                "Müller",
                "DE",
                "Hauptstraße 2",
                "Berlin",
                "12345",
                "Abcd1234"
        );

        assertThat(dto.getSalutation()).isEqualTo(Salutation.MR);
        assertThat(dto.getFirstName()).isEqualTo("Anna");
        assertThat(dto.getLastName()).isEqualTo("Müller");
        assertThat(dto.getCountryCode()).isEqualTo("DE");
        assertThat(dto.getAddress()).isEqualTo("Hauptstraße 2");
        assertThat(dto.getCity()).isEqualTo("Berlin");
        assertThat(dto.getZip()).isEqualTo("12345");
        assertThat(dto.getPassword()).isEqualTo("Abcd1234");
    }
}
