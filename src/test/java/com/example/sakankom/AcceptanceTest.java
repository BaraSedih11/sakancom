package com.example.sakankom;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "features",
        plugin = {"summary","html:target/cucumber/report.html"},
        monochrome = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        glue = "com.example.sakankom"
)

public class AcceptanceTest {
}
