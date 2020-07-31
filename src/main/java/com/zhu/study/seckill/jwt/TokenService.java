package com.zhu.study.seckill.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhu.study.seckill.entity.User;

/**
 * 用来生成token
 * @author zhusl 
 * @date 2020年7月24日  上午10:31:29
 *
 */
@Service
public class TokenService {
	// 设置token过期时间5分钟
    private static final long EXPIRE_TIME = (60 * 1000 * 5);

    public String getToken(User user) {
        return JWT.create()
                // 把userId放入token中
                .withAudience(String.valueOf(user.getUId()))
                // 设置token过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                // 用户密码当作密钥
                .sign(Algorithm.HMAC256(user.getUPassword()));
    }
}
