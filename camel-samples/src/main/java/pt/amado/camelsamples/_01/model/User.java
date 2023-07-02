package pt.amado.camelsamples._01.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    private long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private Gender gender;
    @JsonProperty("ip_address")
    private String ipAddress;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User userParsed))
            return false;

        return userParsed.getFirstName().equals(firstName) && userParsed.getEmail().equals(email);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{
            firstName,
            email
        });
    }
}
