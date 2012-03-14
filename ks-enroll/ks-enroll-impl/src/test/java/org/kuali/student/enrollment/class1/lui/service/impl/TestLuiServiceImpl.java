package org.kuali.student.enrollment.class1.lui.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestLuiServiceImpl {

    @Resource
    // look up bean via variable name, then type
    private LuiService luiServiceValidation;

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testLuiServiceSetup() {
        assertNotNull(luiServiceValidation);
    }

    @Test
    public void testGetLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            try {
                luiServiceValidation.getLui("Lui-blah", callContext);
            } catch (DoesNotExistException enee) {}

            LuiInfo obj = luiServiceValidation.getLui("Lui-1", callContext);
            assertNotNull(obj);
            assertEquals("Lui one", obj.getName());
            assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, obj.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, obj.getTypeKey());

            assertEquals("Lui Desc 101", obj.getDescr().getPlain());

            assertNotNull(obj.getOfficialIdentifier());
            assertEquals("Chem 123", obj.getOfficialIdentifier().getShortName());
            assertNotNull(obj.getAlternateIdentifiers());
            assertEquals("LUI-IDENT-2", obj.getAlternateIdentifiers().get(0).getId());

            assertNotNull(obj.getEffectiveDate());
            assertNotNull(obj.getExpirationDate());

            assertEquals("cluId1", obj.getCluId());
            assertEquals("atpId1", obj.getAtpId());
            assertEquals(1, obj.getLuiCodes().size());
            assertEquals("LUI-lu-cd-1", obj.getLuiCodes().get(0).getId());

            assertNotNull(obj.getMaximumEnrollment());
            assertEquals(200, obj.getMaximumEnrollment().intValue());
            assertNotNull(obj.getMinimumEnrollment());
            assertEquals(50, obj.getMinimumEnrollment().intValue());
            assertEquals("ref.url", obj.getReferenceURL());

            assertNotNull(obj.getCluCluRelationIds());
            assertEquals(2, obj.getCluCluRelationIds().size());
            assertTrue(obj.getCluCluRelationIds().contains("CluClu-2"));
            assertNotNull(obj.getUnitsContentOwner());
            assertEquals(1, obj.getUnitsContentOwner().size());
            assertTrue(obj.getUnitsContentOwner().contains("Org-2"));
            assertNotNull(obj.getUnitsDeployment());
            assertEquals(1, obj.getUnitsDeployment().size());
            assertTrue(obj.getUnitsDeployment().contains("Org-1"));
            assertNotNull(obj.getResultValuesGroupKeys());
            assertEquals(3, obj.getResultValuesGroupKeys().size());
            assertTrue(obj.getResultValuesGroupKeys().contains("Val-Group-3"));

            assertNotNull(obj.getFees());
            assertEquals(3, obj.getFees().size());
            assertNotNull(obj.getRevenues());
            assertEquals(2, obj.getRevenues().size());
            assertNotNull(obj.getExpenditure());
            assertEquals("LUI-Expen-1", obj.getExpenditure().getId());
            assertNotNull(obj.getMeetingSchedules());
            assertEquals(4, obj.getMeetingSchedules().size());

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetLuiIdsByRelation() throws InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            List<String> luiIds = luiServiceValidation.getLuiIdsByRelation("Lui-2", "kuali.lui.lui.relation.associated", callContext);
            assertNotNull(luiIds);
            assertEquals(2, luiIds.size());
            assertEquals("Lui-1", luiIds.get(0));
            assertEquals("Lui-5", luiIds.get(1));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetLuisByRelation() throws InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            List<LuiInfo> luis = luiServiceValidation.getLuisByRelation("Lui-2", "kuali.lui.lui.relation.associated", callContext);
            assertNotNull(luis);
            assertEquals(2, luis.size());
            assertEquals("Lui-1", luis.get(0).getId());
            assertEquals("Lui-5", luis.get(1).getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateLui() throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo info = new LuiInfo();
        info.setName("Test lui one");
        info.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        info.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted("<p>Test lui one description</p>");
        rtInfo.setPlain("Test lui one description");
        info.setDescr(rtInfo);
        info.setEffectiveDate(Calendar.getInstance().getTime());
        info.setMaximumEnrollment(25);
        info.setMinimumEnrollment(10);
        info.setReferenceURL("ref.create.url");
        if (info.getOfficialIdentifier() != null) {
            fail("official identifier is not null but it was never set");
        }
        
        LuiIdentifierInfo identifier = new LuiIdentifierInfo();
        identifier.setShortName("identifier.shortname");
        identifier.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        identifier.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        info.setOfficialIdentifier(identifier);
        
        LuiIdentifierInfo identifier2 = new LuiIdentifierInfo();
        identifier2.setShortName("alternate.identifier.shortname");
        identifier2.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
        identifier2.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        info.getAlternateIdentifiers().add(identifier2);
        
        info.setCluId("testCluId");
        info.setAtpId("testAtpId1");
        
        List<String> cluCluRelationIds = new ArrayList<String>();
        cluCluRelationIds.add("CluClu-2");
        info.setCluCluRelationIds(cluCluRelationIds);
        
        List<String> unitsContentOwner = new ArrayList<String>();
        unitsContentOwner.add("Org-2");
        info.setUnitsContentOwner(unitsContentOwner);
        
        List<String> unitsDeployment = new ArrayList<String>();
        unitsDeployment.add("Org-1");
        info.setUnitsDeployment(unitsDeployment);
        
        List<String> resultValueGroupKeys = new ArrayList<String>();
        resultValueGroupKeys.add("Val-Group-3");
        info.setResultValuesGroupKeys(resultValueGroupKeys);
        
        LuCodeInfo luCode = new LuCodeInfo();
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("fee.plain");
        rt.setFormatted("fee.formatted");
        luCode.setDescr(rt);
        info.getLuiCodes().add(luCode);
        
        FeeInfo fee = new FeeInfo();
        RichTextInfo rtf = new RichTextInfo();
        rtf.setPlain("fee.plain");
        rtf.setFormatted("fee.formatted");
        fee.setDescr(rtf);
        info.getFees().add(fee);
        
        RevenueInfo revenue = new RevenueInfo();
        info.getRevenues().add(revenue);
        
        ExpenditureInfo expenditure = new ExpenditureInfo();
        info.setExpenditure(expenditure);
        
        MeetingScheduleInfo meetingSchedule = new MeetingScheduleInfo();
        info.getMeetingSchedules().add(meetingSchedule);

        LuiInfo created = null;
        created = luiServiceValidation.createLui("testCluId", "testAtpId1", info, callContext);
        assertNotNull(created);
        assertNull("official identifier was non-null after creation even though it was never set intentionally", created.getOfficialIdentifier());
        assertEquals("Test lui one", created.getName());
        assertEquals(LuiServiceConstants.LUI_DRAFT_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, created.getTypeKey());
        assertNotNull(created.getEffectiveDate());
        assertEquals(null, created.getExpirationDate());
        
        assertNotNull(created.getOfficialIdentifier());
        assertEquals("identifier.shortname", created.getOfficialIdentifier().getShortName());
        assertEquals(1, created.getAlternateIdentifiers().size());
        assertEquals("alternate.identifier.shortname", created.getAlternateIdentifiers().get(0).getShortName());
        
        assertEquals(Integer.valueOf(25), created.getMaximumEnrollment());
        assertEquals(Integer.valueOf(10), created.getMinimumEnrollment());
        assertEquals("testCluId", created.getCluId());
        assertEquals("testAtpId1", created.getAtpId());
        assertEquals("ref.create.url", created.getReferenceURL());
        
        assertTrue(created.getCluCluRelationIds().contains("CluClu-2"));
        assertTrue(created.getUnitsContentOwner().contains("Org-2"));
        assertTrue(created.getUnitsDeployment().contains("Org-1"));
        assertTrue(created.getResultValuesGroupKeys().contains("Val-Group-3"));
        
        assertEquals(1, created.getLuiCodes().size());
        assertEquals(1, created.getFees().size());
        assertEquals(1, created.getRevenues().size());
        assertNotNull(created.getExpenditure().getId());
        assertEquals(1, created.getMeetingSchedules().size());

    }

    @Test
    public void testUpdateLui() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiInfo info = luiServiceValidation.getLui("Lui-1", callContext);
        assertNotNull(info);
        assertEquals("Lui-1", info.getId());
        assertEquals("Lui one", info.getName());

        LuiInfo modified = new LuiInfo(info);
        modified.setStateKey(LuiServiceConstants.LUI_APROVED_STATE_KEY);
        modified.setMaximumEnrollment(25);
        modified.setMinimumEnrollment(10);
        assertNotNull(modified.getOfficialIdentifier());
        modified.getOfficialIdentifier().setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        modified.getOfficialIdentifier().setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        modified.getOfficialIdentifier().setShortName("identifier.shortname");
        
        modified.getAlternateIdentifiers().clear();
        LuiIdentifierInfo identifier2 = new LuiIdentifierInfo();
        identifier2.setShortName("alternate.identifier.shortname");
        identifier2.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
        identifier2.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        modified.getAlternateIdentifiers().add(identifier2);
        
        modified.setReferenceURL("ref.update.url");
        modified.setCluId("updateCluId");
        modified.setAtpId("updateAtpId1");

        modified.getCluCluRelationIds().remove("CluClu-2");
        modified.getCluCluRelationIds().add("CluClu-22");
        modified.getUnitsContentOwner().add("Org-22");
        modified.getUnitsDeployment().add("Org-11");
        modified.getResultValuesGroupKeys().remove("Val-Group-2");
        modified.getResultValuesGroupKeys().remove("Val-Group-3");
        modified.getResultValuesGroupKeys().add("Val-Group-33");
        
        LuCodeInfo luCode = new LuCodeInfo();
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain("fee.plain");
        rt.setFormatted("fee.formatted");
        luCode.setDescr(rt);
        modified.getLuiCodes().add(luCode);
        
        FeeInfo fee = new FeeInfo();
        RichTextInfo rtf = new RichTextInfo();
        rtf.setPlain("fee.plain");
        rtf.setFormatted("fee.formatted");
        fee.setDescr(rtf);
        modified.getFees().add(fee);
        
        RevenueInfo revenue = new RevenueInfo();
        modified.getRevenues().add(revenue);
        
        ExpenditureInfo expenditure = new ExpenditureInfo();
        modified.setExpenditure(expenditure);
        
        MeetingScheduleInfo meetingSchedule = new MeetingScheduleInfo();
        modified.getMeetingSchedules().add(meetingSchedule);
        
        try {
            LuiInfo updated = luiServiceValidation.updateLui("Lui-1", modified, callContext);
            
            assertNotNull(updated);
            assertEquals(LuiServiceConstants.LUI_APROVED_STATE_KEY, updated.getStateKey());
            assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, updated.getTypeKey());
            assertEquals(Integer.valueOf(25), updated.getMaximumEnrollment());
            assertEquals(Integer.valueOf(10), updated.getMinimumEnrollment());
            assertEquals("ref.update.url", updated.getReferenceURL());
            assertNotNull(updated.getEffectiveDate());
            assertNotNull(updated.getExpirationDate());
            assertEquals("updateCluId", updated.getCluId());
            assertEquals("updateAtpId1", updated.getAtpId());
            
            assertNotNull(updated.getOfficialIdentifier());
            assertEquals("identifier.shortname", updated.getOfficialIdentifier().getShortName());
            assertEquals(1, updated.getAlternateIdentifiers().size());
            assertEquals("alternate.identifier.shortname", updated.getAlternateIdentifiers().get(0).getShortName());
            
            assertEquals(2, updated.getResultValuesGroupKeys().size());
            assertTrue(updated.getCluCluRelationIds().contains("CluClu-1"));
            assertTrue(updated.getCluCluRelationIds().contains("CluClu-22"));
            assertEquals(2, updated.getResultValuesGroupKeys().size());
            assertTrue(updated.getUnitsContentOwner().contains("Org-22"));
            assertEquals(2, updated.getResultValuesGroupKeys().size());
            assertTrue(updated.getUnitsDeployment().contains("Org-11"));
            assertEquals(2, updated.getResultValuesGroupKeys().size());
            assertTrue(updated.getResultValuesGroupKeys().contains("Val-Group-33"));
            assertTrue(!updated.getResultValuesGroupKeys().contains("Val-Group-2"));
            assertTrue(!updated.getResultValuesGroupKeys().contains("Val-Group-3"));
            
            assertEquals(2, updated.getLuiCodes().size());
            assertEquals(4, updated.getFees().size());
            assertEquals(3, updated.getRevenues().size());
            assertTrue(!"LUI-Expen-1".equals(updated.getExpenditure().getId()));
            assertEquals(5, updated.getMeetingSchedules().size());
            
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testUpdateLuiLuiRelation() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        LuiLuiRelationInfo info = luiServiceValidation.getLuiLuiRelation("LUILUIREL-2", callContext);
        assertNotNull(info);
        info.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_INACTIVE_STATE_KEY);
        luiServiceValidation.updateLuiLuiRelation(info.getId(), info, callContext);
        assertEquals(info.getStateKey(), LuiServiceConstants.LUI_LUI_RELATION_INACTIVE_STATE_KEY);
    }

    @Test
    public void testGetLuiLuiRelation() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            LuiLuiRelationInfo obj = luiServiceValidation.getLuiLuiRelation("LUILUIREL-1", callContext);
            assertNotNull(obj);
            assertEquals("Lui-1", obj.getLuiId());
            assertEquals("Lui-2", obj.getRelatedLuiId());

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testGetLuiLuiRelationsByLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        try {
            try {
                luiServiceValidation.getLuiLuiRelationsByLui("Lui-Lui-Blah", callContext);
            } catch (DoesNotExistException enee) {}

            List<LuiLuiRelationInfo> objs = luiServiceValidation.getLuiLuiRelationsByLui("Lui-1", callContext);
            assertNotNull(objs);
            assertEquals(1, objs.size());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testCreateLuiLuiRelation() throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo info = new LuiInfo();
        info.setName("Test lui-Lui relation");
        info.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        info.setTypeKey(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
        info.setEffectiveDate(Calendar.getInstance().getTime());
        info.setMaximumEnrollment(25);
        info.setMinimumEnrollment(10);
        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted("<p>Test lui-Lui relation description</p>");
        rtInfo.setPlain("Test lui-Lui relation description");
        info.setDescr(rtInfo);

        LuiInfo newLui = luiServiceValidation.createLui("testCluId", "testAtpId1", info, callContext);
        LuiLuiRelationInfo created = null;
        try {
            LuiLuiRelationInfo rel = new LuiLuiRelationInfo();
            rel.setLuiId("Lui-1");
            rel.setRelatedLuiId(newLui.getId());
            rel.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY);
            rel.setTypeKey(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY);
            rel.setEffectiveDate(Calendar.getInstance().getTime());
            rtInfo = new RichTextInfo();
            rtInfo.setFormatted("<p>Test lui-Lui relation description</p>");
            rtInfo.setPlain("Test lui-Lui relation description");
            rel.setDescr(rtInfo);
            created = luiServiceValidation.createLuiLuiRelation("Lui-1", newLui.getId(), LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, rel, callContext);

            assertNotNull(created);
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, created.getStateKey());
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, created.getTypeKey());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }

        try {
            LuiLuiRelationInfo retrieved = luiServiceValidation.getLuiLuiRelation(created.getId(), callContext);
            assertNotNull(retrieved);
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, retrieved.getStateKey());
            assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, retrieved.getTypeKey());

            List<LuiLuiRelationInfo> objs = luiServiceValidation.getLuiLuiRelationsByLui("Lui-1", callContext);
            assertNotNull(objs);
            assertEquals(2, objs.size());
            for (LuiLuiRelationInfo obj : objs) {
                assertTrue(obj.getRelatedLuiId().equals("Lui-2") || obj.getRelatedLuiId().equals(newLui.getId()));
            }

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
    public void testDeleteLui() throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo info = luiServiceValidation.getLui("Lui-3", callContext);
        assertNotNull(info);

        List<LuiLuiRelationInfo> objs = luiServiceValidation.getLuiLuiRelationsByLui("Lui-3", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());

        try {
            try {
                luiServiceValidation.deleteLui("Lui-3-blah", callContext);
            } catch (DoesNotExistException ee) {}

            StatusInfo status = luiServiceValidation.deleteLui("Lui-3", callContext);
            assertTrue(status.getIsSuccess());

            try {
                luiServiceValidation.getLuiLuiRelationsByLui("Lui-3", callContext);
            } catch (DoesNotExistException ee) {}

            try {
                luiServiceValidation.getLui("Lui-3", callContext);
            } catch (DoesNotExistException ee) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
