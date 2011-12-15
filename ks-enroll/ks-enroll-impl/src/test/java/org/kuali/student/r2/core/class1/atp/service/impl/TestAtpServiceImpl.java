package org.kuali.student.r2.core.class1.atp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
public class TestAtpServiceImpl {

    @Resource(name="atpServiceAuthDecorator")
    public AtpService atpService;

    public static String principalId = "123";

    public ContextInfo callContext = ContextInfo.newInstance();

    
    @Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testAtpServiceValidationSetup() {
        assertNotNull(atpService);
    }

    @Test
    public void testGetAtp() throws DoesNotExistException, InvalidParameterException,
								    MissingParameterException, OperationFailedException, PermissionDeniedException {
		try{
			AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
			assertNotNull(atpInfo);
			assertEquals("testAtpId1", atpInfo.getKey());
			assertEquals("testAtp1", atpInfo.getName());
			assertEquals("Desc 101", atpInfo.getDescr().getPlain());
			assertEquals("kuali.atp.state.Draft", atpInfo.getStateKey());
			assertEquals("kuali.atp.type.AcademicCalendar", atpInfo.getTypeKey());
			try {
			    atpService.getAtp("totallyBogusAtpId999", callContext);
			    fail("AtpService did not throw DoesNotExistException on getAtp() of nonexistent ATP");
			}
			catch (DoesNotExistException dnee) {}
		} catch (Exception ex) {
			fail("exception from service call :" + ex.getMessage());
		}
    }

    @Test
    public void testAtpCrud()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        // test create
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setKey("newId");
        atpInfo.setName("newId");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("TestDesc1");
        atpInfo.setDescr(rt);
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        AtpInfo created = null;
        try {
            created = atpService.createAtp("newId", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
        } catch (AlreadyExistsException e) {
            fail(e.getMessage());
        } catch (DataValidationErrorException e) {
            fail(e.getMessage());
        }
        
        // test read
		AtpInfo fetched = atpService.getAtp("newId", callContext);
		assertNotNull(fetched);
		assertEquals("newId", fetched.getKey());
		assertEquals("newId", fetched.getName());
		assertEquals("TestDesc1", fetched.getDescr().getPlain());
		assertEquals("kuali.atp.state.Draft", fetched.getStateKey());
		assertEquals("kuali.atp.type.AcademicCalendar", fetched.getTypeKey());
         
        // test update
        String atpNameOrig = fetched.getName();
        AtpInfo modified = new AtpInfo(fetched);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        try {
	        updated = atpService.updateAtp(fetched.getKey(), modified, callContext);
        } catch (Exception e) {
            fail("Exception thrown when updating ATP: " + e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
        
        // test delete
        atpInfo = atpService.getAtp("testDeleteAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId1", atpInfo.getKey());
        
        try{
	        atpService.deleteAtp("testDeleteAtpId1", callContext);
	        try {
		        atpService.getAtp("testDeleteAtpId1", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e){
            fail(e.getMessage());
        }
        // undo the update done above
        updated = atpService.getAtp(updated.getKey(), callContext);
        updated.setName(atpNameOrig);
        
        try {
	        updated = atpService.updateAtp(updated.getKey(), updated, callContext);
        } catch (Exception e) {
            fail("Exception thrown when updating ATP: " + e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig, updated.getName());
    }
    
    @Test
    public void testUpdateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        AtpInfo atpInfo = atpService.getAtp("testAtpId1", callContext);
        assertNotNull(atpInfo);
        assertEquals("testAtpId1", atpInfo.getKey());
        
        String atpNameOrig = atpInfo.getName();
        AtpInfo modified = new AtpInfo(atpInfo);
        modified.setName(atpNameOrig + "updated");
        AtpInfo updated = null;
        try {
            updated = atpService.updateAtp(atpInfo.getKey(), modified, callContext);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(atpNameOrig + "updated", updated.getName());
    }
    
    @Test
    public void testCreateAtp()throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setKey("newId2");
        atpInfo.setName("newId2");
        atpInfo.setTypeKey("kuali.atp.type.AcademicCalendar");
        atpInfo.setStateKey("kuali.atp.state.Draft");
        atpInfo.setStartDate(Calendar.getInstance().getTime());
        atpInfo.setEndDate(Calendar.getInstance().getTime());
        try {
            AtpInfo created = atpService.createAtp("newId2", atpInfo, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getKey());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        // attempt to get
        AtpInfo retrieved = atpService.getAtp("newId2", callContext);
        
        assertNotNull(retrieved);
        assertEquals("newId2", retrieved.getKey());
    }
   
    @Test
    public void testDeleteAtp() throws DoesNotExistException,
    InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        AtpInfo atpInfo = atpService.getAtp("testDeleteAtpId2", callContext);
        assertNotNull(atpInfo);
        assertEquals("testDeleteAtpId2", atpInfo.getKey());
        
        try{
	        atpService.deleteAtp("testDeleteAtpId2", callContext);
	        try {
		        atpService.getAtp("testDeleteAtpId2", callContext);
		        fail("Did not receive DoesNotExistException when attempting to get already-deleted AtpEntity");
	        } catch (DoesNotExistException dnee) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetAtpsByDate()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2011, 0, 1);
        List<AtpInfo> atpInfos = atpService.getAtpsByDate(cal.getTime(), callContext);
        assertNotNull("'getAtpsByDate()' should return a list, not null", atpInfos);

        List<String> requiredKeys = new ArrayList<String>(Arrays.asList(
                "testAtpId1", "testAtpId2", "testTermId1", "testTermId2"));
        int listSize = requiredKeys.size();
        assertTrue("Date '"+ cal.getTime().toString() +"' should return at least "+listSize+" records",
                atpInfos.size() >= listSize);

        // make sure the required keys are in the retrieved list:
        for (AtpInfo atpInfo : atpInfos) {
            requiredKeys.remove(atpInfo.getKey());
        }
        if (!requiredKeys.isEmpty()) {
            fail("Failed to find key '"+ requiredKeys.get(0) +"' in returned list");
        }
    }

    @Test
    public void testCreateMilestone() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setKey("newId");
        milestone.setName("testCreate");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        
        milestone.setStartDate(cal.getTime());
        milestone.setIsDateRange(false);
        milestone.setIsAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
       
        
        try {
            MilestoneInfo created = atpService.createMilestone(milestone, callContext);
            assertNotNull(created);
            assertEquals("newId", created.getKey());
            assertEquals("testCreate", created.getName());
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        // try to get the just-created milestone
        MilestoneInfo found = atpService.getMilestone("newId", callContext);
        assertNotNull(found);
        
        // ensure we cannot create another of the same id
        MilestoneInfo dupeCreated = null;
        try {
            dupeCreated = atpService.createMilestone(milestone, callContext);
            fail("Did not get an AlreadyExistsException when expected");
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        } catch(DataValidationErrorException e){
        }
    }
        
    @Test
    public void testUpdateMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setKey("newId2");
        milestone.setName("testCreate");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2005);
        
        milestone.setStartDate(cal.getTime());
        milestone.setIsDateRange(false);
        milestone.setIsAllDay(true);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        milestone.setTypeKey("kuali.atp.milestone.RegistrationPeriod");
       
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        milestone.setDescr(descr);
       
        
        try {
            MilestoneInfo created = atpService.createMilestone(milestone, callContext);
            assertNotNull(created);
            assertEquals("newId2", created.getKey());
            assertEquals("testCreate", created.getName());
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        MilestoneInfo updateData = atpService.getMilestone("newId2", callContext);
        
        String updatedName = "updated " + updateData.getName();
        
        updateData.setName(updatedName);

        MilestoneInfo updated = null;
        try {
            updated = atpService.updateMilestone("newId2", updateData, callContext);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
        assertNotNull(updated);
        assertEquals(updated.getKey(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        // now fetch the updated milestone fresh, and check fields
        updated = atpService.getMilestone("newId2", callContext);
        assertNotNull(updated);
        assertEquals(updated.getKey(), "newId2");
        assertEquals(updated.getName(), updatedName);
        
        
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.updateMilestone("fakeKey", updated, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testDeleteMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException {
        StatusInfo status = atpService.deleteMilestone("testDeleteId", callContext);
        
        assertTrue(status.getIsSuccess());
        
        StatusInfo noStatus = null;
        try {
            noStatus = atpService.deleteMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(noStatus);
        }
        
        // ensure the delete prevents future gets
        MilestoneInfo shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestone("testDeleteId", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestone() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MilestoneInfo milestoneInfo = atpService.getMilestone("testId", callContext);
        assertNotNull(milestoneInfo);
        assertEquals("testId", milestoneInfo.getKey());
        assertEquals("testId", milestoneInfo.getName());
        assertEquals("Desc 105", milestoneInfo.getDescr().getPlain());
        assertEquals(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY, milestoneInfo.getStateKey());
        assertEquals("kuali.atp.milestone.AdvanceRegistrationPeriod", milestoneInfo.getTypeKey());
        
        MilestoneInfo fakeMilestone = null;
        try {
            fakeMilestone = atpService.getMilestone("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeMilestone);
        }
    }
    
    @Test
    public void testGetMilestonesByKeyList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> milestoneKeys = new ArrayList<String>();
        milestoneKeys.addAll(Arrays.asList("testId", "testId2"));
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByIds(milestoneKeys, callContext);
        
        assertNotNull(milestones);
        assertEquals(milestoneKeys.size(), milestones.size());
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            milestoneKeys.remove(info.getKey());
        }
        
        assertTrue(milestoneKeys.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        
        List<String> fakeKeys = Arrays.asList("testId", "fakeKey1", "fakeKey2");
        
        List<MilestoneInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestonesByIds(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
        
    }
    
    @Test
    public void testGetMilestonesByDates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.YEAR, 2011);
        
        Date startDate = cal.getTime();
        
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        Date endDate = cal.getTime();
        
        List<MilestoneInfo> milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        assertNotNull("getMilestonesByDates() should return a list, not null", milestones);

        List<String> expectedIds = new ArrayList<String>();
        expectedIds.addAll(Arrays.asList("testId", "testId2"));
        int listSize = expectedIds.size();
        assertTrue("Should have returned at least "+ listSize +" records", milestones.size() >= listSize);

        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        cal.set(Calendar.YEAR, 1990);
        
        startDate = cal.getTime();
        endDate = cal.getTime();
        
        milestones = atpService.getMilestonesByDates(startDate, endDate, callContext);
        
        assertTrue(milestones == null || milestones.isEmpty());
    }
    
    @Test
    public void testGetMilestoneIdsByType() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedMilestoneType = "kuali.atp.milestone.RegistrationPeriod";
        List<String> milestoneKeys = atpService.getMilestoneIdsByType(expectedMilestoneType, callContext);
        assertTrue(milestoneKeys.contains("testId2"));
        
        String fakeMilestoneType = "fakeTypeKey";
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getMilestoneIdsByType(fakeMilestoneType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }
    
    @Test
    public void testGetMilestonesForAtp() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<MilestoneInfo> milestones = atpService.getMilestonesForAtp("testAtpId1", callContext);
        
        assertNotNull(milestones);
        assertEquals(1, milestones.size());
        
        List<String> expectedIds = new ArrayList<String>();
        expectedIds.add("testId");
        
        // check that all the expected ids came back
        for(MilestoneInfo info : milestones) {
            expectedIds.remove(info.getKey());
        }
        
        assertTrue(expectedIds.isEmpty());
        
        List<MilestoneInfo> fakeMilestones = null;
        
        try {
            fakeMilestones = atpService.getMilestonesForAtp("fakeKey", callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(fakeMilestones);
        }
        
    }
    

    @Test
    public void testGetType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
	        TypeInfo typeInfo = atpService.getType(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, callContext);
	        assertNotNull(typeInfo);
	        try {
		        typeInfo = atpService.getType("totally.bogus.type.key", callContext);
		        fail("Did not receive DoesNotExistException when getting nonexistent TypeInfo");
	        } catch (DoesNotExistException dnee) { /* expected */ }
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }

    @Test
    public void testGetTypesByRefObjectURI() {
        try {
            List<TypeInfo> typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
            assertNotNull("'getTypesByRefObjectURI()' should return a List<>, not null", typeInfos);

            assertTrue("URI '" + AtpServiceConstants.REF_OBJECT_URI_ATP + "' should have returned at least 26 TypeInfo objects", typeInfos.size() >= 26);

            typeInfos = atpService.getTypesByRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_MILESTONE, callContext);
            assertNotNull("'getTypesByRefObjectURI()' should return a List<>, not null", typeInfos);

            List<String> requiredTypeKeys = new ArrayList<String>(Arrays.asList(
                    "kuali.atp.milestone.AdvanceRegistrationPeriod",
                    "kuali.atp.milestone.RegistrationPeriod",
                    "kuali.atp.milestone.DropDate",
                    "kuali.atp.milestone.GradesDue"));
            int listSize = requiredTypeKeys.size();
            assertTrue("URI '"+AtpServiceConstants.REF_OBJECT_URI_MILESTONE+"' should have returned at least "+listSize+" TypeInfo objects",
                    typeInfos.size() >= listSize);

            // make sure the required type keys are in the retrieved list:
            for (TypeInfo typeInfo : typeInfos) {
                requiredTypeKeys.remove(typeInfo.getKey());
            }
            if (!requiredTypeKeys.isEmpty()) {
                fail("Failed to find type key '"+requiredTypeKeys.get(0)+"' in TypeInfo list");
            }

            try {
	            typeInfos = atpService.getTypesByRefObjectURI("totally.bogus.object.uri", callContext);
		        fail("Did not receive DoesNotExistException when getting TypeInfos for nonexistent refObjectURI");
	        } catch (DoesNotExistException dnee) { /* expected */ }
	    } catch (Exception e) {
	        fail("Caught "+ e.toString() +": "+ e.getMessage());
	    }
    }

    @Test
    public void testGetState()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
    	try{
    		StateInfo stateInfo = atpService.getState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
    		assertNotNull(stateInfo);
    		assertEquals(stateInfo.getKey(), AtpServiceConstants.ATP_DRAFT_STATE_KEY);
    	 } catch (Exception e) {
 	        fail("Caught "+ e.toString() +": "+ e.getMessage());
 	    }
    }

    @Test
    public void testGetStatesByProcess()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException{
    	try{
    		List<StateInfo> stateInfos =
                    atpService.getStatesByProcess(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    		assertNotNull("getStatesByProcess() should return a list, not null", stateInfos);

    		 List<String> requiredKeys = new ArrayList<String>(Arrays.asList(
                "kuali.atp.state.Draft", "kuali.atp.state.Official"));
            int listSize = requiredKeys.size();
            assertTrue("Key '"+ AtpServiceConstants.ATP_PROCESS_KEY +"' should return at least "+listSize+" records",
                stateInfos.size()>=listSize);

            // make sure the required keys are in the retrieved list:
            for (StateInfo stateInfo : stateInfos) {
                requiredKeys.remove(stateInfo.getKey());
            }
            if (!requiredKeys.isEmpty()) {
                fail("Failed to find key '"+ requiredKeys.get(0) +"' in returned list");
            }
    	 } catch (Exception e) {
 	        fail("Caught "+ e.toString() +": "+ e.getMessage());
 	    }
    }

    @Test
    public void testGetProcessByKey()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
    	StateProcessInfo spInfo = atpService.getProcessByKey(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
    	assertNotNull(spInfo);
		assertEquals(AtpServiceConstants.ATP_PROCESS_KEY, spInfo.getKey());
    }

    @Test
    public void testGetInitialValidStates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<StateInfo> stateInfos =
                atpService.getInitialValidStates(AtpServiceConstants.ATP_PROCESS_KEY, callContext);
        assertNotNull("'getInitialValidStates()' should return a list, not null", stateInfos);

        List<String> requiredKeys = new ArrayList<String>(Arrays.asList("kuali.atp.state.Draft"));
        int listSize = requiredKeys.size();
        assertTrue("Key '"+ AtpServiceConstants.ATP_PROCESS_KEY +"' should return at least "+listSize+" records",
                stateInfos.size() >= listSize);

        // make sure the required keys are in the retrieved list:
        for (StateInfo stateInfo : stateInfos) {
            requiredKeys.remove(stateInfo.getKey());
        }
        if (!requiredKeys.isEmpty()) {
            fail("Failed to find key '"+ requiredKeys.get(0) +"' in returned list");
        }
    }

    @Test
    public void testGetNextHappyState()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
        StateInfo stateInfo = atpService.getNextHappyState(AtpServiceConstants.ATP_PROCESS_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, callContext);
        assertNotNull(stateInfo);
        assertEquals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, stateInfo.getKey());
    }

    @Test
    public void testValidateAtpAtpRelation()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey("testAtp1");
        atpRel.setRelatedAtpKey("testAtp2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try{
            List<ValidationResultInfo> vri= atpService.validateAtpAtpRelation("FULL_VALIDATION", "testAtp1", "testAtp2", AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, atpRel, callContext);
            assertTrue(vri.isEmpty());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testCreatAtpAtpRelation()
            throws AlreadyExistsException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey("testAtpId1");
        atpRel.setRelatedAtpKey("testAtpId2");
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpRel.setEffectiveDate(new Date());

        try{
            atpService.createAtpAtpRelation("testAtpId1","testAtpId2",atpRel, callContext);
        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReadOnlyException e) {
            fail(e.getMessage());
        }

        try{
            AtpInfo atpInfo = new AtpInfo();
            atpInfo.setKey("testAtpId1-newCC");
            atpInfo.setName("testAtpId1 to new campus calendar");
            atpInfo.setTypeKey(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY);
            atpInfo.setStateKey("kuali.atp.state.Draft");
            atpInfo.setStartDate(Calendar.getInstance().getTime());
            atpInfo.setEndDate(Calendar.getInstance().getTime());
            AtpInfo cc = null;
            cc = atpService.createAtp("testAtpId1-newCC", atpInfo, callContext);
            assertNotNull(cc);

            atpRel.setRelatedAtpKey("testAtpId1-newCC");
            AtpAtpRelationInfo created = atpService.createAtpAtpRelation("testAtpId1","testAtpId1-newCC",atpRel, callContext);
            assertNotNull(created);

            AtpAtpRelationInfo retrieved = atpService.getAtpAtpRelation(created.getId(), callContext);
            assertEquals("testAtpId1", retrieved.getAtpKey());
            assertEquals("testAtpId1-newCC", retrieved.getRelatedAtpKey());
            assertEquals(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, retrieved.getTypeKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetDataDictionaryEntry()
            throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        DictionaryEntryInfo value = atpService.getDataDictionaryEntry("http://student.kuali.org/wsdl/atp/AtpInfo", callContext);

        assertNotNull(value);

        DictionaryEntryInfo fakeEntry = null;
        try {
            fakeEntry = atpService.getDataDictionaryEntry("fakeKey", callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(fakeEntry);
        }
    }

    @Test
    public void testGetDataDictionaryEntryKeys()
            throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        List<String> results = atpService.getDataDictionaryEntryKeys(callContext);

        assertNotNull(results);
        assertTrue(!results.isEmpty());

        assertTrue(results.contains("http://student.kuali.org/wsdl/atp/AtpInfo"));
    }

    @Test
    public void testGetAtpAtpRelationsByTypeAndAtp() {
        try {
            List<AtpAtpRelationInfo> aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testTermId1", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
            assertEquals(1, aaRelInfos.size());
            assertEquals("testTermId2", aaRelInfos.get(0).getRelatedAtpKey());

	            aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("totallyBogusAtpIdJustMadeUpForTesting", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, callContext);
                assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
//	            fail("atpService.getAtpAtpRelationsByAtpAndRelationType() did not throw expected DoesNotExistException for nonexistent Atp ID");
            // should return empty list for bogus relation type
            aaRelInfos = atpService.getAtpAtpRelationsByTypeAndAtp("testAtpId1", "totallyBogusRelationTypeJustMadeUpForTesting", callContext);
            // sigh. service is returning an empty list, but client seems to get a null. have asked KSDevs chat about how to fix; not finding a solution via my google-fu
            assertTrue(null == aaRelInfos || aaRelInfos.size() == 0);
            // assertEquals(0, aaRelInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetAtpsByKeys()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> atpKeys = new ArrayList<String>();
        atpKeys.addAll(Arrays.asList("testAtpId1", "testAtpId2"));
        
        List<AtpInfo> atps = atpService.getAtpsByKeys(atpKeys, callContext);
        
        assertNotNull(atps);
        assertEquals(atpKeys.size(), atps.size());
        
        // check that all the expected ids came back
        for(AtpInfo info : atps) {
            atpKeys.remove(info.getKey());
        }
        assertTrue(atpKeys.isEmpty());
        
        // now make sure an exception is thrown for any not found keys
        List<String> fakeKeys = Arrays.asList("testAtpId1", "fakeKey1", "fakeKey2");
        List<AtpInfo> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpsByKeys(fakeKeys, callContext);
            fail("Did not get a DoesNotExistException when expected");
        }
        catch(DoesNotExistException e) {
            assertNull(shouldBeNull);
        }
    }

    @Test
    public void testGetAtpsByDates()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Calendar startCal = Calendar.getInstance();
        startCal.clear();
        startCal.set(2000, 8, 1); // Sept 1 2000
        Calendar endCal = Calendar.getInstance();
        endCal.clear();
        endCal.set(2001, 5, 1); // June 1 2001

        List<AtpInfo> atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);
        assertTrue("There should be at least 5 Academic Time Periods between 9/1/00 and 6/1/01.",
                atpInfos.size() >= 5);
        int priorSize = atpInfos.size();

        endCal.add(Calendar.DAY_OF_YEAR, -1); // Roll back to May 31 2001
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);

        int expectedSize = priorSize - 1;
        assertEquals("There should be "+ expectedSize +" Academic Time Periods between 9/1/00 and 5/31/01.",
                expectedSize, atpInfos.size());

        startCal.add(Calendar.DAY_OF_YEAR, 1); // Sept 2 2000
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull(atpInfos);
        assertEquals("There should be 1 Academic Time Period between 9/2/00 and 5/31/01.", 1, atpInfos.size());

        startCal.set(2001, 0, 2); // Jan 2 2001
        atpInfos = atpService.getAtpsByDates(startCal.getTime(), endCal.getTime(), callContext);
        assertNotNull("getAtpsByDates() should return an empty list instead of null.", atpInfos);
        assertEquals("There should be zero Academic Time Periods between 1/2/01 and 5/31/01.", 0, atpInfos.size());
    }

    @Test
    public void testGetAtpKeysByType()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        String expectedAtpType = AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY;
        List<String> atpKeys = atpService.getAtpKeysByType(expectedAtpType, callContext);
        assertTrue(atpKeys.contains("testAtpId1"));
        assertTrue(atpKeys.contains("termRelationTestingAcal1"));
        
        String expectedEmptyAtpType = AtpServiceConstants.ATP_SESSION_G2_TYPE_KEY;
        atpKeys = atpService.getAtpKeysByType(expectedEmptyAtpType, callContext);
        assertTrue(atpKeys == null || atpKeys.isEmpty());
        
        String fakeAtpType = "fakeTypeKey";
        List<String> shouldBeNull = null;
        try {
            shouldBeNull = atpService.getAtpKeysByType(fakeAtpType, callContext);
            fail("Did not get a InvalidParameterException when expected");
        }
        catch(InvalidParameterException e) {
            assertNull(shouldBeNull);
        }
    }

    @Test
    public void testGetAllowedTypesForType() {
        try {
            List<TypeInfo> typeInfos = atpService.getAllowedTypesForType(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.REF_OBJECT_URI_ATP, callContext);
            assertEquals(6, typeInfos.size());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testGetTypeRelationsByOwnerType() {
        try {
            List<TypeTypeRelationInfo> typeInfos = atpService.getTypeRelationsByOwnerType(AtpServiceConstants.ATP_AY_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, callContext);
            assertNotNull(typeInfos);
            assertEquals(3, typeInfos.size());
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
    }

}
