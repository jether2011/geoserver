/*
 * (c) 2018 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 *
 */

/* (c) 2016 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.security.oauth2;

import org.apache.wicket.model.IModel;
import org.geoserver.security.oauth2.GeoServerOAuthAuthenticationFilter;
import org.geoserver.security.oauth2.TerraBrasilisOAuth2FilterConfig;

/**
 * Configuration panel for {@link GeoServerOAuthAuthenticationFilter}.
 *
 * @author Alessio Fabiani, GeoSolutions S.A.S.
 */
public class TerraBrasilisOAuth2AuthProviderPanel
        extends GeoServerOAuth2AuthProviderPanel<TerraBrasilisOAuth2FilterConfig> {

    public TerraBrasilisOAuth2AuthProviderPanel(
            String id, IModel<TerraBrasilisOAuth2FilterConfig> model) {
        super(id, model);
    }
}
