package com.mendix.recipe.fetch.api;

import com.mendix.recipe.fetch.api.helper.XmlHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeFetchApiApplication {

	public static void main(String[] args) {

		XmlHelper.instance = new XmlHelper();
		XmlHelper.instance.parseXml();

		SpringApplication.run(RecipeFetchApiApplication.class, args);
	}
}
