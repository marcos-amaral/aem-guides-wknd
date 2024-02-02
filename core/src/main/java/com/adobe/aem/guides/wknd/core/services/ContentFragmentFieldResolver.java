package com.adobe.aem.guides.wknd.core.services;

import com.adobe.cq.dam.cfm.ContentFragment;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.Map;

public interface ContentFragmentFieldResolver {
    String getContentOrVariation(ContentFragment cf, String variation, String fieldName, Map<String, String> decrypted, ResourceResolver resourceResolver);
    String getImageWebRenditionOrS7(String image);
}