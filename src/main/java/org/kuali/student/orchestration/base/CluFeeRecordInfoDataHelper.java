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
package org.kuali.student.orchestration.base;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CluFeeRecordInfoDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FEE_TYPE ("feeType"),
		FEE_AMOUNT ("feeAmount"),
		AFFILIATED_ORG_INFO_LIST ("affiliatedOrgInfoList"),
		ATTRIBUTES ("attributes"),
		ID ("id");
		
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
	
	public CluFeeRecordInfoDataHelper (Data data)
	{
		this.data = data;
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
	
	
	public void setFeeAmount (CurrencyAmountInfoDataHelper value)
	{
		data.set (Properties.FEE_AMOUNT.getKey (), value.getData ());
	}
	
	
	public CurrencyAmountInfoDataHelper getFeeAmount ()
	{
		return new CurrencyAmountInfoDataHelper ((Data) data.get (Properties.FEE_AMOUNT.getKey ()));
	}
	
	
	public void setAffiliatedOrgInfoList (Data value)
	{
		data.set (Properties.AFFILIATED_ORG_INFO_LIST.getKey (), value);
	}
	
	
	public Data getAffiliatedOrgInfoList ()
	{
		return (Data) data.get (Properties.AFFILIATED_ORG_INFO_LIST.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
}

