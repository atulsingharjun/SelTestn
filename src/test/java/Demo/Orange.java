package Demo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.Assert;

// junit.framework.Assert;

import org.testng.annotations.*;

public class Orange {


	public String baseUrl ="https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;

	@BeforeTest
	public void setup() {
		System.out.println("before execution");

		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

	}
	@Test(priority =1,enabled=false)
	public void loginWithInavlidCredential() throws InterruptedException {

		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admins");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).submit();

		String expectedmsg ="Invalid credentials";
		String actual = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText(); 
		//Assert.assertTrue();
		Assert.assertEquals(expectedmsg, actual);
		Thread.sleep(5000);
	}

	@Test(priority=2,enabled=false)
	public void loginTestWithValid() {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).submit();

		//verify that logi is suceefull 
		String tit = driver.getTitle();
		//		if(tit.equals("OrangeHRM")) {
		//			System.out.println("login sucess");
		//		}else {
		//			System.out.println("login failed");
		//		}
		logout();
		Assert.assertEquals("OrangeHRM", tit);

	}
	@Test(priority=3 , enabled=false)
	public void addEmployee() throws InterruptedException {
		login();
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Arjun");
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("singh");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();


		String confirm = driver.findElement(By.xpath("//h6[text()='Personal Details']")).getText();
		if (confirm.contains("Personal Details"))
		{
			System.out.println("sucessfully added ");
		}
		else {
			System.out.println("failed");
		}



	}
	@Test(priority=4,enabled=false)
	public void searchEmployee() throws InterruptedException {
		login();

		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Employee List')]")).click()	;
		driver.findElement(By.xpath("//input[@placeholder='Type for hints...']")).sendKeys("Adam");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		//verify record
		Thread.sleep(5000);
		List<WebElement> element=	driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));

		String expected_message = "Record Found";
		String message_actual = element.get(0).getText();
		System.out.println(message_actual);



		
		Assert.assertEquals(message_actual,expected_message);
		logout();

	}
	@Test(priority=5,enabled=false)
	public void searchbyEid() {
		
		String empId = "01715";
		String message_actual ="";
		login();

		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Employee List')]")).click()	;
		driver.findElements(By.tagName("input")).get(2).sendKeys(empId);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		List<WebElement> rows = driver.findElements(By.xpath("(//div[@role='row'])"));
		if(rows.size()>1)
		{
			message_actual = driver.findElement(By.xpath("((//div[@role='row'])[2]/div[@role='cell'])[2]")).getText();

		}

		logout();
		Assert.assertEquals(empId, message_actual);
		
		
		
	}
	
	@Test(priority=6)
	public void deleteEmployee() throws InterruptedException
	{
		login();

		//find PIM Menu and click on PIM Menu
		driver.findElement(By.xpath("//span[text()='PIM']")).click();

		//Select Employee List
		driver.findElement(By.xpath("//a[text()='Employee List']")).click();

		//enter employee name
		driver.findElements(By.tagName("input")).get(1).sendKeys("Charles");

		//driver.findElement(By.tagName("input")).get(1).sendKeys("Nesta");


		//Click the search button.
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();


		Thread.sleep(3000);
		

		//click on delete icon of the record
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();


		//click on yes, delete messaage button
		driver.findElement(By.xpath("//button[@class='oxd-button oxd-button--medium oxd-button--label-danger orangehrm-button-margin']")).click();

		//check for message "No Record Found"
		String msg = driver.findElement(By.xpath("(//span[@class='oxd-text oxd-text--span'])[1]")).getText();

		Assert.assertEquals(msg, "No Records Found");

		Thread.sleep(5000);
		logout();

	}


	

	public void login() {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).submit();
	}

	public void logout() {

		driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
		//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
		List <WebElement> elementlist = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		elementlist.get(3).click();
		
		//click on logout


	}



	@AfterTest
	public void down() throws InterruptedException{

		Thread.sleep(5000);
		driver.close();
		driver.quit();


	}

}
