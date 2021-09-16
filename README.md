
### Part 1: Verify that your test fail

In TDD you first implement the test then you write the code to achieve the test expected results.This time you will
start by runing existing test and then write the code to pass the test.

1. Download and sync the project in the repo.
2. Open the class *MongoWeatherServiceTest* and run the tests.

![](img/1.JPG)

3. Read the results and check the code to find what is wrong.

### Part 2: Write to the code to pass the test

1. Implement the code so your tests pass.

##### Report

![](img/14.JPG)

##### findById

![](img/15.JPG)

2. Run your test again and verify that they all pass.

![](img/6.JPG)

### Part 3: Implementing  Unit Tests for MongoWeatherService

1. Write 1 unit test for each method of the *WeatherService* to verify the correct implementation on the *
   MongoWeatherService*.

##### Test ReportNearLocation

![](img/10.JPG)

##### Metodo ReportNearLocation

![](img/9.JPG)

##### Test findWeaterReportByName

![](img/7.JPG)

##### Metodo findWeaterReportByName

![](img/8.JPG)

##### Correctos

![](img/11.JPG)


### Part 4: Testing your Rest Controller

1. Open the *HealthControllerTest* class and run the tests.

![](img/12.JPG)

2. Check the code and understand how to use the Spring Boot testing annotation.
3. Create a new class to test the *WeatherReportController* called *WeatherReportControllerTest*.
4. Copy the configuration annotations you will need from the *HealthControllerTest* class.

![](img/13.JPG)

5. Implement 1 unit test for each method inside the *WeatherReportController*.

##### createTest

![](img/17.JPG)

##### findByIdTest

![](img/18.JPG)

##### findByReportsIdTest

![](img/19.JPG)

##### findByReportsNameTest

![](img/20.JPG)

##### Correctos

![](img/16.JPG)
