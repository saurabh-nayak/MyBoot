package com.saurabh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.saurabh.filter.JwtRequestFilter;




@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("myUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		// TODO Auto-generated method stub
////		auth.inMemoryAuthentication().withUser("saurabh").password("$2a$10$2rs92qe.qf/CYcafsoIaqe0Iv3T2SrrfjJrAKf93wgg0SC.jE.nsO").roles("USER")
////		.and().withUser("user").password("$2a$10$VQ2gwwniIZKwmTg4WoECGejtojxdRlLReHk9Mj6SqHwUIbsOuIkyK").roles("USER")
////		.and().withUser("admin").password("$2a$10$QzqV4o8HAD31N3Z59OQ9P./0EX/tUciBIVG82pve1JDINV2OIw4qi").roles("ADMIN")
////		;
//		auth.userDetailsService(userDetailsService);	
//	
//	}
	
	
	
	@Bean
	public AuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(getPasswordEncoder());
		return provider;
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user").hasAnyRole("USER","ADMIN")
		.antMatchers("/").permitAll().and().formLogin()
		;
//		http.authorizeRequests()
//		.antMatchers("/admin").hasRole("ADMIN")
//		.antMatchers("/user").hasAnyRole("USER","ADMIN")
//		.antMatchers("/").permitAll();
//		
//		http.authorizeRequests().antMatchers("/login")
//		.permitAll().anyRequest().authenticated().and().
//		exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		
		http.csrf().disable()
		.authorizeRequests().antMatchers("/admin","/getUser").hasRole("ADMIN")
		.antMatchers("/user").hasAnyRole("USER","ADMIN")
		.antMatchers("/authenticate","/addUser").permitAll().
				anyRequest().authenticated().and().
				exceptionHandling().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


//		http.csrf().disable()
//		.authorizeRequests().antMatchers("/login")
//		.permitAll().anyRequest().authenticated().and()
//		.formLogin().loginPage("/login")
//		.permitAll()
//		.and().logout().invalidateHttpSession(true)
//		.clearAuthentication(true)
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.logoutSuccessUrl("/login").permitAll();
         
	}
	
	
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/add","/signup");
	}
	


}
