//package com.gamevision.util;
//
//
//import com.gamevision.model.enums.UserRoleEnum;
//import com.gamevision.model.user.GamevisionUserDetails;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//    public class TestUserDataService implements UserDetailsService {
//
//   // SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.ADMIN.name());
//   // SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_" + UserRoleEnum.ADMIN.name());
//   // Set<SimpleGrantedAuthority> auhtorities = new HashSet<>(Set.of(adminAuthority, userAuthority));
//
//
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            return new GamevisionUserDetails("AdminFromTestUserDataService",
//                    "admin",
//                    username,
//                    true,
//                    Collections.emptySet());
//        }
//
//
//}

