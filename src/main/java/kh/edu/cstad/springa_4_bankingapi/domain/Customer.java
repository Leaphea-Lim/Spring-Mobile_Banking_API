package kh.edu.cstad.springa_4_bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
//when use this @, need to mark on primary key
public class Customer {

//jpa annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false) //-> customise the column
    private String fullName;

    @Column(length = 15, nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(length = 100)
    private String address;
    @Column(length = 50)
    private String cityOrProvince;
    @Column(length = 50)
    private String country;
    @Column(length = 50)
    private String zipCode;

    @Column(length = 50)
    private String employmentType;
    @Column(length = 50)
    private String position;
    @Column(length = 50)
    private String companyName;
    @Column(length = 50)
    private String mainSourceOfIncome;
    @Column(length = 50)
    private BigDecimal monthlyIncomeRange;

    @Column(nullable = false)
    private Boolean isDeleted;

//    @Column(nullable = false, unique = true)
//    private String nationalCardId;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KYC kyc;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "segment_id", nullable = false)
//    private SegmentType segmentType;

    @ManyToOne
    @JoinColumn(name = "customer_segment_id", nullable = true)
    private CustomerSegment customerSegment;

}
