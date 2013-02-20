package org.kuali.student.krms.naturallanguage.config.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;
import org.kuali.student.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.krms.naturallanguage.config.context.util.NLCluSet;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Daos( { @Dao(value = "org.kuali.student.r2.lum.lu.dao.impl.LuDaoImpl", testSqlFile = "classpath:ks-lu.sql") })
//@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
@Ignore
public class GPAContextImplTest extends AbstractServiceTest {

    //@Client(value = "org.kuali.student.r2.lum.lu.service.impl.CluServiceImpl", additionalContextFile = "classpath:clu-additional-context.xml")
    private GpaContextImpl gpaContext = new GpaContextImpl();

	private TermDefinitionContract term;
	private TermDefinitionContract term2;
	
	
	private void setupTerm1() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null, TermParameterTypes.GPA_KEY.getId(),null,null,0L));
        term = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	private void setupTerm2() {
        List<TermParameterDefinitionContract> parameterList = new ArrayList<TermParameterDefinitionContract>();
        parameterList.add(KRMSDataGenerator.createTermParameterDefinition(null,TermParameterTypes.GPA_KEY.getId(),null,null,0L));
		term2 = KRMSDataGenerator.createTermDefinition(null,null,parameterList,null,0L);
	}

	@Before
	public void beforeMethod() {
		//cluContext.setCluService(cluService);
		setupTerm1();
		setupTerm2();
	}

	@Test
    public void testCreateContextMap_Gpa() throws OperationFailedException {
		Map<String, Object> contextMap = gpaContext.createContextMap(term, ContextUtils.getContextInfo());
		GPAInfo gpa = (GPAInfo) contextMap.get(GpaContextImpl.GPA_TOKEN);

		Assert.assertNotNull(contextMap);
		Assert.assertEquals("GPA-1", gpa.getId());

//		Assert.assertEquals("kuali.lu.type.CreditCourse", clu.getTypeKey());
//		Assert.assertEquals("Chem 123", clu.getOfficialIdentifier().getShortName());
//		Assert.assertEquals("Chemistry 123", clu.getOfficialIdentifier().getLongName());
	}
	
	@Test
    public void testCreateContextMap_NullTokenValues() throws OperationFailedException {
        Map<String, Object> contextMap = gpaContext.createContextMap(term2, ContextUtils.getContextInfo());
        GPAInfo gpa = (GPAInfo) contextMap.get(GpaContextImpl.GPA_TOKEN);

        Assert.assertNotNull(contextMap);
        Assert.assertEquals(null, gpa.getId());

	}

}
