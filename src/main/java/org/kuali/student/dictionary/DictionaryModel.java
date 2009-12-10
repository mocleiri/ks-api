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

import java.util.List;
import org.kuali.student.search.SearchModel;

/**
 * Abstration of the spreadsheet that holds the data so we can implement both
 * google and excel versions as well as caching and expanding ones.
 * @author nwright
 */
public interface DictionaryModel extends SearchModel
{

 /**
  * get name of the source of this spreadsheet
  * @return
  */
 public List<String> getSourceNames ();

 /**
  * get dictionary entries
  * @return
  */
 public List<Dictionary> getDictionary ();

 /**
  * load lu States
  * @return
  */
 public List<State> getStates ();

 /**
  * get the kuali student types
  * @return
  */
 public List<Type> getTypes ();

 /**
  * load xml types
  * @return
  */
 public List<XmlType> getXmlTypes ();

 /**
  * get fields
  * @return
  */
 public List<Field> getFields ();

 /**
  * get constraints
  * @return
  */
 public List<Constraint> getConstraints ();

 /**
  * get cross object constraints
  * @return
  */
 public List<CrossObjectConstraint> getCrossObjectConstraints ();

 /**
  * get Message Structure Definitions
  * @return
  */
 public List<MessageStructure> getMessageStructures ();

 /**
  * get Orchestration Object Definitions
  * @return
  */
 public List<OrchObj> getOrchObjs ();

}
