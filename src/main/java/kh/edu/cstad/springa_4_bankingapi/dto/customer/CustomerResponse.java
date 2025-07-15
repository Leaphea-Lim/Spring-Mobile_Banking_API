package kh.edu.cstad.springa_4_bankingapi.dto.customer;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String gender,
//        String phoneNumber,
        String email,
//        String remark,
        String nationalCardId,
        SegmentTypeResponse segmentType
) {
}
