/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.enrollment.class2.courseoffering.service.applayer.AutogenRegistrationGroupAppLayer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
 * A test case for the autogen rg app layer helper.
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-autogen-rg-test-context.xml"})
public class TestAutogenRegistrationGroupAppLayerImpl {
    private static final Logger log = KSLog4JConfigurer
            .getLogger(TestAutogenRegistrationGroupAppLayerImpl.class);

    @Resource (name="CourseOfferingService")
    protected CourseOfferingService coService;

    @Resource
    protected CourseService courseService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource(name = "LrcService")
    protected LRCService lrcService;

    
    @Resource
    private AutogenRegistrationGroupAppLayer appLayer;

    private ContextInfo contextInfo;
    
    /**
     * 
     */
    public TestAutogenRegistrationGroupAppLayerImpl() {
    }
    
    @Before
    public void beforeTest() throws Exception {
        dataLoader.beforeTest();
        
        contextInfo = new ContextInfo();
    
        contextInfo.setCurrentDate(new Date());
        
        contextInfo.setPrincipalId("test");
        contextInfo.setAuthenticatedPrincipalId("test");
    }
    
    @After
    public void afterTest() throws Exception {
        dataLoader.afterTest();
    }


    /**
     * This is User Story 5
     */
    @Test
    public void testDeleteActivityOffering()
            throws PermissionDeniedException, MissingParameterException,
                   InvalidParameterException, OperationFailedException, DoesNotExistException {
        // This FO has only 2 AOs in it
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(1, clusters.size());
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, aoInfos.size());
        // Get list of AO ids
        List<String> aoIdsInCluster = new ArrayList<String>();
        aoIdsInCluster.add(aoInfos.get(0).getId());
        aoIdsInCluster.add(aoInfos.get(1).getId());
        List<RegistrationGroupInfo> rgInfos = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(2, rgInfos.size());
        for (RegistrationGroupInfo rg: rgInfos) {
            for (String aoId : rg.getActivityOfferingIds()) {
                assertTrue(aoIdsInCluster.contains(aoId));
            }
        }
        // Now delete the AO
        String aoIdFirst = aoInfos.get(0).getId();
        String aoIdSecond = aoInfos.get(1).getId();
        appLayer.deleteActivityOfferingCascaded(aoIdFirst, aocId, contextInfo);
        List<ActivityOfferingClusterInfo> retrieved =
                coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        // Fetch the AOs again--should only be 1
        aoInfos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(1, aoInfos.size()); // Should only have 1 AO
        // And one RG
        rgInfos = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(1, rgInfos.size());
        assertEquals(1, rgInfos.get(0).getActivityOfferingIds().size());
        assertEquals(aoIdSecond, rgInfos.get(0).getActivityOfferingIds().get(0)); // Id is one that is deleted
    }

    /**
     * This is User Story 7: As a user, I need the system to automatically delete all AOs when I delete an AOC
     * so I don’t have to delete all the AOs first
     */
    @Test
    public void testDeleteActivityOfferingCascaded()
            throws PermissionDeniedException, MissingParameterException,
            InvalidParameterException, OperationFailedException, DoesNotExistException, DependentObjectsExistException {
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(1, clusters.size());
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, activities.size());
        List<ActivityOfferingInfo> activitiesByFo = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        assertEquals(2, activitiesByFo.size());
        // Check the RGs by AOC
        List<RegistrationGroupInfo> rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(2, rgsByAoc.size());
        List<RegistrationGroupInfo> rgsByFo = coService.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(2, rgsByFo.size());
        // Now zap the AOC
        appLayer.deleteActivityOfferingCluster(aocId, contextInfo);
        try {
            coService.getActivityOfferingCluster(aocId, contextInfo);
            assert(false); // Shouldn't get here
        } catch (DoesNotExistException e) {
            // Should go here
        }
        List<ActivityOfferingInfo> activitiesByFo2 = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        assertEquals(0, activitiesByFo2.size());
        List<ActivityOfferingClusterInfo> clusters2 = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(0, clusters2.size());
    }

    /**
     * This is User Story 7: As a user, I need the system to automatically delete all AOs when I delete an AOC
     * so I don’t have to delete all the AOs first
     */
    @Test
    public void testMoveActivityOffering() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        String foId = "CO-2:LEC-ONLY";
        FormatOfferingInfo foInfo = coService.getFormatOffering(foId, contextInfo);
        List<ActivityOfferingClusterInfo> clusters = coService.getActivityOfferingClustersByFormatOffering(foId, contextInfo);
        assertEquals(1, clusters.size());
        String aocId = clusters.get(0).getId();
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByCluster(aocId, contextInfo);
        assertEquals(2, activities.size());
        List<ActivityOfferingInfo> activitiesByFo = coService.getActivityOfferingsByFormatOffering(foId, contextInfo);
        assertEquals(2, activitiesByFo.size());
        // Check the RGs by AOC
        List<RegistrationGroupInfo> rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(2, rgsByAoc.size());
        List<RegistrationGroupInfo> rgsByFo = coService.getRegistrationGroupsByFormatOffering(foId, contextInfo);
        assertEquals(2, rgsByFo.size());
        // Pick an activity to move
        String aoIdSecond = activities.get(1).getId();
        // Store original aoId
        String aoIdFirst = activities.get(0).getId();

        // Now create a new AOC
        ActivityOfferingClusterInfo aocSecond = new ActivityOfferingClusterInfo();
        aocSecond.setFormatOfferingId(foId);
        aocSecond.setPrivateName("Second");
        aocSecond.setName("Second");
        aocSecond.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        aocSecond.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        String aocSecondTypeKey = aocSecond.getTypeKey();
        ActivityOfferingClusterInfo aocSecondCreated =
                coService.createActivityOfferingCluster(foId, aocSecondTypeKey, aocSecond, contextInfo);
        // And move the AO from original AOC to this newly created AOC
        List<BulkStatusInfo> bulkStatuses =
                appLayer.moveActivityOffering(aoIdSecond, aocId, aocSecondCreated.getId(), contextInfo);
        assertEquals(1, bulkStatuses.size());
        String newRgId = bulkStatuses.get(0).getId();
        RegistrationGroupInfo newRg = coService.getRegistrationGroup(newRgId, contextInfo);
        assertEquals(1, newRg.getActivityOfferingIds().size());
        assertEquals(aoIdSecond, newRg.getActivityOfferingIds().get(0));
        // Check RGs in orig AOC
        rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);
        assertEquals(1, rgsByAoc.size());
        assertEquals(1, rgsByAoc.get(0).getActivityOfferingIds().size());
        assertEquals(aoIdFirst, rgsByAoc.get(0).getActivityOfferingIds().get(0));
        // And RGs in the new AOC
        rgsByAoc = coService.getRegistrationGroupsByActivityOfferingCluster(aocSecondCreated.getId(), contextInfo);
        assertEquals(1, rgsByAoc.size());
        assertEquals(1, rgsByAoc.get(0).getActivityOfferingIds().size());
        assertEquals(aoIdSecond, rgsByAoc.get(0).getActivityOfferingIds().get(0));
    }

    @Test
    public void testUserStoryEight () throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        
        // create a default cluster
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByFormatOffering(CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);

        ActivityOfferingClusterInfo defaultAoc = CourseOfferingServiceTestDataUtils.createActivityOfferingCluster(CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, "Default Cluster", activities);

        defaultAoc = coService.createActivityOfferingCluster(CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, defaultAoc, contextInfo);

        // Create a Registration Group
        List<BulkStatusInfo> generatedStatus = coService.generateRegistrationGroupsForCluster(defaultAoc.getId(), contextInfo);
        
        Assert.assertEquals(6,  generatedStatus.size());
        
        
        Integer aocSeatCount = appLayer.getSeatCountByActivityOfferingCluster(defaultAoc.getId(), contextInfo);
        
        Assert.assertNotNull(aocSeatCount);
        Assert.assertEquals(150, aocSeatCount.intValue());

        // need to test a case where the cap is applied.
        
        // 100 seat lecture, 200 seat lecture, 50 seat lab, 75 seat lab.
        // 100, 50
        // 100, 75 100
        // 200, 50
        // 200, 75 125 = 225
        
        /*
         * max (ao.maxEnrollment) for each activity type.  200 + 75 = 275
         * 
         * sum (rg seats) = 250 
         * 
         * It seems like we need some kind of ao aware test
         * 
         * 1
         */
        
        // need to test 2 reg groups
        
        // need to test 4 reg groups
        
        // need to test 16 reg groups.
        
        
        
    }
}
