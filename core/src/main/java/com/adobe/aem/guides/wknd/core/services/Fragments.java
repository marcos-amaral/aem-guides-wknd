package com.adobe.aem.guides.wknd.core.services;

import com.adobe.cq.dam.cfm.ContentFragment;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;
import java.net.MalformedURLException;

public interface Fragments {

    public String[] getCampaignIds(String referrer) throws MalformedURLException;
    public ContentFragment findContentFragment(ResourceResolver resourceResolver, String idCampaign) throws RepositoryException;
}
