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
package org.kuali.student.dictionary.writer;

import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.ServiceMethodError;

/**
 *
 * @author nwright
 */
public class ServiceExceptionWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.service";
 private Service service;
 private ServiceMethodError error;

 public ServiceExceptionWriter (DictionaryModel model,
                          String directory,
                          Service service,
                          ServiceMethodError error)
 {
  super (directory, calcPackage (service), calcClassName (error.getType ()));
  this.model = model;
  this.directory = directory;
  this.service = service;
  this.error = error;
 }


 private static String calcPackage (Service service)
 {
  return ServiceApiMethodsWriter.calcPackage (service);
 }

 public static String calcClassName (String type)
 {
  return new JavaEnumConstantCalculator (type).reverse () + "Exception";
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public class " + calcClassName (error.getType ()) + " extends Exception");
  openBrace ();

  indentPrintln ("");
  indentPrintln ("public " + calcClassName (error.getType ()) + "()");
  openBrace ();
  indentPrintln ("super ();");
  closeBrace ();

  indentPrintln ("");
  indentPrintln ("public " + calcClassName (error.getType ()) + "(Throwable cause)");
  openBrace ();
  indentPrintln ("super (cause);");
  closeBrace ();

  indentPrintln ("");
  indentPrintln ("public " + calcClassName (error.getType ()) + "(String msg, Throwable cause)");
  openBrace ();
  indentPrintln ("super (msg, cause);");
  closeBrace ();
  
  indentPrintln ("");
  closeBrace ();

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }
}
