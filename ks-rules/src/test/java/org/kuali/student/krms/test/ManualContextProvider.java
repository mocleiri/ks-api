package org.kuali.student.krms.test;

import java.util.Map;

import org.kuali.rice.krms.engine.Asset;
import org.kuali.rice.krms.engine.Context;
import org.kuali.rice.krms.engine.ContextProvider;
import org.kuali.rice.krms.engine.SelectionCriteria;

public class ManualContextProvider implements ContextProvider {

	private Context context;
	
	public ManualContextProvider(Context context) {
		this.context = context;
	}
	
	@Override
	public Context loadContext(SelectionCriteria selectionCriteria, Map<Asset, Object> facts, Map<String, String> executionOptions) {
		return context;
	}

}
