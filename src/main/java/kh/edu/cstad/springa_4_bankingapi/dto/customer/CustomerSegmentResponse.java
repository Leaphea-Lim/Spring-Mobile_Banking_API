package kh.edu.cstad.springa_4_bankingapi.dto.customer;

public record CustomerSegmentResponse(

        Integer customerSegmentId,
//        Integer customerId,
        String segmentType,
        Double overLimit
) {
}
