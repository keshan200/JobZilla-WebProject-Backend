package lk.ijse.jobzillabackend.controller;

import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.AuthDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Company;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
        authDTO.setRole(user.getRole());
        authDTO.setToken(token);
        authDTO.setId(user.getUid());
        authDTO.setName(user.getUsername());
        authDTO.setRefreshToken(refreshToken);




        System.out.println(authDTO);

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


    @GetMapping("/api/v1/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                return ResponseEntity.ok("Token is valid.");
            } else {
                return ResponseEntity.status(401).body("Invalid or expired token.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token validation failed: " + e.getMessage());
        }
    }


}
