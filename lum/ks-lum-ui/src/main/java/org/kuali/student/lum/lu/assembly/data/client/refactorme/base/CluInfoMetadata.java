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


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.ConstraintMetadataBank;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluInfoHelper.Properties;


public class CluInfoMetadata
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
		Map <String, Integer> recursions = new HashMap ();
		loadChildMetadata (mainMeta, type, state, recursions);
		return mainMeta;
	}
	
	public void loadChildMetadata (Metadata mainMeta, String type, String state,  Map<String, Integer> recursions)
	{
		int recurseLevel = increment (recursions, "CluInfoMetadata");
		
		Metadata childMeta;
		Metadata listMeta;
		
		// metadata for officialIdentifier
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.OFFICIAL_IDENTIFIER.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluIdentifierInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for alternateIdentifiers
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ALTERNATE_IDENTIFIERS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluIdentifierInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for academicSubjectOrgs
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ACADEMIC_SUBJECT_ORGS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new AcademicSubjectOrgInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for studySubjectArea
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.STUDY_SUBJECT_AREA.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("code"));
		}
		
		// metadata for desc
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DESC.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new RichTextInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for marketingDesc
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.MARKETING_DESC.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new RichTextInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for campusLocationList
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.CAMPUS_LOCATION_LIST.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("code"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for accreditation
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ACCREDITATION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new AccreditationInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for primaryAdminOrg
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.PRIMARY_ADMIN_ORG.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new AdminOrgInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for alternateAdminOrgs
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ALTERNATE_ADMIN_ORGS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new AdminOrgInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for primaryInstructor
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.PRIMARY_INSTRUCTOR.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluInstructorInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for instructors
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.INSTRUCTORS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluInstructorInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for expectedFirstAtp
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EXPECTED_FIRST_ATP.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("atp.types"));
		}
		
		// metadata for effectiveDate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EFFECTIVE_DATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATE);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("date.time"));
		}
		
		// metadata for expirationDate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.EXPIRATION_DATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATE);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("date.time"));
		}
		
		// metadata for intensity
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.INTENSITY.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new TimeAmountInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for stdDuration
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.STD_DURATION.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new TimeAmountInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for canCreateLui
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.CAN_CREATE_LUI.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("boolean"));
		}
		
		// metadata for referenceURL
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.REFERENCE_U_R_L.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("url"));
		}
		
		// metadata for luCodes
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.LU_CODES.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.DATA);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new LuCodeInfoMetadata ().loadChildMetadata (listMeta, type, state, recursions);
		}
		
		// metadata for publishingInfo
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.PUBLISHING_INFO.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluPublishingInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for nextReviewPeriod
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.NEXT_REVIEW_PERIOD.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("atp.types"));
		}
		
		// metadata for isEnrollable
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.IS_ENROLLABLE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("boolean"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("default.true"));
		}
		
		// metadata for offeredAtpTypes
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.OFFERED_ATP_TYPES.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.LIST);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("atp.types"));
		}
		listMeta = new Metadata ();
		listMeta.setDataType (Data.DataType.STRING);
		listMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		childMeta.getProperties ().put (QueryPath.getWildCard (), listMeta);
		
		// metadata for hasEarlyDropDeadline
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.HAS_EARLY_DROP_DEADLINE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("boolean"));
		}
		
		// metadata for defaultEnrollmentEstimate
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.INTEGER);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("non-negative.integer"));
		}
		
		// metadata for defaultMaximumEnrollment
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.DEFAULT_MAXIMUM_ENROLLMENT.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.INTEGER);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("non-negative.integer"));
		}
		
		// metadata for isHazardousForDisabledStudents
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.IS_HAZARDOUS_FOR_DISABLED_STUDENTS.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.BOOLEAN);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("boolean"));
		}
		
		// metadata for feeInfo
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.FEE_INFO.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluFeeInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for accountingInfo
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ACCOUNTING_INFO.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new CluAccountingInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for attributes
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ATTRIBUTES.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("optional"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("repeating"));
		}
		
		// metadata for metaInfo
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.META_INFO.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.DATA);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("read.only"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
		}
		if (recurseLevel >= 1)
		{
			mainMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		}
		else
		{
			new MetaInfoMetadata ().loadChildMetadata (childMeta, type, state, recursions);
		}
		
		// metadata for type
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.TYPE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ON_CREATE);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.type"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lu.types"));
		}
		
		// metadata for state
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.STATE.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.ALWAYS);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("required"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.state"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("lu.states"));
		}
		
		// metadata for id
		childMeta = new Metadata ();
		mainMeta.getProperties ().put (Properties.ID.getKey (), childMeta);
		childMeta.setDataType (Data.DataType.STRING);
		childMeta.setWriteAccess (Metadata.WriteAccess.NEVER);
		if (this.matches (type, state, "(default)", "(default)"))
		{
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("read.only"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("single"));
			childMeta.getConstraints ().add (ConstraintMetadataBank.BANK.get ("kuali.id"));
		}
		
	}
	
	private int increment (Map<String, Integer> recursions, String key)
	{
		Integer recurseLevel = recursions.get (key);
		if (recurseLevel == null)
		{
			recursions.put (key, 0);
			return 0;
		}
		recursions.put (key, recurseLevel.intValue () + 1);
		return recurseLevel.intValue ();
	}
}

