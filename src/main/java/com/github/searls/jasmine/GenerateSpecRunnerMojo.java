package com.github.searls.jasmine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;

import com.github.searls.jasmine.io.scripts.TargetDirScriptResolver;
import com.github.searls.jasmine.runner.*;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.github.searls.jasmine.format.JasmineResultLogger;
import com.github.searls.jasmine.model.JasmineResult;


/**
 * @component
 * @goal generateSpecRunner
 * @phase generate-sources
 * @execute lifecycle="jasmine-lifecycle" phase="process-test-resources"
git://github.com/searls/jasmine-maven-plugin.git */
public class GenerateSpecRunnerMojo extends AbstractJasmineMojo {

	public void run() throws Exception {
		getLog().info("Generating spec runner");
		File runnerFile = writeSpecRunnerToOutputDirectory();
	}

	private File writeSpecRunnerToOutputDirectory() throws IOException {

		SpecRunnerHtmlGenerator generator = new SpecRunnerHtmlGeneratorFactory().create(ReporterType.JsApiReporter, this, new TargetDirScriptResolver(this));

		String html = generator.generate();

		getLog().debug("Writing out Spec Runner HTML " + html + " to directory " + jasmineTargetDir);
		File runnerFile = new File(jasmineTargetDir,specRunnerHtmlFileName);
		FileUtils.writeStringToFile(runnerFile, html);
		return runnerFile;
	}
}
