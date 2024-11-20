package com.kimtr.web.security_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// https://velog.io/@woosim34/Spring-Spring-Security-%EC%84%A4%EC%A0%95-%EB%B0%8F-%EA%B5%AC%ED%98%84SessionSpring-boot3.0-%EC%9D%B4%EC%83%81
	@Autowired
	private MyUserDetailsService myUserDetailsService;

    @Bean  // password빈 등록   >> MemberServiceImpl 파일에서  주입받음.
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http 
        .csrf((csrfConfig) ->   //csrf 무력화
                csrfConfig.disable()
        ) // 1번
       /* .headers((headerConfig) ->
                headerConfig.frameOptions(frameOptionsConfig ->
                        frameOptionsConfig.disable()
                )
        )// 2번*/
        .authorizeHttpRequests((authorizeRequests) ->   //url mapping로 인가
                authorizeRequests
                        .requestMatchers("/", "/login**/**","/join**","/img/**").permitAll()
                        .requestMatchers("/board/**", "/view","/mod","/del").hasRole(Role.USER.name())   // role은 database에 지정
                        .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
        )// 3번
        .exceptionHandling((exceptionConfig) ->  // 401, 403예외처리 구문
                exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
        ) // 401 403 관련 예외처리
        .formLogin((formLogin) ->   // 로그인시 파라미터 받고, 성공과 실패시 이동하는 url 환경 ㅓㄹ정
        	formLogin
                .loginPage("/login-form")  //login page url
                .usernameParameter("id")     //view에서 보낸 파라미터
                .passwordParameter("pass")   //view에서 보낸 파라미터
                .loginProcessingUrl("/login") //view에서 보낸 url, 즉 로그인 form action
                .defaultSuccessUrl("/login_success") //로그인 성공시 이동  url    >> 세션 작업을 할까? 고민중
        )
        .logout((logoutConfig) ->
        	logoutConfig.logoutSuccessUrl("/") 
        )
        .userDetailsService(myUserDetailsService);   // 인증절차 처리 
		return http.build();
	}	
	private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
         //       ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                String json = new ObjectMapper().writeValueAsString(fail);
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                PrintWriter writer = response.getWriter();
//                writer.write(json);
//                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
           //     ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
//                String json = new ObjectMapper().writeValueAsString(fail);
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                PrintWriter writer = response.getWriter();
//                writer.write(json);
//                writer.flush();
            };

  	@Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        public ErrorResponse(HttpStatus forbidden, String string) {
			// TODO Auto-generated constructor stub
        	this.status=forbidden;
        	this.message=string;
		}
		private final HttpStatus status;
        private final String message;
    }

}
