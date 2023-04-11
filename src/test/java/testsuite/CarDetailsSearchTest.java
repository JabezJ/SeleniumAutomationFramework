package testsuite;

import core.BaseClass;
import core.webdriverfactory.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(Parameterized.class)
public class CarDetailsSearchTest extends BaseClass {

    private String regNum;
    private Map<String, String> expectedCarDetails;

    public CarDetailsSearchTest(String regNum){
        this.regNum = regNum;
    }

    @Before
    public void read_expected_car_details() {
        //Read given output file: to retrieve the expected car make and model
        String outputFile;
        outputFile = System.getProperty("outputFile");
        if (outputFile==null){
            outputFile = "car_output_v2.txt";
        }
        expectedCarDetails = new HashMap<>();
        try {
            expectedCarDetails = readOutputFile(outputFile,regNum.replaceAll("\\s", ""));
            System.out.println("Expected Car details for "+ regNum + "---"+expectedCarDetails);
            if (expectedCarDetails.size()==0){
                System.out.println("Expected Car details not available for "+ regNum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Parameterized.Parameters
    public static Collection carRegistrationNumbers() throws IOException {
        String inputFile;
        inputFile = System.getProperty("inputFile");
        if (inputFile==null){
            inputFile = "car_input_v2.txt";
        }
        //Read given input file: to retrieve the car reg list
        List<String> inputVehicleRegList = readInputFile(inputFile);
        return inputVehicleRegList;
    }

    @Test
    public void validate_value_your_car_search() throws IOException {

        final Logger logger = LogManager.getLogger(CarDetailsSearchTest.class);
        // Page Object Model
        logger.info("Test case started: check_value_your_car_on_cazoo.co.uk");

        // Open the application and check car valuation page title
        driver.get(base_url_app);
        String expected = "Value your car with our free car valuation calculator | Cazoo";
        String actual = driver.getTitle();
        Assert.assertEquals("Application Page Title validation", expected, actual);

        //Accept cookie and Search for each car reg
        CarValuationSearchPageObjects carValuationSearchPageObjects = new CarValuationSearchPageObjects(driver);
        carValuationSearchPageObjects.acceptCookie();

       // inputVehicleRegList.forEach((regNum) -> {
        System.out.println("Input vehicle registration number" + " - " + regNum);
        carValuationSearchPageObjects.enterCarRegistrationNumber(regNum.replaceAll("\\s", ""));
        carValuationSearchPageObjects.startCarValuation();

        //Wait for 'about your car page' to be displayed
        WebDriverWait webDriverWait = new WebDriverWait(driver,10);
        webDriverWait.until(ExpectedConditions.titleIs("About your car"));

        //Verify car reg,make & model
        AboutYourCarPageObjects AboutYourCarPageObjects = new AboutYourCarPageObjects(driver);
        AboutYourCarPageObjects.validateCarRegistrationNumber(regNum.replaceAll("\\s", ""));
        AboutYourCarPageObjects.validateCarMakeAndModel(expectedCarDetails.get("make"),expectedCarDetails.get("model"));

        driver.navigate().back();

        logger.info("Test case Ended: check_value_your_car_on_cazoo.co.uk");
    }

    private static List<String> readInputFile(String fileName) throws IOException {
        String vehicle_reg_pattern = "\\w{2}\\d{2}\\s?\\w{3}";
        List<String> extractedVehicleRegList;
        Path filePath = Path.of(System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\"+fileName);
        String input = Files.readString(filePath);
        extractedVehicleRegList = extractSubstring(input, vehicle_reg_pattern);
        if (!extractedVehicleRegList.isEmpty()) {
            System.out.println("Extracted vehicle reg: " + extractedVehicleRegList);
        } else {
            System.out.println("No matching vehicle reg found.");
        }
        return extractedVehicleRegList;
    }

   private static List<String> extractSubstring(String input, String pattern) {
        List<String> vehicleRegList=new ArrayList<String>();
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(input);

        while(matcher.find()){
            System.out.println(matcher.group(0));
            vehicleRegList.add(matcher.group(0));
        }
       return vehicleRegList;
    }

    private static Map<String, String> readOutputFile(String fileName,String regNum) throws IOException {
        Map<String, String> expectedOutput = new HashMap<>();
        String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\"+fileName;
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        reader.readLine();
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",");
            if (parts[0].equals(regNum))
            {
                expectedOutput.put("make", parts[1]);
                expectedOutput.put("model", parts[2]);
            }
        }
        reader.close();
        return expectedOutput;
    }

}

