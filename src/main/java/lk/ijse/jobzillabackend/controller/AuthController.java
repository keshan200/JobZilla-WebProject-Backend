package lk.ijse.jobzillabackend.controller;

import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.AuthDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.enums.Status;
import lk.ijse.jobzillabackend.service.impl.UserServiceImpl;
import lk.ijse.jobzillabackend.util.JwtUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;
    private final PasswordEncoder passwordEncoder;


    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/signIn")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {

        try{
           authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized,"Invalid Credential",e.getMessage()));
        }

        UserDTO user = userService.loadUserByEmail(userDTO.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict,"Account is Deactivated!,Contact Support Team",null));
        }

        String token = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict,"Authorization Failure ",null));
        }

        if (user.getStatus() != Status.ACTIVE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO(VarList.Forbidden, "Your account is suspended. Please contact support.", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(user.getEmail());
        authDTO.setToken(token);
        authDTO.setRefreshToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", authDTO));

    }


    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody AuthDTO authDTO) {
        try {

            if (!jwtUtil.validateRefreshToken(authDTO.getRefreshToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Invalid Refresh Token", null));
            }


            String email = jwtUtil.getUsernameFromToken(authDTO.getRefreshToken());
            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Invalid Refresh Token", null));
            }


            UserDTO user = userService.loadUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
            }




            String newAccessToken = jwtUtil.generateToken(user);
            if (newAccessToken == null || newAccessToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Token Generation Failure", null));
            }




            AuthDTO newAuthDTO = new AuthDTO();
            newAuthDTO.setEmail(user.getEmail());
            newAuthDTO.setToken(newAccessToken);
            newAuthDTO.setRefreshToken(authDTO.getRefreshToken());

            return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Token Refreshed Successfully", newAuthDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Error refreshing token", e.getMessage()));
        }
    }


}
