/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dictionary.service.impl.DictionaryServiceImpl;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;

public class TestMetadataServiceDictionary
{

 @Test
 public void testMetadataService ()
 {
  Set<Class<?>> startingClasses = new LinkedHashSet ();
  startingClasses.add (CourseInfo.class);
//  startingClasses.add (StatementTreeViewInfo.class);
  startingClasses.add (MajorDisciplineInfo.class);
  startingClasses.add (ProposalInfo.class);
  startingClasses.add (CluSetInfo.class);
  startingClasses.add (StatementInfo.class);
  startingClasses.add (ReqComponentInfo.class);
  startingClasses.add (ReqCompFieldInfo.class);

  DictionaryService courseDictService = new DictionaryServiceImpl (
    "classpath:ks-courseInfo-dictionary-context.xml");
  DictionaryService progDictService = new DictionaryServiceImpl (
    "classpath:ks-programInfo-dictionary-context.xml");
  DictionaryService cluSetDictService = new DictionaryServiceImpl (
    "classpath:ks-cluSetInfo-dictionary-context.xml");
  DictionaryService proposalDictService = new DictionaryServiceImpl (
    "classpath:ks-proposalInfo-dictionary-context.xml");
  DictionaryService statementDictService = new DictionaryServiceImpl (
    "classpath:ks-statement-dictionary-context.xml");
  MetadataServiceImpl metadataService = new MetadataServiceImpl (
    courseDictService,
    progDictService,
    cluSetDictService,
    proposalDictService,
    statementDictService);
  metadataService.setUiLookupContext ("classpath:lum-ui-lookup-context.xml");
	 String outFile = "target/metadata.txt";
  File file = new File (outFile);
  OutputStream outputStream = null;
  try
  {
   outputStream = new FileOutputStream (file, false);
  }
  catch (FileNotFoundException ex)
  {
   throw new RuntimeException (ex);
  }
  PrintStream out =new PrintStream (outputStream);
  out.println ("(!) This page was automatically generated on "
                      + new Date ());
  out.println ("DO NOT UPDATE MANUALLY!");
  out.println ("");
  out.print (
    "This page represents a formatted view of the lum ui dictionary");
  for (Class<?> clazz : startingClasses)
  {
   out.println ("# " + clazz.getName ());
  }
  out.println ("");
  out.println ("----");
  out.println ("{toc}");
  out.println ("----");

  for (Class<?> clazz : startingClasses)
  {
//   out.println ("getting meta data for " + clazz.getName ());
   Metadata metadata = metadataService.getMetadata (clazz.getName ());
   assertNotNull (metadata);
   MetadataFormatter formatter = new MetadataFormatter (clazz.getName (),
                                                        metadata, null, new HashSet (),
                                                        1);
   out.println (formatter.formatForWiki ());

  }
 }
}
