/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.common.infc;

//import com.sun.xml.internal.bind.AnyTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
//@XmlJavaTypeAdapter(AnyTypeAdapter.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface ContextInfc {

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setPrincipalId(String principalId);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getPrincipalId();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setLocaleLanguage(String localeLanguage);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getLocaleLanguage();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setLocaleVariant(String localeVariant);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getLocaleVariant();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setLocaleRegion(String localeRegion);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getLocaleRegion();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setLocaleScript(String localeScript);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getLocaleScript();

 /**
  * Set ????
  *
  * Type: String
  *
  * ???
  */
 public void setTimeZone(String timeZone);

 /**
  * Get ????
  *
  * Type: String
  *
  * ???
  */
 public String getTimeZone();
}

