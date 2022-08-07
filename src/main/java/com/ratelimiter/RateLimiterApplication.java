package com.ratelimiter;

import com.ratelimiter.util.rule.RuleProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:rules.properties"})
@EnableConfigurationProperties (value = {RuleProperties.class})
public class RateLimiterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(RateLimiterApplication.class, args);

		printSuccess();

		loadProperties(applicationContext);
	}

	private static void loadProperties(ConfigurableApplicationContext applicationContext) {
		RuleProperties ruleProperties = applicationContext.getBean(RuleProperties.class);
		System.out.println(ruleProperties);
	}

	private static void printSuccess() {
		System.out.println("\n==================================================\n||" +
				"\t\tratelimiter application started :)\t\t" +
				"||\n==================================================\n");
	}
}
