/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class1.lpr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.enrollment.class1.lpr.service.decorators.LuiPersonRelationServiceValidationDecorator;
import org.kuali.student.enrollment.lpr.dto.LprRosterInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author sambit
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lpr-test-context.xml"})
public class TestLuiPersonRelationServiceImpl extends AbstractServiceTest {

    private LuiPersonRelationService lprService;

    public void setLprService(LuiPersonRelationServiceValidationDecorator lprService) {
        this.lprService = lprService;
    }

    private static String principalId = "123";
    private static String LPRID1 = "testLprId1";
    private static String LUIID1 = "testLuiId1";
    private static String PERSONID1 = "testPersonId1";
    private static String LUIID2 = "testLuiId2";
    private static String PERSONID2 = "testPersonId2";

    // LPR Roster Constants
    private final static String ATP_DURATION_KEY = "semester1";
    private final static String LPR_TRANSACTION_NAME = "NEW TRANSACTION TEST";
    private final static int TIME_QTY = 1;
    private final static String LPR_ROSTER_NAME = "LPR_ROSTER_TEST";
    private final static String LPR_ROSTER_DESC = "LPR ROSTER DESC";
    private final static String ATTRIBUTE_KEY = "Key";
    private final static String ATTRIBUTE_VALUE = "Value";
    private final static String STATE_KEY = "kuali.lpr.state.registered";
    private final static List<String> RESULT_OPTION_IDS = new ArrayList<String>();
    private final static String TYPE_KEY = "kuali.lpr.type.registrant";
    private final static String LUI_ID = "Lui-1";
    private final static int MAX_CPTY = 10;

    public ContextInfo callContext = ContextInfo.newInstance();

    private LprTransactionInfo createLprTransaction() {
        LprTransactionInfo lprTransactionInfo = new LprTransactionInfo();
        lprTransactionInfo.setName(LPR_TRANSACTION_NAME);
        lprTransactionInfo.setRequestingPersonId(PERSONID1);
        lprTransactionInfo.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);
        lprTransactionInfo.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        LprTransactionItemInfo lprTransactionItem = new LprTransactionItemInfo();
        lprTransactionItem.setExistingLuiId(LUIID1);
        lprTransactionItem.setNewLuiId(LUIID2);
        lprTransactionItem.setStateKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        lprTransactionItem.setTypeKey(LuiPersonRelationServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);

        lprTransactionItem.setResultOptionIds(RESULT_OPTION_IDS);
        List<LprTransactionItemInfo> lprTransItemList = new ArrayList<LprTransactionItemInfo>();
        lprTransItemList.add(lprTransactionItem);
        lprTransactionInfo.setLprTransactionItems(lprTransItemList);

        return lprTransactionInfo;
    }

    private LprRosterInfo createLprRosterInfo() {

        LprRosterInfo lprRosterInfo = new LprRosterInfo();
        lprRosterInfo.setMaximumCapacity(MAX_CPTY);
        lprRosterInfo.setCheckInRequired(true);

        TimeAmountInfo timeAmountInfo = new TimeAmountInfo();
        timeAmountInfo.setAtpDurationTypeKey(ATP_DURATION_KEY);
        timeAmountInfo.setTimeQuantity(TIME_QTY);
        lprRosterInfo.setCheckInFrequency(timeAmountInfo);

        RichTextInfo desc = new RichTextInfo();
        desc.setPlain(LPR_ROSTER_DESC);
        lprRosterInfo.setDescr(desc);

        lprRosterInfo.setName(LPR_ROSTER_NAME);
        List<String> luiIds = new ArrayList<String>();
        luiIds.add(LUI_ID);
        lprRosterInfo.setAssociatedLuiIds(luiIds);
        lprRosterInfo.setStateKey(STATE_KEY);
        lprRosterInfo.setTypeKey(TYPE_KEY);

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        AttributeInfo attribute = new AttributeInfo();
        attribute.setKey(ATTRIBUTE_KEY);
        attribute.setValue(ATTRIBUTE_VALUE);
        attributes.add(attribute);
        lprRosterInfo.setAttributes(attributes);

        return lprRosterInfo;
    }

    @Autowired
    public void setLuiPersonRelationService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    @Before
    public void setUp() {
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    // @Test
    public void testGetLuiPersonRelation() {
        try {
            LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
            assertNotNull(lpr);
            assertEquals(LUIID1, lpr.getLuiId());
            assertEquals(PERSONID1, lpr.getPersonId());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    // TODO implement @Test
    public void testCreateLuiPersonRelation() {
        LuiPersonRelationInfo lprInfo = new LuiPersonRelationInfo();
        lprInfo.setLuiId(LUIID2);
        lprInfo.setPersonId(PERSONID2);
        lprInfo.setTypeKey("kuali.lpr.type.registrant");
        lprInfo.setStateKey("kuali.lpr.state.registered");
        lprInfo.setEffectiveDate(new Date());
        lprInfo.setExpirationDate(DateUtils.addYears(new Date(), 20));
        String lprId = null;
        LuiPersonRelationInfo lpr2 = null;
        try {
            lprId = lprService.createLpr(PERSONID2, LUIID2, "kuali.lpr.type.registrant", lprInfo, callContext);
            assertNotNull(lprId);
            lpr2 = lprService.getLpr(lprId, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lpr2);
        assertEquals(LUIID2, lpr2.getLuiId());
        assertEquals(PERSONID2, lpr2.getPersonId());
    }

    // @Test
    public void testGetLuiPersonRelationsForLui() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprInfoList = lprService.getLprsByLui(LUIID1, ContextInfo.newInstance());
        assertNotNull(lprInfoList);
        assertEquals(1, lprInfoList.size());

        LuiPersonRelationInfo personRelationInfo = lprInfoList.get(0);
        assertNotNull(personRelationInfo);
        assertEquals(LUIID1, personRelationInfo.getLuiId());
        assertEquals(PERSONID1, personRelationInfo.getPersonId());
        // TODO - add attributes to ks-lpr.sql:
        // assertEquals(2, personRelationInfo.getAttributes().size());
    }

    // TODO implement @Test
    public void testCreateBulkRelationshipsForPerson() {
        try {
            List<String> createResults = lprService.createBulkRelationshipsForPerson(principalId,
                    new ArrayList<String>(), "", "", new LuiPersonRelationInfo(), callContext);
            assertNotNull(createResults);
            assertEquals(1, createResults.size());
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    // TODO implement @Test
    public void testCreateBulkRelationshipsForPersonExceptions() {
        try {
            lprService.createBulkRelationshipsForPerson("", new ArrayList<String>(), "", "",
                    new LuiPersonRelationInfo(), callContext);
        } catch (Exception ex) {
            // ex.printStackTrace();
            assertTrue(ex instanceof OperationFailedException);
        }
    }

    // @Test
    public void testDeleteLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, AlreadyExistsException, DisabledIdentifierException, ReadOnlyException {
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot delete", lpr);

        try {
            lprService.deleteLpr(LPRID1, callContext);
        } catch (Exception ex) {
            fail("Exception from service call: " + ex.getMessage());
        }

        LuiPersonRelationInfo deletedLpr = lprService.getLpr(LPRID1, callContext);
        assertNull("LPR entity '" + LPRID1 + "' was not deleted", deletedLpr);
        // put it back for later test(s)
        lprService.createLpr(lpr.getPersonId(), lpr.getLuiId(), lpr.getTypeKey(), lpr, callContext);
    }

    // TODO implement @Test
    public void testGetLuiPersonRelations() throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<LuiPersonRelationInfo> lprList = lprService.getLprsByLuiAndPerson(PERSONID1, LUIID1, callContext);
        assertNotNull("Method LuiPersonRelationServiceImpl.getLuiPersonRelations() is not implemented yet", lprList);
        assertEquals(1, lprList.size());
        // add asserts
        fail("Test method not implemented yet");
    }

    // TODO implement @Test
    public void testGetLuiPersonRelationsForPersonAndAtp() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

    // TODO implement @Test
    public void testGetLuiPersonRelationByState() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

    @Ignore
    @Test
    public void testUpdateLuiPersonRelation() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        LuiPersonRelationInfo lpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist; cannot update", lpr);

        final Float initialCommitPercent = lpr.getCommitmentPercent();
        final Float updatedCommitPercent;
        if (initialCommitPercent == null) {
            updatedCommitPercent = .5F;
        } else {
            updatedCommitPercent = initialCommitPercent + .05F;
        }
        lpr.setCommitmentPercent(updatedCommitPercent);

        Date expirationDate = lpr.getExpirationDate();
        lpr.setExpirationDate(new Date());

        LuiPersonRelationInfo updatedLpr = null;
        try {
            updatedLpr = lprService.updateLpr(LPRID1, lpr, callContext);
        } catch (Exception ex) {
            fail("Exception from service call: " + ex.getMessage());
        }

        LuiPersonRelationInfo finalLpr = lprService.getLpr(LPRID1, callContext);
        assertNotNull("LPR entity '" + LPRID1 + "' does not exist after being updated", finalLpr);
        assertNotNull("'commitmentPercent' property does not exist after being updated", finalLpr.getCommitmentPercent());
        assertEquals("'commitmentPercent' property was not updated properly.", updatedCommitPercent, finalLpr.getCommitmentPercent());
        assertNotSame("'expirationDate' property was not updated", expirationDate, finalLpr.getExpirationDate());
    }

    // TODO implement @Test
    public void testValidateLuiPersonRelation() throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        fail("Test method not implemented yet");
    }

    @Test
    public void testCreateLprRoster() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        LprRosterInfo lprRosterInfo = createLprRosterInfo();

        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);
        LprRosterInfo info = lprService.getLprRoster(lprRosterId, callContext);

        assertEquals(info.getMaximumCapacity().intValue(), MAX_CPTY);
        assertTrue(info.getCheckInRequired());
        assertEquals(info.getTypeKey(), TYPE_KEY);
        assertEquals(info.getStateKey(), STATE_KEY);
        assertEquals(info.getDescr().getPlain(), LPR_ROSTER_DESC);
        assertEquals(info.getCheckInFrequency().getAtpDurationTypeKey(), ATP_DURATION_KEY);
        assertEquals(info.getCheckInFrequency().getTimeQuantity().intValue(), TIME_QTY);
        assertEquals(info.getName(), LPR_ROSTER_NAME);
        assertEquals(info.getAssociatedLuiIds().size(), 1);
        assertEquals(info.getAssociatedLuiIds().get(0), LUI_ID);

        /**
         * FIXME: Attributes not working now.
         */
        // assertEquals(info.getAttributes().size(),1);
        // assertEquals(info.getAttributes().get(0).getKey(),"Key");
        // assertEquals(info.getAttributes().get(0).getKey(),"Value");
    }

    @Test
    public void testDeleteLprRoster() throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, AlreadyExistsException, DisabledIdentifierException {

        LprRosterInfo lprRosterInfo = createLprRosterInfo();
        String lprRosterId = lprService.createLprRoster(lprRosterInfo, callContext);
        StatusInfo info = lprService.deleteLprRoster(lprRosterId, callContext);

        assertTrue(info.getIsSuccess());

    }

    @Test
    public void testGetLprIdsByLuiAndPerson() {
        List<String> lprIds = null;
        try {
            lprIds = lprService.getLprIdsByLuiAndPerson("testPersonId1", "testLuiId1", callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lprIds);
        assertEquals(1, lprIds.size());
    }

    @Ignore
    @Test
    public void testCreateLprTransaction() {
        LprTransactionInfo lprTransactionInfo = createLprTransaction();
        try {
            lprTransactionInfo = lprService.createLprTransaction(lprTransactionInfo, callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            assertNotNull(lprService.getLprTransaction(lprTransactionInfo.getId(), callContext));
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Ignore
    @Test
    public void testUpdateLprTransaction() {
        String updateName = "NEW TRANSACTION TEST 1";
        LprTransactionInfo lprTransactionInfo = createLprTransaction();
        try {
            lprTransactionInfo = lprService.createLprTransaction(lprTransactionInfo, callContext);
            lprTransactionInfo = lprService.getLprTransaction(lprTransactionInfo.getId(), callContext);
            lprTransactionInfo.setName(updateName);
            lprTransactionInfo.setStateKey(LuiPersonRelationServiceConstants.ACTIVE_STATE_KEY);
            lprTransactionInfo = lprService.updateLprTransaction(lprTransactionInfo.getId(), lprTransactionInfo,
                    callContext);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertNotNull(lprTransactionInfo);
        assertTrue(lprTransactionInfo.getName().equals(updateName));
    }
}