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
package org.kuali.student.orchestration.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class FeeInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FEE_TYPE ("feeType"),
		FEE_AMOUNT ("feeAmount"),
		TAXABLE ("taxable"),
		FEE_DESC ("feeDesc"),
		INTERNAL_NOTATION ("internalNotation"),
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
	
	private FeeInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static FeeInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new FeeInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setFeeType (String value)
	{
		data.set (Properties.FEE_TYPE.getKey (), value);
	}
	
	
	public String getFeeType ()
	{
		return (String) data.get (Properties.FEE_TYPE.getKey ());
	}
	
	
	public void setFeeAmount (String value)
	{
		data.set (Properties.FEE_AMOUNT.getKey (), value);
	}
	
	
	public String getFeeAmount ()
	{
		return (String) data.get (Properties.FEE_AMOUNT.getKey ());
	}
	
	
	public void setTaxable (String value)
	{
		data.set (Properties.TAXABLE.getKey (), value);
	}
	
	
	public String getTaxable ()
	{
		return (String) data.get (Properties.TAXABLE.getKey ());
	}
	
	
	public void setFeeDesc (String value)
	{
		data.set (Properties.FEE_DESC.getKey (), value);
	}
	
	
	public String getFeeDesc ()
	{
		return (String) data.get (Properties.FEE_DESC.getKey ());
	}
	
	
	public void setInternalNotation (String value)
	{
		data.set (Properties.INTERNAL_NOTATION.getKey (), value);
	}
	
	
	public String getInternalNotation ()
	{
		return (String) data.get (Properties.INTERNAL_NOTATION.getKey ());
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

