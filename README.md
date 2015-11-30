# api-backend

The backend is the application core which is responsible for managing data storage, it relies on the following technologies
•	IBM Bluemix cloud service for hosting the application, this is different from what we had specified on our speciation document.  We had planned to use Tomcat for our hosting but we chose to use bluemix because makes it easy to run and test your application without initial set up and maintenance.
•	IBM DB2 for relational data storage on the cloud, which provides the ease of accessing the data from anywhere.
•	Java Persistence which provides persistence model for object-relational mapping making it easy to preform crud operations on the data with ease
•	Java API for REST Web Services (JAX-RS, defined in JSR 311).

For details
Go to http://cs6310-api-v1.mybluemix.net/
 OR
https://github.com/6310-Chronicles/api-backend/blob/master/documentation/API-CS6310-BACKEND.docx


Installation Process
1. Open an Account http://www.ibm.com/cloud-computing/bluemix/
2. The Cloud Foundry V6 command-line interface for your system https://github.com/cloudfoundry/cli/blob/master/INSTALL.md?cm_mc_uid=20516774442114469310833&cm_mc_sid_50200000=1448887008
3. Maven for compiling and do mvn package in upper directory
4. Then follow https://www.ng.bluemix.net/docs/starters/install_cli.html to push your app to the cloud
