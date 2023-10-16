package threads.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.domain.activity.Activity;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.activity.dto.ActivityDto;
import threads.server.domain.activity.dto.ActivityUserDto;
import threads.server.domain.activity.dto.ReadActivityDto;
import threads.server.domain.activity.repository.ActivityRepositorySupport;
import threads.server.domain.follow.FollowRepository;
import threads.server.domain.user.dto.ReadUserDto;
import threads.server.domain.user.dto.UserDto;
import threads.server.domain.user.repository.UserRepository;
import threads.server.domain.user.repository.UserRepositorySupport;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final UserRepositorySupport userRepositorySupport;
    private final ActivityRepositorySupport activityRepositorySupport;

    public UserDto getOneByUserId(Long userId, Long visitorId) {
        UserDto userDto = userRepositorySupport.findOneByUserId(userId);
        userDto.setFollowed(followRepository.findByToUserIdAndFromUserId(userId, visitorId).isPresent());
        return userDto;
    }

    public ReadUserDto findAllUnfollowersByUserId(Pageable pageable, Long userId) {
        List<Long> followingIds = followRepository.findAllByFollowingIds(userId);
        followingIds.add(userId); // 팔로우 목록에 자신의 프로필이 노출되지 않도록 요청자 id를 추가

        PageImpl<User> userPage = userRepositorySupport.findUserPageByFollowingIds(pageable, followingIds);
        List<UserDto> userDtoList = userRepositorySupport.findAllUnfollowers(pageable, followingIds)
                .stream()
                .peek(user -> {
                    // 팔로워 수를 map 내부에서 찾는 이유는
                    // follow가 매우 많아질 경우 한방 쿼리로 모든 유저의 팔로워를 구한 후 page로 자르는 것보다 성능상 이점을 취할 수 있다고 판단
                    user.setFollowerCount(followRepository.countByToUserId(user.getId()));
                    user.setFollowingCount(followRepository.countByFromUserId(user.getId()));
                })
                .toList();

        return new ReadUserDto(
                userPage.getTotalPages(),
                userPage.getTotalElements(),
                userDtoList
        );
    }

    public ReadUserDto search(Pageable pageable, String keyword, Long userId) {
        List<Long> followingIds = followRepository.findAllByFollowingIds(userId);
        followingIds.add(userId);

        PageImpl<User> searchedUserPage = userRepositorySupport.findKeywordPageByKeyword(pageable, keyword, followingIds);
        // 검색은 팔로워 수를 세지 않음, 나중에 기획이 변경되면 추가될 수도
        List<UserDto> userDtoList = userRepositorySupport.searchByUnfollowers(pageable, keyword, followingIds);

        return new ReadUserDto(
                searchedUserPage.getTotalPages(),
                searchedUserPage.getTotalElements(),
                userDtoList
        );
    }

    public ReadActivityDto findAllActivitiesByUserId(Pageable pageable, Long userId, ActivityStatus status) {
        PageImpl<Activity> searchedUserPage = activityRepositorySupport.findActivityPageByUserId(pageable, userId, status);
        List<ActivityDto> activityDtoList = activityRepositorySupport.findAllActivitiesByUserIdAndStatus(pageable, userId, status)
                .stream()
                .peek(activity -> activity.setFromUser(ActivityUserDto.toActivityUserDto(activity.getFromUserEntity())))
                .toList();

        return new ReadActivityDto(
                searchedUserPage.getTotalPages(),
                searchedUserPage.getTotalElements(),
                activityDtoList
        );
    }

//    스프링에서는 인증, 인가가 이뤄지지 않음
//    public UserDto signUp(SignUpDTO userDTO) {
//        userRepository.findByEmail(userDTO.email()).ifPresent((user) -> {
//            throw new BadRequestException("이미 존재하는 이메일입니다.");
//        });
//
//        User user = userRepository.save(new User(null, userDTO.email(), userDTO.password(), userDTO.name(), userDTO.nickname(), userDTO.userRole(), null, null, false, null, null));
//        return toDto(user);
//    }
//
//    public UserDto signIn(SignInDTO userDTO) {
//        String password = userDTO.password();
//        User user = userRepository.findOneByEmailAndPassword(userDTO.email(), password).orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
//        return toDto(user);
//    }
}
