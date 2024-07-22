package com.kuit.healthmate.supplement;

import com.kuit.healthmate.dto.supplement.SupplementRegisterRequest;
import com.kuit.healthmate.dto.supplement.SupplementResponse;
import com.kuit.healthmate.service.SupplementService;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SupplementServiceTest {

    @Autowired
    SupplementService supplementService;

    @Test
    void 메인화면_영양제_조회() {
        List<SupplementResponse> supplementChallengesByUserId = supplementService.getSupplementChallengesByUserId(1L);
        Assertions.assertEquals(2, supplementChallengesByUserId.size());
    }

    @Test
    void 새로운_영양제_습관_추가() {
        supplementService.registerSupplement(new SupplementRegisterRequest(
                1L,
                "test3",
                30,
                "0000000",
                true,
                true,
                true,
                List.of(LocalTime.of(12, 12, 12))
        ));
        Assertions.assertEquals(supplementService.getSupplementChallengesByUserId(1L).size(), 3);
    }
}
