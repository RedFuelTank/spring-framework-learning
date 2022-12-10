package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserDto {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean isActive;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities",
            joinColumns = {
                    @JoinColumn(name = "username")
            })
    private List<Authority> authorities;

    public List<String> getAuthorities() {
        return authorities.stream()
                .map(Authority::getAuthorityName)
                .toList();
    }

    @Embeddable
    @Data
    private static class Authority {
        @Column(name = "authority")
        private String authorityName;
    }
}
