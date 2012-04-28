package org.kuali.student.enrollment.class1.lui.service.impl;

import org.junit.Ignore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;

import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional

public class TestLuiServiceImpl {

    @Resource(name = "luiService")
    private LuiService luiService;

    @Resource(name = "luiDao")
    private LuiDao luiDao;

    public LuiLuiRelationDao getLuiLuiRelationDao() {
        return luiLuiRelationDao;
    }

    public void setLuiLuiRelationDao(LuiLuiRelationDao luiLuiRelationDao) {
        this.luiLuiRelationDao = luiLuiRelationDao;
    }

    @Resource(name = "luiLuiRelationDao")
    private LuiLuiRelationDao luiLuiRelationDao;

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public LuiDao getLuiDao() {
        return luiDao;
    }

    public void setLuiDao(LuiDao luiDao) {
        this.luiDao = luiDao;
    }

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new LuiTestDataLoader(luiDao, luiLuiRelationDao).loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testLuiServiceSetup() {
        assertNotNull(luiService);
    }

    @Test
    public void testGetLui() throws Exception {

        try {
            luiService.getLui("Lui-blah", callContext);
            fail("should not exist");
        } catch (DoesNotExistException ex) {
            // expected
        }

        LuiInfo obj = luiService.getLui("Lui-1", callContext);
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

        assertEquals("cluId1", obj.getCluId());
        assertEquals("atpId1", obj.getAtpId());
//        assertEquals(1, obj.getLuiCodes().size());
//        assertEquals("LUI-lu-cd-1", obj.getLuiCodes().get(0).getId());

        assertNotNull(obj.getMaximumEnrollment());
        assertEquals(200, obj.getMaximumEnrollment().intValue());
        assertNotNull(obj.getMinimumEnrollment());
        assertEquals(50, obj.getMinimumEnrollment().intValue());
        assertEquals("ref.url", obj.getReferenceURL());

//        assertNotNull(obj.getCluCluRelationIds());
//        assertEquals(2, obj.getCluCluRelationIds().size());
//        assertTrue(obj.getCluCluRelationIds().contains("CluClu-2"));
//        assertNotNull(obj.getUnitsContentOwnerOrgIds());
//        assertEquals(1, obj.getUnitsContentOwnerOrgIds().size());
//        assertTrue(obj.getUnitsContentOwnerOrgIds().contains("Org-2"));
//        assertNotNull(obj.getUnitsDeploymentOrgIds());
//        assertEquals(1, obj.getUnitsDeploymentOrgIds().size());
//        assertTrue(obj.getUnitsDeploymentOrgIds().contains("Org-1"));
//        assertNotNull(obj.getResultValuesGroupKeys());
//        assertEquals(3, obj.getResultValuesGroupKeys().size());
//        assertTrue(obj.getResultValuesGroupKeys().contains("Val-Group-3"));
//
//        assertNotNull(obj.getFeeIds());
//        assertEquals(3, obj.getFeeIds().size());
//        assertNotNull(obj.getRevenues());
//        assertEquals(2, obj.getRevenues().size());
//        assertNotNull(obj.getExpenditure());
//        assertEquals("LUI-Expen-1", obj.getExpenditure().getId());
    }

    @Test
//    @Ignore
    public void testGetLuiIdsByRelation() throws Exception {
        try {
            List<String> luiIds = luiService.getLuiIdsByRelation("Lui-2", "kuali.lui.lui.relation.associated", callContext);
            assertNotNull(luiIds);
            assertEquals(2, luiIds.size());
            assertEquals("Lui-1", luiIds.get(0));
            assertEquals("Lui-5", luiIds.get(1));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
//    @Ignore
    public void testGetLuisByRelation() throws Exception {
        try {
            List<LuiInfo> luis = luiService.getLuisByRelation("Lui-2", "kuali.lui.lui.relation.associated", callContext);
            assertNotNull(luis);
            assertEquals(2, luis.size());
            assertEquals("Lui-1", luis.get(0).getId());
            assertEquals("Lui-5", luis.get(1).getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateLui() throws Exception {
        LuiInfo orig = new LuiInfo();
        orig.setName("Test lui one");
        orig.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        orig.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted("<p>Test lui one description</p>");
        rtInfo.setPlain("Test lui one description");
        orig.setDescr(rtInfo);
        orig.setEffectiveDate(Calendar.getInstance().getTime());
        orig.setMaximumEnrollment(25);
        orig.setMinimumEnrollment(10);
        orig.setReferenceURL("ref.create.url");

        LuiIdentifierInfo identifier = new LuiIdentifierInfo();
        identifier.setShortName("identifier.shortname");
        identifier.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        identifier.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        orig.setOfficialIdentifier(identifier);

        LuiIdentifierInfo identifier2 = new LuiIdentifierInfo();
        identifier2.setShortName("alternate.identifier.shortname");
        identifier2.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY);
        identifier2.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        orig.getAlternateIdentifiers().add(identifier2);

        orig.setCluId("testCluId");
        orig.setAtpId("testAtpId1");

//        List<String> cluCluRelationIds = new ArrayList<String>();
//        cluCluRelationIds.add("CluClu-2");
//        info.setCluCluRelationIds(cluCluRelationIds);
//
//        List<String> unitsContentOwner = new ArrayList<String>();
//        unitsContentOwner.add("Org-2");
//        info.setUnitsContentOwner(unitsContentOwner);
//
//        List<String> unitsDeployment = new ArrayList<String>();
//        unitsDeployment.add("Org-1");
//        info.setUnitsDeployment(unitsDeployment);
//
//        List<String> resultValueGroupKeys = new ArrayList<String>();
//        resultValueGroupKeys.add("Val-Group-3");
//        info.setResultValuesGroupKeys(resultValueGroupKeys);
//
//        LuCodeInfo luCode = new LuCodeInfo();
//        RichTextInfo rt = new RichTextInfo();
//        rt.setPlain("fee.plain");
//        rt.setFormatted("fee.formatted");
//        luCode.setDescr(rt);
//        info.getLuiCodes().add(luCode);
//
//        FeeInfo fee = new FeeInfo();
//        RichTextInfo rtf = new RichTextInfo();
//        rtf.setPlain("fee.plain");
//        rtf.setFormatted("fee.formatted");
//        fee.setDescr(rtf);
//        info.getFeeIds().add(fee);
//
//        RevenueInfo revenue = new RevenueInfo();
//        info.getRevenues().add(revenue);
//
//        ExpenditureInfo expenditure = new ExpenditureInfo();
//        info.setExpenditure(expenditure);

        LuiInfo info = luiService.createLui(orig.getCluId(), orig.getAtpId(), orig.getTypeKey(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());

        assertNotNull(info.getOfficialIdentifier());
        assertEquals(orig.getOfficialIdentifier().getShortName(), info.getOfficialIdentifier().getShortName());
        assertEquals(orig.getAlternateIdentifiers().size(), info.getAlternateIdentifiers().size());
        assertEquals(orig.getAlternateIdentifiers().get(0).getShortName(), info.getAlternateIdentifiers().get(0).getShortName());

        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getCluId(), info.getCluId());
        assertEquals(orig.getAtpId(), info.getAtpId());
        assertEquals(orig.getReferenceURL(), info.getReferenceURL());

//        assertTrue(info.getCluCluRelationIds().contains("CluClu-2"));
//        assertTrue(info.getUnitsContentOwnerOrgIds().contains("Org-2"));
//        assertTrue(info.getUnitsDeploymentOrgIds().contains("Org-1"));
//        assertTrue(info.getResultValuesGroupKeys().contains("Val-Group-3"));
//
//        assertEquals(1, info.getLuiCodes().size());
//        assertEquals(1, info.getFeeIds().size());
//        assertEquals(1, info.getRevenues().size());
//        assertNotNull(info.getExpenditure().getId());
        // test fetch after the create
        info = luiService.getLui(info.getId(), callContext);
        assertNotNull(info);
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getStateKey(), info.getStateKey());
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());

        assertNotNull(info.getOfficialIdentifier());
        assertEquals(orig.getOfficialIdentifier().getShortName(), info.getOfficialIdentifier().getShortName());
        assertEquals(orig.getAlternateIdentifiers().size(), info.getAlternateIdentifiers().size());
        assertEquals(orig.getAlternateIdentifiers().get(0).getShortName(), info.getAlternateIdentifiers().get(0).getShortName());

        assertEquals(orig.getMaximumEnrollment(), info.getMaximumEnrollment());
        assertEquals(orig.getMinimumEnrollment(), info.getMinimumEnrollment());
        assertEquals(orig.getCluId(), info.getCluId());
        assertEquals(orig.getAtpId(), info.getAtpId());
        assertEquals(orig.getReferenceURL(), info.getReferenceURL());
    }

    @Test
    public void testUpdateLui() throws Exception {
        LuiInfo info = luiService.getLui("Lui-1", callContext);
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
        //        modified.getFeeIds().add(fee);

        //        RevenueInfo revenue = new RevenueInfo();
        //        modified.getRevenues().add(revenue);

        //        ExpenditureInfo expenditure = new ExpenditureInfo();
        // modified.setExpenditure(expenditure);

        LuiInfo updated = luiService.updateLui("Lui-1", modified, callContext);

        assertNotNull(updated);
        assertEquals(LuiServiceConstants.LUI_APROVED_STATE_KEY, updated.getStateKey());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, updated.getTypeKey());
        assertEquals(Integer.valueOf(25), updated.getMaximumEnrollment());
        assertEquals(Integer.valueOf(10), updated.getMinimumEnrollment());
        assertEquals("ref.update.url", updated.getReferenceURL());
        assertNotNull(updated.getEffectiveDate());

        assertNotNull(updated.getOfficialIdentifier());
        assertEquals("identifier.shortname", updated.getOfficialIdentifier().getShortName());
        assertEquals(1, updated.getAlternateIdentifiers().size());
        assertEquals("alternate.identifier.shortname", updated.getAlternateIdentifiers().get(0).getShortName());
//
//        assertTrue(updated.getCluCluRelationIds().contains("CluClu-1"));
//        assertTrue(updated.getCluCluRelationIds().contains("CluClu-22"));
//        assertEquals(2, updated.getResultValuesGroupKeys().size());
//        assertTrue(updated.getUnitsContentOwnerOrgIds().contains("Org-22"));
//        assertEquals(2, updated.getResultValuesGroupKeys().size());
//        assertTrue(updated.getUnitsDeploymentOrgIds().contains("Org-11"));
//        assertEquals(2, updated.getResultValuesGroupKeys().size());
//        assertTrue(updated.getResultValuesGroupKeys().contains("Val-Group-33"));
//        assertTrue(!updated.getResultValuesGroupKeys().contains("Val-Group-2"));
//        assertTrue(!updated.getResultValuesGroupKeys().contains("Val-Group-3"));

//        assertEquals(2, updated.getLuiCodes().size());
        //        assertEquals(4, updated.getFeeIds().size());
        //        assertEquals(3, updated.getRevenues().size());
        //        assertTrue(!"LUI-Expen-1".equals(updated.getExpenditure().getId()));

    }

    @Test
    public void testUpdateLuiLuiRelation() throws Exception{
        LuiLuiRelationInfo info = luiService.getLuiLuiRelation("LUILUIREL-2", callContext);
        assertNotNull(info);
        info.setStateKey(LuiServiceConstants.LUI_LUI_RELATION_INACTIVE_STATE_KEY);
        luiService.updateLuiLuiRelation(info.getId(), info, callContext);
        assertEquals(info.getStateKey(), LuiServiceConstants.LUI_LUI_RELATION_INACTIVE_STATE_KEY);
    }

    @Test
    public void testGetLuiLuiRelation() throws Exception {
        try {
            LuiLuiRelationInfo obj = luiService.getLuiLuiRelation("LUILUIREL-1", callContext);
            assertNotNull(obj);
            assertEquals("Lui-1", obj.getLuiId());
            assertEquals("Lui-2", obj.getRelatedLuiId());

        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    @Test
//    @Ignore
    public void testGetLuiLuiRelationsByLui() throws Exception {

        luiService.getLuiLuiRelationsByLui("Lui-Lui-Blah", callContext);
        List<LuiLuiRelationInfo> objs = luiService.getLuiLuiRelationsByLui("Lui-1", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());
    }

    @Test
    public void testCreateLuiLuiRelation() throws Exception{
        LuiInfo info = new LuiInfo();
        info.setCluId("testCluId");
        info.setAtpId("testAtpId1");
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

        LuiInfo newLui = luiService.createLui(info.getCluId(), info.getAtpId(), info.getTypeKey(), info, callContext);
        LuiLuiRelationInfo created = null;
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
        created = luiService.createLuiLuiRelation("Lui-1", newLui.getId(), LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, rel, callContext);

        assertNotNull(created);
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, created.getStateKey());
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, created.getTypeKey());



        LuiLuiRelationInfo retrieved = luiService.getLuiLuiRelation(created.getId(), callContext);
        assertNotNull(retrieved);
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY, retrieved.getStateKey());
        assertEquals(LuiServiceConstants.LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY, retrieved.getTypeKey());

        List<LuiLuiRelationInfo> objs = luiService.getLuiLuiRelationsByLui("Lui-1", callContext);
        assertNotNull(objs);
        assertEquals(2, objs.size());
        for (LuiLuiRelationInfo obj : objs) {
            assertTrue(obj.getRelatedLuiId().equals("Lui-2") || obj.getRelatedLuiId().equals(newLui.getId()));
        }
    }

    @Test
    public void testDeleteLui()
            throws Exception{
        LuiInfo info = luiService.getLui("Lui-3", callContext);
        assertNotNull(info);

        List<LuiLuiRelationInfo> objs = luiService.getLuiLuiRelationsByLui("Lui-3", callContext);
        assertNotNull(objs);
        assertEquals(1, objs.size());

        try {
            luiService.deleteLui("Lui-3-blah", callContext);
        } catch (DoesNotExistException ee) {
        }

        StatusInfo status = luiService.deleteLui("Lui-3", callContext);
        assertTrue(status.getIsSuccess());

        luiService.getLuiLuiRelationsByLui("Lui-3", callContext);

        try {
            luiService.getLui("Lui-3", callContext);
        } catch (DoesNotExistException ee) {
        }
    }

    @Test
    public void testGetLuisByIds() throws Exception {
       List<String> luiIds = new ArrayList<String>();
        luiIds.add("Lui-1");
        luiIds.add("Lui-2");
        luiIds.add("Lui-3");
        List<LuiInfo> luis =  luiService.getLuisByIds(luiIds, callContext);

        assertNotNull(luis);
        assertTrue(luis.size() == 3);

        try{
            luiIds.add("Lui-3b");
            luis =  luiService.getLuisByIds(luiIds, callContext);
        }catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
        }

    }
    @Test
    public void testGetLuiIdsByType() throws Exception {

        List<String> luis =  luiService.getLuiIdsByType("kuali.lui.type.course.offering", callContext);

        assertNotNull(luis);
        assertTrue(luis.size() == 2);

    }

}
