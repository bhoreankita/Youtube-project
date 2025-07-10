package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Wrappers 
{
    public static void goToUrl(WebDriver driver)
    {
        driver.get("https://YouTube.com"); // YouTube
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3)); // Timeout set to 3 second
         wait.until(webDriver->((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
         
    }


    public static void clickTillUnclikable(WebDriver driver, By locator) //for right arrow
    {
         try{
            
            WebElement element = driver.findElement(locator);
            while(element.isDisplayed())   // while loop until element is displayed
            {
                element.click();
            }
         }catch(Exception e)
         {
            System.out.println("Exception occur : " +e.getMessage());
         }
    }

    public static List<String> getMovie(WebDriver driver, By locator)
    {
        
        List<WebElement> movieList = driver.findElements(locator); // Get movie list
        System.out.println("Number of movies : " +movieList.size());
        String movieCategory= movieList.get(movieList.size()-1).findElement(By.xpath(".//span[contains(@class,'grid-movie-renderer-metadata')]")).getText(); //get movie category
        System.out.println(movieCategory);
        movieCategory= movieCategory.split(" . ")[0]; //remove .(dot)
        System.out.println(movieCategory);
        String movieBadge = movieList.get(movieList.size()-1).findElement(By.xpath(".//ytd-badge-supported-renderer[contains(@class,'badges')]/div[2]/p")).getText(); //get badge
        System.out.println(movieBadge);
        return Arrays.asList(movieCategory,movieBadge); //return movie category and movie badge to function
        
    
    }


    public static int getMusic(WebDriver driver, By locator)
    {
        
         WebElement songsParent = driver.findElement(locator);
         List<WebElement> musicList = songsParent.findElements(By.xpath(".//div[@id='content']")); //locate all song list 12
         String playListTitle= musicList.get(3).findElement(By.xpath(".//a[contains(@class,'wiz__title')]/span")).getText(); //locate 4th music and get the title
         System.out.println("Playlist name : " +playListTitle); //title of 4th music album in India's Biggest Hits
         String mostRightSongCount = musicList.get(3).findElement(By.xpath(".//div[@class='badge-shape-wiz__text']")).getText();
         return Integer.parseInt(mostRightSongCount.split(" ")[0]); //50 from "50 songs"
         
    }

    public static void findElementAndClick(WebDriver driver, By locator) throws InterruptedException
    {
        try{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); //wait for the element to be clickable
        wait.until(ExpectedConditions.visibilityOf(element)); // wait until element is visible after scrolling
        element.click(); //click on element
        Thread.sleep(8000);
        }
        catch(Exception e)
        {
            System.out.println("Exception occur : " +e.getMessage());
        }
    }

   

    public static void findElementAndPrintWe(WebDriver driver, WebElement we, By locator)
    {
        WebElement element = we.findElement(locator);
        // scroll to the element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);

    }

   
    public static long convertToNumericValue(String value) 
    {
        

        // trim the value to remove any leading or trailing spaces
        value = value.trim().toUpperCase();
        
        //check if the last character is non-numeric and determine the multiplier
        char lastChar = value.charAt(value.length() - 1); //"100K" 4-1 =3 100 4th position character-1st position character
        int multiplier = 1;
        switch(lastChar)
        {
            case 'K' :
            multiplier = 1000;
            break;

            case 'M' :
            multiplier = 1000000;
            break;

            case 'B' :
            multiplier = 1000000000;
            break;
        
            default:
            //if the last character is numeric , parse the entire string
            if(Character.isDigit(lastChar))
            {
                return Long.parseLong(value);
            }
            throw new IllegalArgumentException("Invalid format: " +value);
        }
        
        //Extract the numeric part before the last character
        String numericPart = value.substring(0, value.length() - 1); //100K (0, 3) -----> 100
        double number = Double.parseDouble(numericPart); //1.2K, 3.5M
    
        //Calculate the final value
        return (long) (number * multiplier);
       
    }


}






