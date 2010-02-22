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
package org.kuali.student.dictionary.writer.service;

import java.io.Serializable;
import java.util.List;
import org.kuali.student.dictionary.DictionaryExecutionException;
import org.kuali.student.dictionary.model.DictionaryModel;
import org.kuali.student.dictionary.model.MessageStructure;
import org.kuali.student.dictionary.model.Service;
import org.kuali.student.dictionary.model.XmlType;
import org.kuali.student.dictionary.model.util.ModelFinder;
import org.kuali.student.dictionary.writer.JavaClassWriter;

/**
 *
 * @author nwright
 */
public class ServiceDTOWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 public static final String ROOT_PACKAGE = "org.kuali.student.service";
 private Service service;
 private XmlType type;
 private ModelFinder finder;

 public ServiceDTOWriter (DictionaryModel model,
                          String directory,
                          Service service,
                          XmlType type)
 {
  super (directory, calcPackage (service), calcClassName (type.getName ()));
  this.model = model;
  this.finder = new ModelFinder (model);
  this.directory = directory;
  this.service = service;
  this.type = type;
 }

 public static String calcPackage (Service service)
 {
  return ServiceMethodsWriter.calcPackage (service);
 }

 public static String calcClassName (String name)
 {
  if (name.endsWith ("Info"))
  {
   name = name.substring (0, name.length () - "Info".length ()) + "DTO";
  }
  return name.substring (0, 1).toUpperCase () + name.substring (1);

 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public class " + calcClassName (type.getName ()));
  incrementIndent ();
  indentPrint (" implements "
   + ServiceInfoWriter.calcClassName (type.getName ()));
  importsAdd (ServiceInfoWriter.calcPackage (service)
   + "." + ServiceInfoWriter.calcClassName (type.getName ()));
  this.importsAdd (Serializable.class.getName ());
  indentPrintln (", Serializable");
  openBrace ();

  indentPrintln ("");
	 indentPrintln ("private static final long serialVersionUID = 1L;");

  List<MessageStructure> list =
  finder.findMessageStructures (type.getName ());
  if (list.size () == 0)
  {
   throw new DictionaryExecutionException ("xmlType " + type.getName ()
    + " has no fields defined in the message structure tab");
  }
  for (MessageStructure ms : list)
  {
   String fieldType = this.calcFieldTypeToUse (ms.getType ());
   String name = ms.getShortName ();
   indentPrintln ("");
   indentPrintln ("private " + fieldType + " " + name + ";");
   indentPrintln ("");
   indentPrintln ("@Override");
   indentPrintln ("public void " + calcSetter (ms) + "(" + fieldType + " " + name + ")");
   openBrace ();
   indentPrintln ("this." + name + " = " + name + ";");
   closeBrace ();

   indentPrintln ("");
   indentPrintln ("@Override");
   indentPrintln ("public " + fieldType + " " + calcGetter (ms) + "()");
   openBrace ();
   indentPrintln ("return this." + name + ";");
   closeBrace ();
   indentPrint ("");

   indentPrint ("");
  }
  indentPrintln ("");
  closeBrace ();

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private String calcGetter (MessageStructure ms)
 {
  if (calcFieldTypeToUse (ms.getType ()).equals ("Boolean"))
  {
   return "is" + calcProperName (ms.getShortName ());
  }
  return "get" + calcProperName (ms.getShortName ());
 }

 private String calcSetter (MessageStructure ms)
 {
  return "set" + calcProperName (ms.getShortName ());
 }

 private String calcProperName (String name)
 {
  return name.substring (0, 1).toUpperCase () + name.substring (1);
 }

 private String calcFieldTypeToUse (String type)
 {
  return ServiceMessageStructureTypeCalculator.calculate (this, model, type, null);
 }

}
