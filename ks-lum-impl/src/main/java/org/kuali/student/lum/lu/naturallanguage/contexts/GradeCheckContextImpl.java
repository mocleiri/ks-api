package org.kuali.student.lum.lu.naturallanguage.contexts;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.naturallanguage.util.ReqComponentTypes;

/**
 * This class creates the template context for grade check type.
 */
public class GradeCheckContextImpl extends AbstractContext<ReqComponent> {
	/** GPA template token */ 
	private final static String GPA_TOKEN = "gpa";
	
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws DoesNotExistException {
    	Map<String, Object> contextMap = new HashMap<String, Object>();
    	contextMap.put(GPA_TOKEN, getReqCompFieldValue(reqComponent, ReqComponentTypes.ReqCompFieldTypes.GPA_KEY.getKey()));

        return contextMap;
    }
}
