package com.club.escalade.security;

import com.club.escalade.dao.MembreRepository;
import com.club.escalade.entity.Membre;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MembreRepository membreRepository;

    public CustomUserDetailsService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Membre membre = membreRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        Set<String> authorities = membre.getAuthorities().isEmpty()
                ? Set.of("ROLE_USER")
                : membre.getAuthorities();

        return User.withUsername(membre.getEmail())
                .password(membre.getMotDePasse())
                .authorities(authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build();
    }
}
