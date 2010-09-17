/*
 * Copyright 2010 The Kuali Foundation
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
package org.kuali.student.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.kuali.student.wsdl.course.DataValidationErrorException;
import org.kuali.student.wsdl.organization.OrgInfo;

/**
 *
 * @author nwright
 */
public class OrganizationLoader
{

 private OrgService orgService;

 public OrgService getOrgService ()
 {
  return orgService;
 }

 public void setOrgService (OrgService orgService)
 {
  this.orgService = orgService;
 }

 public OrganizationLoader ()
 {
 }
 private Iterator<Organization> inputDataSource;

 public Iterator<Organization> getInputDataSource ()
 {
  return inputDataSource;
 }

 public void setInputDataSource (Iterator<Organization> inputDataSource)
 {
  this.inputDataSource = inputDataSource;
 }

 public List<OrganizationLoadResult> update ()
 {
  List<OrganizationLoadResult> results = new ArrayList (500);
  int row = 0;
  while (inputDataSource.hasNext ())
  {
   OrganizationLoadResult result = new OrganizationLoadResult ();
   results.add (result);
   Organization cc = inputDataSource.next ();
   row ++;
   OrgInfo info = new OrganizationToOrgInfoConverter (cc).convert ();
   result.setRow (row);
   result.setOrganization (cc);
   result.setOrgInfo (info);
   try
   {
    OrgInfo createdInfo = orgService.createOrganization (info);
    result.setOrgInfo (createdInfo);
    result.setSuccess (true);
   }
//   catch (DataValidationErrorException ex)
//   {
//    result.setSuccess (false);
//    result.setDataValidationErrorException (ex.getFaultInfo ());
//   }
   catch (Exception ex)
   {
    result.setSuccess (false);
    result.setException (ex);
   }
  }
  return results;
 }

 public static OrganizationLoaderModelFactory getInstance (String excelFile)
 {
  Properties props = new Properties ();
  props.putAll (OrganizationLoaderModelFactory.getDefaultConfig ());
  props.put (OrganizationLoaderModelFactory.EXCEL_FILES_DEFAULT_DIRECTORY_KEY, "src/main/"
   + OrganizationLoaderModelFactory.RESOURCES_DIRECTORY);
  props.put (OrganizationLoaderModelFactory.SERVICE_HOST_URL, "src/main/"
   + OrganizationLoaderModelFactory.RESOURCES_DIRECTORY);
  System.out.println ("Current Directory=" + System.getProperty ("user.dir"));
  OrganizationLoaderModelFactory factory = new OrganizationLoaderModelFactory ();
  factory.setConfig (props);
  return factory;
 }

 


}
