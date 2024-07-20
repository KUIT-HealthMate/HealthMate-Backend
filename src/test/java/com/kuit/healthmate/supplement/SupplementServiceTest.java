package com.kuit.healthmate.supplement;

import com.kuit.healthmate.dto.supplement.SupplementResponse;
import com.kuit.healthmate.service.SupplementService;
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
}
