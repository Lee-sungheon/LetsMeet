package com.anjanda.letsmeet.config;
/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현, 김동빈, 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 모든 접근에 대한 보안 관련 클래스
 * @Description
 *	- 일단, 설명이 제대로 된 것이 없기도하고, 모든 접근에 관해 설정을 다 해주어야하는 작업이므로, 추후 작업
 *	- 후순위 작업임 (할수도, 안할수도)
 */

public class SecurityConfig{
	
}
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/guest/**").permitAll()
//                .antMatchers("/manager/**").hasRole("MANAGER")
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/swagger-resources/**").permitAll() //swagger
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers("/webjars/**").permitAll();
//
//        http.formLogin();
//        http.exceptionHandling().accessDeniedPage("/accessDenied");
//        http.logout().logoutUrl("/logout").invalidateHttpSession(true);
//
//    }
//    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                .withUser("manager")
//                .password("{noop}1111")
//                .roles("MANAGER");
//    }
//}
