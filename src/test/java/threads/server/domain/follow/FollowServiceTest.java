package threads.server.domain.follow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import threads.server.domain.follow.dto.FollowingDto;
import threads.server.domain.follow.dto.UnfollowingDto;
import threads.server.domain.user.User;
import threads.server.domain.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

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
        private Long toUserId;
        private Long fromUserId;

        @BeforeEach
        void 설정() {
            toUserId = userRepository.save(new User(null)).getId();
            fromUserId = userRepository.save(new User(null)).getId();
        }
        @Test
        @DisplayName("팔로우 테스트")
        void 팔로우하기() {
            FollowingDto inputFollowDto = new FollowingDto(toUserId, fromUserId);
            followService.follow(inputFollowDto);
            assertThat(followRepository.findAll().size()).isEqualTo(1);
        }

        @Test
        @DisplayName("팔로우 끊기 테스트")
        void 팔로우끊기() {
            FollowingDto followDto = new FollowingDto(toUserId, fromUserId);
            followService.follow(followDto);
            UnfollowingDto inputFollowDto = new UnfollowingDto(1L, toUserId, fromUserId);
            followService.unfollow(inputFollowDto);

            assertThat(followRepository.findAll().size()).isEqualTo(0);
        }
    }
}
