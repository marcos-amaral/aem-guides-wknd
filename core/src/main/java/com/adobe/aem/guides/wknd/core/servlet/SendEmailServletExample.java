package com.adobe.aem.guides.wknd.core.servlet;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

import java.io.IOException;

import javax.servlet.Servlet;

@Component(service = { Servlet.class }, property = {
        "sling.servlet.paths=/bin/sendemail",
        "sling.servlet.methods=GET",
})
public class SendEmailServletExample extends SlingAllMethodsServlet {
    private static Logger log = LoggerFactory.getLogger(SendEmailServletExample.class);

    @Reference
    private MessageGatewayService messageGatewayService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        boolean sent = false;
        try {
            String[] recipients = { "mpma06@aem.com" };
            sendEmail("This is an test email",
                    "This is the email body", recipients);
            response.setStatus(200);
            sent = true;

        } catch (EmailException e) {
            response.setStatus(500);
        }
        try {
            jsonResponse.put("result", sent ? "done" : "something went wrong");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().write(jsonResponse.toString());
    }

    private void sendEmail(String subjectLine, String msgBody, String[] recipients) throws EmailException {
        Email email = new HtmlEmail();
        for (String recipient : recipients) {
            email.addTo(recipient, recipient);
        }
        email.setSubject(subjectLine);
        email.setMsg(msgBody);
        email.setFrom("mpma06@aem.com");
        MessageGateway<Email> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);
        if (messageGateway != null) {
            log.debug("sending out email");
            messageGateway.send((Email) email);
        } else {
            log.error("The message gateway could not be retrieved.");
        }
    }
}
