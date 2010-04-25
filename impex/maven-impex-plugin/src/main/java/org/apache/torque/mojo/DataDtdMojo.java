package org.apache.torque.mojo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
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
	 * The directory in which the DTD will be generated
	 * 
	 * @parameter property="outputDir" expression="${outputDir}" default-value="${project.build.directory}/data/impex"
	 */
	@SuppressWarnings("unused")
	private String dummy1;

	/**
	 * The location where the report file will be generated, relative to outputDir.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}"
	 *            default-value="../../impex/report.${project.artifact.artifactId}.datadtd.generation"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/impex/context.datadtd.properties"
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
	 * The Maven Project Object
	 * 
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * The name of the schema.xml file to process
	 * 
	 * @parameter expression="${schemaXMLFile}"
	 *            default-value="${basedir}/src/main/impex/schema/${project.artifactId}-schema.xml"
	 * @required
	 */
	private String schemaXMLFile;

	/**
	 * Creates a new SQLMojo object.
	 */
	public DataDtdMojo() {
		super(new TorqueDataModelTask());
	}

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
		super.configureTask();
		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();
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

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}
}
