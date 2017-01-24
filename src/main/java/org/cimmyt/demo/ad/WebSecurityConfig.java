package org.cimmyt.demo.ad;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

/**
 * Defines security settings for the whole application 
 * @author cimmyt-siu
 * @since 1.0.0
 *
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableAuthorizationServer
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean()
	LDAPResources config() {
		return new LDAPResources();
	}	

	@Bean
	public OauthClientResources googleResources() {
		return new OauthClientResources();
	}

	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	
	/**
	 * Defines which URLs are going to be secured.
	 * URLs with public access:
	 * <ul>
	 * <li>static resources</li>
	 * <li>login and logout pages</li>
	 * <li>home page</li>
	 * </ul> 
	 * <p>Everything else requires authentication</p>
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home","/css/**","/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
             .and()
				.addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Configures the authentication mechanism (LDAP-based in this case) 
     * @param amb for fluent configuration of authentication mechanism
     * @throws Exception if configuration fails to get a connection to LDAP server
     */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder amb) throws Exception {
		
		amb.ldapAuthentication()
		.contextSource()
			.ldif(config().getUrl())
		.and()
		.userDnPatterns(config().getSearchFilter())
		.userSearchBase(config().getSearchBase())
		;
	}

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Override
		/**
		 * Configures endpoints requiring exclusively OAuth authentication, for example: Admin pages or APIs
		 */
		public void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api")
				.authorizeRequests()
			.anyRequest()
				.authenticated();
		}
	}


	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	/**
	 * Exposes a composite filter to allow for multiple SSO OAuth filters to be registered as one,
	 * For example: Facbook, Github, etc.
	 * @return a SSO Filter with configurations of one or more Authentication Servers 
	 */
	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(googleResources(), "/login/google"));
		filter.setFilters(filters);
		return filter;
	}
	
	private Filter ssoFilter(OauthClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = 
				new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate oAuth2RestTemplate = 
				new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		UserInfoTokenServices tokenServices = 
			new UserInfoTokenServices(
				client.getResource().getUserInfoUri(),
				client.getClient().getClientId());
		
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		return oAuth2ClientAuthenticationFilter;
	}


}

