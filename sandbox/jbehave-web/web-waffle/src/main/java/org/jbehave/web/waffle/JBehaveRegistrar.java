package org.jbehave.web.waffle;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.codehaus.waffle.io.RequestFileUploader;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.codehaus.waffle.registrar.AbstractRegistrar;
import org.codehaus.waffle.registrar.Registrar;
import org.codehaus.waffle.view.ViewResolver;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.web.io.FileManager;
import org.jbehave.web.io.FileUnzipper;
import org.jbehave.web.waffle.controllers.ScenarioController;
import org.jbehave.web.waffle.controllers.UploadController;

public class JBehaveRegistrar extends AbstractRegistrar {

	public JBehaveRegistrar(Registrar delegate) {
		super(delegate);
	}

	@Override
	public void application() {
		registerMenu();
		registerConfiguration();
		registerScenarioParser();
		registerScenarioRunner();
		registerSteps();
		register("scenario/scenario", ScenarioController.class);
		configureViews();
	}

	@Override
	public void request() {
		register("fileItemFactory", DiskFileItemFactory.class);
		register("uploader", RequestFileUploader.class);
		register("unzipper", FileUnzipper.class);
		register("manager", FileManager.class);
		register("data/upload", UploadController.class);
	}

	protected void registerMenu() {
		register("home", MenuAwareController.class);
		registerInstance(createMenu());
	}

	protected Menu createMenu() {
		Map<String, List<String>> content = new HashMap<String, List<String>>();
		content.put("Home", asList("Home:home"));
		content.put("Data", asList("Upload:data/upload"));
		content.put("Scenario", asList("Run Scenario:scenario/scenario"));
		return new Menu(content);
	}

	protected void configureViews() {
		ViewResolver viewResolver = getComponentRegistry().locateByKey(ViewResolver.class);
		viewResolver.configureView("home", "ftl/home");
		viewResolver.configureView("data/upload", "ftl/data/upload");
		viewResolver.configureView("scenario/scenario", "ftl/scenario/scenario");
	}

	protected void registerConfiguration() {
		register(MostUsefulConfiguration.class);
	}

	protected void registerScenarioParser() {
		register(PatternScenarioParser.class);
	}

	protected void registerScenarioRunner() {
		register(ScenarioRunner.class);
	}

	protected void registerSteps() {
		register(Steps.class);
	}

}