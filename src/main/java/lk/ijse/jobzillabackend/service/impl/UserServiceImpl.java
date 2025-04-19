package lk.ijse.jobzillabackend.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.enums.Status;
import lk.ijse.jobzillabackend.enums.UserRole;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.UserService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService , UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return authorities;
    }

    public UserDTO loadUserByEmail(String email) throws UsernameNotFoundException {
        User byEmail = userRepository.findByEmail(email);
        return modelMapper.map(byEmail, UserDTO.class);
    }


    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userRepository.save(modelMapper.map(userDTO, User.class));
            createDefaultAdmin();
            return VarList.Created;
        }

    }

    @Transactional
    public void createDefaultAdmin() {
        String adminEmail = "ad@gmail.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            try {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(new BCryptPasswordEncoder().encode("Admin@123"));
                admin.setUsername("Admin");
                admin.setMobile(1234567890);
                admin.setRole(UserRole.ADMIN);

                userRepository.save(admin);
                System.out.println("Default admin account created.");
            } catch (Exception e) {
                System.err.println("Error creating default admin: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Default admin account already exists.");
        }
    }




    @Override
    public int updateUser(UserDTO userDTO) {

        if (userRepository.findById(userDTO.getUid()).isPresent()) {
            userDTO.setEmail(userDTO.getEmail());
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        } else {
            return VarList.Not_Modified;
        }
    }

    @Override
    public int deleteUser(int id) {
        return 0;
    }


    @Override
    public List<UserDTO> getAll() {
        return modelMapper.map(userRepository.findAll(),new TypeToken<List<UserDTO>>(){}.getType());
    }





    }



