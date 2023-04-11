package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class AboutYourCarPageObjects {

    private static final Logger logger = LogManager.getLogger(AboutYourCarPageObjects.class);

    private WebDriver driver;

    private By text_car_reg  = By.xpath("//p[contains(text(),'Reg' )]");

    private By text_car_make_model  = By.xpath("//p[contains(text(),'Reg' )]/following-sibling::p");

    public AboutYourCarPageObjects(WebDriver driver){
        this.driver = driver;
    }

    public String retrieveCarRegistrationNumber(String regNum){
        if (existsElement(text_car_reg)) {
            String regInfo = driver.findElement(text_car_reg).getText();
            logger.info("Car Registration Number could be retrieved: " + regInfo);
            return regInfo;
        }else {
            logger.error(regNum + " - Car Registration details could not be retrieved");
            logger.error("Renew the MOT and then come back to complete your valuation");
            return "NotDisplayed";
        }
    }

    public String retrieveCarMakeAndModel(){
        if (existsElement(text_car_make_model)) {
            String makeInfo = driver.findElement(text_car_make_model).getText();
            logger.info("Car Make and Model could retrieved: " + makeInfo);
            return makeInfo;
        }
        else {
            logger.error("Car Make and Model could not be retrieved");
            Assert.fail("Car Make and Model could not be retrieved");
            return "NotDisplayed";
        }
    }

    public void validateCarRegistrationNumber(String regNum){
            if (retrieveCarRegistrationNumber(regNum).contains(regNum)) {
                Assert.assertTrue(true);
                logger.info(regNum + " - Car Registration Number could be correctly displayed");
            } else {
                logger.error(regNum + " - Car Registration details could not be retrieved");
                logger.error("Renew the MOT and then come back to complete your valuation");
                Assert.fail(regNum + " - Car Registration details could not be retrieved. Renew the MOT and then come back to complete your valuation");
            }
        }

    public void validateCarMakeAndModel(String make,String model){
        if (retrieveCarMakeAndModel().contains(make)){
            Assert.assertTrue(true);
            logger.info(make +" - Car Make is correctly displayed");
            if (retrieveCarMakeAndModel().contains(model)){
                Assert.assertTrue(true);
                logger.info(model +" - Car Model is correctly displayed");
            }else{
                logger.error(model +" - Car Model is not displayed");
                Assert.fail("Car Model is not correct");
            }
        }else{
            logger.error(make +model +" - Car Make and Model is not correctly displayed");
            Assert.fail("Car Make is not correctly displayed");
        }
    }

    private boolean existsElement(By by) {
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

}

