package org.apache.maven.plugins.site;

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

import org.apache.maven.doxia.siterenderer.RendererException;
import org.apache.maven.doxia.siterenderer.SiteRenderingContext;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.reporting.MavenReport;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Generates the site for a single project.
 * <p>
 * Note that links between module sites in a multi module build will <b>not</b>
 * work.
 * </p>
 *
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse</a>
 * @author <a href="mailto:vincent.siveton@gmail.com">Vincent Siveton</a>
 * @version $Id: SiteMojo.java 890094 2009-12-13 19:31:42Z ltheussl $
 * @goal site
 * @requiresDependencyResolution test
 */
public class SiteMojo
    extends AbstractSiteRenderingMojo
{
    /**
     * Directory containing the generated project sites and report distributions.
     *
     * @parameter expression="${siteOutputDirectory}" default-value="${project.reporting.outputDirectory}"
     * @required
     */
    protected File outputDirectory;

    /**
     * Convenience parameter that allows you to disable report generation.
     *
     * @parameter expression="${generateReports}" default-value="true"
     */
    private boolean generateReports;

    /**
     * Generate a sitemap. The result will be a "sitemap.html" file at the site root.
     *
     * @parameter expression="${generateSitemap}" default-value="false"
     * @since 2.1
     */
    private boolean generateSitemap;

    /**
     * {@inheritDoc}
     *
     * Generate the project site
     * <p/>
     * throws MojoExecutionException if any
     *
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        List filteredReports;
        if ( generateReports )
        {
            filteredReports = filterReports( reports );
        }
        else
        {
            filteredReports = Collections.EMPTY_LIST;
        }

        try
        {
            List localesList = siteTool.getAvailableLocales( locales );

            // Default is first in the list
            Locale defaultLocale = (Locale) localesList.get( 0 );
            Locale.setDefault( defaultLocale );

            for ( Iterator iterator = localesList.iterator(); iterator.hasNext(); )
            {
                Locale locale = (Locale) iterator.next();

                renderLocale( locale, filteredReports );
            }
        }
        catch ( RendererException e )
        {
            throw new MojoExecutionException( "Error during page generation", e );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error during site generation", e );
        }
    }

    private void renderLocale( Locale locale,
                               List reports )
        throws IOException, RendererException, MojoFailureException, MojoExecutionException
    {
        SiteRenderingContext context = createSiteRenderingContext( locale );

        context.setInputEncoding( getInputEncoding() );
        context.setOutputEncoding( getOutputEncoding() );

        Map documents = locateDocuments( context, reports, locale );

        File outputDir = getOutputDirectory( locale );

        // For external reports
        for ( Iterator i = reports.iterator(); i.hasNext(); )
        {
            MavenReport report = (MavenReport) i.next();
            report.setReportOutputDirectory( outputDir );
        }

        siteRenderer.render( documents.values(), context, outputDir );

        if ( generateSitemap )
        {
            getLog().info( "Generating Sitemap." );

            new SiteMap( getOutputEncoding(), i18n )
                    .generate( context.getDecoration(), generatedSiteDirectory, locale );
        }

        // Generated docs must be done afterwards as they are often generated by reports
        context.getSiteDirectories().clear();
        context.addSiteDirectory( generatedSiteDirectory );

        documents = siteRenderer.locateDocumentFiles( context );

        siteRenderer.render( documents.values(), context, outputDir );
    }

    private File getOutputDirectory( Locale locale )
    {
        File file;
        if ( locale.getLanguage().equals( Locale.getDefault().getLanguage() ) )
        {
            file = outputDirectory;
        }
        else
        {
            file = new File( outputDirectory, locale.getLanguage() );
        }

        // Safety
        if ( !file.exists() )
        {
            file.mkdirs();
        }

        return file;
    }
}
