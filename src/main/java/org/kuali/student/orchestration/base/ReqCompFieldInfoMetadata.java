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
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.orchestration.ConstraintMetadataBank;
import org.kuali.student.orchestration.base.ReqCompFieldInfoHelper.Properties;


public class ReqCompFieldInfoMetadata
{
	
	public boolean matches (String inputType, String inputState, String dictType, String dictState)
	{
		// TODO: code more complex matches
		return true;
	}
	
	public Metadata getMetadata (String type, String state)
	{
		Metadata mainMeta = new Metadata ();
		mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		loadChildMetadata (mainMeta, type, state);
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state)
	{
		Metadata childMeta;
		
		// metadata for value
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.VALUE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
		}
		
		// metadata for key
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.KEY.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.type"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lu.requirement.component.field.keys"));
		}
		
	}
}

