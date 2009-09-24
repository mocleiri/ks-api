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

import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * This reads an excel spreadsheet using the JExcelAPI from SourceForge
 * @author nwright
 */
public class ExcelSpreadsheetReader implements SpreadsheetReader
{

 private String spreadsheetFileName;

 public ExcelSpreadsheetReader (String spreadsheetFileName)
 {
  this.spreadsheetFileName = spreadsheetFileName;
 }

 private transient Workbook workbook = null;

 protected Workbook getWorkbook ()
 {
  if (this.workbook != null)
  {
   return this.workbook;
  }
  try
  {
   workbook = Workbook.getWorkbook (new File (spreadsheetFileName));
  }
  catch (BiffException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  catch (IOException ex)
  {
   throw new DictionaryExecutionException (ex);
  }
  return workbook;
 }

 @Override
 public WorksheetReader getWorksheetReader (String name)
 {
  return new ExcelWorksheetReader (this, name);
 }

 @Override
 public void close ()
 {
  if (workbook == null)
  {
   return;
  }
  workbook.close ();
  workbook = null;
 }




}
