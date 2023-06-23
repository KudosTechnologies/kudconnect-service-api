package ro.kudostech.kudconnect.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Candidate {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
