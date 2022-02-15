/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.configuration;
/**
 *
 * @author ELCOT
 */
import com.ZamZam.ZamZam.jwtFilter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@EnableWebSecurity
public class ApiConfig extends WebSecurityConfigurerAdapter{
	  @Autowired
    private JwtFilter jwtFilter;
	@Primary
	@Bean 
	public FreeMarkerConfigurationFactoryBean factoryBean() {
		FreeMarkerConfigurationFactoryBean bean=new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates");
		return bean;
	}
        @Autowired
    UserDetailsService userDetailsService;

   public AuthenticationProvider authenticationProvider()
   {
       DaoAuthenticationProvider ao=new DaoAuthenticationProvider();
       ao.setUserDetailsService(userDetailsService);
       ao.setPasswordEncoder(new BCryptPasswordEncoder());
       return ao;
   } 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
                .csrf().disable().authorizeRequests()
                .antMatchers("/filter","/buy/authenticate","/cart/authenticate","/authenticate","/login","/pgresponse","/buyCSS/*","/contact","/proview/{id}","/search","/products","/Faild","/otps","/otp","/","/createAccount","/createuser","/homeJS/*","/contactCSS/*","/createAccountJS/*","/homeCSS/*","/loginCSS/*","/loginJS/*","/uploadProducts/*","/serchProCSS/*","/serchProJS/*","/viewCSS/*","/viewJS/*").permitAll().anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
}                                                                                 
   @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

