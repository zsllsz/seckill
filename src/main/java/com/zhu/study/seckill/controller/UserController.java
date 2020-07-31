package com.zhu.study.seckill.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhu.study.seckill.entity.User;
import com.zhu.study.seckill.enums.UserEnum;
import com.zhu.study.seckill.jwt.SkipToken;
import com.zhu.study.seckill.jwt.TokenService;
import com.zhu.study.seckill.result.JsonResult;
import com.zhu.study.seckill.service.UserService;

/**
 * @author zhusl 
 * @date 2020年7月22日  上午10:29:03
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;
	
	@GetMapping("/{id}")
	public JsonResult<?> User(@PathVariable("id") Integer id) {
		return new JsonResult<User>(UserEnum.SUCCESS.getCode(), UserEnum.SUCCESS.getMessage(), userService.findUserById(id));
	}
	
	@PostMapping("/login")
	@SkipToken
	public JsonResult<?> login(User user){
		User dbUser = userService.login(user);
		if (dbUser == null) {
			return new JsonResult<>(UserEnum.LOGIN_FAIL.getCode(), UserEnum.LOGIN_FAIL.getMessage());
		} else {
			String token = tokenService.getToken(dbUser);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("user", dbUser);
            return new JsonResult<>(UserEnum.SUCCESS.getCode(), UserEnum.SUCCESS.getMessage(), resultMap);
		}
	}
}
