package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.UserDTO;

import java.util.List;


public interface UserService {
    int saveUser(UserDTO userDTO);
    int updateUser(UserDTO userDTO);
    int deleteUser(int id);
    List<UserDTO> getAll();
}
