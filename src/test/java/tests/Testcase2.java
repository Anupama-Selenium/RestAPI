package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePageEvents;
import pages.LoginPageEvents;
import utils.ElementFetch;

public class Testcase2 extends BaseTest{
	ElementFetch ele = new ElementFetch();
    HomePageEvents hmpage = new HomePageEvents();
    LoginPageEvents lginpage = new LoginPageEvents();

    @Test
    public void verifyScreenshotFunctionality()
    {
    	getLogger().info("verifyScreenshotFunctionality method started");
    	System.out.println("testing Screenshot capture");
    	Assert.fail("Intentional failure to capture screenshot");
    }
   

}
