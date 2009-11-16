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

/**
 *
 * @author nwright
 */
public interface TestConstants
{
  public static String RESOURCES_DIRECTORY = "src/test/resources/dictionary/";
  //public static String DIRECTORY_TO_WRITE_SOURCE = "target/";
  public static String DIRECTORY_TO_WRITE_SOURCE = "../../maven-component-sandbox/trunk/src/main/java";
  //public static String DIRECTORY_TO_WRITE_SOURCE = "C:/svn/kuali-student/maven-component-sandbox/trunk/src/main/java";
  
  public static String TYPE_STATE_DICTIONARY_EXCEL_FILE = RESOURCES_DIRECTORY + "type-state configuration.xls";
  public static String ORCHESTRATION_DICTIONARY_EXCEL_FILE = RESOURCES_DIRECTORY + "orchestration-dictionary.xls";
}
