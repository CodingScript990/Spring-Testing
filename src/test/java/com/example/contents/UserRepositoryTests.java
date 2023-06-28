package com.example.contents;

import com.example.contents.entity.UserEntity;
import com.example.contents.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired // 왠만하면 지양할 것
    private UserRepository userRepository;
    // 새 UserEntity 를 데이터베이스에 추가 성곡
    @Test
    @DisplayName("새 UserEntity 를 데이터베이스에 추가 성공")
    public void testSaveNew() {
        // given : 테스트가 진행되기 위한 전체 조건을 준비하는 구간
        // 새로운 userEntity 준비
        String username = "test.dev";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        // when : 테스트 하고싶은 실제 기능을 작성하는 구간
        user = userRepository.save(user);
        // then : 실행한 결과가 기대한 것과 같은지를 검출하는 구간
        // 1. 새로 반환받은 user 의 id 는 null 이 아님
        assertNotNull(user.getId());
        // 2. 새로 반환받은 user 의 username 은 우리가 넣었던 username 과 동일
        assertEquals(username, user.getUsername());
    }

    @Test
    @DisplayName("새 UserEntity 를 데이터베이스에 추가 실패")
    public void testSaveNewFail() {
        // given : username 을 가지고 UserEntity 를 먼저 save
        String username = "test.dev";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        userRepository.save(user);
        // when : 같은 username 을 가진 새 UserEntity save 시도
        user = new UserEntity();
        user.setUsername(username);
        // then : 예외 발생
        UserEntity finalUser = user;
        assertThrows(Exception.class, () -> userRepository.save(finalUser));
    }

    @Test
    @DisplayName("username 으로 UserEntity 찾기")
    public void testFindByUsername() {
        // given : 검색할 UserEntity 미리 생성
        String username = "test.dev";
        UserEntity userGiven = new UserEntity();
        userGiven.setUsername(username);
        userRepository.save(userGiven);

        // when : userRepository.findByUsername()
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        // then : Optional.isPresent(), username == username
         assertTrue(optionalUser.isPresent());
        assertEquals(username, optionalUser.get().getUsername());
    }

    // username 으로 찾기 실패

    // username 으로 존재하는지 확인

    // id 로 userEntity 삭제

}
