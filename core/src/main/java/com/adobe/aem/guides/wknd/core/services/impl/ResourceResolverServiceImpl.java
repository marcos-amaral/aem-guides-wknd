package com.adobe.aem.guides.wknd.core.services.impl;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.utils.AppConstants;
import com.adobe.aem.guides.wknd.core.services.ResourceResolverService;

import java.util.HashMap;
import java.util.Map;

@Component(service = ResourceResolverService.class)
public class ResourceResolverServiceImpl implements ResourceResolverService {

    private static final String TAG = ResourceResolverServiceImpl.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceResolverServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    private ResourceResolver resourceResolver;

    @Activate
    protected void activate() {
        try {
            Map<String, Object> serviceUserMap = new HashMap<>();
            serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, AppConstants.SUB_SERVICE);
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
            LOGGER.info("RR: {}", resourceResolver);
        } catch (LoginException e) {
            LOGGER.error("{}: Exception occurred while getting resource resolver: {}", TAG, e.getMessage());
        }
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }
}

