package org.kuali.student.core.academiccalendar.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.core.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.core.academiccalendar.dto.CampusCalendarInfo;

import org.kuali.student.core.academiccalendar.dto.HolidayInfo;
import org.kuali.student.core.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.core.academiccalendar.dto.RegistrationDateGroupInfo;
import org.kuali.student.core.academiccalendar.dto.TermInfo;
import org.kuali.student.core.academiccalendar.service.AcademicCalendarService;

import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.test.utilities.MockHelper;

public class AcademicCalendarServiceMockImpl implements AcademicCalendarService {

	private static Map<String, AcademicCalendarInfo> acCache = new HashMap<String, AcademicCalendarInfo>();
	private static Map<String, CampusCalendarInfo> ccCache = new HashMap<String, CampusCalendarInfo>();
	private static Map<String, TermInfo> termsCache = new HashMap<String, TermInfo>();	
	private static Map<String, KeyDateInfo> keyDateCache = new HashMap<String, KeyDateInfo>();	
	private static Map<String, HolidayInfo> holidaysCache = new HashMap<String, HolidayInfo>();
	private static Map<String,TypeTypeRelationInfo> typeTypeCache = new HashMap<String, TypeTypeRelationInfo>();


	private DataDictionaryService dataDictionaryService;

	private AtpService atpService;

	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}



	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context)
	throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		return dataDictionaryService.getDataDictionaryEntry(entryKey, context);
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
	throws OperationFailedException, MissingParameterException, PermissionDeniedException {
		return this.dataDictionaryService.getDataDictionaryEntryKeys(context);
	}



	@Override
	public AcademicCalendarInfo getAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		AcademicCalendarInfo acObject = this.acCache.get(academicCalendarKey);
		if (acObject == null) {
			throw new DoesNotExistException(academicCalendarKey);
		}
		return acObject;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(
			List<String> academicCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<AcademicCalendarInfo> infos = new ArrayList<AcademicCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {
			// TODO: consider speeding up the list search by converting to a hashmap
			if (academicCalendarKeyList.contains(info.getKey())) {
				infos.add(info);
			}
		}
		return infos;

	}

	@Override
	public List<String> getAcademicCalendarKeysByType(
			String academicCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List <String> academicCalendarTypes = new ArrayList<String>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getTypeKey().equals(academicCalendarTypeKey)) {
				academicCalendarTypes.add(info.getKey());
			}
		}
		return academicCalendarTypes;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List <AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getStartDate().getYear() == year.intValue() ) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(
			String credentialProgramTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List <AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCredentialProgramTypeKey().equals(credentialProgramTypeKey) ) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	@Override
	public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(
			String credentialProgramTypeKey, Integer year, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List <AcademicCalendarInfo> academicCalendars = new ArrayList<AcademicCalendarInfo>(); 
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCredentialProgramTypeKey().equals(credentialProgramTypeKey) && info.getStartDate().getYear()==year.intValue() ) {
				academicCalendars.add(info);
			}
		}
		return academicCalendars;
	}

	@Override
	public List<ValidationResultInfo> validateAcademicCalendar(
			String validationType, AcademicCalendarInfo academicCalendarInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcademicCalendarInfo createAcademicCalendar(
			String academicCalendarKey,
			AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		AcademicCalendarInfo.Builder builder = new AcademicCalendarInfo.Builder(academicCalendarInfo);
		MockHelper helper = new MockHelper();
		builder.setKey (academicCalendarKey);
		builder.setMetaInfo(helper.createMeta(context));
		AcademicCalendarInfo copy = builder.build();
		this.acCache.put(copy.getKey(), copy);
		return copy;
	}

	@Override
	public AcademicCalendarInfo updateAcademicCalendar(
			String academicCalendarKey,
			AcademicCalendarInfo academicCalendarInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {
		AcademicCalendarInfo existingAC = this.acCache.get(academicCalendarKey);
		if (existingAC == null) {
			throw new DoesNotExistException(academicCalendarKey);
		}
		if (!academicCalendarInfo.getMetaInfo().getVersionInd().equals(
				existingAC.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"Updated by " + existingAC.getMetaInfo().getUpdateId() + " on "
					+ existingAC.getMetaInfo().getUpdateId() + " with version of "
					+ existingAC.getMetaInfo().getVersionInd());
		}
		MockHelper helper = new MockHelper();
		AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(academicCalendarInfo);
		acBuilder.setMetaInfo(helper.updateMeta(existingAC.getMetaInfo(), context));
		AcademicCalendarInfo copy = acBuilder.build();
		this.acCache.put(academicCalendarKey, copy);
		// mirroring what was done before immutable DTO's; why returning copy of copy?
		return new AcademicCalendarInfo.Builder(copy).build();
	}


	@Override
	public StatusInfo deleteAcademicCalendar(String academicCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo.Builder sBuilder = new StatusInfo.Builder();
		if(acCache.containsKey(academicCalendarKey)){
			acCache.remove(academicCalendarKey);
			sBuilder.setSuccess(Boolean.TRUE);
		}else{
			throw new DoesNotExistException(academicCalendarKey);
		}

		return sBuilder.build();

	}

	@Override
	public AcademicCalendarInfo copyAcademicCalendar(
			String academicCalendarKey, String newAcademicCalendarKey,
			ContextInfo context) throws AlreadyExistsException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		if(this.acCache.containsKey(academicCalendarKey)){
			AcademicCalendarInfo dupCalendar = this.acCache.get(academicCalendarKey);

			AcademicCalendarInfo.Builder acBuilder = new AcademicCalendarInfo.Builder(dupCalendar);
			acBuilder.setEndDate(null);
			acBuilder.setStartDate(null);
			acBuilder.setKey(newAcademicCalendarKey);

			AcademicCalendarInfo  copiedCalendar  = acBuilder.build();
			acCache.put(newAcademicCalendarKey, copiedCalendar);
			return copiedCalendar;
		}else{
			throw new DoesNotExistException(academicCalendarKey);
		}

	}

	@Override
	public String getAcademicCalendarData(String academicCalendarKey,
			String calendarDataFormatTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		// TODO sambit complete the impl later
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getKey().equals(academicCalendarKey) ) {

			}
		}
		return null;
	}

	@Override
	public CampusCalendarInfo getCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (info.getCampusCalendarKey().equals(campusCalendarKey)) {

				CampusCalendarInfo campusCalendar  =  ccCache.get(campusCalendarKey) ;
				return campusCalendar;
			}
		}
		throw new DoesNotExistException(campusCalendarKey);
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByKeyList(
			List<String> campusCalendarKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (AcademicCalendarInfo info : this.acCache.values()) {

			if (campusCalendarKeyList.contains(info.getCampusCalendarKey())    ) {

				campusCalendars.add( ccCache.get(info.getCampusCalendarKey()) );

			}
		}
		return campusCalendars;
	}

	@Override
	public List<String> getCampusCalendarKeysByType(
			String campusCalendarTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<String> campusCalendarKeys = new ArrayList<String>();

		for (CampusCalendarInfo ccInfo : ccCache.values()){
			if(ccInfo.getTypeKey().equals(campusCalendarTypeKey)){
				campusCalendarKeys.add(ccInfo.getKey());
			}
		}

		return campusCalendarKeys;
	}

	@Override
	public List<CampusCalendarInfo> getCampusCalendarsByYear(Integer year,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<CampusCalendarInfo> campusCalendars = new ArrayList<CampusCalendarInfo>();
		for (CampusCalendarInfo info : this.ccCache.values()) {
			if(info.getStartDate().getYear()== year.intValue()){
				campusCalendars.add(info);
			}
		}

		return campusCalendars;
	}

	@Override
	public List<ValidationResultInfo> validateCampusCalendar(
			String validationType, CampusCalendarInfo campusCalendarInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CampusCalendarInfo createCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		CampusCalendarInfo.Builder newCampusCalendarBuilder = new CampusCalendarInfo.Builder(campusCalendarInfo);
		newCampusCalendarBuilder.setKey(campusCalendarKey);
		CampusCalendarInfo newCampusCalendar = newCampusCalendarBuilder.build();
		ccCache.put(campusCalendarKey,newCampusCalendar);
		return newCampusCalendar;
	}

	@Override
	public CampusCalendarInfo updateCampusCalendar(String campusCalendarKey,
			CampusCalendarInfo campusCalendarInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {

		CampusCalendarInfo.Builder campusCalendarBuilder = new CampusCalendarInfo.Builder(campusCalendarInfo);
		campusCalendarBuilder.setKey(campusCalendarKey);
		ccCache.remove(campusCalendarKey);
		ccCache.put(campusCalendarKey, campusCalendarBuilder.build());
		return campusCalendarInfo;
	}

	@Override
	public StatusInfo deleteCampusCalendar(String campusCalendarKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		StatusInfo.Builder statusInfo = new StatusInfo.Builder();
		CampusCalendarInfo ccInfo=  ccCache.remove(campusCalendarKey);
		statusInfo.setSuccess(ccInfo==null);

		return statusInfo.build();

	}

	@Override
	public TermInfo getTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return termsCache.get(termKey);

	}

	@Override
	public List<TermInfo> getTermsByKeyList(List<String> termKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<TermInfo> matchingTerms =  new ArrayList<TermInfo>();
		for (TermInfo termInfo : this.termsCache.values()) {	
			if ( termKeyList.contains(termInfo.getKey()) ){
				matchingTerms.add (termInfo);
			}
		}
		return matchingTerms;
	}

	@Override
	public List<String> getTermKeysByType(String termTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> matchingTermKeys =  new ArrayList<String>();
		for (TermInfo termInfo : termsCache.values() ) {
			if ( termInfo.getTypeKey().equals(termTypeKey) ){
				matchingTermKeys.add(termInfo.getKey());
			}
		}
		return matchingTermKeys;
	}

	@Override
	public KeyDateInfo getKeyDate(String keyDateKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return keyDateCache.get(keyDateKey);
	}

	@Override
	public List<KeyDateInfo> getKeyDatesByKeyList(List<String> keyDateKeyList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		for(String keyDate:keyDateKeyList){
			if(keyDateCache.containsKey(keyDate)){
				keyDates.add(keyDateCache.get(keyDate) );
			}else{
				throw new DoesNotExistException(keyDate);
			}
		}
		return keyDates;
	}

	@Override
	public List<String> getKeyDateKeysByType(String keyDateTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> keyDates = new ArrayList<String>();
		for(KeyDateInfo keyDate:keyDateCache.values()){
			if(keyDate.getTypeKey().equals(keyDateTypeKey) )
				keyDates.add(keyDate.getKey());
		}
		return keyDates;
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		return null;
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public List<KeyDateInfo> getAllKeyDatesForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateKeyDate(String validationType,
			KeyDateInfo keyDateInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public KeyDateInfo createKeyDateForTerm(String termKey, String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}
	/*
	@Override
	public KeyDateInfo createKeyDateForCampusCalendar(String campusCalendarKey,
			String keyDateKey, KeyDateInfo keyDateInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}
	 */
	@Override
	public KeyDateInfo updateKeyDate(String keyDateKey,
			KeyDateInfo keyDateInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public StatusInfo deleteKeyDate(String keyDateKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		// TODO implement after DateRange changes 
		return null;
	}

	@Override
	public List<HolidayInfo> getHolidaysForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		AcademicCalendarInfo acInfo = acCache.get(academicCalendarKey);
		CampusCalendarInfo ccInfo =  ccCache.get(acInfo.getKey());
		List<MilestoneInfo> milestonesForAtp = this.atpService.getMilestonesByAtp(ccInfo.getKey(), context);
		List<HolidayInfo> holidays = new ArrayList<HolidayInfo>();
		// TODO convert milestonesForAtp to HolidayInfo list
		return holidays;
	}

	@Override
	public List<ValidationResultInfo> validateHoliday(String validationType,
			HolidayInfo holidayInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HolidayInfo createHolidayForCampusCalendar(String campusCalendarKey,
			String holidayKey, HolidayInfo holidayInfo, ContextInfo context)
	throws AlreadyExistsException, DataValidationErrorException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {

		if( !holidaysCache.containsKey(holidayKey)){
			holidaysCache.put(holidayKey,holidayInfo );
		}

		CampusCalendarInfo ccInfo = 	ccCache.get(campusCalendarKey);

		AtpMilestoneRelationInfo.Builder atpMilestoneRelationInfoBuilder = new AtpMilestoneRelationInfo.Builder();
		atpMilestoneRelationInfoBuilder.setMilestoneKey(holidayKey);
		atpMilestoneRelationInfoBuilder.setAtpKey(campusCalendarKey);

		AtpMilestoneRelationInfo newAtpInfo = this.atpService.createAtpMilestoneRelation(atpMilestoneRelationInfoBuilder.build(), context);

		return  holidaysCache.get(newAtpInfo.getMilestoneKey()) ;
	}

	@Override
	public HolidayInfo updateHoliday(String holidayKey,
			HolidayInfo holidayInfo, ContextInfo context)
	throws DataValidationErrorException, DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException,
	VersionMismatchException {

		holidaysCache.remove(holidayKey);
		holidaysCache.put(holidayKey, holidayInfo);
		return  holidayInfo;
	}

	@Override
	public StatusInfo deleteHoliday(String holidayKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {

		holidaysCache.remove(holidayKey);

		List <AtpMilestoneRelationInfo> atpMilestoneRelations =  this.atpService.getAtpMilestoneRelationsByMilestone(holidayKey, context);

		for(AtpMilestoneRelationInfo atpMilestoneRelation: atpMilestoneRelations){
			this.atpService.deleteAtpMilestoneRelation(atpMilestoneRelation.getId(), context);
		}


		StatusInfo.Builder statInfoBuilder = new StatusInfo.Builder();
		statInfoBuilder.setSuccess(true);
		return statInfoBuilder.build();
	}



	@Override
	public Integer getInstructionalDaysForTerm(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		Date classEndDate = getRegistrationDateGroup(termKey, context).getClassEndDate()  ;
		Date classStartDate =getRegistrationDateGroup(termKey, context).getClassStartDate() ;

		Calendar startCalendar = new GregorianCalendar(classStartDate.getYear(), classStartDate.getMonth(), classStartDate.getDate());
		Calendar endCalendar = new GregorianCalendar(classEndDate.getYear(), classEndDate.getMonth(), classEndDate.getDate());

		//TODO Use some open source library like joda-time to compute these
		// 
		//TODO  Finally the calendar should remove weekend days, non-instructional holidays - any other days such as exams which are not instructional

		return new Integer(0);




	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(
			String academicCalendarKey, Date startDate, Date endDate,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<MilestoneInfo> mileStoneInfos =  this.atpService.getMilestonesByAtp(academicCalendarKey, context);
		//convert MileStones to KeyDateInfo 
		return new ArrayList<KeyDateInfo>();



	}

	@Override
	public List<KeyDateInfo> getKeyDatesForTermByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<KeyDateInfo> keyDates = new ArrayList<KeyDateInfo>();
		List<MilestoneInfo> milestones =  this.atpService.getMilestonesByAtp(termKey, context);
		for(MilestoneInfo milestoneInfo : milestones){
			if(milestoneInfo.getStartDate().equals(startDate)&& milestoneInfo.getEndDate().equals(endDate)){
				//convert milestone to keydate
				keyDates.add(null);
			}
		}
		return keyDates;
	}

	@Override
	public List<KeyDateInfo> getKeyDatesForAllTermsByDate(String termKey,
			Date startDate, Date endDate, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return null;
	}

	@Override
	public List<TermInfo> getTermsForTerm(List<String> termCalendar,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateTerm(String validationType,
			TermInfo termInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		//get this from term info
		AtpInfo atpInfo = null;
		return this.atpService.validateAtp(validationType, atpInfo , context);
	}

	@Override
	public TermInfo createTerm(String termKey, TermInfo termInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		TermInfo.Builder newTermInfoBuilder =  new TermInfo.Builder(termInfo);   
		newTermInfoBuilder.setKey(termKey);
		TermInfo newTermInfo = newTermInfoBuilder.build(); 
		termsCache.put(termKey, newTermInfo);
		return newTermInfo;



	}

	@Override
	public TermInfo updateTerm(String termKey, TermInfo termInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {

		TermInfo.Builder termInfoBuilder = new TermInfo.Builder(termInfo);
		termInfoBuilder.setKey(termKey);
		termsCache.remove(termKey);
		TermInfo newTerm = termInfoBuilder.build();
		termsCache.put(termKey, newTerm);
		return newTerm;
	}

	@Override
	public StatusInfo deleteTerm(String termKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		termsCache.remove(termKey);

		StatusInfo.Builder ssBuilder =  new StatusInfo.Builder();
		ssBuilder.setSuccess(true);
		return ssBuilder.build();
	}



	@Override
	public RegistrationDateGroupInfo getRegistrationDateGroup(String termKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateRegistrationDateGroup(
			String validationType,
			RegistrationDateGroupInfo registrationDateGroupInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationDateGroupInfo updateRegistrationDateGroup(
			String termKey,
			RegistrationDateGroupInfo registrationDateGroupInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TermInfo> getTermsForAcademicCalendar(
			String academicCalendarKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		//TODO terms not there?

		AcademicCalendarInfo acInfo =  acCache.get(academicCalendarKey);
		return null;

	}

	@Override
	public TypeInfo getAcademicCalendarType(String academicCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAcademicCalendarTypes(ContextInfo context)
	throws InvalidParameterException, MissingParameterException,
	OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getCampusCalendarType(String campusCalendarTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getCampusCalendarTypes(ContextInfo context)
	throws InvalidParameterException, MissingParameterException,
	OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getTermType(String termTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getTermTypes(ContextInfo context)
	throws InvalidParameterException, MissingParameterException,
	OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getKeyDateType(String keyDateTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getKeyDateTypesForTermType(String termTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getHolidayType(String holidayTypeKey, ContextInfo context)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getHolidayTypesForCampusCalendarType(
			String campusCalendarTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAcademicCalendarState(
			String academicCalendarStateKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getTermState(String termStateKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

}
