Spring Batch MongoDB Loader
===========================

This is basically a pet project to learn [Spring Batch](http://static.springsource.org/spring-batch/) and [MongoDB](http://www.mongodb.org/). :-)
The aim is to have a batch loader that you can configure to suck large amounts of data from an existing relational database and load that data into MongoDB for further processing or visualizing (with [Cube](http://square.github.com/cube/) for instance).

Prerequisites
-------------

You will need the following in order to build and use the project:

* Apache Maven 2.2.1
* JDK 1.6.x

Test case
---------

A system test that uses a memory-based source database ([H2](http://www.h2database.com/)) with country sample data (taken from [MONDIAL](http://www.dbis.informatik.uni-goettingen.de/Mondial/)) is at `src/test/java/casadelhuerto/mongodb/loader/CountryBatchLoadingTests.java`.
Once you have MongoDB up and running execute the test for smoke testing.
Please note that at the end of the test we explicitly delete the inserted data! Comment out that line to be able to see the inserted
data in the MongoDB console.

How to use it for your own purposes?
------------------------------------

The supported scenario is that you have a *source* database from which you want to extract data via an SQL query and insert it into a target MongoDB database. Follow these steps to adapt the existing configuration:

1. Make sure you add the Maven dependency for the JDBC driver of your source database to the `pom.xml` file.
2. Adapt the database connection configuration in file `src/main/java/resources/source-db-config.xml`.
3. Adapt the MongoDB connection configuration in file `src/main/java/resources/mongodb-config.xml`.
4. In file `src/main/java/resources/batch-job-processors-config.xml`:
   1. Adapt the SQL query that will be use against the source database to extract the data (in the Spring bean `databaseRowsReader`)
   2. Adapt the MongoDB collection in which you would like to store the documents  (in the Spring bean `mongoDbWriter`)
5. Execute the class `casadelhuerto.mongodb.loader.SpringBatchLauncher` to start the process.


Logging
-------

Check the loggin settings in `src/main/java/resources/log4j.xml` and tune them appropriately to your case.

TODOs
-----

* Currently Spring Batch is configured to use a memory-based HSQL database which will be whipped out after execution!
* Currently transaction management is only done on the database where Spring Batch persists its state.
* Add Maven configuration to generate a self-executable JAR that can be exectued from the command-line.
  * Check also http://static.springsource.org/spring-batch/reference/html-single/index.html#runningJobsFromCommandLine