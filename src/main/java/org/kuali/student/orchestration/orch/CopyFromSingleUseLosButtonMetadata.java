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
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.orchestration.ConstraintMetadataBank;
import org.kuali.student.orchestration.LookupMetadataBank;
import org.kuali.student.orchestration.RecursionCounter;
import org.kuali.student.orchestration.orch.CopyFromSingleUseLosButtonHelper.Properties;


public class CopyFromSingleUseLosButtonMetadata
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
		
		// metadata for Description
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DESCRIPTION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.setLookupMetadata (LookupMetadataBank.LOOKUP_BANK.get ("kuali.lookup.singleUseLos".toLowerCase ()));
		childMeta.setLookupContextPath (null);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		recursions.increment (this.getClass ().getName ());
	}
}

