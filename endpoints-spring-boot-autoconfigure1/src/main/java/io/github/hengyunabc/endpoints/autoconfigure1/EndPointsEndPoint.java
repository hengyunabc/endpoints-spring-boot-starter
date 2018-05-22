package io.github.hengyunabc.endpoints.autoconfigure1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoints;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

/**
 *
 * @author duanling 2017-07-13
 * @see org.springframework.boot.actuate.endpoint.RequestMappingEndpoint
 */
@ConfigurationProperties("endpoints.endpoints")
public class EndPointsEndPoint extends AbstractMvcEndpoint {

    @Autowired
    ConfigurableApplicationContext context;

    public EndPointsEndPoint() {
        super("/endpoints", false);
    }

    @RequestMapping
    @ResponseBody
    public List<Map<String, Object>> invoke() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        MvcEndpoints mvcEndpoints = context.getBean(MvcEndpoints.class);
        if (mvcEndpoints != null) {
            for (MvcEndpoint endpoint : mvcEndpoints.getEndpoints()) {

                Map<String, Object> endpointInfo = new HashMap<String, Object>();

                endpointInfo.put("sensitive", endpoint.isSensitive());

                endpointInfo.put("path", endpoint.getPath());
                if (endpoint.getEndpointType() != null) {
                    endpointInfo.put("endpointType", endpoint.getEndpointType().getName());
                } else {
                    endpointInfo.put("endpointType", endpoint.getClass().getName());
                }
                result.add(endpointInfo);
            }
        }
        return result;
    }

    @RequestMapping("mappings")
    @ResponseBody
    public Map<String, Object> mappings() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        extractHandlerMappings(this.context, result);
        extractMethodMappings(this.context, result);
        return result;
    }

    @SuppressWarnings("rawtypes")
    private void extractMethodMappings(ApplicationContext applicationContext, Map<String, Object> result) {
        if (applicationContext != null) {
            for (Entry<String, AbstractHandlerMethodMapping> bean : applicationContext
                    .getBeansOfType(AbstractHandlerMethodMapping.class).entrySet()) {
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

    private void extractHandlerMappings(ApplicationContext applicationContext, Map<String, Object> result) {
        if (applicationContext != null) {
            Map<String, AbstractUrlHandlerMapping> mappings = applicationContext
                    .getBeansOfType(AbstractUrlHandlerMapping.class);
            for (Entry<String, AbstractUrlHandlerMapping> mapping : mappings.entrySet()) {
                Map<String, Object> handlers = getHandlerMap(mapping.getValue());
                for (Entry<String, Object> handler : handlers.entrySet()) {
                    result.put(handler.getKey(), Collections.singletonMap("bean", mapping.getKey()));
                }
            }
        }
    }

    private Map<String, Object> getHandlerMap(AbstractUrlHandlerMapping mapping) {
        if (AopUtils.isCglibProxy(mapping)) {
            // If the AbstractUrlHandlerMapping is a cglib proxy we can't call
            // the final getHandlerMap() method.
            return Collections.emptyMap();
        }
        return mapping.getHandlerMap();
    }
}
