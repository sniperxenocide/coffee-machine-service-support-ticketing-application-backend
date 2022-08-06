package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.config.JwtTokenUtil;
import com.cgd.cvm_technical_support.model.primary.Privilege;
import com.cgd.cvm_technical_support.model.primary.Role;
import com.cgd.cvm_technical_support.model.primary.User;
import com.cgd.cvm_technical_support.repository.primary.RePrivilege;
import com.cgd.cvm_technical_support.repository.primary.ReRole;
import com.cgd.cvm_technical_support.repository.primary.ReUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class SeCommon {

    private final JwtTokenUtil jwtTokenUtil;
    private final ReUser reUser;
    private final ReRole reRole;
    private final RePrivilege rePrivilege;

    @Autowired
    public SeCommon(JwtTokenUtil jwtTokenUtil, ReUser reUser, ReRole reRole, RePrivilege rePrivilege)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.reUser = reUser;
        this.reRole = reRole;
        this.rePrivilege = rePrivilege;
    }

    public String getTokenFromCookie(HttpServletRequest request){
        if(request.getCookies()!=null){
            for (Cookie c:request.getCookies()) {
                if (c.getName().equals("Authorization")){
                    return "Bearer "+c.getValue();
                }
            }
        }
        return null;
    }

    public String getTokenFromHeader(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public String getToken(HttpServletRequest request){
        String tokenHeader = getTokenFromHeader(request);
        if(tokenHeader==null) tokenHeader = getTokenFromCookie(request);

        if(tokenHeader==null) return null;
        else if(tokenHeader.startsWith("Bearer")) return tokenHeader.substring(7);
        return null;
    }

    public User getUser(HttpServletRequest request){
        try {
            String token = getToken(request);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            return reUser.findByUsername(userName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getUser(String userName){
        try {
            return reUser.findByUsername(userName);
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public boolean isAuthorizedRequest(Long userId,String api){
        Privilege privilege = rePrivilege.findByApi(api).orElse(null);
        if(privilege==null) return false;
        User user = reUser.getUserByPrivilege(userId, privilege.getId()).orElse(null);
        return user!=null;
    }

    public User getUser(Long id){
        return reUser.findById(id).orElse(null);
    }

    public boolean checkUserRole(User user,String roleName){
        for(Role role: user.getRoles())
            if (role.getName().equalsIgnoreCase(roleName)) return true;
        return false;
    }

    public void recordLastLoginTime(User user){
        try {
            user.setLastLoginTime(LocalDateTime.now());
            reUser.save(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
