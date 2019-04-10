package com.share.jwtsecurity.service;

import com.share.jwtsecurity.model.ApplicationUser;
import com.share.jwtsecurity.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByUsername(username);

        if (null == user) {
            throw new UsernameNotFoundException("No user found for :" + username);
        }
        return new User(user.getUsername(), user.getPassword(), getAuthorities(user.getUserRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<String> userRoles) {
        List<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for (String userRole : userRoles) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole);
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}
