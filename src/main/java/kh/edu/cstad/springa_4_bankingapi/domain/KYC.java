package kh.edu.cstad.springa_4_bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity

//kyc = know ur customer
public class KYC {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id; //id-> uuid
//    @Column(unique = true)
    private String nationalCardId;
    private Boolean isVerified;
    private Boolean isDeleted;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cust_id")
    private Customer customer;

}
