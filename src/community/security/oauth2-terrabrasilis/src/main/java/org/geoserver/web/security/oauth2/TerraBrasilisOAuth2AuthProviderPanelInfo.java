/* (c) 2016 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.security.oauth2;

import org.geoserver.security.oauth2.GeoServerOAuthAuthenticationFilter;
import org.geoserver.security.oauth2.TerraBrasilisOAuth2FilterConfig;
import org.geoserver.security.oauth2.TerraBrasilisOAuthAuthenticationFilter;
import org.geoserver.security.web.auth.AuthenticationFilterPanelInfo;

/**
 * Configuration panel extension for {@link GeoServerOAuthAuthenticationFilter}.
 *
 * @author Alessio Fabiani, GeoSolutions S.A.S.
 */
public class TerraBrasilisOAuth2AuthProviderPanelInfo
        extends AuthenticationFilterPanelInfo<
                TerraBrasilisOAuth2FilterConfig, TerraBrasilisOAuth2AuthProviderPanel> {

    /** serialVersionUID */
    private static final long serialVersionUID = 4587996462800153425L;

    public TerraBrasilisOAuth2AuthProviderPanelInfo() {
        setComponentClass(TerraBrasilisOAuth2AuthProviderPanel.class);
        setServiceClass(TerraBrasilisOAuthAuthenticationFilter.class);
        setServiceConfigClass(TerraBrasilisOAuth2FilterConfig.class);
    }
}
