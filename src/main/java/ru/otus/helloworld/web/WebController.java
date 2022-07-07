package ru.otus.helloworld.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.helloworld.dao.entity.UserEntity;
import ru.otus.helloworld.dao.repository.UserRepository;
import ru.otus.helloworld.models.InlineResponse200;
import ru.otus.helloworld.models.User;
import ru.otus.helloworld.models.UserRequest;
import ru.otus.helloworld.models.api.UserApi;

@RestController
public class WebController implements UserApi {

    private final UserRepository userRepository;

    @Autowired
    public WebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<InlineResponse200> createUser(UserRequest addUserRequest) {
        var userEntity = new UserEntity();
        userEntity.setUsername(addUserRequest.getUsername());
        userEntity.setFirstName(addUserRequest.getFirstName());
        userEntity.setLastName(addUserRequest.getLastName());
        userEntity.setPhone(addUserRequest.getPhone());
        userEntity.setEmail(addUserRequest.getEmail());
        var savedUser = userRepository.save(userEntity);
        return ResponseEntity.ok(new InlineResponse200().id(savedUser.getId()));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteUser(Long userId) {
        var entity = userRepository.findById(userId);
        if (entity.isPresent()) {
            userRepository.delete(entity.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<User> findUserById(Long userId) {
        return userRepository.findById(userId)
            .map(userEntity -> {
                var user = new User()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .email(userEntity.getEmail())
                    .phone(userEntity.getPhone());
                return ResponseEntity.ok(user);
            }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<Void> updateUser(Long userId, UserRequest user) {
        var entity = userRepository.findById(userId)
            .map(userEntity -> {
                userEntity.setUsername(user.getUsername());
                userEntity.setFirstName(user.getFirstName());
                userEntity.setLastName(user.getLastName());
                userEntity.setPhone(user.getPhone());
                userEntity.setEmail(user.getEmail());
                return userEntity;
            });
        if (entity.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
