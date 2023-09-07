package pl.foodflow.infrastructure.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.foodflow.infrastructure.security.user.UserEntityJpaRepository;

@Service
@RequiredArgsConstructor
public class FoodFlowUserDetailsService implements UserDetailsService {

    private final UserEntityJpaRepository userEntityJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityJpaRepository.findByUsername(username).map(FoodFlowUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
