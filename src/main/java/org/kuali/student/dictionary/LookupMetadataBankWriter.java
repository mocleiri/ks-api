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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.common.assembly.client.LookupImplMetadata;
import org.kuali.student.common.assembly.client.LookupMetadata;
import org.kuali.student.common.assembly.client.LookupParamMetadata;
import org.kuali.student.common.assembly.client.LookupResultMetadata;

/**
 *
 * @author nwright
 */
public class LookupMetadataBankWriter extends JavaClassWriter
{

 private DictionaryModel model;
 private String directory;
 private String rootPackage;
 public static final String JAVA_CLASS_NAME = "LookupMetadataBank";

 public LookupMetadataBankWriter (DictionaryModel model,
                                  String directory,
                                  String rootPackage)
 {
  super (directory, rootPackage, JAVA_CLASS_NAME);
  this.model = model;
  this.directory = directory;
  this.rootPackage = rootPackage;
 }

 /**
  * Write out the entire file
  * @param out
  */
 public void write ()
 {
  indentPrintln ("public class " + JAVA_CLASS_NAME);
  openBrace ();

  imports.add (Map.class.getName ());
  imports.add (HashMap.class.getName ());
  imports.add (LookupMetadata.class.getName ());
  indentPrintln ("public static final Map <String, LookupMetadata> LOOKUP_BANK = new HashMap ();");
  indentPrintln ("public static final Map <String, LookupMetadata> SEARCH_BANK = new HashMap ();");
  indentPrintln ("");
  indentPrintln ("// static initiliazer");
  indentPrintln ("static");
  openBrace ();
  indentPrintln ("LookupMetadata lookup = null;");
  indentPrintln ("LookupParamMetadata param = null;");
  indentPrintln ("LookupResultMetadata result = null;");
  indentPrintln ("LookupImplMetadata impl = null;");
  imports.add (LookupMetadata.class.getName ());
  imports.add (LookupParamMetadata.class.getName ());
  imports.add (LookupResultMetadata.class.getName ());
  imports.add (LookupImplMetadata.class.getName ());

  // basic conversion
  List<LookupMetadata> lookupMetas =
   new SearchTypesToLookupMetadataBankConverter (model).getLookups ();

  // now write them out
  for (LookupMetadata lookupMeta : lookupMetas)
  {
   indentPrintln ("");
   indentPrintln ("//");
   indentPrintln ("// new lookup metadata");
   indentPrintln ("lookup = new LookupMetadata ();");
   new LookupMetadataWriter (this, "lookup", lookupMeta).write ();
   indentPrintln ("SEARCH_BANK.put (lookup.getKey ().toLowerCase (), lookup);");
   indentPrintln ("LOOKUP_BANK.put (lookup.getLookupKey ().toLowerCase (), lookup);");
  }

  for (LookupMetadata lookupMeta : lookupMetas)
  {
   for (LookupParamMetadata param : lookupMeta.getParams ())
   {
    if (param.getChildLookup () != null)
    {
     String childLookupKey = param.getChildLookup ().getKey ();
     String lookupKey = lookupMeta.getLookupKey ();
     String paramKey = param.getKey ();
     indentPrintln ("");
     indentPrintln ("// set childLookup " + childLookupKey);
     indentPrintln ("// on " + lookupKey + "." + paramKey);
     indentPrintln ("param = findParam (" + quote (lookupKey) + ", " +
      quote (paramKey) + ");");
     indentPrintln ("lookup = LOOKUP_BANK.get (" + quote (childLookupKey) +
      ".toLowerCase ());");
     indentPrintln ("param.setChildLookup (lookup);");
    }
   }
  }
  closeBrace (); // end static initializer

  indentPrintln ("");
  indentPrintln ("private static Date asDate (String value)");
  openBrace ();
  imports.add (Date.class.getName ());
  imports.add (SimpleDateFormat.class.getName ());
  //imports.add (ParseException.class.getName ());
  indentPrintln ("try");
  openBrace ();
  indentPrintln ("return new SimpleDateFormat (\"yyyy-MM-dd\").parse (value);");
  closeBrace ();
  indentPrintln ("catch (Exception e)");
  openBrace ();
  indentPrintln ("assert (false); // this should never happen");
  closeBrace ();
  indentPrintln ("return null;");
  closeBrace ();

  indentPrintln ("");
  indentPrintln ("private static LookupParamMetadata findParam (String lookupKey, String paramKey)");
  openBrace ();
  indentPrintln ("return findParam (LOOKUP_BANK.get (lookupKey), paramKey);");
  closeBrace ();

  indentPrintln ("");
  indentPrintln ("private static LookupParamMetadata findParam (LookupMetadata lookup, String paramKey)");
  openBrace ();
  indentPrintln ("for (LookupParamMetadata param : lookup.getParams ())");
  openBrace ();
  indentPrintln ("if (param.getKey ().equalsIgnoreCase (paramKey))");
  openBrace ();
  indentPrintln ("return param;");
  closeBrace ();
  closeBrace ();
  indentPrintln ("return null;");
  closeBrace ();

  imports.add (List.class.getName ());
  imports.add (ArrayList.class.getName ());
  indentPrintln ("");
  indentPrintln ("public static List<LookupMetadata> findAdditional (String lookupKey)");
  openBrace ();
  indentPrintln ("List<LookupMetadata> list = new ArrayList ();");
  indentPrintln ("int sequence = 0;");
  indentPrintln ("while (true)");
  openBrace ();
  indentPrintln ("sequence++;");
  indentPrintln ("LookupMetadata meta = LOOKUP_BANK.get (lookupKey + \".additional.\" + sequence);");
  indentPrintln ("if (meta == null)");
  openBrace ();
  indentPrintln ("return list;");
  closeBrace ();
  indentPrintln ("list.add (meta);");
  closeBrace ();
  closeBrace ();

  closeBrace (); // end class

  this.writeJavaClassAndImportsOutToFile ();
  this.getOut ().close ();
 }

 private String quote (String str)
 {
  return StringQuoter.quote (str);
 }

}

