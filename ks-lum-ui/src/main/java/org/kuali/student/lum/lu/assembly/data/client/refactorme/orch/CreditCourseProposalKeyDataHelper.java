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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CreditCourseProposalKeyDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		TITLE ("title"),
		PROPOSER ("proposer"),
		DEPARTMENT ("department");
		
		private final String key;
		
		private Properties (final String key)
		{
			this.key = key;
		}
		
		@Override
		public String getKey ()
		{
			return this.key;
		}
	}
	private Data data;
	
	private CreditCourseProposalKeyDataHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseProposalKeyDataHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseProposalKeyDataHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setTitle (String value)
	{
		data.set (Properties.TITLE.getKey (), value);
	}
	
	
	public String getTitle ()
	{
		return (String) data.get (Properties.TITLE.getKey ());
	}
	
	
	public void setProposer (String value)
	{
		data.set (Properties.PROPOSER.getKey (), value);
	}
	
	
	public String getProposer ()
	{
		return (String) data.get (Properties.PROPOSER.getKey ());
	}
	
	
	public void setDepartment (String value)
	{
		data.set (Properties.DEPARTMENT.getKey (), value);
	}
	
	
	public String getDepartment ()
	{
		return (String) data.get (Properties.DEPARTMENT.getKey ());
	}
	
}

