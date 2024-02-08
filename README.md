# Sending form to SMTP server
Example of a page with a form that sends a message to a pre-configured SMTP server

## 📋 References

### 📦 [Form Container Component](https://experienceleague.adobe.com/docs/experience-manager-core-components/using/wcm-components/forms/form-container.html?lang=en)
  
![image](https://github.com/marcos-amaral/aem-guides-wknd/assets/79855686/ca47f31a-da84-4c27-80b7-7f50fcfeb56d)

### ⚙️ [Email Service](https://experienceleague.adobe.com/docs/experience-manager-learn/cloud-service/networking/examples/email-service.html?lang=en)

### OSGi config
```
\ui.config\src\main\content\jcr_root\apps\wknd\osgiconfig\config\com.day.cq.mailer.DefaultMailService.cfg.json
{
    "smtp.host": "smtp.freesmtpservers.com",
    "smtp.user": "",
    "smtp.password": "",
    "smtp.port": 25,
    "from.address": "mpma06@aem.com",
    "smtp.ssl": false,
    "smtp.starttls": false,
    "smtp.requiretls": false,
    "debug.email": false,
    "oauth.flow": false
}
```

### Configmgr  
http://localhost:4502/system/console/configMgr  
*Day CQ Mail Service*  
![image](https://github.com/marcos-amaral/aem-guides-wknd/assets/79855686/74b045b2-07b0-4a7a-a6a1-ea71ab98f762)
