package org.kuali.student.lum.lu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.service.LuService;


@Daos( { @Dao(value = "org.kuali.student.lum.lu.dao.impl.LuDaoImpl",testSqlFile="classpath:ks-lu.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/lu-persistence.xml")
public class TestLuServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.lum.lu.service.impl.LuServiceImpl", port = "8181")
	public LuService client;

	@Test
	public void testClu() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		CluInfo clu = client.getClu("CLU-1");
		assertNotNull(clu);
		assertEquals(clu.getId(), "CLU-1");

		try {
			clu = client.getClu("CLX-1");
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			clu = client.getClu(null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}


		List<String> ids = new ArrayList<String>(1);
		ids.add("CLU-2");
		List<CluInfo> clus = client.getClusByIdList(ids);
		assertNotNull(clus);
		assertEquals(1, clus.size());

		ids.clear();
		ids.add("CLX-42");
		clus = client.getClusByIdList(ids);
		assertTrue(clus == null || clus.size() == 0);

		try {
			clus = client.getClusByIdList(null);
			assertTrue(false);
		} catch (MissingParameterException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetLui() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		LuiInfo luiInfo;
		try {
			luiInfo = client.getLui("notARealLui");
			fail("LuService.getLui() did not throw DoesNotExistException for non-existent Lui");
		} catch (DoesNotExistException dnee) {
		} catch (Exception e) {
			fail("LuService.getLui() threw unexpected " + e.getClass().getSimpleName() + " for null Lui ID");
		}
		try {
			luiInfo = client.getLui(null);
			fail("LuService.getLui() did not throw MissingParameterException for null Lui ID");
		} catch (MissingParameterException mpe) {
		}
		luiInfo = client.getLui("LUI-1");
		assertEquals("CLU-1", luiInfo.getCluId());
	}
	
	@Test
	public void testGetLuisByIdList() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
		List<LuiInfo> luiInfos;
		try {
			luiInfos = client.getLuisByIdList(null);
			fail("LuService.getLuiByIdList() did not throw MissingParameterException for null Lui ID");
		} catch (MissingParameterException mpe) {
		} catch (Exception e) {
			fail("LuService.getLuiByIdList() threw unexpected " + e.getClass().getSimpleName() + " for null Lui ID");
		}
		luiInfos = client.getLuisByIdList(Arrays.asList("Not a LUI ID", "Another one that ain't"));
		assertTrue(luiInfos == null || luiInfos.size() == 0);
		
		luiInfos = client.getLuisByIdList(Arrays.asList("LUI-1", "LUI-3"));
		assertEquals("CLU-1", luiInfos.get(0).getCluId());
		assertEquals("CLU-2", luiInfos.get(1).getCluId());
	}
}