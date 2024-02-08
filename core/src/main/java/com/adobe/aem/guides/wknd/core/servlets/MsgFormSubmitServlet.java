package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "wknd/components/page", methods = { HttpConstants.METHOD_GET,
                HttpConstants.METHOD_POST }, selectors = { "wknd", "test" }, extensions = "txt")
@ServiceDescription("Simple Servlet using OSGi DS 1.4")
public class MsgFormSubmitServlet extends SlingAllMethodsServlet {

        Logger LOGGER = LoggerFactory.getLogger(MsgFormSubmitServlet.class);

        @Override
        protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
                        throws ServletException, IOException {

                final Resource resource = request.getResource();
                response.setContentType("text/plain");
                response.getWriter().write("Page Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
        }

        @Override
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
                        throws ServletException, IOException {

                try {
                        LOGGER.info("\n --------------- STARTED POST ---------------");

                        List<RequestParameter> requestParameters = request.getRequestParameterList();

                        for (RequestParameter requestParameter : requestParameters) {
                                LOGGER.info("\n ==PARAMETERS====> {} : {}", requestParameter.getName(),
                                                requestParameter.getString());
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

                response.getWriter().write("====== FORM SUBMITTED ======");
        }

}
