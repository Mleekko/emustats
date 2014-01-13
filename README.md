Stats for eMunie network
==========

## Prerequisites
* Java 7 installed + JAVA_HOME set and JAVA_HOME/bin added to classpath
* Maven 3 (m2 should also work) + M2 set and present in classpath
* eMunie client running and keeping MySQL database up-to-date

## Running the project
1. In project root folder create file "local.properties" from "local.properties.template". Specify your MySQL credentials (and, if required, ip/port) there.
2. Run `mvn clean jetty:run` from command line. Go to http://localhost:8080/ after app starts.

## Running tests
1. In project root folder create file "local.properties" from "local.properties.template". Specify your MySQL credentials (and, if required, ip/port) there.
2. Run ~~mvn clean verify~~  `mvn clean test` from command line.
