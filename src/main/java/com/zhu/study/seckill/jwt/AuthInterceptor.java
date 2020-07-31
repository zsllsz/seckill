package com.zhu.study.seckill.jwt;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zhu.study.seckill.entity.User;
import com.zhu.study.seckill.enums.UserEnum;
import com.zhu.study.seckill.result.JsonResult;
import com.zhu.study.seckill.service.UserService;
import com.zhu.study.seckill.util.JsonUtil;

/**
 * 拦截除了有@SkipToken注解的方法
 * @author zhusl 
 * @date 2020年7月24日  上午10:44:03
 *
 */
public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserService userService;
	
	@Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object object) {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 只拦截方法，不是方法直接返回true
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        
        if (method.isAnnotationPresent(SkipToken.class)) { // 有SkipToken注解的直接跳过认证
            return true;
        } else { // 没有SkipToken注解的都需要认证
            if (token == null) {
                String msg = JSONObject.wrap(new JsonResult<>(UserEnum.NULL_TOKEN.getCode(), UserEnum.NULL_TOKEN.getMessage())).toString();
                JsonUtil.writeJsonToPage(httpServletResponse, msg);
                return false;
            }
            // 获取 token 中的 userId
            String userId = null;
            try {
                userId = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
            	String msg = JSONObject.wrap(new JsonResult<>(UserEnum.SYS_ERROR.getCode(), UserEnum.SYS_ERROR.getMessage())).toString();
                JsonUtil.writeJsonToPage(httpServletResponse, msg);
                return false;
            }
            // 根据userId查询数据库
            User user = userService.findUserById(Integer.valueOf(userId));
            if (user == null) {
            	String msg = JSONObject.wrap(new JsonResult<>(UserEnum.LOGIN_FAIL.getCode(), UserEnum.LOGIN_FAIL.getMessage())).toString();
                JsonUtil.writeJsonToPage(httpServletResponse, msg);
                return false;
            }
            // 用查出来的user的密码去校验token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUPassword())).build();
            try {
                // 没发生异常就表示校验通过
                jwtVerifier.verify(token);
                return true;
            } catch (JWTVerificationException e) {
            	String msg = JSONObject.wrap(new JsonResult<>(UserEnum.SYS_ERROR.getCode(), UserEnum.SYS_ERROR.getMessage())).toString();
                JsonUtil.writeJsonToPage(httpServletResponse, msg);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o,ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Object o, Exception e) {
    }

}
