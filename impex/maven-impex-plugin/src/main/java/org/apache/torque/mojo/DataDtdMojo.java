package org.apache.torque.mojo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.task.TorqueDataModelTask;
import org.kuali.core.db.torque.Utils;

/**
 * Generates a DTD for the database tables from a schema XML file
 * 
 * @goal datadtd
 * @phase generate-sources
 */
public class DataDtdMojo extends DataModelTaskMojo {
	/** The context property for the name of the project. */
	public static final String PROJECT_CONTEXT_PROPERTY = "project";

	/**
	 * @parameter expression="${antCompatibilityMode}" antCompatibilityMode="false"
	 */
	boolean antCompatibilityMode;

	/**
	 * Only used if antCompatibilityMode is set to true. If so, the dtd that gets generated will be copied to
	 * 
	 * @parameter expression="${copyToFile}" default-value="${basedir}/src/main/impex/data.dtd"
	 */
	String copyToFile;

	/**
	 * The directory in which the DTD will be generated
	 * 
	 * @parameter property="outputDir" expression="${outputDir}"
	 *            default-value="${project.build.directory}/generated-impex"
	 */
	@SuppressWarnings("unused")
	private String dummy1;

	/**
	 * The location where the report file will be generated, relative to outputDir.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}"
	 *            default-value="../reports/report.${project.artifactId}.datadtd.generation"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/reports/context.datadtd.properties"
	 */
	@SuppressWarnings("unused")
	private String dummy3;

	/**
	 * The name of the project
	 * 
	 * @parameter expression="${projectName}" default-value="impex"
	 */
	private String projectName;

	/**
	 * The name of the schema.xml file to process
	 * 
	 * @parameter expression="${schemaXMLFile}" default-value="${basedir}/src/main/impex/${project.artifactId}.xml"
	 * @required
	 */
	private String schemaXMLFile;

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		configuration.addProperty(PROJECT_CONTEXT_PROPERTY, getProjectName());
		configuration.addProperty("version", getProject().getVersion());
		return configuration;
	}

	/**
	 * Configures the Texen task wrapped by this mojo
	 */
	protected void configureTask() throws MojoExecutionException {
		TorqueDataModelTask task = new TorqueDataModelTask();
		setAntTask(task);
		super.configureTask();
		boolean exists = new Utils().isFileOrResource(getSchemaXMLFile());
		if (!exists) {
			throw new MojoExecutionException("Unable to locate: " + getSchemaXMLFile());
		}
		task.setXmlFile(getSchemaXMLFile());

	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "data/Control.vm"
	 */
	protected String getControlTemplate() {
		return "data/Control.vm";
	}

	/**
	 * Returns the name of the project
	 * 
	 * @return the name of the project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the name of the project
	 * 
	 * @param project
	 *            the name of the project.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Returns the name of the xml file to process.
	 * 
	 * @return the name of the xml file to process.
	 */
	public String getSchemaXMLFile() {
		return schemaXMLFile;
	}

	/**
	 * Sets the name of the xml file to process.
	 * 
	 * @param project
	 *            the name of the xml file to process.
	 */
	public void setSchemaXMLFile(String xmlFile) {
		this.schemaXMLFile = xmlFile;
	}

	@Override
	public void executeMojo() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Generating database DTD");
		getLog().info("------------------------------------------------------------------------");
		super.executeMojo();
		if (antCompatibilityMode) {
			File srcFile = getSrcFile();
			File destFile = new File(copyToFile);
			getLog().info("Creating " + destFile.getAbsolutePath() + " for Ant compatibility mode");
			try {
				FileUtils.copyFile(srcFile, destFile);
			} catch (IOException e) {
				throw new MojoExecutionException("Error copying file", e);
			}
		}
	}

	protected File getSrcFile() throws MojoExecutionException {
		FilenameFilter dtdFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return (name.endsWith(".dtd"));
			}
		};
		File dir = new File(getOutputDir());
		String[] files = dir.list(dtdFilter);
		if (files.length != 1) {
			throw new MojoExecutionException("Located more than one DTD file to process");
		}
		return new File(dir.getAbsolutePath() + FS + files[0]);
	}

	public boolean isAntCompatibilityMode() {
		return antCompatibilityMode;
	}

	public void setAntCompatibilityMode(boolean antCompatibilityMode) {
		this.antCompatibilityMode = antCompatibilityMode;
	}

	public String getCopyToFile() {
		return copyToFile;
	}

	public void setCopyToFile(String copyToFile) {
		this.copyToFile = copyToFile;
	}
}
