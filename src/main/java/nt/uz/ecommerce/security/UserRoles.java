package nt.uz.ecommerce.security;

import java.util.ArrayList;
import java.util.List;

import static nt.uz.ecommerce.security.UserAuthorities.*;

public enum UserRoles{
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_SUPER_ADMIN;
//    final List<UserAuthorities> authorities;
//    UserRoles (List<UserAuthorities> authorities){
//        this.authorities=authorities;
//    }
//    public List<String> getAuthorities(){
//        List<String> list = new ArrayList<>(this.authorities.stream()
//                .map(UserAuthorities::getName)
//                .toList());
//        list.add("ROLE_"+this.name());
//        return list;
//    }
    public String getName() {
        return UserRoles.ROLE_USER.getName();
    }
}
