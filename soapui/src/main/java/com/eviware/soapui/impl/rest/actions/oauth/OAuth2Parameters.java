package com.eviware.soapui.impl.rest.actions.oauth;

import com.eviware.soapui.impl.rest.OAuth2Profile;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.propertyexpansion.PropertyExpander;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Wrapper class that performs property expansion on the values in an OAuth2Profile instance.
 */
public class OAuth2Parameters
{

	private final OAuth2Profile profile;

	final String authorizationUri;
	final String redirectUri;
	final String accessTokenUri;
	final String clientId;
	final String clientSecret;
	final String scope;
	final String refreshToken;
	public OAuth2Profile.AccessTokenRetrievalLocation accessTokenRetrievalLocation;

	/**
	 * Constructs an OAuth2Parameters object
	 *
	 * @param profile the profile to be wrapped
	 */
	public OAuth2Parameters( OAuth2Profile profile )
	{
		this.profile = profile;
		this.authorizationUri = expandProperty( profile, profile.getAuthorizationURI() );
		this.redirectUri = expandProperty( profile, profile.getRedirectURI() );
		this.accessTokenUri = expandProperty( profile, profile.getAccessTokenURI() );
		this.clientId = expandProperty( profile, profile.getClientID() );
		this.clientSecret = expandProperty( profile, profile.getClientSecret() );
		this.scope = expandProperty( profile, profile.getScope() );
		this.accessTokenRetrievalLocation = profile.getAccessTokenRetrievalLocation();
		this.refreshToken = expandProperty( profile, profile.getRefreshToken() );
	}


	public void startAccessTokenFlow()
	{
		profile.startAccessTokenFlow();
	}


	/**
	 * Sets the accessToken property on the wrapped OAuth2Profile instance
	 *
	 * @param accessToken the access token String
	 */
	void setAccessTokenInProfile( String accessToken )
	{
		profile.applyRetrievedAccessToken( accessToken );
	}

	public void setRefreshTokenInProfile( String refreshToken )
	{
		profile.setRefreshToken( refreshToken );
	}

	public void setAccessTokenExpirationTimeInProfile( long expirationTime )
	{
		profile.setAccessTokenExpirationTime( expirationTime );
	}

	public void setAccessTokenIssuedTimeInProfile( long issuedTime )
	{
		profile.setAccessTokenIssuedTime( issuedTime );
	}

	public void waitingForAuthorization()
	{
		profile.waitingForAuthorization();
	}

	private String expandProperty( OAuth2Profile profile, String value )
	{
		return PropertyExpander.expandProperties( profile.getContainer().getProject(), value );
	}

	public void receivedAuthorizationCode()
	{
		profile.receivedAuthorizationCode();
	}

	public void applyRetrievedAccessToken( String accessToken )
	{
		profile.applyRetrievedAccessToken( accessToken );
	}

	public OAuth2Profile.OAuth2Flow getOAuth2Flow()
	{
		return profile.getOAuth2Flow();
	}

	public List<String> getJavaScripts()
	{
		WsdlProject project = profile.getContainer().getProject();
		return Lists.transform( profile.getJavaScripts(), new PropertyExpansionFunction( project ) );
	}

}
