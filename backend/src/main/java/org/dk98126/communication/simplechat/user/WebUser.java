package org.dk98126.communication.simplechat.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Пользователь чата
 */
@Entity
@Data
@NoArgsConstructor
public class WebUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;

    @ElementCollection(targetClass = WebUserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "web_user_role", joinColumns = @JoinColumn(name = "web_user_id"))
    @Enumerated(EnumType.STRING)
    private Set<WebUserRole> roles;

    public WebUser(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return roles.contains(WebUserRole.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
