package org.kuali.student.lum.lu.naturallanguage;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.entity.ReqComponentType;

public class StatementTestUtil {
	private LuDao luDao;

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	public LuStatement createStatement(LuStatement luStatement) {
		return this.luDao.create(luStatement);
	}

	public ReqComponent createReqComponent(String reqComponentTypeId, List<ReqComponentField> fieldList) throws DoesNotExistException {
		ReqComponentType reqCompType = this.luDao.fetch(ReqComponentType.class, reqComponentTypeId);
		
		ReqComponent reqComponent = new ReqComponent();
		reqComponent.setRequiredComponentType(reqCompType);
		reqComponent.setReqCompField(fieldList);
		reqComponent = this.luDao.create(reqComponent);

		return reqComponent;
    }
    
    public List<ReqComponentField> createReqComponentFields(String expectedValue, String operator, String cluSetId) {
		List<ReqComponentField> fieldList = new ArrayList<ReqComponentField>();
		ReqComponentField field1 = new ReqComponentField();
		field1.setKey("reqCompFieldType.requiredCount");
		field1.setValue(expectedValue);
		field1.prePersist();
		fieldList.add(field1);
		luDao.create(field1);
		
		ReqComponentField field2 = new ReqComponentField();
		field2.setKey("reqCompFieldType.operator");
		field2.setValue(operator);
		field2.prePersist();
		fieldList.add(field2);
		luDao.create(field2);
		
		ReqComponentField field3 = new ReqComponentField();
		field3.setKey("reqCompFieldType.cluSet");
		field3.setValue(cluSetId);
		field3.prePersist();
		fieldList.add(field3);
		luDao.create(field3);
		
//		reqComponent.setReqCompField(fieldList);
//		reqComponent = this.luDao.update(reqComponent);
		return fieldList;
    }
}
