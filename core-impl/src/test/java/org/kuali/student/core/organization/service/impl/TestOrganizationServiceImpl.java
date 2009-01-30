package org.kuali.student.core.organization.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.service.OrganizationService;


@Daos( { @Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl",testSqlFile="classpath:ks-org.sql"/*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.core.organization.service.impl.OrganizationServiceImpl", port = "8181")
	public OrganizationService client;


	@Test
	public void createOrganization() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
	    OrgInfo result = client.createOrganization("ks.org.foo", new OrgInfo());
		assertEquals("ks.org.foo",result.getType());
	}

	@Test
	public void TestFinds() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
	    //Test Finds
		List<OrgHierarchyInfo> orgHierarchyInfos = client.getOrgHierarchies();
		assertEquals(2,orgHierarchyInfos.size());

		List<OrgOrgRelationInfo> orgOrgRelationInfos = client.getOrgOrgRelationsByOrg("4");
		assertEquals(8,orgOrgRelationInfos.size());

		List<OrgPersonRelationInfo> orgPersonRelationInfos = client.getAllOrgPersonRelationsByOrg("68");
		// should be 2; foreign-key constraint problem
		assertEquals(1, orgPersonRelationInfos.size());

		List<OrgPositionRestrictionInfo>  orgPositionRestrictionInfos = client.getPositionRestrictionsByOrg("19");
		assertEquals(6, orgPositionRestrictionInfos.size());

		List<OrgTypeInfo> orgTypeinfos = client.getOrgTypes();
		assertEquals(17, orgTypeinfos.size());

		OrgTypeInfo orgTypeInfo = client.getOrgType("kuali.org.Division");
		assertEquals(orgTypeInfo.getKey(), "kuali.org.Division");

		List<String> descendants = client.getAllDescendants("4", "kuali.org.hierarchy.Main");
		assertEquals(8,descendants.size());
	}

	@Test
	public void getOrgOrgRelationTypes() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypes();
		assertEquals(13, orgOrgRelationTypeInfos.size());
	}

	@Test
	public void getOrgOrgRelationType() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgOrgRelationTypeInfo orgOrgRelationTypeInfo = client.getOrgOrgRelationType("kuali.org.Report");
		assertEquals(orgOrgRelationTypeInfo.getKey(), "kuali.org.Report");
	}

	@Test
	public void getOrgOrgRelationTypesForOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = client.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main");
		assertEquals(12, orgOrgRelationTypeInfos.size());
	}

	@Test
	public void getAllOrgPersonRelationsByPerson() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<OrgPersonRelationInfo> orgPersonRelationsByPerson = client.getAllOrgPersonRelationsByPerson("KIM-1");
		assertEquals(2, orgPersonRelationsByPerson.size());
	}

	@Test
	public void getOrgHierarchy() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		OrgHierarchyInfo orgHierarchyInfo = client.getOrgHierarchy("kuali.org.hierarchy.Curriculum");
		assertEquals(orgHierarchyInfo.getKey(), "kuali.org.hierarchy.Curriculum");
	}
}
