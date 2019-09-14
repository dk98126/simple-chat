package org.dk98126.communication.simplechat.service;

import org.dk98126.communication.simplechat.repository.WebUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WebUserService implements UserDetailsService {

    @Autowired
    private WebUserRepo webUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return webUserRepo.findByUsername(username);
    }
}
