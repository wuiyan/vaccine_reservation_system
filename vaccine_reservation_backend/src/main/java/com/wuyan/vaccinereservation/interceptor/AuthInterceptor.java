package com.wuyan.vaccinereservation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.wuyan.vaccinereservation.common.GlobalConstant.LOGIN_STATUS;

/**
 * 自定义拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行拦截

        // 检查用户是否已登录
        boolean isLoggedIn = checkUserLoggedIn(request);

        if (!isLoggedIn) {
            // 用户未登录，返回未登录错误
            throw new BusinessException(ResponseCode.NO_LOGIN);
        }

        // 用户已登录，允许请求继续执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后进行拦截，可以在此处进行一些后处理操作
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求完成之后进行拦截，可以在此处进行一些清理操作
    }

    // 这里假设你已经有了判断用户是否登录的方法
    private boolean checkUserLoggedIn(HttpServletRequest request) {
        // 根据实际情况实现用户登录状态的检查逻辑
        // 返回 true 表示用户已登录，返回 false 表示用户未登录
        // 这里简单地假设用户已登录
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(LOGIN_STATUS);
        if (attribute == null){
            return false;
        }
        return true;
    }
}
