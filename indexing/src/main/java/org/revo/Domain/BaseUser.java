package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static java.util.stream.Collectors.toList;
import static org.revo.Domain.Role.*;

/**
 * Created by ashraf on 17/04/17.
 */
@Getter
@Setter
public abstract class BaseUser implements UserDetails {
    @JsonProperty(access = READ_ONLY)
    private boolean locked = true;
    @JsonProperty(access = READ_ONLY)
    private boolean enable = false;

    private Collection<CustomGrantedAuthority> authorities;

    @Override
    public Collection<CustomGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<CustomGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}