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


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class CreditCourseCrossListingsHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		TYPE ("type"),
		DEPARTMENT ("department"),
		SUBJECT_AREA ("subjectArea"),
		COURSE_NUMBER_SUFFIX ("courseNumberSuffix"),
		_RUNTIME_DATA ("_runtimeData");
		
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
	
	private CreditCourseCrossListingsHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseCrossListingsHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseCrossListingsHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setDepartment (String value)
	{
		data.set (Properties.DEPARTMENT.getKey (), value);
	}
	
	
	public String getDepartment ()
	{
		return (String) data.get (Properties.DEPARTMENT.getKey ());
	}
	
	
	public void setSubjectArea (String value)
	{
		data.set (Properties.SUBJECT_AREA.getKey (), value);
	}
	
	
	public String getSubjectArea ()
	{
		return (String) data.get (Properties.SUBJECT_AREA.getKey ());
	}
	
	
	public void setCourseNumberSuffix (String value)
	{
		data.set (Properties.COURSE_NUMBER_SUFFIX.getKey (), value);
	}
	
	
	public String getCourseNumberSuffix ()
	{
		return (String) data.get (Properties.COURSE_NUMBER_SUFFIX.getKey ());
	}
	
	
	public void set_runtimeData (RuntimeDataHelper value)
	{
		data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RuntimeDataHelper get_runtimeData ()
	{
		return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
	}
	
}

