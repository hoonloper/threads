package threads.server.domain.follow;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class FollowServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowService followService;

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        private Long userAId;
        private Long userBId;

        @BeforeEach
        void 설정() {
            userAId = userRepository.save(new User(null)).getId();
            userBId = userRepository.save(new User(null)).getId();
        }
        @Test
        @DisplayName("팔로우 테스트")
        void 팔로우하기() {
            FollowDTO followDto = new FollowDTO(null, userAId, userBId, LocalDateTime.now());
            followService.follow(followDto);
            assertThat(followRepository.findAll().size()).isEqualTo(1);
        }

        @Test
        @DisplayName("팔로우 끊기 테스트")
        void 팔로우끊기() {
            FollowDTO followDto = new FollowDTO(null, userAId, userBId, LocalDateTime.now());
            followService.follow(followDto);
            followService.unfollow(1L);

            assertThat(followRepository.findAll().size()).isEqualTo(0);
        }
    }
}
