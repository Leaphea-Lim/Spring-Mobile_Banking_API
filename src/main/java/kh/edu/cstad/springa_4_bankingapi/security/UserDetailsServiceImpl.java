package kh.edu.cstad.springa_4_bankingapi.security;

import kh.edu.cstad.springa_4_bankingapi.domain.User;
import kh.edu.cstad.springa_4_bankingapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setUser(user);

        return customerDetails;
    }
}
