/* (c) 2016 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.security.oauth2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.geoserver.security.config.SecurityNamedServiceConfig;
import org.geoserver.security.impl.GeoServerRole;
import org.geoserver.security.impl.GeoServerUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/** @author Alessio Fabiani, GeoSolutions S.A.S. */
public class TerraBrasilisOAuthAuthenticationFilter extends GeoServerOAuthAuthenticationFilter {

    public TerraBrasilisOAuthAuthenticationFilter(
            SecurityNamedServiceConfig config,
            RemoteTokenServices tokenServices,
            GeoServerOAuth2SecurityConfiguration oauth2SecurityConfiguration,
            OAuth2RestOperations oauth2RestTemplate) {
        super(config, tokenServices, oauth2SecurityConfiguration, oauth2RestTemplate);
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response) {

        String principal = null;
        try {
            principal = getPreAuthenticatedPrincipal(request, response);
        } catch (IOException e1) {
            LOGGER.log(Level.FINE, e1.getMessage(), e1);
            principal = null;
        } catch (ServletException e1) {
            LOGGER.log(Level.FINE, e1.getMessage(), e1);
            principal = null;
        }

        LOGGER.log(
                Level.FINE,
                "preAuthenticatedPrincipal = " + principal + ", trying to authenticate");

        PreAuthenticatedAuthenticationToken result = null;

        if (principal == null || principal.trim().length() == 0) {
            result =
                    new PreAuthenticatedAuthenticationToken(
                            principal, null, Collections.singleton(GeoServerRole.ANONYMOUS_ROLE));
        } else {
            if (GeoServerUser.ROOT_USERNAME.equals(principal)) {
                result =
                        new PreAuthenticatedAuthenticationToken(
                                principal,
                                null,
                                Arrays.asList(
                                        GeoServerRole.ADMIN_ROLE,
                                        GeoServerRole.GROUP_ADMIN_ROLE,
                                        GeoServerRole.AUTHENTICATED_ROLE));
            } else {
                Collection<GeoServerRole> roles = null;

                roles = new ArrayList<GeoServerRole>();

                roles.add(GeoServerRole.AUTHENTICATED_ROLE);

                result = new PreAuthenticatedAuthenticationToken(principal, null, roles);
            }
        }

        result.setDetails(getAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(result);
    }
}
