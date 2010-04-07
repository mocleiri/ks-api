package com.eviware.soapui.maven2;

/*
 *  soapUI, copyright (C) 2004-2009 eviware.com 
 *
 *  SoapUI is free software; you can redistribute it and/or modify it under the 
 *  terms of the GNU Lesser General Public License as published by the Free Software Foundation; 
 *  either version 2.1 of the License, or (at your option) any later version.
 *
 *  SoapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details at gnu.org.
 */

//import java.io.File;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.tools.SoapUIToolRunner;

/**
 * Runs soapUI tools
 * 
 * @goal tool
 */

public class ToolMojo extends AbstractMojo {
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (projectFile == null) {
			throw new MojoExecutionException("soapui-project-file setting is required");
		}

		SoapUIToolRunner runner = new SoapUIToolRunner("soapUI " + SoapUI.SOAPUI_VERSION + " Maven2 Tool Runner");
		runner.setProjectFile(projectFile);

		if (iface != null)
			runner.setInterface(iface);

		if (tool != null)
			runner.setTool(tool);

		if (settingsFile != null)
			runner.setSettingsFile(settingsFile);

		if (projectPassword != null)
			runner.setProjectPassword(projectPassword);

		if (settingsPassword != null)
			runner.setSoapUISettingsPassword(settingsPassword);

		if (outputFolder != null)
			runner.setOutputFolder(outputFolder);

		if (soapuiProperties != null && soapuiProperties.size() > 0)
			for (Object key : soapuiProperties.keySet()) {
				getLog().info("Setting " + (String) key + " value " + soapuiProperties.getProperty((String) key));
				System.setProperty((String) key, soapuiProperties.getProperty((String) key));
			}

		try {
			runner.run();
		} catch (Exception e) {
			getLog().error(e.toString());
			throw new MojoFailureException(this, "SoapUI Tool(s) failed", e.getMessage());
		}
	}

	/**
	 * The soapUI project file to test with
	 * 
	 * @parameter expression="${soapui.projectFile}"
	 *            default-value="${project.artifactId}-soapui-project.xml"
	 */

	private String projectFile;

	/**
	 * The tool to run
	 * 
	 * @parameter expression="${soapui.tool}"
	 */

	private String tool;

	/**
	 * The interface to run for
	 * 
	 * @parameter expression="${soapui.iface}"
	 */

	private String iface;

	/**
	 * Specifies soapUI settings file to use
	 * 
	 * @parameter expression="${soapui.settingsFile}"
	 */

	private String settingsFile;

	/**
	 * Specifies password for encrypted soapUI project file
	 * 
	 * @parameter expression="${soapui.project.password}"
	 */
	private String projectPassword;

	/**
	 * Specifies password for encrypted soapui-settings file
	 * 
	 * @parameter expression="${soapui.settingsFile.password}"
	 */
	private String settingsPassword;

	/**
	 * Specifies output forder for report created by runned tool
	 * 
	 * @parameter expression="${soapui.outputFolder}"
	 */
	private String outputFolder;

	/**
	 * SoapUI Properties.
	 * 
	 * @parameter expression="${soapuiProperties}"
	 */
	private Properties soapuiProperties;
}
