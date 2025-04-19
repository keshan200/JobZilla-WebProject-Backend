package lk.ijse.jobzillabackend.config;


import lk.ijse.jobzillabackend.service.UserService;
import lk.ijse.jobzillabackend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserServiceImpl userService;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
             throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:63342"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }




    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/user/register",
                                "/api/v1/auth/signIn",
                                "/api/v1/auth/refreshToken",
                                "/api/v1/job/getAll",
                                "/api/v1/company/getAll",
                                "/api/v1/png/upload",
                                "/api/v1/auth/validate",
                                "/api/v1/img/uploads",
                                "/api/v1/application/getAll",
                                "/uploads/**",
                                "/api/v1/company/all/{cid}",
                                "/api/v1/job/getJobsByJobId/{jobId}",
                                "/api/v1/socialMedia/save",
                                "/api/v1/candidate/getAll",
                                "/api/v1/candidate/getCandidateByCandId/{candId}",
                                "/ws/**",
                                "/api/v1/job/search",
                                "/api/v1/message/between/{senderId}/{receiverId}",
                                "/api/v1/job/search-page",
                                "/auth/forgot-password/{email}",
                                "/auth/reset-password"


                        ).permitAll()


                        .requestMatchers(
                                         "/api/v1/company/register",
                                         "/api/v1/company/user/{userId}",
                                         "/api/v1/job/company/{companyId}",
                                         "/api/v1/application/company/{companyId}",
                                         "/api/v1/job/job-count/{companyId}",
                                         "/api/v1/jobCategory/getAll"
                                         ).hasAuthority("EMPLOYER")

                        .requestMatchers(
                                "/api/v1/candidate/user/{userId}",
                                "/api/v1/application/apply-jobs/{candidateId}",
                                "/api/v1/message/receivers/{senderId}"
                           ).hasAuthority("CANDIDATE")


                        .requestMatchers(
                                "/api/v1/jobCategory/save",
                                "/api/v1/jobCategory/getAll"
                        ).hasAuthority("ADMIN")


                        .requestMatchers("/api/v1/job/save","/api/v1/job/post-job")
                        .hasAuthority("EMPLOYER")
                        .anyRequest().authenticated()





                )


                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}



/*"api/v1/company/register",
        "/api/v1/company/update",
        "api/v1/socialMedia/register"*/