package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.ContentFragmentFieldResolver;
import com.adobe.aem.guides.wknd.core.services.ResourceResolverService;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.VariationDef;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component(service = ContentFragmentFieldResolver.class )
public class ContentFragmentFieldResolverImpl implements ContentFragmentFieldResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentFragmentFieldResolverImpl.class);
    public static final String scene7Domain = "dam:scene7Domain";
    public static final String scene7File = "dam:scene7File";
    public static final String scene7FileStatus = "dam:scene7FileStatus";
    public static final String scene7Folder = "is/image/";

    @Reference
    ResourceResolverService resourceResolverService;

    @Override
    public String getContentOrVariation(ContentFragment cf, String variation, String fieldName, Map<String, String> decrypted, ResourceResolver resourceResolver) {
        boolean hasVariation = variation != null;
        boolean isValidVariation = false;
        Iterator<VariationDef> variations = cf.listAllVariations();
        while (variations.hasNext()) {
            if (variations.next().getName().equals(variation)) {
                isValidVariation = true;
                break;
            }
        }

        String result = null;
        if (cf.getElement(fieldName) != null) {
            if (hasVariation && isValidVariation) {
                result = cf.getElement(fieldName).getVariation(variation).getContent().trim();
            } else {
                result = this.replaceTokens(cf.getElement(fieldName).getContent().trim(), decrypted);
            }
        }

        //if (result != null && (fieldName.equals(BACKGROUND_IMAGE_REFERENCE) || fieldName.equals(IMAGE_REFERENCE) || fieldName.equals(MOBILE_IMAGE))) {
        //    return getImageWebRenditionOrS7(result);
        //}

        return result;
    }

    private String replaceTokens(String text, Map<String, String> parameters) {
        return new StringSubstitutor(parameters).replace(text);
    }

    @Override
    public String getImageWebRenditionOrS7(String image) {
        ResourceResolver resourceResolver = resourceResolverService.getResourceResolver();
        if (StringUtils.isNotEmpty(image)) {
            try {
                Resource imageResource = resourceResolver.getResource(image);
                if (imageResource != null) {
                    Asset asset = imageResource.adaptTo(Asset.class);
                    if(asset != null) {
                        String assetStatus = asset.getMetadataValue(scene7FileStatus);
                        LOGGER.info("ASSET S7 STATUS: {}", assetStatus);
                        if("PublishComplete".equals(assetStatus)) {
                            String s7Domain = asset.getMetadataValue(scene7Domain);
                            String s7FileName = asset.getMetadataValue(scene7File);
                            return s7Domain + scene7Folder + s7FileName;
                        } else {
                            if (!asset.getMimeType().equals("image/png")) {
                                List<Rendition> renditions = asset.getRenditions();
                                for (Rendition rendition : renditions) {
                                    if (rendition.getName().startsWith("cq5dam.web")) {
                                        return rendition.getPath();
                                    }
                                }
                            } else {
                                return image;
                            }
                        }
                    } else {
                        return image;
                    }
                }
            } catch (SlingException e) {
                LOGGER.error("Resource not found: {}", e.getMessage());
                return image;
            } catch (IllegalStateException e) {
                LOGGER.error("Inappropriate time to invoke method: {}", e.getMessage());
                return image;
            }
        }
        return image;
    }
}