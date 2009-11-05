/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.dictionary;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwright
 */
public class ExcelSpreadsheetReaderTest implements TestConstants
{

 public ExcelSpreadsheetReaderTest ()
 {
 }

 @BeforeClass
 public static void setUpClass ()
  throws Exception
 {
 }

 @AfterClass
 public static void tearDownClass ()
  throws Exception
 {
 }

 ExcelSpreadsheetReader instance;

 @Before
 public void setUp ()
 {
  System.out.println ("reading " + TYPE_STATE_DICTIONARY_EXCEL_FILE);
  instance = new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE);
 }

 @After
 public void tearDown ()
 {
  instance.close ();
  instance = null;
 }


 /**
  * Test of getWorksheetReader method, of class ExcelSpreadsheetReader.
  */
 @Test
 public void testGetWorksheetReader ()
 {
  System.out.println ("getWorksheetReader");
  String name = "Constraints";
  WorksheetReader reader = instance.getWorksheetReader (name);
  assertNotNull (reader);
 }

}