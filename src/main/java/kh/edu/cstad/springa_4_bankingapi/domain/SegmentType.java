package kh.edu.cstad.springa_4_bankingapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "segment_types")
public class SegmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer segmentId;

    @Column(nullable = false, unique = true)
    private String segmentType;

    @Column(nullable = false)
    private Integer overLimit;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "segmentType")
    private List<CustomerSegment> customerSegments;
}
