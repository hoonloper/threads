package threads.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.BadRequestException;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.follow.FollowRepository;
import threads.server.domain.user.dto.ReadUserDto;
import threads.server.domain.user.dto.SignInDTO;
import threads.server.domain.user.dto.SignUpDTO;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.user.dto.UserDto.toDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRepositorySupport userRepositorySupport;
    private final FollowRepository followRepository;

    public UserDto signUp(SignUpDTO userDTO) {
        userRepository.findByEmail(userDTO.email()).ifPresent((user) -> {
            throw new BadRequestException("이미 존재하는 이메일입니다.");
        });

        User user = userRepository.save(new User(null, userDTO.email(), userDTO.password(), userDTO.name(), userDTO.nickname(), userDTO.userRole(), null, null, false));
        return toDto(user);
    }

    public UserDto signIn(SignInDTO userDTO) {
        String password = userDTO.password();
        User user = userRepository.findOneByEmailAndPassword(userDTO.email(), password).orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        return toDto(user);
    }

    public ReadUserDto findAllUnfollowersByUserId(Pageable pageable, Long userId) {
        List<Long> followingIds = followRepository.findAllByFollowingIds(userId);
        followingIds.add(userId); // 팔로우 목록에 자신의 프로필이 노출되지 않도록 요청자 id를 추가

        PageImpl<User> userPage = userRepositorySupport.findUserPageByFollowingIds(pageable, followingIds);
        List<UserDto> userDtoList = userRepositorySupport.findAllUnfollowers(pageable, followingIds)
                .stream()
                .map(user -> {
                    // 팔로워 수를 map 내부에서 찾는 이유는
                    // follow가 매우 많아질 경우 한방 쿼리로 모든 유저의 팔로워를 구한 후 page로 자르는 것보다 성능상 이점을 취할 수 있다고 판단
                    user.setFollowerCount(followRepository.countByToUserId(user.getId()));
                    return user;
                })
                .toList();

        return ReadUserDto.builder()
                .totalPage(userPage.getTotalPages())
                .totalElement(userPage.getTotalElements())
                .items(userDtoList)
                .build();
    }
}
