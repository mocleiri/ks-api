/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.r2.core.scheduling.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.scheduling.dao.ScheduleRequestDao;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-impl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestScheduleRequest {
    @Resource(name = "schedulingServiceImpl")
    private SchedulingService schedulingService;

    @Resource(name = "scheduleRequestDao" )
    private ScheduleRequestDao scheduleRequestDao;

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public ScheduleRequestDao getScheduleRequestDao() {
        return scheduleRequestDao;
    }

    public void setScheduleRequestDao(ScheduleRequestDao scheduleRequestDao) {
        this.scheduleRequestDao = scheduleRequestDao;
    }
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            new ScheduleRequestTestDataLoader(scheduleRequestDao).loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @Test
    public void testSchedulingServiceSetup() {
        assertNotNull(schedulingService);
    }

}
