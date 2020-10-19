package xyz.zwhzwhzwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *程序的入口点，负责启动app以及初始化
 * */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**"). //允许所有映射
				allowCredentials(true). 
				allowedOrigins("*"). //允许所有原始域
				allowedMethods(new String[] {"GET", "POST", "DELETE", "PUT"}).allowedHeaders("*"); // 允许所有方法;
			}
		};
	}

}
