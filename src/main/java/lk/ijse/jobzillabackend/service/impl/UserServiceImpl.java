package lk.ijse.jobzillabackend.service.impl;

import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.UserService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }

    }

    @Override
    public int updateUser(UserDTO userDTO) {

        if (userRepository.findById(userDTO.getUid()).isPresent()) {

            userDTO.setUsername(userDTO.getUsername());
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
