Junit Based Selenium Automation Framework and TestSuite


To execute testsuite : 
mvn clean test -Dbrowser=chrome -DinputFile="car_input_v2.txt" -DoutputFile="car_output_v2.txt"

mvn clean test -Dbrowser=headless

mvn clean test -Dbrowser=firefox

* Please ensure correct driver version of browser is stored in \src\test\resources\drivers
* Browser configuration is passed via command line argument browser
* Test suite should be capable of execution in multiple browsers
* Java version level 11 or higher is needed to run the testsuite
* Test Data files can be passed via command line arguments browser inputFile and outputFile
* Please ensure input and output testdata files are stored in \src\test\resources\testData

To generate surefire Junit reports (stored in \junit-selenium-automation\target\sitesurefire-report.html)
mvn clean test surefire-report:report
mvn site

