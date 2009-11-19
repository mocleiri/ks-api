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


import java.util.Date;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CluResultInfoDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		DESC ("desc"),
		CLU_ID ("cluId"),
		RESULT_OPTIONS ("resultOptions"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		META_INFO ("metaInfo"),
		TYPE ("type"),
		STATE ("state"),
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
	private ModifiableData data;
	
	public CluResultInfoDataHelper (ModifiableData data)
	{
		this.data = data;
	}
	
	public ModifiableData getData ()
	{
		return data;
	}
	
	
	public void setDesc (RichTextInfoDataHelper value)
	{
		data.set (Properties.DESC.getKey (), value.getData ());
	}
	
	
	public RichTextInfoDataHelper getDesc ()
	{
		return new RichTextInfoDataHelper ((Data) data.get (Properties.DESC.getKey ()));
	}
	
	
	public void setCluId (String value)
	{
		data.set (Properties.CLU_ID.getKey (), value);
	}
	
	
	public String getCluId ()
	{
		return (String) data.get (Properties.CLU_ID.getKey ());
	}
	
	
	public void setResultOptions (Data value)
	{
		data.set (Properties.RESULT_OPTIONS.getKey (), value);
	}
	
	
	public Data getResultOptions ()
	{
		return (Data) data.get (Properties.RESULT_OPTIONS.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		data.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return (Date) data.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		data.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return (Date) data.get (Properties.EXPIRATION_DATE.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoDataHelper value)
	{
		data.set (Properties.META_INFO.getKey (), value.getData ());
	}
	
	
	public MetaInfoDataHelper getMetaInfo ()
	{
		return new MetaInfoDataHelper ((Data) data.get (Properties.META_INFO.getKey ()));
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
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

