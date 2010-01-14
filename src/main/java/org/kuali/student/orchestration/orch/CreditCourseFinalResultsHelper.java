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


public class CreditCourseFinalResultsHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		EVAL_TYPE ("evalType"),
		QUALITATIVE_EVAL ("qualitativeEval"),
		CREDIT_TYPE ("creditType"),
		CREDIT_VALUE ("creditValue"),
		MAX_CREDITS ("maxCredits");
		
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
	
	private CreditCourseFinalResultsHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseFinalResultsHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseFinalResultsHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setEvalType (String value)
	{
		data.set (Properties.EVAL_TYPE.getKey (), value);
	}
	
	
	public String getEvalType ()
	{
		return (String) data.get (Properties.EVAL_TYPE.getKey ());
	}
	
	
	public void setQualitativeEval (Boolean value)
	{
		data.set (Properties.QUALITATIVE_EVAL.getKey (), value);
	}
	
	
	public Boolean isQualitativeEval ()
	{
		return (Boolean) data.get (Properties.QUALITATIVE_EVAL.getKey ());
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
	
}

