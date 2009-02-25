/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.enumerationmanagement.dto.mock;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dictionary.dto.Context;
import org.kuali.student.core.dictionary.dto.Contexts;

/**
 * Creates a Contexts (List) of Context DTOs, use to initialize database for testing 
 * @author  garystruthers Kuali Student Team 
 * @Immutable all fields are private, all mutator methods are private
 * @Threadsafe immutable classes are threadsafe
 * Contexts (List) and its Context objects returned by this class are NOT immutable or threadsafe
 */
public class MockContextDTOs {
	
	private String contextType = "Type";
	private String contextValue = "Value";

	private Contexts contexts;

	/**
	 * MockContextDTOs Default Constructor
	 * 
	 */
	public MockContextDTOs() {
		this(0,10);
	}
	
	/**
	 * MockContextDTOs Constructor
	 * @param initial index value
	 * @param last index value
	 */
	public MockContextDTOs(int initial, int last) {
		this.contexts = createContexts(initial, last);
	}

	/**
	 * @return the contexts
	 */
	public Contexts getContexts() {
		return copyContexts(contexts);
	}
	
	/**
	 * @return a newly created Context
	 * @param i index value for setting unique field values
	 */
	private Context createContext(int i) {
		Context context = new Context();
		context.setType(contextType);
		context.setValue(contextValue + i);
		return context;
	}
	
	/**
	 * @return a newly created Contexts
	 * @param i initial index value for setting unique field values
	 * @param j last index value for setting unique field values
	 */
	private Contexts createContexts(int initial, int last) {
		List<Context> context = new ArrayList<Context>();		
		for(int i = initial; i < last; i++) {
			context.add(createContext(i));
		}
		Contexts contexts = new Contexts();
		contexts.setContext(context);
		return contexts;
	}
	
	/**
	 * @return a copy of Context
	 */
	private Context copyContext(Context src) {
		Context copy = new Context();
		copy.setType(src.getType());
		copy.setValue(src.getValue());
		return copy;
	}
	
	
	/**
	 * @return a copy of Contexts
	 * 
	 */
	private Contexts copyContexts(Contexts contexts) {
		List<Context> src = contexts.getContext();
		List<Context> copy = new ArrayList<Context>();		
		for(Context e : src) {
			copy.add(copyContext(e));
		}
		Contexts copyContexts = new Contexts();
		copyContexts.setContext(copy);
		return copyContexts;
	}
}