package org.gestionstock.stock.Security.Service;

import org.gestionstock.stock.EntityRepository.UserRepository;
import org.gestionstock.stock.Exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException{
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException());
    }
}
