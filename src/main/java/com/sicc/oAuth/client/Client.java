package com.sicc.oAuth.client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="oauth_client_details", schema="oauth")
public class Client implements Serializable{

    @Id
    private String clientId;
    
    private String clientSecret;

    private String scope;

    private String resourceIds;

    private String authorizedGrantTypes;

    private String registeredRedirectUris;

    private String autoApproveScopes;

    private String authorities;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(String registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public String getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(String autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", scope='" + scope + '\'' +
                ", resourceIds='" + resourceIds + '\'' +
                ", authorizedGrantTypes='" + authorizedGrantTypes + '\'' +
                ", registeredRedirectUris='" + registeredRedirectUris + '\'' +
                ", autoApproveScopes='" + autoApproveScopes + '\'' +
                ", authorities='" + authorities + '\'' +
                ", accessTokenValiditySeconds=" + accessTokenValiditySeconds +
                ", refreshTokenValiditySeconds=" + refreshTokenValiditySeconds +
                '}';
    }
}

