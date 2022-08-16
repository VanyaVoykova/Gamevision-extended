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


//For some reason, the IDE cannot detect that the HttpSecurity bean is configured by Spring Boot. You can get rid of the error by adding @EnableWebSecurity to your configuration class, it solves it because the annotation imports the HttpSecurityConfiguration configuration class.
@EnableWebSecurity
@Configuration
public class GamevisionSecurityConfiguration {

    //Here we have to expose 3 things:
    // 1. PasswordEncoder @Bean
    // 2. UserDetailsService (with user repo in constructor) @Bean
    // 3. SecurityFilterChain


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new GamevisionUserDetailsService(userRepository);
    }

    @Bean //defines which pages should require authentication / specific role - type http. to see them all
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //    http.csrf().ignoringAntMatchers("https://www.youtube.com"); //to allow playthrough videos
        // define which requests are allowed and which not

        //  http.headers().frameOptions().sameOrigin().defaultsDisabled(); //for deleting playthroughs, SS doesn't like the <iframe> with the video

        //antMatchers ORDER MATTERS
        http.authorizeRequests()
                // everyone can download static resources (css, js, images)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                //pages everyone can access - authentication is required only for likes and comments + admin & moderator functions
                //.antMatchers("/**").permitAll()

                .antMatchers("/admin/**", "/games/add", "/games/{id}/edit", "/games/{id}/delete", "/games/{id}/playthroughs/add").hasRole(UserRoleEnum.ADMIN.name())
                //TODO: for some reason "/games/add" can be accessed by guests????? Shouldn't the POST antMatchers above be overridden?

                //The only POST accessible to unauthenticated users
                .antMatchers(HttpMethod.POST, "/users/register", "/users/login").anonymous()

                .antMatchers(HttpMethod.GET, "/**", "/games/{id}", "/games/{id}/comments").permitAll() // everyone can GET               games/view/* - view a game, * is id; removed "/api/**"
                //removed from above: "/about", "/users/forum", "/games/**", "/api/**"       "/games/{id}"   games/{id}/*",     "/games/all", "/games/{id}/playthroughs/all",


                .antMatchers(HttpMethod.POST, "/**").authenticated() //only authenticated can POST; admin-specific and additional authorizations below
                // removed api/games/{gameId}

                // .antMatchers("/pages/moderators").hasRole(UserRoleEnum.MODERATOR.name()) ///games/{gameId}/playthroughs/add/(gameId=*{id})}" //uncomment for MODERATOR

//TODO: add for admins - users/{userId} - user management

                //TODO add for profile
                .antMatchers("/users/profile").authenticated()

                //TODO: "api/**" is for comments - check if only authenticated users can make POST requests (post and like comments)
//** is that the second matches the entire directory tree
                //* only matches at the level it's specified at.


                //TODO fix cannot post comments, going with api/** .permitAll for now above
                //only authenticated users can post and like comments, everybody can view comments    //TODO: NCOMMENT

                //  .antMatchers(HttpMethod.POST, "/api/games/{gameId}").authenticated() //post is actually from /games/{gameId}


                //All other pages available for authenticated users (aka simple users)
                .anyRequest()
                .authenticated()

                .and()
                //login <form> configuration
                .formLogin()
                // the login page with its url
                .loginPage("/users/login")
                //check what credentials are used for login, usually username and password
                // the name of the username <form> field;     //simpler alternative: userNameParemeter("username")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) //alternative:  .usernameParameter("username")
                // the name of the password <form> field; naming is very important
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY) //alternative:   .passwordParameter("password")

                // where to go on successful login
                .defaultSuccessUrl("/") //sometimes goes to /games ????  <- error
                // where to go in case on failed login, just a mapping in controller is enough, no separate template needed, just redirect to login
                .failureForwardUrl("/users/login-error") //("/users/login-error") or put a query param ("/users/login?error=true")

                .and()
                // configure logout
                .logout()
                //the logout url, must be POST request (remember to use POST in controller and template)
                .logoutUrl("/users/logout")
                .clearAuthentication(true)
                // invalidate the session and delete the cookies
                .invalidateHttpSession(true) //Pathfinder iss without this
                .deleteCookies("JSESSIONID")
                // on logout go to the home page; It shouldn't be able to fail, right?
                .logoutSuccessUrl("/"); //redirect only after the above
        //add ; and remove the rest if you want to use cors


        //todo add cors for Youtube - needed for playthrough videos

        //TODO: COMMENT OUT these two, add ; above
        //cannot find csrf tokens if disabled, of course
        // .and()
        // .csrf().disable();


        //.csrf().disable(); //if not using any cors, tokens


        return http.build();
    }


}
