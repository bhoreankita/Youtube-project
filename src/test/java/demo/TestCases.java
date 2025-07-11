package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

       @Test
       public void testCase01() throws InterruptedException
       {
         System.out.println("Test case 1 started"); //start test case 
         Wrappers.goToUrl(driver); // Go to URL
         String currentUrl = driver.getCurrentUrl();  //actual url
         String expectedUrl = "https://www.youtube.com/"; // expected url
         SoftAssert sa = new SoftAssert();
         sa.assertEquals(currentUrl , expectedUrl , "You are on incorrect url");
         sa.assertAll();
         Thread.sleep(3000);
         Wrappers.findElementAndClick(driver, By.xpath("//a[text()='About']")); //find About element and click on it
         WebElement aboutText = driver.findElement(By.xpath("//section[contains(@class,'ytabout__content')]")); //get about section text
         System.out.println(aboutText.getText()); //print it
         System.out.println("Test case 1 ended"); //start test case

       }

       
       @Test
       public void testCase02() throws InterruptedException
       {
             System.out.println("Test case 2 started"); //start test case
            
             Wrappers.goToUrl(driver); // Go to URL
             Wrappers.findElementAndClick(driver, By.xpath("//a[@title='Movies']")); //click on Movie
             Thread.sleep(4000);
             By rightArrow = By.xpath("//span[contains(text(),'Top selling')]/ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"); //right arrow locator
             Wrappers.clickTillUnclikable(driver, rightArrow); //click on right arrow until it is displayed
             Thread.sleep(4000);
             By movieList =By.xpath("//span[contains(text(),'Top selling')]/ancestor::div[@id='dismissible']//ytd-grid-movie-renderer"); //list of movie
             List<String> movieCategoryDetails = Wrappers.getMovie(driver, movieList); //store into list of string
             SoftAssert sa = new SoftAssert();

             String movieCategory = movieCategoryDetails.get(0);
             sa.assertTrue(movieCategory.equals("Comedy") || movieCategory.equals("Animation") || movieCategory.equals("Drama") || movieCategory.equals("Indian cinema")); 
             String movieBadge = movieCategoryDetails.get(1);   
             sa.assertTrue(movieBadge.equals("A") || movieCategory.equals("U/A 13+") || movieCategory.equals("U/A"));
             sa.assertAll();

             System.out.println("Test case 2 ended"); //end test case

        }


        @Test
        public void testCase03() throws InterruptedException
        {
            System.out.println("Test case 3 started"); //start test case
            Wrappers.goToUrl(driver); // Go to URL
            Wrappers.findElementAndClick(driver, By.xpath("//a[@title='Music']")); // click on music title
            Thread.sleep(4000);
            By locator_musicDetail = By.xpath("//span[contains(text(),\"India's Biggest Hits\")]/ancestor::div[@id='dismissible']"); // go to section India's Biggest Hits
            int songCount = Wrappers.getMusic(driver, locator_musicDetail);
            SoftAssert sa = new SoftAssert();
            sa.assertTrue(songCount<=50);
            System.out.println("Song count of last album : "+songCount);
            System.out.println("Test case 3 ended"); //end test case

        }


        @Test
        public void testCase04() throws InterruptedException
        {
             System.out.println("Test case 4 started");
             Wrappers.goToUrl(driver); // Go to URL
             Wrappers.findElementAndClick(driver, By.xpath("//a[@title='News']")); //click on News
             Thread.sleep(3000);
        
             //locate latest news posts section
              WebElement newsSection = driver.findElement(By.xpath("//span[contains(text(),\"Latest news posts\")]/ancestor::div[@id='dismissible']")); //latest news section
              List<WebElement> newsList = newsSection.findElements(By.xpath(".//ytd-post-renderer")); //list of news in latest news posts section       
             long sumOfVotes = 0;
             
             for(int i=0; i<3; i++)   //get first 3 news contents
             {
             String title = newsList.get(i).findElement(By.xpath(".//a[@id='author-text']")).getText(); //get title text
             System.out.println("Title of news : " +title); //print it
             String body = newsList.get(i).findElement(By.xpath(".//yt-formatted-string[@id ='home-content-text']")).getText(); //get body text
             System.out.println("Body of news : " +body); //print it
             WebElement votes= newsList.get(i).findElement(By.xpath(".//div[@id='toolbar']//span[@id='vote-count-middle']")); //locate votes of first three news
            try{
             String voteCount = votes.getText(); //get vote text
             sumOfVotes = sumOfVotes + Wrappers.convertToNumericValue(voteCount);  //get total vote count
             
             }
            catch (Exception e) 
             {
                   System.out.println(" Vote not present : " +e.getMessage()); //if no votes present
             }
             

             }
              System.out.println("Total number of votes : " +sumOfVotes);
               System.out.println("Test case 4 ended"); //end test case
  }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}