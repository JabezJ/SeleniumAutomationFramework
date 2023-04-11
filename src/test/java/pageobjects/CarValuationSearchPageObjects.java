package pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarValuationSearchPageObjects {

    private static final Logger logger = LogManager.getLogger(CarValuationSearchPageObjects.class);

    private WebDriver driver;

    private By button_cookie_consent  = By.xpath("//button/span[text()='Accept All']");

    private By input_text_car_reg  = By.id("vrm");

    private By button_start_valuation  = By.xpath("//button[@type='submit']/span[text()='Start valuation']");

    public CarValuationSearchPageObjects(WebDriver driver){
        this.driver = driver;
    }

    public void acceptCookie(){
        driver.findElement(button_cookie_consent).click();
        logger.info("Car Valuation page cookie accepted...");
    }

    public void enterCarRegistrationNumber(String regNum){
        driver.findElement(input_text_car_reg).clear();
        driver.findElement(input_text_car_reg).sendKeys(regNum);
        logger.info("Car Registration Number entered in reg search: " + regNum);
    }

    public void startCarValuation(){
        driver.findElement(button_start_valuation).click();
        logger.info("Car Valuation started...");
    }

}
