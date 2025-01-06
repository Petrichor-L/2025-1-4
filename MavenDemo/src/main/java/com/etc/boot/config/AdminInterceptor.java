package com.etc.boot.config;

import com.etc.boot.Pojo.User;
import com.etc.boot.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否是管理员
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.setContentType("application/json;charset=utf-8");
            Result<Object> result = Result.error("无权限访问");
            new ObjectMapper().writeValue(response.getWriter(), result);
            return false;
        }
        return true;
    }
} 