package com.RYPS.usermicroservice.Service;

import com.RYPS.usermicroservice.Repositories.UserRepository;
import com.RYPS.usermicroservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("Error the user: "+s+" do not exist.");
        }
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map((SimpleGrantedAuthority::new)).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(s,user.getPassword(),true,true,true,true,authorities);

    }

    public User save(User user){
        return this.userRepository.save(user);
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }
    public Iterable<User> findAll(){
        return userRepository.findAll();
    }
    public void delete(User user){
        userRepository.delete(user);
    }
    public Optional<User> findByid(Integer id){
        return userRepository.findById(id);
    }


}
