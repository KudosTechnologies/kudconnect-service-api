package ro.kudostech.kudconnect.candidates.adapters.output.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.kudostech.kudconnect.common.PropertyPath;

@Entity
@Table(name = "candidate")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Size(min = 3, max = 255)
    @PropertyPath("/firstName")
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
