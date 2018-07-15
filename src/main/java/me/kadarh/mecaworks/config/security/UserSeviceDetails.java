package me.kadarh.mecaworks.config.security;

import me.kadarh.mecaworks.repo.UserRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service(BeanIds.USER_DETAILS_SERVICE)
@Primary
@Transactional
public class UserSeviceDetails implements UserDetailsService {

    private final UserRepo userRepo;

    public UserSeviceDetails(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s)
                .map(user -> new User(user.getUsername(), user.getPassword(), user.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())))
                .orElseThrow(() -> new UsernameNotFoundException("No user : " + s));
    }
}
