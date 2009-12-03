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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CreditInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CREDIT_TYPE ("creditType"),
		CREDIT_VALUE ("creditValue"),
		MAX_CREDITS ("maxCredits"),
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
	
	private CreditInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCreditType (String value)
	{
		data.set (Properties.CREDIT_TYPE.getKey (), value);
	}
	
	
	public String getCreditType ()
	{
		return (String) data.get (Properties.CREDIT_TYPE.getKey ());
	}
	
	
	public void setCreditValue (String value)
	{
		data.set (Properties.CREDIT_VALUE.getKey (), value);
	}
	
	
	public String getCreditValue ()
	{
		return (String) data.get (Properties.CREDIT_VALUE.getKey ());
	}
	
	
	public void setMaxCredits (String value)
	{
		data.set (Properties.MAX_CREDITS.getKey (), value);
	}
	
	
	public String getMaxCredits ()
	{
		return (String) data.get (Properties.MAX_CREDITS.getKey ());
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

