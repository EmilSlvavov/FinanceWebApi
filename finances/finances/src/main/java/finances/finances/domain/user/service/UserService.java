package finances.finances.domain.user.service;

import finances.finances.domain.user.entity.User;
import finances.finances.domain.user.repository.UserRepository;
import finances.finances.dtos.UserFilterRequest;
import finances.finances.dtos.UserRequest;
import finances.finances.dtos.UserResponse;
import finances.finances.specifications.BaseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Async("taskExecutor")
    public CompletableFuture<UserResponse> create(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        return CompletableFuture.completedFuture(toResponse(userRepository.save(user)));
    }

    @Async("taskExecutor")
    public CompletableFuture<Page<UserResponse>> findAll(UserFilterRequest filter) {
        Specification<User> spec = Specification.allOf(
                BaseSpecification.equal("role", filter.getRole()),
                BaseSpecification.equal("isActive", filter.getIsActive())
        );

        Sort sort = filter.getSortDir().equalsIgnoreCase("asc")
                ? Sort.by(filter.getSortBy()).ascending()
                : Sort.by(filter.getSortBy()).descending();

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        return CompletableFuture.completedFuture(
                userRepository.findAll(spec, pageable).map(this::toResponse)
        );
    }

    @Async("taskExecutor")
    public CompletableFuture<UserResponse> findById(Integer id) {
        return CompletableFuture.completedFuture(toResponse(getOrThrow(id)));
    }

    @Async("taskExecutor")
    public CompletableFuture<UserResponse> update(Integer id, UserRequest request) {
        User user = getOrThrow(id);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return CompletableFuture.completedFuture(toResponse(userRepository.save(user)));
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> delete(Integer id) {
        userRepository.delete(getOrThrow(id));
        return CompletableFuture.completedFuture(null);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private User getOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setRole(user.getRole());
        response.setIsActive(user.getIsActive());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}