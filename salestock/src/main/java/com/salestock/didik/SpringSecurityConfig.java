package com.salestock.didik;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${upload.folder}")
    private String IMAGE_LOCATION;
    @Value("${image.url}")
    private String IMAGE_PATH;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
		http.csrf().disable().authorizeRequests().antMatchers("/v1/admin/**").hasRole("ADMIN").and().httpBasic();
		http.csrf().disable().authorizeRequests().antMatchers("/v1/**").authenticated().and().httpBasic();
		
		http.authorizeRequests().antMatchers("/v2/api-docs").permitAll();
		http.authorizeRequests().antMatchers("/configuration/ui").permitAll();
		http.authorizeRequests().antMatchers("/swagger-resources/**").permitAll();
		http.authorizeRequests().antMatchers("/configuration/**").permitAll();
		http.authorizeRequests().antMatchers("/swagger-ui.html").permitAll();
		http.authorizeRequests().antMatchers("/webjars/**").permitAll();
		http.authorizeRequests().antMatchers("/images/**").permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("admin@gmail.com").password("admin123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user@gmail.com").password("user123").roles("USER");
		auth.inMemoryAuthentication().withUser("user1@gmail.com").password("user123").roles("USER");
		auth.inMemoryAuthentication().withUser("user2@gmail.com").password("user123").roles("USER");
		auth.inMemoryAuthentication().withUser("user3@gmail.com").password("user123").roles("USER");
	}
	
	@Bean
    WebMvcConfigurer configurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler(IMAGE_PATH + "**").
                        addResourceLocations("file:" + IMAGE_LOCATION);
            }
        };
    }
	
	public AccessDeniedHandler getAccessDeniedHandler(){
		AccessDeniedHandler adh = new AccessDeniedHandler() {
			
			@Override
			public void handle(
					HttpServletRequest request,
					HttpServletResponse response,
					org.springframework.security.access.AccessDeniedException accessDeniedException)
					throws IOException, ServletException {
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print("{" +
						"    \"message\": \"Akses Ditolak\"," +
						"    \"status\": \"failed\"" +
						"}");
				out.flush();
			}
		};
		return adh;
	}
}
