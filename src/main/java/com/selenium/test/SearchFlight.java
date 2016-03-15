package com.selenium.test;


import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.util.PoiXlReader;
import com.selenium.util.PoiXlWriter;


public class SearchFlight {
	private static WebDriver driver;
	private static	String URL = "https://www.orbitz.com/";
	String execute ="";
    String fromCity = "";
    String toCity = "";
    String departureDate = "";
    String arrivalDate = "";
    String tripType ="";
    String adults = "";
    String children = "";
    String ages1 = null;
    String ages2 = null;
    String[][] data =null;
    String path,resFile;
    String sheet ;
    
    
	
	@Before
	public  void setUp(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		path="pathToXl";
		resFile = "pathToResults";
		sheet = "Sheet1";
		
	}
	
	@Test
	public void search() throws InterruptedException{
			try {
				 data = PoiXlReader.read(path, sheet);
				int rows = data.length;
				
            for(int i =1; i<rows ;i++){
            	execute = data[i][1];
              tripType = data[i][2];
              fromCity = data[i][3];
              toCity = data[i][4];
              departureDate  = data[i][5];
              arrivalDate = data[i][6];
              adults = data[i][7];
              children = data[i][8];
              ages1 = data[i][9];
              ages2 = data[i][10];
             
              if(execute.equals("Y")){
            	  TestSearch(i);
                }else{
		        	System.out.println("Execute is N");
		        	data[i][14]="Skipped" ;
		         }
            }

			} catch (Exception e) {
				System.out.println("Error in search "+e);
			}
            
	}
	@After
	public  void teardown(){
		PoiXlWriter.writeXl(resFile, sheet, data);
		driver.quit();
	}
	public void TestSearch(int rowNum) {
		try{
		driver.get(URL);
		driver.findElement(By.linkText("Flights")).click();
	         
		 if(tripType.equals("Round")){
        	 driver.findElement(By.id("flight-type-roundtrip-label")).click();
        	 //departure date
             driver.findElement(By.id("flight-departing")).sendKeys(departureDate);
            //arrival date
             driver.findElement(By.id("flight-returning")).sendKeys(arrivalDate);
            
         }else if (tripType.equals("One-Way")){
        	 driver.findElement(By.id("flight-type-one-way-label")).click();
        	//departure date
             driver.findElement(By.id("flight-departing")).sendKeys(departureDate);
            
         }else if(tripType.equals("Multi")){
        	 
         }
   
    //flying from
   
    driver.findElement(By.id("flight-origin")).clear();
    driver.findElement(By.id("flight-origin")).sendKeys(fromCity);
   
    //flying to
   
    driver.findElement(By.id("flight-destination")).clear();
    driver.findElement(By.id("flight-destination")).sendKeys(toCity);
    
    
    //adults
    Select adultsNumb  = new Select(driver.findElement(By.id("flight-adults")));
    adultsNumb.selectByValue(adults);
    
    //children
    Select childrenNumb = new Select(driver.findElement(By.id("flight-children")));
    childrenNumb.selectByValue(children);
    
    //Age
   
    Select age1 = new Select(driver.findElement(By.id("flight-age-select-1")));
    age1.selectByValue(ages1);
    
    Select age2 = new Select(driver.findElement(By.id("flight-age-select-2")));
    age2.selectByValue(ages2);
   
    //Search
    driver.findElement(By.id("search-button")).click();
    
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='title-departure']"));
		data[rowNum][14] = "pass";

	}catch (InterruptedException e) {
		System.out.println("Error in TestSearch"+e);
		data[rowNum][14] = "fail" ;
	}
	catch (Exception e) {
		System.out.println("Error in TestSearch"+e);
		data[rowNum][14] = "fail" ;
	}
	}
}



