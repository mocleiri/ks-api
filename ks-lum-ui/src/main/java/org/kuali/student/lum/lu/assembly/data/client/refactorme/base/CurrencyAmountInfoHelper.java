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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.base;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CurrencyAmountInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CURRENCY_TYPE_KEY ("currencyTypeKey"),
		CURRENCY_QUANTITY ("currencyQuantity");
		
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
	
	public CurrencyAmountInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCurrencyTypeKey (String value)
	{
		data.set (Properties.CURRENCY_TYPE_KEY.getKey (), value);
	}
	
	
	public String getCurrencyTypeKey ()
	{
		return (String) data.get (Properties.CURRENCY_TYPE_KEY.getKey ());
	}
	
	
	public void setCurrencyQuantity (Integer value)
	{
		data.set (Properties.CURRENCY_QUANTITY.getKey (), value);
	}
	
	
	public Integer getCurrencyQuantity ()
	{
		return (Integer) data.get (Properties.CURRENCY_QUANTITY.getKey ());
	}
	
}

