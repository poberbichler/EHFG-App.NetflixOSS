package org.ehfg.app.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.ehfg.app.zuul.CustomZuulProperties.CustomZuulRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkState;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author patrick
 * @since 12.2016
 */
@Component
@EnableConfigurationProperties(CustomZuulProperties.class)
public class ServicePathFilter extends ZuulFilter {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, CustomZuulRoute> routesWithServicePath;

	@Autowired
	public ServicePathFilter(CustomZuulProperties customZuulProperties) {
		this.routesWithServicePath = customZuulProperties.getRoutes().entrySet().stream()
				.filter(entry -> Objects.nonNull(entry.getValue().getServicePath()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		// this filter should always be called after Spring's PreDecorationFilter
		return PreDecorationFilter.FILTER_ORDER + 10;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return findRoute(ctx) != null;
	}

	private CustomZuulRoute findRoute(RequestContext ctx) {
		// proxy is equal to the route's id, these properties should already be defined by the PreDecorationFilter
		CustomZuulRoute route = routesWithServicePath.get(ctx.get("proxy"));
		if (route == null) {
			return null;
		}

		checkState(route.getServiceId().equals(ctx.get("serviceId")), "serviceIds do not match");
		return route;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		CustomZuulRoute route = findRoute(ctx);

		String requestURI = (String) ctx.get("requestURI");
		String redirectURI = route.getServicePath() + requestURI;

		logger.debug("redirecting requested URI [{}] to [{}]", requestURI, redirectURI);
		ctx.set("requestURI", redirectURI);

		return null;
	}
}
