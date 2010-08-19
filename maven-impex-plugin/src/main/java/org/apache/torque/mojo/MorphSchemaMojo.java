package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Convert an Ant impex schema XML file into a maven-impex-plugin schema XML file
 * 
 * @goal morphschema
 * @phase generate-sources
 */
public class MorphSchemaMojo extends MorpherMojo {

	// Ant impex has kfs hard coded
	String defaultSchemaName = "kfs";
	// Token we can look for to positively identify this schema XML file as an Ant Impex file
	String defaultSchemaToken = "name=\"" + defaultSchemaName + "\"";
	// Ant impex is hard coded to database.dtd
	String defaultDTDString = "\"database.dtd\"";
	// The Kuali database.dtd
	String newDTDString = "\"http://www.kuali.org/dtd/database.dtd\"";
	// Ant impex comment
	String defaultComment = "<!-- Autogenerated by KualiTorqueJDBCTransformTask! -->";
	// Ant impex comment
	String newComment = "<!-- Autogenerated by the Maven Impex Plugin -->";

	/**
	 * The XML file describing the database schema
	 * 
	 * @parameter expression="${newSchemaXMLFile}"
	 *            default-value="${project.build.directory}/generated-impex/${project.artifactId}-schema.xml"
	 * @required
	 */
	private File newSchemaXMLFile;

	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${oldSchemaXMLFile}" default-value="${basedir}/src/main/impex/schema.xml"
	 * @required
	 */
	private File oldSchemaXMLFile;

	protected void executeMorph() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting schema XML file");
		getLog().info("------------------------------------------------------------------------");
		try {
			// Read the "old" schema XML file into a string
			String contents = readFileToString(oldSchemaXMLFile, getEncoding());

			// Check it to see if it was created by Ant
			if (!isAntImpexSchemaXML(contents)) {
				getLog().warn("Unable to determine if this is an export from Ant Impex");
			}

			// May not need to morph
			if (isMorphNeeded(contents)) {
				contents = morph(contents, getProject().getArtifactId());
			} else {
				getLog().info("No morphing needed");
			}

			// Write the schema to the file system
			writeStringToFile(newSchemaXMLFile, contents, getEncoding());
		} catch (IOException e) {
			throw new MojoExecutionException("Error doing file system IO", e);
		}
	}

	/**
	 * Morph an Ant Impex XML file into a Maven Impex Plugin XML file
	 */
	protected String morph(String contents, String schemaName) {
		contents = StringUtils.replace(contents, defaultDTDString, newDTDString);
		contents = StringUtils.replace(contents, defaultComment, newComment);
		return StringUtils.replace(contents, "name=\"" + defaultSchemaName + "\">", "name=\"" + schemaName + "\">");
	}

	/**
	 * Attempt to determine if this content is from an Ant Impex XML export
	 */
	protected boolean isAntImpexSchemaXML(String contents) {
		if (contents == null) {
			return false;
		}
		if (contents.indexOf(defaultSchemaToken) == -1) {
			return false;
		}
		if (contents.indexOf(defaultDTDString) == -1) {
			return false;
		}
		if (contents.indexOf(defaultComment) == -1) {
			return false;
		}
		// All 3 tokens we know about were present in the String
		// Pretty good chance it is content from an Ant Impex export
		return true;
	}

	/**
	 * Return true if we need to morph this file
	 */
	protected boolean isMorphNeeded(String content) {
		// Look for the DTD the Maven Impex Plugin uses
		int pos = content.indexOf(newDTDString);

		if (pos == -1) {
			// It isn't there so we should morph
			return true;
		} else {
			// It is already there, we are good to go
			return false;
		}
	}

	public File getNewSchemaXMLFile() {
		return newSchemaXMLFile;
	}

	public void setNewSchemaXMLFile(File newSchemaXMLFile) {
		this.newSchemaXMLFile = newSchemaXMLFile;
	}

	public File getOldSchemaXMLFile() {
		return oldSchemaXMLFile;
	}

	public void setOldSchemaXMLFile(File oldSchemaXMLFile) {
		this.oldSchemaXMLFile = oldSchemaXMLFile;
	}
}
