package com.Appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

public class MyAppiumFunctions {
	private AppiumDriver driver;
//	EnvironmentHelperUtil environmentHelper = new EnvironmentHelperUtil();
//	Map<String, String> credentials = environmentHelper.getEnvironments();
	

	public AppiumDriver getAppDriver(String mobile) throws Exception {

		switch (mobile) {
		case "android":
			driver = getAndroidDriver();
			break;
		case "ios":
			driver = getIOSDriver();
			break;
		default:
			driver = getAndroidDriver();
			break;
		}
		return driver;
	}

	// This is to run tests on Android mobile
	public AppiumDriver getAndroidDriver() throws MalformedURLException {
		File file = new File("src/test/resources/apk");
		File fs = new File(file, "KidCheck.apk");
		DesiredCapabilities dc = new DesiredCapabilities();
		/*
		 * capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		 * capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "OnePlus 6"); //
		 * Android Device capabilities.setCapability(MobileCapabilityType.APP,
		 * fs.getAbsolutePath());
		 * capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
		 * "UiAutomator2");
		 */
		dc.setCapability("appium:platformName", "Android");
		//dc.setCapability("appium:deviceName", "OPPO F21 Pro 5G"); // Android Device
		//dc.setCapability("appium:deviceName", "realme C12");
		dc.setCapability("appium:app", fs.getAbsolutePath());
		dc.setCapability("appium:platformVersion", "13");
		//dc.setCapability("appium:platformVersion", "11");
		dc.setCapability("appium:automationName", "UiAutomator2");
		dc.setCapability("autoGrantPermissions", "true");
		// dc.setCapability("autoAcceptAlerts", "true");
		//dc.setCapability("appium:udid", "f08086da");
		dc.setCapability("appium:udid", "emulator-5554");
		dc.setCapability("appPackage", "com.kidcheck.mobile");
		dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.kidcheck.kidcheck.Activities.MainActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), dc);

//		try {
//			driver = new AndroidDriver(new URL("http://0.0.0.0:5073/wd/hub"), dc);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			e.getMessage();
//		}
		return driver;
	}

	public AppiumDriver getIOSDriver() {
		// File file = new File("src/test/resources/apk");
		// File fs = new File(file, "KidCheck_stage.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ios");
		// capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "whatever"); //
		// Android Device
		capabilities.setCapability("appium:platformName", "ios");
		capabilities.setCapability("appium:deviceName", "iPhone 13 mini");
		capabilities.setCapability("appium:bundleId", "com.kidcheck.KidCheck");
		capabilities.setCapability("appium:automationName", "XCUITest");
		capabilities.setCapability("autoAcceptAlerts", true);
		// capabilities.setCapability("appium:app",
		// "/Users/cennest-pc/Downloads/KidCheck.ipa");
		// capabilities.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		try {
			driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

	public WebElement getElement(RemoteWebDriver driver, By by) {
		return driver.findElement(by);
	}

	public List<WebElement> getElements(WebDriver driver, By by) {
		return driver.findElements(by);
	}


	public int getValuePositionInList(List<WebElement> element, String value) {
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
			if (element.get(i).getText().trim().toLowerCase().contains(value.toLowerCase())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}
