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
package org.kuali.student.brms.repository.rule;

import java.util.ArrayList;

/**
 * This class contains a list of rule and/or rule set compilation errors
 * <code>CompilerResult</code>. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class CompilerResultList 
    extends ArrayList<CompilerResult> 
    implements java.io.Serializable {
    
    /** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** Compilation results for object E.g. Compiling org.drools.rule.Package */
    private Object object;

    /**
     * Constructs a compilation error list.
     * E.g. Compilation errors for org.drools.rule.Package
     * 
     * @param obj Compilation results for object
     */
    public CompilerResultList(Object obj) {
        this.object = obj;
    }

    /**
     * Gets the object that failed compilation.
     * 
     * @return An object
     */
    public Object getObject() {
        return this.object;
    }
}
