package com.sicc.oAuth.config;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.sicc.oAuth.client.ClientService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("phstauth.jks"), "0811".toCharArray())
                .getKeyPair("hstauth");
        converter.setKeyPair(keyPair);
        logger.debug(keyPair.toString());
        return converter;
    }
    
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(getDataSourceHibernateEntityManager());
    }
    
    public DataSource getDataSourceHibernateEntityManager() {
        EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
        return info.getDataSource();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(authenticationManager);
                  //  .exceptionTranslator(new LoginFailHandle());
        /*
                .pathMapping("/oauth/token", "/token")
                .pathMapping("/oauth/authorize", "/authorize")
                .pathMapping("/oauth/token_key", "/token_key")
                .pathMapping("/oauth/check_token", "/check_token");*/

    }
    
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return (accessToken, authentication) -> {
        	if (authentication.isAuthenticated()) {
	        	Map<String, Object> additionalInfo = new HashMap<>();
	            String clientId = authentication.getOAuth2Request().getClientId();
	            logger.debug("client ID : " + clientId);
	            
                additionalInfo.put("company", "phs");
                additionalInfo.put("serial", "B9027652");
                
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
	            
        	}
            return accessToken;
        };

    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer configurer) throws Exception {
        configurer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
