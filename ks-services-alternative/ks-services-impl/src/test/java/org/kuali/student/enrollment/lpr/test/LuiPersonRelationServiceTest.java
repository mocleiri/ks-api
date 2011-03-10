package org.kuali.student.enrollment.lpr.test;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.enrollment.lpr.dto.ContextInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.test.utilities.DataLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.kuali.student.enrollment.lpr.test.Constants.LUI_ID1;

/**
 * @author Igor
 */
public class LuiPersonRelationServiceTest {

    private LuiPersonRelationService personRelationService;

    private DataLoader dataLoader;

    public LuiPersonRelationServiceTest() {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"test.xml"});
        personRelationService = (LuiPersonRelationService) appContext.getBean("luiPersonRelationService");
        dataLoader = (DataLoader) appContext.getBean("dataLoader");
    }

    @Before
    public void setUp() throws Exception {
        dataLoader.load();
    }

    @Test
    public void testFindLuiPersonRelationsForLui() throws MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<LuiPersonRelationInfo> personRelationInfos = personRelationService.findLuiPersonRelationsForLui(LUI_ID1, new ContextInfo());
        assertNotNull(personRelationInfos);
        assertEquals(personRelationInfos.size(), 1);

        LuiPersonRelationInfo personRelationInfo = personRelationInfos.get(0);
        assertNotNull(personRelationInfo.getAttributes());
        assertEquals(personRelationInfo.getAttributes().size(), 2);
    }
}
