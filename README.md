# Setup ContextHub for Personalization 
https://experienceleague.adobe.com/docs/experience-manager-learn/sites/personalization/context-hub-technical-video-setup.html?lang=en

Steps:
1. Adding ContextHub to Page Component
    - Include the contexthub component in the \<head\> section of your web page.  
      **/apps/wknd/components/page/customheaderlibs.html**
      ```
      <!--/* Include Context Hub */-->
      <sly data-sly-resource="${'contexthub' @ resourceType='granite/contexthub/components/contexthub'}"/>
      ```
2. Site Configuration and ContextHub Segments  
    Go to
    - Tools > General > Configuration Browser > check WKND Site > Properites > check ContextHub segments
    - Tools > General > Configuration Browser > check WKND Site > Properites > check Cloud Configurations
      ![image](https://github.com/marcos-amaral/aem-guides-wknd/assets/79855686/a6692044-f2cf-4bf2-b82b-3b13bf7e675b)
3. Segment Creation
    Go to
    - Personalization > Audiences > WKND Site
    ![image](https://github.com/marcos-amaral/aem-guides-wknd/assets/79855686/ae0c325f-676b-45d6-9e20-b014a66e54f5)
4. Cloud conf, Segment Path and ContextHub path
    

# Creating Custom Store Candidates 
https://experienceleague.adobe.com/docs/experience-manager-cloud-service/content/implementing/personalization/extending-contexthub.html?lang=en#creating-custom-store-candidates
