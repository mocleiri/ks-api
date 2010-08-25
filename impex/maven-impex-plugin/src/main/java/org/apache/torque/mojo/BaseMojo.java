package org.apache.torque.mojo;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

/**
 * Mojo essentials. Contains the "skip" logic that is the de facto standard for maven plugins. Contains a number of
 * maven related properties that are common to most mojos
 */
public abstract class BaseMojo extends AbstractMojo {
	public static final String FS = System.getProperty("file.separator");
	public static final String SKIP_PACKAGING_TYPE = "pom";

	/**
	 * When <code>true</code>, skip the execution.
	 * 
	 * @since 1.0
	 * @parameter default-value="false"
	 */
	private boolean skip;

	/**
	 * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
	 * usually.
	 * 
	 * @parameter expression="${forceMorpherExecution}" default-value=false
	 * @required
	 */
	private boolean forceMojoExecution;

	/**
	 * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
	 */
	private String encoding;

	/**
	 * The Maven project this plugin runs in.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * @parameter expression="${settings}"
	 * @required
	 * @since 1.0
	 * @readonly
	 */
	private Settings settings;

	/**
	 * @parameter default-value="${session}"
	 * @required
	 * @readonly
	 */
	private MavenSession mavenSession;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skipMojo()) {
			return;
		}
		executeMojo();
	}

	protected abstract void executeMojo() throws MojoExecutionException, MojoFailureException;

	/**
	 * <p>
	 * Determine if the mojo execution should get skipped.
	 * </p>
	 * This is the case if:
	 * <ul>
	 * <li>{@link #skip} is <code>true</code></li>
	 * <li>if the mojo gets executed on a project with packaging type 'pom' and {@link #forceMojoExecution} is
	 * <code>false</code></li>
	 * </ul>
	 * 
	 * @return <code>true</code> if the mojo execution should be skipped.
	 */
	protected boolean skipMojo() {
		if (skip) {
			getLog().info("Skipping execution");
			return true;
		}

		if (!forceMojoExecution && project != null && SKIP_PACKAGING_TYPE.equals(project.getPackaging())) {
			getLog().info("Skipping execution for project with packaging type '" + SKIP_PACKAGING_TYPE + "'");
			return true;
		}

		return false;
	}

	/**
	 * Returns the maven project.
	 * 
	 * @return The maven project where this plugin runs in.
	 */
	public MavenProject getProject() {
		return project;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public boolean isForceMojoExecution() {
		return forceMojoExecution;
	}

	public void setForceMojoExecution(boolean forceMojoExecution) {
		this.forceMojoExecution = forceMojoExecution;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public MavenSession getMavenSession() {
		return mavenSession;
	}

	public void setMavenSession(MavenSession mavenSession) {
		this.mavenSession = mavenSession;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}
}
