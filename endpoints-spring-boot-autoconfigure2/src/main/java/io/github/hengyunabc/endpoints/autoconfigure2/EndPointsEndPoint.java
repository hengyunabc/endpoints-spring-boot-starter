package io.github.hengyunabc.endpoints.autoconfigure2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.AbstractWebMvcEndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.web.servlet.ControllerEndpointHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;

/**
 *
 * see org.springframework.boot.actuate.endpoint.RequestMappingEndpoint
 */
@ControllerEndpoint(id = "endpoints")
public class EndPointsEndPoint {

	@Autowired
	private ManagementApplicationcontextHolder contextHolder;

	@Autowired(required = false)
	private WebEndpointsSupplier webEndpointsSupplier;
	@Autowired(required = false)
	private ServletEndpointsSupplier servletEndpointsSupplier;
	@Autowired(required = false)
	private ControllerEndpointsSupplier controllerEndpointsSupplier;

	public EndPointsEndPoint() {
	}

	@RequestMapping
	@ResponseBody
	public List<Map<String, Object>> invoke() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<ExposableEndpoint<?>> allEndpoints = new ArrayList<ExposableEndpoint<?>>();

		if (webEndpointsSupplier != null) {
			allEndpoints.addAll(webEndpointsSupplier.getEndpoints());
		}
		if (servletEndpointsSupplier != null) {
			allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
		}
		if (controllerEndpointsSupplier != null) {
			allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
		}

		for (ExposableEndpoint<?> endpoint : allEndpoints) {
			Map<String, Object> endpointInfo = new HashMap<String, Object>();
			endpointInfo.put("id", endpoint.getId());
			endpointInfo.put("enableByDefault", endpoint.isEnableByDefault());
			result.add(endpointInfo);
		}

		return result;
	}

	@RequestMapping("mappings")
	@ResponseBody
	public Map<String, Object> mappings() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		extractMethodMappings(contextHolder.getManagementApplicationContext(), result);
		return result;
	}

	@SuppressWarnings("rawtypes")
	private void extractMethodMappings(ApplicationContext applicationContext, Map<String, Object> result) {
		if (applicationContext != null) {
			for (Entry<String, AbstractHandlerMethodMapping> bean : applicationContext
					.getBeansOfType(AbstractHandlerMethodMapping.class).entrySet()) {
				AbstractHandlerMethodMapping methodMapping = bean.getValue();
				if (methodMapping instanceof AbstractWebMvcEndpointHandlerMapping
						|| methodMapping instanceof ControllerEndpointHandlerMapping) {
					@SuppressWarnings("unchecked")
					Map<?, HandlerMethod> methods = bean.getValue().getHandlerMethods();
					for (Entry<?, HandlerMethod> method : methods.entrySet()) {
						Map<String, String> map = new LinkedHashMap<String, String>();
						map.put("bean", bean.getKey());
						map.put("method", method.getValue().toString());
						result.put(method.getKey().toString(), map);
					}
				}
			}
		}
	}

}
