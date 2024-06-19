package com.bzu.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(Set.of(Permission.ADMIN_READ, Permission.ADMIN_CREATE, Permission.ADMIN_UPDATE, Permission.ADMIN_DELETE)),
    MANAGER(Set.of(Permission.MANAGER_READ, Permission.MANAGER_CREATE, Permission.MANAGER_UPDATE, Permission.MANAGER_DELETE)),
    USER(Set.of());

    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}