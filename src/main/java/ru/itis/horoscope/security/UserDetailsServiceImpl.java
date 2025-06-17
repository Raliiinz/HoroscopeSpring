package ru.itis.horoscope.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.service.ClientService;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientService clientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client user = clientService.findClientByEmail(username);
        return new User(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_CLIENT")));
    }
}
