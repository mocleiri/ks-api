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
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.RuntimeDataHelper.Properties;


public class RuntimeDataMetadata
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
		loadChildMetadata (mainMeta, type, state);
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state)
	{
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for Created
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.CREATED.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.setDefaultValue (new Data.BooleanValue (Boolean.FALSE));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for Deleted
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DELETED.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.setDefaultValue (new Data.BooleanValue (Boolean.FALSE));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for Updated
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.UPDATED.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.setDefaultValue (new Data.BooleanValue (Boolean.FALSE));
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		
		// metadata for Versions
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.VERSIONS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		new RuntimeDataVersionsMetadata ().loadChildMetadata (listMeta, type, state);
		
	}
}

