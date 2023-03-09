package com.Selenium;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyCoreFunctions {
	WebDriver driver;
	private static ChromeDriverService chromeService = null;

	MyCoreFunctions(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public WebDriver getBrowserDriver(String browser) {

		switch (browser) {
		case "chrome":
			driver = getChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "safari":
			driver = new EdgeDriver();
			break;
		case "desktop":
			driver = getDesktopDriver();
			break;
		default:
			driver = getChromeDriver();
			break;
		}
		return driver;
	}

	public static ChromeDriverService createAndStartChromeService(String browser) {
		String os = System.getProperty("os.name");
		// String browser = "desktop"; //System.getProperty("browser");
		File file = new File("src/test/resources");
		File path = null;

		if (os.contains("Windows") && browser.equals("desktop")) {

			path = new File(file, "windows/92/chromedriver.exe");

		} else if (os.contains("Windows") && !browser.equals("desktop")) {

			path = new File(file, "windows/101/chromedriver.exe");
		}

		if (os.contains("Mac") && browser.equals("desktop")) {

			path = new File(file, "mac/92/chromedriver");

		} else if (os.contains("Mac") && !browser.equals("desktop")) {

			path = new File(file, "mac/101/chromedriver.exe");
		}

		if (chromeService == null) {

			chromeService = new ChromeDriverService.Builder().usingDriverExecutable(new File(path.getAbsolutePath()))
					.usingAnyFreePort().build();
		}
		return chromeService;
	}

	public WebDriver getChromeDriver() {

		ChromeOptions options = new ChromeOptions();
		System.setProperty("webdriver.chrome.driver", "src/test/resources/windows/103/chromedriver.exe");
		driver = new ChromeDriver(options);
		return driver;
	}

	public WebDriver getDesktopDriver() {
		String os = System.getProperty("os.name");
		ChromeOptions options = new ChromeOptions();
		if (os.contains("Windows")) {

			options.setBinary("C:\\Program Files (x86)\\KidCheck Client\\KidCheckClient.exe"); // set desktop
																								// application path
		}
		if (os.contains("Mac")) {

			options.setBinary("/Applications/KidCheckClient.app/Contents/MacOS/KidCheckClient"); // set desktop
																									// application path
		}

		options.addArguments("remote-debugging-port=13776");
		// driver = new RemoteWebDriver(chromeService.getUrl(), options);
		if (os.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/windows/92/chromedriver.exe");
		}
		if (os.contains("Mac")) {

			System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		}
		driver = new ChromeDriver(options);
		return driver;
	}

	public void stopChromeService() {

		chromeService.stop();
		chromeService = null;
	}

	public int getValuePositionInList(List<WebElement> element, String value) {

		// List<WebElement> element = element;
		int index = 0;
		for (int i = 0; i < element.size(); i++) {

			if (element.get(i).getText().trim().toLowerCase().contains(value.toLowerCase())) {

				index = i;
				break;
			}
		}

		return index;
	}

	public int getValuePositionInGetAttributes(List<WebElement> element, String value) {

		// List<WebElement> element = element;
		int index = 0;
		for (int i = 0; i < element.size(); i++) {
			if (element.get(i).getAttribute("value").trim().contains(value)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public boolean isValuePresentInList(List<WebElement> element, String value) {

		boolean flag = false;
		for (int i = 0; i < element.size(); i++) {
			System.out.println("actual : " + element.get(i).getText().trim().toLowerCase());
			System.out.println(value.toLowerCase());
			if (element.get(i).getText().trim().toLowerCase().contains(value.toLowerCase())) {
				System.out.println("****** in");
				flag = true;
				break;
			}
		}

		return flag;
	}

	public WebElement doclick(By locator) {
		getElement(locator).click();
		return getElement(locator);
	}

	public WebElement Enter(By locator, String value) {
		getElement(locator).sendKeys(value);
		return getElement(locator);
	}

	public void getUrl(String link) {
		driver.get(link);
	}

	public void alertAccept() {
		Alert al = driver.switchTo().alert();
		al.accept();
	}

	public void alertDismiss() {
		Alert al = driver.switchTo().alert();
		al.dismiss();
	}

	public void scroll_page(int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
	}

	public void switchToWindowNumber(int index) {
		Set<String> windows = driver.getWindowHandles();
		int totalWin = windows.size();
		System.out.println(totalWin);

		String winTitle = null;
		for (int i = 0; i < totalWin; i++) {
			if (i == index) {
				winTitle = windows.toArray()[i].toString();
			}
		}
		driver.switchTo().window(winTitle);
		System.out.println(winTitle);
	}

	public void to_ChildWindow() {
		Set<String> set = driver.getWindowHandles();
		Iterator<String> it = set.iterator();
		String parentWindowId = it.next();
		String childWindowId = it.next();
		System.out.println(set);
		System.out.println(parentWindowId + "/n" + childWindowId);
		driver.switchTo().window(childWindowId);
	}

	public void sortDropDown(By locator) {
		WebElement drpElement = getElement(locator);
		Select drpselect = new Select(drpElement);
		List<WebElement> options = drpselect.getOptions();

		ArrayList<String> originallist = new ArrayList<String>();
		ArrayList<String> templist = new ArrayList<String>();

		for (WebElement option : options) {
			originallist.add(option.getText());
			templist.add(option.getText());
		}
		System.out.println("Original list: " + originallist);
		System.out.println("Temp list: " + templist);

		Collections.sort(templist); // sorting

		System.out.println("Original list: " + originallist);
		System.out.println("Temp list: " + originallist);
		if (originallist.equals(templist)) {
			System.out.println("Dropdown sorted..");
		} else {
			System.out.println("Dropdown not sorted..");
		}
	}

	public void drop_Down_byIndex(By locator, int by_Index) {
		Select S = new Select(getElement(locator));
		S.selectByIndex(by_Index);
	}

	public void drop_Down_byVisibleText(By locator, String by_VisibleText) {
		Select S = new Select(getElement(locator));
		S.selectByVisibleText(by_VisibleText);
	}

	public void drop_Down_ByValue(By locator, String by_Value) {
		Select S = new Select(getElement(locator));
		S.selectByValue(by_Value);
	}

	public void switchFrame(String frameNameId) {
		driver.switchTo().frame(frameNameId);
		driver.switchTo().defaultContent();
	}

	public void close_browser() {
		driver.quit();
	}

	@SuppressWarnings("deprecation")
	public void wait_Implicit(int s) {
		driver.manage().timeouts().implicitlyWait(s, TimeUnit.SECONDS);
	}

	public void waitFluently() {

	}

	public void take_screenshot(String screenshotname) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File("./Screenshot/" + screenshotname + ".png"));
	}

	public void pick_Date(By locator, By locator_1, String date_Value) {
		// click on date picker so that we can get the calendar in view
		getElement(locator).click();

		// this will find all matching nodes in calendar
		List<WebElement> allDates = getElements(locator_1);

		// now we will iterate all values and will capture the text. We will select when
		// date is 28
		for (WebElement ele : allDates) {
			String date = ele.getText();
			// once date is 28 then click and break
			if (date.equalsIgnoreCase(date_Value)) {
				ele.click();
				break;
			}
		}
	}

	public void upload_File(By locator, String file_path) {
		WebElement browse = getElement(locator);
		// click on Choose file to upload the desired file
		browse.sendKeys(file_path); // Uploading the file using sendKeys
		System.out.println("File is Uploaded Successfully");
	}

//	@SuppressWarnings("deprecation")
//	public  WebElement uploadfile_AutoIt(By locator,String file_path,By uploadButton) throws IOException, InterruptedException {
//	WebElement browse =getElement(locator);   //Browse button
//	   browse.click();                                
//
//	   Runtime.getRuntime().exec(file_path);
//	  Thread.sleep(3000);
//	  getElement(uploadButton).click();           //Upload button
//	  System.out.println("File Uploaded Successfully");           // Confirmation message
//	}

	public void all_linktext(By locator) {
		String url = "";
		List<WebElement> allURLs = getElements(locator);
		System.out.println("Total links on the Wb Page: " + allURLs.size());
		// We will iterate through the list and will check the elements in the list.
		Iterator<WebElement> iterator = allURLs.iterator();
		while (iterator.hasNext()) {
			url = iterator.next().getText();
			System.out.println(url);
		}
	}

	public void wait_PresenceOfElementLocated(By locator, Duration s) {
		WebDriverWait wait = new WebDriverWait(driver, s);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void wait_ElementToBeClickable(By locator, Duration s) {
		WebDriverWait wait = new WebDriverWait(driver, s);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void wait_FrameToBeAvailableAndSwitchToIt(By locator, Duration s) {
		WebDriverWait wait = new WebDriverWait(driver, s);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public void wait_tVisibilityOfElementLocated(By locator, Duration s) {
		WebDriverWait wait = new WebDriverWait(driver, s);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void QuitDriver() {
		driver.quit();
	}

	public String randomNameSet() {
		String DummyName = "ABCDEFGHIJKLMNOPQRSTUXYZ";
		String randomName = "";
		int length = 5;

		Random R = new Random();
		char[] text = new char[length];
		for (int i = 0; i <= length; i++) {
			text[i] = DummyName.charAt(R.nextInt(DummyName.length()));
		}
		for (int i = 0; i <= text.length; i++) {
			randomName += text[i];

		}
		return randomName;

	}

	public String randomNumberSet() {
		String DummyNumber = "1234567890";
		String randomNumber = "";
		int length = 10;

		Random R = new Random();
		char[] text = new char[length];
		for (int i = 0; i <= length; i++) {
			text[i] = DummyNumber.charAt(R.nextInt(DummyNumber.length()));
		}
		for (int i = 0; i <= text.length; i++) {
			randomNumber += text[i];

		}
		return randomNumber;

	}
}
