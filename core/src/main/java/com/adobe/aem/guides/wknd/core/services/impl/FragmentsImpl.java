package com.adobe.aem.guides.wknd.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.services.Fragments;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@Component(service = { Fragments.class })
public class FragmentsImpl implements Fragments {

    private static final Logger LOGGER = LoggerFactory.getLogger(FragmentsImpl.class);

    @Override
    public String[] getCampaignIds(String referrer) throws MalformedURLException {
        Map<String, String> params = new HashMap<>();

        try {
            for (String param : referrer.split("&")) {
                String[] pair = param.split("=");
                String key = URLDecoder.decode(pair[0], "UTF-8");
                String value = URLDecoder.decode(pair[1], "UTF-8");
                params.put(key, value);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
        }

        String contentFragmentParam = params.get("author");

        return contentFragmentParam.split(",");
    }

    @Override
    public ContentFragment findContentFragment(ResourceResolver resourceResolver, String idCampaign)
            throws RepositoryException {
        Map<String, String> map = new HashMap<>();
        map.put("path", "/content/dam");
        map.put("boolproperty", "jcr:content/contentFragment");
        map.put("boolproperty.value", "true");
        map.put("1_property", "jcr:content/data/cq:model");
        map.put("1_property.1_value", "/conf/wknd-shared/settings/dam/cfm/models/article");

        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);

        Query query = queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));

        final SearchResult result = query.getResult();

        for (Hit hit : result.getHits()) {
            ContentFragment cf = hit.getResource().adaptTo(ContentFragment.class);

            ContentFragment cf2 = resourceResolver.resolve(cf.getElement("authorFragment").getContent()).adaptTo(ContentFragment.class);

            if(idCampaign.equals(cf2.getElement("firstName").getContent())) {
                return cf2;
            }
        }

        return null;
    }
}