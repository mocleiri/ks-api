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
package org.kuali.student.core.organization.assembly.data.client;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.organization.assembly.data.client.ConstraintMetadataBank;
import org.kuali.student.core.organization.assembly.data.client.RecursionCounter;
import org.kuali.student.core.organization.assembly.data.client.OrgProposalHelper.Properties;


public class OrgProposalMetadata
{
	
	public boolean matches (String inputType, String inputState, String dictType, String dictState)
	{
		// TODO: code more complex matches
		return true;
	}
	
	public Metadata getMetadata (String type, String state)
	{
		Metadata mainMeta = new Metadata ();
		mainMeta.setDataType (Data.DataType.DATA);
		mainMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		loadChildMetadata (mainMeta, type, state, new RecursionCounter ());
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state,  RecursionCounter recursions)
	{
		if (recursions.decrement (this.getClass ().getName ()) < 0)
		{
			recursions.increment (this.getClass ().getName ());
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
			return;
		}
		
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for Org
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ORG.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		new OrgMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		
		recursions.increment (this.getClass ().getName ());
	}
}

