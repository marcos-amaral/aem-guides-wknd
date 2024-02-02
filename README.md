## Create a system user

1. http://localhost:4502/crx/explorer/index.jsp  
2. Click User Administration  
3. Click Create system user  
4. Fill userID  
5. Click ok (green v) > close  

## Give all rights needed

1. http://localhost:4502/useradmin
2. Search your <systemUser> > select it
3. Click permissions tab
4. Check needed permissions
5. Click save

## Map system user against your packege

1. http://localhost:4502/system/console/configMgr
2. Search Apache Sling Service User Mapper Service Amendment and click to edit
3. Fill Service Mappings: <bundleid>:<subServiceName>=<systemUser> (ex: aem-guides-wknd.core:wkndReader=systemUser)
4. Click save
