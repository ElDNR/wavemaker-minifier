package com.coders.generic.wavemaker_minifier.implementations;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.coders.generic.wavemaker_minifier.interfaces.IMinifier;
import com.coders.generic.wavemaker_minifier.interfaces.IPagesConfiguration;

/**
 * 
 * @author Diego Nunez
 *
 */
public class SimpleMinifier implements IMinifier {

	public void minify(String inputPath) {
		File pagesDirectory = new File(inputPath);

		if (pagesDirectory.exists() && pagesDirectory.isDirectory()) {
			FilenameFilter filenameFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().equals("pages-config.json");
				}
			};

			File[] pagesConfigurationFiles = pagesDirectory.listFiles(filenameFilter);

			if (pagesConfigurationFiles.length == 1) {
				String template = this.getResourceFile("template.html");

				IPagesConfiguration pagesConfiguration = new SimplePagesConfiguration();

				for (File pageDirectory : pagesDirectory.listFiles()) {
					if (pageDirectory.isDirectory() && pagesConfiguration.isPage(pageDirectory.getName())) {
						System.out.println("Processing: " + pageDirectory.getName());

						String baseName = FilenameUtils.getBaseName(pageDirectory.getName());
						String minifiedPage = template.replace("${page}", baseName);
						boolean foundCss = false, foundHtml = false, foundJs = false, foundVariables = false;

						String cssSectionSource = baseName + ".css", htmlSectionSource = baseName + ".html",
								jsSectionSource = baseName + ".js",
								variablesSectionSource = baseName + ".variables.json";

						for (File pageComponent : pageDirectory.listFiles()) {
							if (pageComponent.isFile()) {
								if (pageComponent.getName().equals(cssSectionSource)) {
									System.out.println("- Processing CSS...");

									minifiedPage = this.processTemplate(minifiedPage, "${css}", pageComponent);
									foundCss = true;
								} else if (pageComponent.getName().equals(htmlSectionSource)) {
									System.out.println("- Processing HTML...");

									minifiedPage = this.processTemplate(minifiedPage, "${html}", pageComponent);
									foundHtml = true;
								} else if (pageComponent.getName().equals(jsSectionSource)) {
									System.out.println("- Processing JavaScript...");

									minifiedPage = this.processTemplate(minifiedPage, "${js}", pageComponent);
									foundJs = true;
								} else if (pageComponent.getName().equals(variablesSectionSource)) {
									System.out.println("- Processing Variables...");

									minifiedPage = this.processTemplate(minifiedPage, "${variables}", pageComponent);
									foundVariables = true;
								}
							}
						}

						if (foundCss && foundHtml && foundJs && foundVariables) {
							this.writeHtmlFile(pageDirectory, minifiedPage);
						}
					}
				}
			} else {
				System.out.println("Pages configuration files is missing!");
			}
		} else {
			System.out.println("Please specify a valid directory!");
		}
	}

	private String getResourceFile(String fileName) {
		String result;

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try {
			result = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			result = e.getMessage();
		}

		return result;
	}

	private String processTemplate(String template, String placeholder, File sourceFile) {
		String data;
		try {
			data = FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8"));
		} catch (IOException e) {
			data = e.getMessage();
		}

		return template.replace(placeholder, data);
	}

	private void writeHtmlFile(File directory, String data) {
		if (directory.exists() && directory.isDirectory()) {
			File minifiedFile = new File(directory.getAbsolutePath() + "/page.min.html");

			try {
				System.out.println("-- Generating minified page...");

				FileUtils.writeStringToFile(minifiedFile, data, Charset.forName("UTF-8"), false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Invalid directory!");
		}
	}

}
