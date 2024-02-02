/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;

import org.apache.http.HttpHeaders;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.services.Fragments;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Optional;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class })
public class HelloWorldModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldModel.class);

    @ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "No resourceType")
    protected String resourceType;

    protected static final String AUTHOR_PARAM = "author";
    protected static final String EDITOR_PARAM = "/editor.html/";

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private Fragments fragments;

    @SlingObject
    SlingHttpServletRequest request;

    private String message;

    @PostConstruct
    protected void init() throws MalformedURLException, RepositoryException {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");

        String referrer = request.getQueryString();
        ContentFragment contentFragment = null;
        
        if (!referrer.contains(EDITOR_PARAM) && referrer.contains(AUTHOR_PARAM)) {
            String[] campaignIds = fragments.getCampaignIds(referrer);

            if (campaignIds.length > 0 && campaignIds[0] != null) {
                String[] vds = campaignIds[0].split("\\|");

                contentFragment = fragments.findContentFragment(resourceResolver, vds[0]);
                if (contentFragment != null) {
                    updateModelFromCF(contentFragment);
                }
            }
        }

        message = "Hello World!\n"
                + "Resource type is: " + resourceType + "\n"
                + "Current page is:  " + currentPagePath + "\n"
                + "author:  " + (contentFragment!=null?contentFragment.getElement("firstName").getContent()+" "+contentFragment.getElement("lastName").getContent():"") + "\n";

    }

    private void updateModelFromCF(ContentFragment contentFragment) {
        
    }

    public String getMessage() {
        return message;
    }

}