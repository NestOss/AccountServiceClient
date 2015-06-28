#Client for Account service https://github.com/NestOss/AccountService.

A RMI client for account service.

  In endless loop invokes getAmount and addAmount service methods this random id and value arguments. Range of random value argument is [-15000, 15000]. Each service method  call executes in separate thread.

Command line arguments:  <--rCount rCount> <--wCount wCount> <--idList minId maxId>

  rCount - number of threads, which invoke getAmount service method. Valid value from 1 to 10000;

  wCount - number of threads, which invoke addAmount service method. Valid value from 1 to 10000;

  minId and maxId - left and right range boundary. Inclusive defines range of random id. Nonnegative integers. The arguments may be passed in the order minId,maxId or maxId,minId. 

Client run:

  Ensure RMI connection settings in src/main/resources/rmi.xml. Change current directory to AccountServiceClient. Use Maven to compile, test and run project.  Parameters --rCount, --wCount, --idList defined in pom.xml in build section.
  
To test project type: mvn test

To run project type: mvn exec:java
