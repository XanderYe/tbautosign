package cn.xanderye.tbautosign.interceptor;

import cn.xanderye.tbautosign.base.RequestContextHolder;
import cn.xanderye.tbautosign.entity.User;
import cn.xanderye.tbautosign.enums.ErrorCode;
import cn.xanderye.tbautosign.exception.BusinessException;
import cn.xanderye.tbautosign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Xander on 2018-11-05.
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String remoteAddr = request.getRemoteAddr();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        //不拦截OPTION请求
        if (method.equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        String userToken = request.getHeader("md-token");
        log.info("remoteAddr={},  method={}, uri={}, userToken={}", remoteAddr, method, uri, userToken);

        if (userToken == null) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_EXCEPTION, "remoteAddr={}, method={}, uri={}, userToken={}", remoteAddr, method, uri, userToken);
        }

        User user = userService.selectByToken(userToken);
        if (user == null) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_EXCEPTION, "remoteAddr={}, method={}, uri={}, userToken={}", remoteAddr, method, uri, userToken);
        }

        RequestContextHolder.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContextHolder.reset();
    }
}