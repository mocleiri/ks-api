/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class OrchestrationObjectsDataWriterTest implements TestConstants
{

 public OrchestrationObjectsDataWriterTest ()
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

 @Before
 public void setUp ()
 {
 }

 @After
 public void tearDown ()
 {
 }

 /**
  * Test of write method, of class OrchestrationObjectsDataWriter.
  */
 @Test
 public void testWrite ()
 {
  System.out.println ("write");
  SpreadsheetReader dictReader =
   new ExcelSpreadsheetReader (TYPE_STATE_DICTIONARY_EXCEL_FILE);
  SpreadsheetReader orchReader =
   new ExcelSpreadsheetReader (ORCHESTRATION_DICTIONARY_EXCEL_FILE);
  try
  {
   DictionaryModelLoader loader =
    new DictionaryModelLoader (dictReader, orchReader);
   DictionaryModel cache = new DictionaryModelCache (loader);
   OrchestrationObjectsDataWriter instance =
    new OrchestrationObjectsDataWriter (cache, SOURCE_DIRECTORY);
   instance.write ();
  }
  finally
  {
   dictReader.close ();
   orchReader.close ();
  }
  assertEquals (true, true);
 }

}
