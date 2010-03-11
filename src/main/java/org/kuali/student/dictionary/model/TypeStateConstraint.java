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
package org.kuali.student.dictionary.model;

import org.kuali.student.dictionary.model.Constraint;

/**
 *
 * @author nwright
 */
public class TypeStateConstraint
{

 public TypeStateConstraint (String type, String state, Constraint constraint)
 {
  this.type = type;
  this.state = state;
  this.constraint = constraint;
 }

 
 private String type;

 public String getType ()
 {
  return type;
 }

 public void setType (String type)
 {
  this.type = type;
 }

 private String state;

 public String getState ()
 {
  return state;
 }

 public void setState (String state)
 {
  this.state = state;
 }

 private Constraint constraint;

 public Constraint getConstraint ()
 {
  return constraint;
 }

 public void setConstraint (Constraint constraint)
 {
  this.constraint = constraint;
 }

}
