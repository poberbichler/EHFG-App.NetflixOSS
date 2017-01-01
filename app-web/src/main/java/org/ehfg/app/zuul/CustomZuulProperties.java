package org.ehfg.app.zuul;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.Map;

/**
 * @author patrick
 * @since 12.2016
 */
@ConfigurationProperties("zuul")
public class CustomZuulProperties {

	private Map<String, CustomZuulRoute> routes;

	public Map<String, CustomZuulRoute> getRoutes() {
		return routes;
	}

	public void setRoutes(Map<String, CustomZuulRoute> routes) {
		this.routes = routes;
	}

	public static class CustomZuulRoute extends ZuulProperties.ZuulRoute {
		private String servicePath;

		public String getServicePath() {
			return servicePath;
		}

		public void setServicePath(String servicePath) {
			this.servicePath = servicePath;
		}
	}
}
