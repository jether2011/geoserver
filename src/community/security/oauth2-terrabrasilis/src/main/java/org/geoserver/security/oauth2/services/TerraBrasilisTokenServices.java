/* (c) 2016 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.security.oauth2.services;

import java.util.Map;
import org.geoserver.security.oauth2.GeoServerAccessTokenConverter;
import org.geoserver.security.oauth2.GeoServerOAuthRemoteTokenServices;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Remote Token Services for GeoNode token details.
 *
 * @author Alessio Fabiani, GeoSolutions S.A.S.
 */
public class TerraBrasilisTokenServices extends GeoServerOAuthRemoteTokenServices {

    public TerraBrasilisTokenServices() {
        super(new GeoServerAccessTokenConverter());
    }

    protected Map<String, Object> checkToken(String accessToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("token", accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizationHeader(accessToken));
        return postForMap(checkTokenEndpointUrl, formData, headers);
    }

    protected void transformNonStandardValuesToStandardValues(Map<String, Object> map) {
        LOGGER.debug("Original map = " + map);
        map.put("client_id", map.get("id")); // TerraBrasilis sends 'client_id' as 'is'
        LOGGER.debug("Transformed = " + map);
    }

    protected String getAuthorizationHeader(String accessToken) {

        return "Bearer " + accessToken;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {

        Map<String, Object> checkTokenResponse = checkToken(accessToken);

        verifyTokenResponse(accessToken, checkTokenResponse);

        transformNonStandardValuesToStandardValues(checkTokenResponse);

        Assert.state(
                checkTokenResponse.containsKey("token"),
                "Client id must be present in response from auth server");
        OAuth2Authentication oauthAuthentication =
                tokenConverter.extractAuthentication(checkTokenResponse);

        //        oauthAuthentication.getAuthorities().add(GeoServerRole.AUTHENTICATED_ROLE);

        return oauthAuthentication;
    }

    @Override
    protected void verifyTokenResponse(String accessToken, Map<String, Object> checkTokenResponse) {
        if (checkTokenResponse.containsKey("error")) {
            logger.debug("check_token returned error: " + checkTokenResponse.get("error"));
            throw new InvalidTokenException(accessToken);
        }
    }
}
