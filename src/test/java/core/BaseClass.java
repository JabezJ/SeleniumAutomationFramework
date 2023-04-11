package core;

import core.webdriverfactory.WebDriverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.WebDriver;


public class BaseClass {
    protected WebDriver driver;
    protected final static String base_url_app = "https://www.cazoo.co.uk/value-my-car/";

    private String getBrowserName(){
        String browserDefault = "chrome";
        String browserSentFromCmd = System.getProperty("browser");
        if (browserSentFromCmd==null){
            return browserDefault;
        }else{
            return browserSentFromCmd;
        }
    }

    @Before
    public void set_up(){
        String browser = getBrowserName();
        try{
            driver = WebDriverFactory.getWebDriverForBrowser(browser);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail("Browser Initialization failed. Check the Stack Trace. " + e.getMessage());
        }
    }

    @After
    public void clean_up(){
        driver.quit();
    }
}
