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
A sensor can call this API endpoint with a request payload containing all weather metrics values measured by this sensor at a specific time, and the API will store these data in the WEATHER_DATA database table for this sensor. The sensor’s request payload includes sensor ID, a collection of metricsName-metricsValue pairs and the time of measurement. All three attributes in the payload are required.

API#2: http://127.0.0.1/weather/weatherdata/get  (POST)
A client can call this API endpoint to get all weather metrics data of one, more or all sensors in a specific period of time. The client’s request payload may include an array of sensor IDs, start date and end date. All three attributes in the payload are optional. This API has three functions: 1) If the request payload includes one or more sensor IDs, the API’s response will include all the weather metrics data of all the selected sensor IDs. 2) If the payload doesn’t include any sensor ID, the API’s response will include all the weather metrics data of all sensor IDs. 3) If the start date and end date are not provided in the payload, all the weather metrics data for one, more or all sensors in the last one day will be provided in the response. All the weather metrics data in the response will be ordered by sensor ID, metrics name, and created/measured date.

API#3. http://127.0.0.1/weather/metricsaverage/find (POST) 
A client can call this API endpoint to get the average value of a specific single weather metrics for one, more or all sensors in a specific period of time. The client’s request payload may include an array of sensor IDs, a metrics name, start date and end date. Only the metrics name in the payload is required. This API has three functions: 1) If the request payload includes one or more sensor IDs, the API’s response will include the average value of the selected single weather metrics for all the selected sensor IDs. 2) If the payload doesn’t include any sensor ID, the API’s response will include the average value of the selected single weather metrics for all the sensor IDs. 3) If the start date and end date are not provided in the payload, the average value of the selected single weather metrics for one, more or all sensors in the last one day will be provided in the response.

API#4.  http://127.0.0.1/weather/metricsstatistic/find  (POST)
A client can call this API endpoint to get the statistic values (min, max, sum and average) of a single weather metrics for one, more or all sensors grouped by sensor ID and metrics name in a specific period of time. The client’s request payload may include an array of sensor IDs, a metrics name, start date and end date. Only metrics name attribute in the payload is required. This API has three functions: 1) If the request payload includes one or more sensor IDs, the API’s response will include the statistic values (min, max, sum and average) of the selected single weather metrics for all the selected sensor IDs. 2) If the payload doesn’t include any sensor ID, the API’s response will include the statistic values (min, max, sum and average) of the selected single weather metrics for all sensor IDs. 3) If the start date and end date are not provided in the payload, the statistic values (min, max, sum and average) of the selected single weather metrics for one, more or all sensors in the last one day will be provided in the response.

API#5. http://127.0.0.1/weather/metricsstatistic/findall  (POST)
A client can call this API endpoint to get the statistic values (min, max, sum and average) of all weather metrics for all sensors grouped by sensor ID and metrics name in a specific period of time. The client’s request payload may include start date and end date. Both attributes in the payload are optional. If the start date and end date are not provided in the payload, the statistic values (min, max, sum and average) of all the weather metrics for all sensors in the last one day will be provided in the response.

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

> CREATE SEQUENCE WEATHER_DOMAIN.SEQ_WEATHER_DATA_ID
> START WITH     1000
> INCREMENT BY   1
> NOCACHE
> NOCYCLE;

2. Create a new table WEATHER_METRICS using the following DDL. This table defines weather metrics names such as temperature, humidity, wind speed, etc.

> CREATE TABLE WEATHER_DOMAIN.WEATHER_METRICS (	
>       METRIC_NAME VARCHAR2(20) NOT NULL, 
>       DESCRIPTION VARCHAR2(100) NULL,
>       CREATED_DATE DATE NULL,
>       CONSTRAINT WEATHER_METRICS_PK PRIMARY KEY (METRICS_NAME)
> )

3. Create a new table WEATHER_DATA using the following DDL. This table contains all whether metrics values such as temperature, humidity, wind speed, etc. collected from all sensors at different times.

> CREATE TABLE PAYWALL_TEST.WEATHER_DATA (   
>      DATA_ID NUMBER NOT NULL,
>      SENSOR_ID VARCHAR2(20) NOT NULL,
>      METRIC_NAME VARCHAR2(20) NOT NULL, 
>      METRIC_VALUE NUMBER NOT NULL,
>      CREATED_DATE DATE NOT NULL, 
>      CONSTRAINT WEATHER_DATA_PK PRIMARY KEY (DATA_ID), 
>      CONSTRAINT WEATHER_DATA_FK FOREIGN KEY (METRIC_NAME)
>      REFERENCES WEATHER_METRICS(METRIC_NAME)
> )

4. We may need to create a SENSORS table defining all sensors. Due to the time limit, I will not create this table at this time.



