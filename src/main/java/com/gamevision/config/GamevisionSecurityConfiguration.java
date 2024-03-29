package com.gamevision.config;

import com.gamevision.model.enums.UserRoleEnum;
import com.gamevision.repository.UserRepository;
import com.gamevision.service.GamevisionUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //Imports HttpSecurityConfiguration, in case HttpSecurity bean is not detected by Spring Boot
@Configuration
public class GamevisionSecurityConfiguration {

    //Here we have to expose 3 @Beans:
    // 1. PasswordEncoder
    // 2. UserDetailsService (with user repo in constructor)
    // 3. SecurityFilterChain


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new GamevisionUserDetailsService(userRepository);
    }

    @Bean //authentication is required only for likes and comments + admin & moderator functions
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //antMatchers ORDER MATTERS - more specific rules go first
        http.authorizeRequests()
                .antMatchers("/admin/**", "/games/add", "/games/{id}/edit", "/games/{id}/delete", "/games/{id}/playthroughs/add").hasRole(UserRoleEnum.ADMIN.name())

                .antMatchers(HttpMethod.POST, "/users/register", "/users/login").anonymous()

                //FIXME: guests should see comments; check GameController * JS
                .antMatchers(HttpMethod.GET, "/games/{id}/comments", "/api/games/{id}/comments", "/games/{id}/playthroughs", "/games/{id}").permitAll() // everyone can view games, comments and playthroughs
                //removed from above: "/about", "/users/forum", "/games/**", "/api/**"       "/games/{id}"   games/{id}/*",     "/games/all", "/games/{id}/playthroughs/all",

                .antMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                // .antMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name()) ///games/{gameId}/playthroughs/add/(gameId=*{id})}" //uncomment for MODERATOR

//TODO: add for admins - users/{userId} - user management

                //TODO add for profile
                .antMatchers("/users/profile").authenticated()

                //All other pages available for authenticated users (aka simple users)
                .anyRequest()
                .authenticated()

                .and()

                .formLogin()
                .loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) //alternative:  .usernameParameter("username")
                // the name of the password <form> field; naming is very important
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY) //alternative:   .passwordParameter("password")
                .defaultSuccessUrl("/") //sometimes goes to /games ????  <- error
                //just a mapping in controller is enough, no separate template needed, just redirect to login
                .failureForwardUrl("/users/login-error") //("/users/login-error") or put a query param ("/users/login?error=true")

                .and()

                .logout()
                //must be POST request (remember to use POST in controller and template)
                .logoutUrl("/users/logout")
                .clearAuthentication(true)
                // invalidate the session and delete the cookies
                .invalidateHttpSession(true) //Pathfinder iss without this
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/"); //redirect only after the above
        //add ; and remove the rest if you want to use cors

        //.csrf().disable(); //if not using any cors, tokens


        return http.build();
    }


}
