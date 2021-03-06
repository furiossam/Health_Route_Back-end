package br.com.univali.healthroutes.security.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.univali.healthroutes.security.filter.JWTAuthenticationFilter;
import br.com.univali.healthroutes.security.filter.JWTLoginFilter;
import br.com.univali.healthroutes.security.handler.HealthRoutesAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfig {

	@Override
	protected String usersByUsernameQuery() {
		return "select username, password, enable from login where username=?";
	}

	@Override
	protected String authoritiesByUsernameQuery() {
		return "select username, null as authority from login where username=?";
	}

	@Override
	protected void httpRoutesConfig(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.cors().and().authorizeRequests()
		
				// Authentication
		
				.antMatchers(HttpMethod.POST, "/userSubmit").permitAll()
				
				.anyRequest().authenticated().and().httpBasic().disable()

				// Filter login requests
				.addFilterBefore(
						new JWTLoginFilter("/login", authenticationManager(), new HealthRoutesAuthenticationFailureHandler()),
						UsernamePasswordAuthenticationFilter.class)
				// Filter others requests to check JWT in header
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", 
				"/configuration/security", "/configuration/ui","/webjars/**", "/csrf");
	}
}
