package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.entity.Salutation;

public class UserDto {
    public Long id;
    public Salutation salutation;
    public String first_name;
    public String last_name;
    public String email;
    public String username;
}
