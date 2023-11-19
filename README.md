# WEATHER DATA SERVICE

Weather Data Service is a RESTful web service providing five REST APIs that can receive different weather metrics data such as temperature, humidity, wind speed, etc. from various sensors, store them in the database, and allow clients querying weather metrics data from one, more or all sensors. All unit test cases have been created for all methods in all APIs, data access objects (DAO) and service classes. All the unit test cases have been run successfully. Since I don’t have enough time to install Oracle database in my MacBook, I will not create an Oracle database & required tables and deployed my service's build to an application server to do real APIs running test using Google Postman at this time. 

# Technologies used to develop, build and run the Weather Data Service:

- Programming Language: Java
- Framework used to develop RESTful web service: JBoss/Red Hat RESTEasy
- Database: Oracle database
- Persistence Framework: EclipseLink JPA
- JSON data-binding technology: FasterXML/Jackson-databind
- Testing Framework: JUnit/Mockito
- Application server: JBoss/Red Hat WildFly

# Description of Five REST APIs:

API#1: http://127.0.0.1/weather/weatherdata/add (POST)
A sensor can call this API endpoint with a request payload containing all kinds of weather metrics values measured by this sensor at a specific time, and the API will store these data in the WEATHER_DATA database table for this sensor. The sensor’s request payload includes sensor ID, a collection of metricsName-metricsValue pairs and the time of measurement. All three attributes in the payload are required.

API#2: http://127.0.0.1/weather/weatherdata/get  (POST)
A client can call this API endpoint to get all kinds of weather metrics data from one, more or all sensors in a specific period of time. The client’s request payload may include an array of sensor IDs, start date and end date. All three attributes in the request payload are optional. This API has three functions: 1) If the request payload includes one or more sensor IDs, the API’s response will include all kinds of weather metrics data from all the selected sensors. 2) If the request payload doesn’t include any sensor ID, the API’s response will include all kinds of weather metrics data from all sensors. 3) If either start date or end date (or both) are not provided in the request payload, the latest weather metrics data of all kinds from one, more or all sensors will be provided in the response. If both start date and end date are provided in the request payload, all kinds of weather metrics data in the response will be ordered by sensor ID, metrics name, and measured date/time.

API#3. http://127.0.0.1/weather/metricsaverage/find (POST) 
A client can call this API endpoint to get the average values of one, more or all kinds of weather metrics for one, more or all sensors in a specific period of time. The client’s request payload may include an array of sensor IDs, an array of metrics names, start date and end date. All the four attributes in the request payload are optional. This API has four functions: 1) If the request payload includes one or more sensor IDs and one or more metrics names, the API’s response will include the average values of all the selected weather metrics for all the selected sensors. 2) If the request payload doesn’t include any sensor ID but includes one or more metrics names, the API’s response will include the average values of all the selected weather metrics for all sensors. 3) If the request payload doesn’t include both sensor ID and metrics name, the API’s response will include the average values of all the weather metrics for all sensors. 4) If either start date or end date (or both) are not provided in the request payload, the latest values of all the selected (or all) weather metrics for all the selected (or all) sensors will be provided in the response depending on whether an array of sensor IDs or an array of metrics names or both are provided in the request payload. 

API#4.  http://127.0.0.1/weather/metricsstatistic/find  (POST)
A client can call this API endpoint to get the statistic values (min, max, sum and average) of a single weather metrics for one, more or all sensors grouped by sensor ID and metrics name in a specific period of time. The client’s request payload may include an array of sensor IDs, a metrics name, start date and end date. Only metrics name attribute in the request payload is required. This API has three functions: 1) If the request payload includes one or more sensor IDs, the API’s response will include all the statistic values (min, max, sum and average) of the selected single weather metrics for all the selected sensors. 2) If the payload doesn’t include any sensor ID, the API’s response will include all the statistic values (min, max, sum and average) of the selected single weather metrics for all sensors. 3) If either start date or end date or both are not provided in the payload, the statistic values (min, max, sum and average) of the selected single weather metrics for one, more or all sensors in the last one hour will be provided in the response.

API#5. http://127.0.0.1/weather/metricsstatistic/findall  (POST)
A client can call this API endpoint to get all the statistic values (min, max, sum and average) of all weather metrics for all sensors grouped by sensor ID and metrics name in a specific period of time. The client’s request payload may include start date and end date. Both attributes in the payload are optional. If either start date or end date are not provided in the request payload, the statistic values (min, max, sum and average) of all the weather metrics for all sensors in the last one hour will be provided in the response.

# Steps to run APIs:

Step#1: Install Oracle Database and create sequence SEQ_WEATHER_DATA_ID and two database tables: WEATHER_METRICS and WEATHER_DATA. Create public synonym, etc.

Step#2: Install WildFly or other application servers and add Oracle JDBC driver to the application server. Create a data source “jdbc/weather” from the application server’s admin console.

Step#3: Check out the source codes of RESTful web service “weather-data-service” from https://github.com/weatherDataService/weather-data-service

Step#4: Build the source code of “weather-data-service” to generate weather-data-service-1.0-SNAPSHOT.war file using maven command: mvn clean install 

Step#5: Deploy weather-data-service-1.0-SNAPSHOT.war to WildFly or other application servers.

Step#6: Create request payloads in JSON format for different APIs. Copy/paste each payload message to Postman’s Body area against corresponding API endpoint link and click the “Send” button to run the APIs.

Step#7: Check the response in JSON format from Postman. 

# Database Design:

Create the following entities in Oracle database:

1. Create a new sequence SEQ_WEATHER_DATA_ID using the following DDL. This sequence helps to generate “DATA_ID” column value in WEATHER_DATA table (Primary key). 
```
CREATE SEQUENCE WEATHER_DOMAIN.SEQ_WEATHER_DATA_ID
START WITH     1000
INCREMENT BY   1
NOCACHE
NOCYCLE;
```
2. Create a new table WEATHER_METRICS using the following DDL. This table defines weather metrics names such as temperature, humidity, wind speed, etc.
```
CREATE TABLE WEATHER_DOMAIN.WEATHER_METRICS (	
      METRICS_NAME VARCHAR2(20) NOT NULL, 
      DESCRIPTION VARCHAR2(100) NULL,
      CREATED_DATE DATE NULL,
      CONSTRAINT WEATHER_METRICS_PK PRIMARY KEY (METRICS_NAME)
)
```
3. Create a new table WEATHER_SENSOR using the following DDL. This table defines weather sensor ID and location.
```
CREATE TABLE WEATHER_DOMAIN.WEATHER_SENSOR (	
      SENSOR_ID VARCHAR2(20) NOT NULL, 
      LOCATION VARCHAR2(100) NULL,
      CREATED_DATE DATE NULL,
      CONSTRAINT WEATHER_SENSOR_PK PRIMARY KEY (SENSOR_ID)
)
```
4. Create a new table WEATHER_DATA using the following DDL. This table contains all whether metrics values such as temperature, humidity, wind speed, etc. collected from all sensors at different times.
```
CREATE TABLE PAYWALL_TEST.WEATHER_DATA (   
     DATA_ID NUMBER NOT NULL,
     SENSOR_ID VARCHAR2(20) NOT NULL,
     METRICS_NAME VARCHAR2(20) NOT NULL, 
     METRICS_VALUE NUMBER NOT NULL,
     CREATED_DATE DATE, 
     CONSTRAINT WEATHER_DATA_PK PRIMARY KEY (DATA_ID), 
     CONSTRAINT WEATHER_DATA_FK_A FOREIGN KEY (METRIC_NAME)
     REFERENCES WEATHER_METRICS(METRIC_NAME),
     CONSTRAINT WEATHER_DATA_FK_B FOREIGN KEY (SENSOR_ID)
     REFERENCES WEATHER_SENSOR(SENSOR_ID)
)
```

