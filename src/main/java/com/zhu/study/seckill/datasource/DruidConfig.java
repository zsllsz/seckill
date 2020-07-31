package com.zhu.study.seckill.datasource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

/**
 * druid连接池配置，需要引入log4j依赖，否则启动会报错
 * @author zhusl 
 * @date 2020年7月23日  下午6:29:31
 *
 */
@Configuration
public class DruidConfig {

	// 将配置文件中druid相关配置注入
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}
	
	/**
	 * 配置druid的监控界面</br>
	 * 浏览器访问：http://localhost/druid/login.html即可进入监控页面</br>
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        HashMap<String,String> initParameters = new HashMap<>();
        // 登录监控界面的用户名和密码
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","123456");
        // 允许谁可以访问
        initParameters.put("allow","");
        bean.setInitParameters(initParameters);
        return bean;
    }
	
}
