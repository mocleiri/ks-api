package org.kuali.student.core.organization.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgAttribute;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.core.organization.entity.OrgType;

@PersistenceFileLocation("classpath:META-INF/organization-persistence.xml")
public class TestOrganizationDao extends AbstractTransactionalDaoTest {
	@Dao(value = "org.kuali.student.core.organization.dao.impl.OrganizationDaoImpl", testSqlFile = "classpath:ks-org.sql")
	public OrganizationDao dao;

	@Test
	public void testGetOrgTreeInfo(){
		List<OrgTreeInfo> orgTreeInfos = dao.getOrgTreeInfo("4", "kuali.org.hierarchy.Main");
		assertEquals(8,orgTreeInfos.size());
	}
	
//	@Test
//	@Rollback(false)
//	public void createNestedData(){
//		List<OrgHierarchy> ohs = dao.find(OrgHierarchy.class);
//		for(OrgHierarchy oh:ohs){
//			updateNested(oh.getRootOrg().getId(),oh.getKey(),1,new HashSet<String>());
//		}
//		
//	}
//	
//	private long updateNested(String orgId, String orgHierarchy, long i,
//			HashSet<String> visited) {
//		long curr = i;
//		for(OrgOrgRelation oor:dao.getOrgOrgRelationsByOrg(orgId)){
//			if(oor.getType().getOrgHierarchy().getKey().equals(orgHierarchy)&&!visited.contains(oor.getId())){
//				visited.add(oor.getId());
//				curr++;
//				oor.setLeft(String.valueOf(curr));
//				curr = updateNested(oor.getRelatedOrg().getId(), orgHierarchy, curr,visited)+1;
//				oor.setRight(String.valueOf(curr));
//				dao.update(oor);
//				em.flush();
//			}
//		}
//		return curr;
//	}

	@Test
	public void testCreateOrganization() throws DoesNotExistException {

		OrgType orgType = new OrgType();

		orgType.setKey("kuali.org.CorporateEntity");
		orgType.setName("Corporate Entity");
		orgType.setDesc("A legal corporate entity");

		Org org = new Org();
		org.setType(orgType);
		org.setShortName("KU");
		org.setDesc("Kuali University");

		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.Main");
		orgHierarchy.setName("Main");
		orgHierarchy.setDesc("Main Organizational Hierarchy");
		orgHierarchy.setRootOrg(org);

		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);

		assertNotNull(dao.fetch(OrgHierarchy.class,
				"kuali.org.hierarchy.Curriculum"));

		// Check the alias attribute
		Org borgOrg = dao.fetch(Org.class, "2");
		assertEquals(1, borgOrg.getAttributes().size());
		assertEquals("Governors", borgOrg.getAttributes().get(0).getValue());

		OrgAttribute borgAlias = new OrgAttribute();
		borgAlias.setName("Alias");
		borgAlias.setValue("Governors");
		borgAlias.setOwner(borgOrg);

		borgOrg.getAttributes().add(borgAlias);

		em.persist(borgOrg);
	}

	@Test
	public void testDeleteOrganizationByReference() {

		OrgType orgType = new OrgType();
		orgType.setKey("kauli.org.TestOrgTypeKey1");
		orgType.setName("Test OrgType 1");
		orgType.setDesc("A test OrgType");

		Org org = new Org();
		org.setType(orgType);
		org.setShortName("TestOrg1");
		org.setDesc("Test Org 1");

		OrgAttribute orgAttr1 = new OrgAttribute();
		orgAttr1.setValue("orgAttr1Value");
		OrgAttribute orgAttr2 = new OrgAttribute();
		orgAttr1.setValue("orgAttr2Value");

		org.setAttributes(Arrays
				.asList(new OrgAttribute[] { orgAttr1, orgAttr2 }));

		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.TestOrgHierarchy1");
		orgHierarchy.setName("TestOrgHeir1");
		orgHierarchy.setDesc("Test Organizational Hierarchy 1");
		orgHierarchy.setRootOrg(org);

		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);

		String orgID = org.getId();

		try {
			assertNotNull(org = dao.fetch(Org.class, orgID));
		} catch (DoesNotExistException dnee) {
			fail("TestOrganizationDao#fetch(Org.class, <id>) failed: "
					+ dnee.getMessage());
		}
		assertEquals(2, org.getAttributes().size());
		List<String> attrIDs = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			attrIDs.add(org.getAttributes().get(i).getId());
		}
		dao.delete(org);

		try {
			assertNull(dao.fetch(Org.class, orgID));
			fail("OrganizationDAO#fetch(Org.class, <id>) of a deleted Org did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}

		// make sure Attrs were deleted
		try {
			for (String id : attrIDs) {
				assertNull(dao.fetch(OrgAttribute.class, id));
			}
			fail("OrganizationDAO#fetch(OrgAttribute.class, <id> of a deleted OrgAttribute did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}
	}

	@Test
	public void testDeleteOrganizationByKey() {

		OrgType orgType = new OrgType();
		orgType.setKey("kauli.org.TestOrgTypeKey1");
		orgType.setName("Test OrgType 1");
		orgType.setDesc("A test OrgType");

		Org org = new Org();
		org.setType(orgType);
		org.setShortName("TestOrg1");
		org.setDesc("Test Org 1");

		OrgAttribute orgAttr1 = new OrgAttribute();
		orgAttr1.setValue("orgAttr1Value");
		OrgAttribute orgAttr2 = new OrgAttribute();
		orgAttr1.setValue("orgAttr2Value");

		org.setAttributes(Arrays
				.asList(new OrgAttribute[] { orgAttr1, orgAttr2 }));

		OrgHierarchy orgHierarchy = new OrgHierarchy();
		orgHierarchy.setKey("kuali.org.TestOrgHierarchy1");
		orgHierarchy.setName("TestOrgHeir1");
		orgHierarchy.setDesc("Test Organizational Hierarchy 1");
		orgHierarchy.setRootOrg(org);

		dao.create(orgType);
		dao.create(org);
		dao.create(orgHierarchy);

		String orgID = org.getId();

		try {
			assertNotNull(org = dao.fetch(Org.class, orgID));
		} catch (DoesNotExistException e) {
			fail("TestOrganizationDao#fetch(Org.class, <id>) failed: "
					+ e.getMessage());
		}
		assertEquals(2, org.getAttributes().size());
		List<String> attrIDs = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			attrIDs.add(org.getAttributes().get(i).getId());
		}
		try {
			dao.delete(Org.class, orgID);
		} catch (DoesNotExistException e) {
			fail("TestOrganizationDao#deleteOrganizationByKey failed: "
					+ e.getMessage());
		}

		try {
			// assertNull(dao.fetch(Org.class, orgID));
			dao.fetch(Org.class, orgID);
			fail("OrganizationDAO#fetch(Org.class, <id>) of a deleted Org did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}
		// make sure Attrs were deleted
		try {
			for (String id : attrIDs) {
				assertNull(dao.fetch(OrgAttribute.class, id));
			}
			fail("OrganizationDAO#fetch(OrgAttribute.class, <id> of a deleted OrgAttribute did not throw org.kuali.student.core.exceptions.DoesNotExistException");
		} catch (DoesNotExistException dnee) {
		}
	}

	@Test
	public void getOrganizationsByIdList() {
		List<String> orgIdList = new ArrayList<String>();
		orgIdList.add("2");
		orgIdList.add("3");
		orgIdList.add("4");
		List<Org> orgs = dao.getOrganizationsByIdList(orgIdList);
		assertEquals(3, orgs.size());
		assertEquals("BORG", orgs.get(0).getShortName());
		assertEquals("ChancellorsOffice", orgs.get(1).getShortName());
		assertEquals("KU", orgs.get(2).getShortName());
	}

	@Test
	public void getOrgOrgRelationsByOrg() {
		List<OrgOrgRelation> orgOrgRelations = dao
				.getOrgOrgRelationsByOrg("60");
		assertEquals(1, orgOrgRelations.size());
	}

	@Test
	public void getAllOrgPersonRelationsByPerson() {
		List<OrgPersonRelation> orgPersonRelations = dao
				.getAllOrgPersonRelationsByPerson("KIM-1");
		assertEquals(3, orgPersonRelations.size());
	}

	@Test
	public void getAllOrgPersonRelationsByOrg() {
		List<OrgPersonRelation> orgPersonRelations = dao
				.getAllOrgPersonRelationsByOrg("68");
		assertEquals(3, orgPersonRelations.size());
	}

	@Test
	public void getPersonIdsForOrgByRelationType() {
		List<String> orgPersonRelations = dao.getPersonIdsForOrgByRelationType(
				"147", "kuali.org.PersonRelation.Coordinator");
		assertEquals(1, orgPersonRelations.size());
		assertEquals("KIM-3", orgPersonRelations.get(0));
		orgPersonRelations = dao.getPersonIdsForOrgByRelationType(
				"68", "kuali.org.PersonRelation.Professor");
		assertEquals(2, orgPersonRelations.size());
	}

	@Test
	public void getPositionRestrictionsByOrg() {
		List<OrgPositionRestriction> orgPositionRestrictions = dao
				.getPositionRestrictionsByOrg("2");
		assertEquals(2, orgPositionRestrictions.size());
		assertEquals("Member of the Board of Regents", orgPositionRestrictions
				.get(0).getTitle());
		assertEquals("Treasurer of the Board of Regents",
				orgPositionRestrictions.get(1).getTitle());
	}

	@Test
	public void getAllDescendants() {
		List<String> descendents = dao.getAllDescendants("1",
				"kuali.org.hierarchy.Main");
		assertEquals(2, descendents.size());
		assertEquals("4", descendents.get(0));
		assertEquals("2", descendents.get(1));
	}

	@Test
	public void getOrgOrgRelationTypesForOrgHierarchy() {
		List<OrgOrgRelationType> relationTypes = dao
				.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Main");
		assertEquals(12, relationTypes.size());
		assertEquals("kuali.org.Section", relationTypes.get(1).getKey());
		assertEquals("kuali.org.Subcommittee", relationTypes.get(11).getKey());
		relationTypes = dao.getOrgOrgRelationTypesForOrgHierarchy("kuali.org.hierarchy.Curriculum");
		assertEquals(1, relationTypes.size());
		assertEquals("kuali.org.CurriculumChild", relationTypes.get(0).getKey());

	}
	
	@Test
	public void validatePositionRestriction() {
		assertTrue(dao.validatePositionRestriction("3", "kuali.org.PersonRelation.ViceChancellor"));
		assertFalse(dao.validatePositionRestriction("3", "kuali.org.PersonRelation.VicePresident"));
		assertTrue(dao.validatePositionRestriction("3", "kuali.org.PersonRelation.Chancellor"));
		assertTrue(dao.validatePositionRestriction("2", "kuali.org.PersonRelation.Treasurer"));
		assertFalse(dao.validatePositionRestriction("49", "kuali.org.PersonRelation.Chair"));
		assertTrue(dao.validatePositionRestriction("50", "kuali.org.PersonRelation.Chair"));
	}
	
	@Test
	public void getOrgPersonRelationTypesForOrgType() {
		List<OrgPersonRelationType> personRelationTypes = dao.getOrgPersonRelationTypesForOrgType("kuali.org.School");
		assertEquals(2, personRelationTypes.size());
		assertEquals("Head", personRelationTypes.get(0).getName());
		assertEquals("Professor", personRelationTypes.get(1).getName());
	}
	
	@Test
	public void getOrgOrgRelationsByIdList() {
		List<OrgOrgRelation> orgOrgRelations = dao.getOrgOrgRelationsByIdList(Arrays.asList("1", "3", "6"));
		assertEquals(3, orgOrgRelations.size());
		assertEquals("Board", orgOrgRelations.get(0).getType().getName());
		assertEquals("Board", orgOrgRelations.get(1).getType().getName());
		assertEquals("Advisory", orgOrgRelations.get(2).getType().getName());
	}
	
	@Test
	public void getOrgPersonRelationsByIdList() {
		List<OrgPersonRelation> orgPersonRelations = dao.getOrgPersonRelationsByIdList(Arrays.asList("1", "3", "6"));
		assertEquals(3, orgPersonRelations.size());
		assertEquals("68", orgPersonRelations.get(0).getOrg().getId());
		assertEquals("Head", orgPersonRelations.get(0).getType().getDesc());
		assertEquals("KIM-1", orgPersonRelations.get(0).getPersonId());
		assertEquals("147", orgPersonRelations.get(1).getOrg().getId());
		assertEquals("KIM-3", orgPersonRelations.get(1).getPersonId());
		assertEquals("Professor", orgPersonRelations.get(2).getType().getDesc());
		assertEquals("KIM-1", orgPersonRelations.get(2).getPersonId());
	}
	
	@Test
	public void getOrgPersonRelationsByPerson() {
		List<OrgPersonRelation> orgPersonRelations = dao.getOrgPersonRelationsByPerson("KIM-1", "68");
		assertEquals(2, orgPersonRelations.size());
		assertEquals("Head", orgPersonRelations.get(0).getType().getDesc());
		assertEquals("Professor", orgPersonRelations.get(1).getType().getDesc());
	}
	
	@Test
	public void getOrgOrgRelationTypesForOrgType() {
		List<OrgOrgRelationType> orgOrgRelationTypes = dao.getOrgOrgRelationTypesForOrgType("kuali.org.Program");
		assertEquals(5, orgOrgRelationTypes.size());
		assertEquals("Chartered", orgOrgRelationTypes.get(1).getName());
		assertEquals("Part", orgOrgRelationTypes.get(4).getName());
	}
	
	@Test
	public void getOrgOrgRelationsByRelatedOrg() {
		List<OrgOrgRelation> orgOrgRelations = dao.getOrgOrgRelationsByRelatedOrg("25");
		assertEquals(2, orgOrgRelations.size());
		assertEquals("VPStudentsOffice", orgOrgRelations.get(0).getOrg().getShortName());
		assertEquals("UndergraduateProgram", orgOrgRelations.get(1).getOrg().getShortName());
	}
}
