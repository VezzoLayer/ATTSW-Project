package com.book.bookmanager;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/bdd/resources", glue = "com.book.bookmanager.steps", monochrome = true)
public class BookWebAppBDD {

	@BeforeClass
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	}
}