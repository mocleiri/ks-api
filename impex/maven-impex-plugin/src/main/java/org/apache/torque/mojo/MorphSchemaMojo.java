package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
import org.apache.torque.mojo.morph.SchemaMorpher;
import org.codehaus.plexus.util.FileUtils;

/**
 * Convert an Ant impex schema XML file into a maven-impex-plugin schema XML file
 * 
 * @goal morphschema
 * @phase generate-sources
 */
public class MorphSchemaMojo extends BaseMojo {

	/**
	 * The XML file describing the database schema
	 * 
	 * @parameter expression="${newSchemaXMLFile}"
	 *            default-value="${project.build.directory}/generated-impex/${project.artifactId}.xml"
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

	protected void executeMojo() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting schema XML file");
		getLog().info("------------------------------------------------------------------------");
		try {
			FileUtils.forceMkdir(new File(FileUtils.getPath(newSchemaXMLFile.getAbsolutePath())));
			MorphRequest request = new MorphRequest(oldSchemaXMLFile.getName(), new FileInputStream(oldSchemaXMLFile), new FileOutputStream(newSchemaXMLFile));
			request.setEncoding(getEncoding());
			Morpher morpher = new SchemaMorpher(request, getProject().getArtifactId());
			morpher.executeMorph();
		} catch (IOException e) {
			throw new MojoExecutionException("Unexpected error while attempting to morph " + oldSchemaXMLFile.getAbsolutePath(), e);
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
