/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.common.util.krms.proposition;

import org.kuali.rice.krms.framework.engine.Proposition;

import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;

/**
 * @author alubbers
 *
 * Abstraction of propositions with no children
 */
public abstract class AbstractLeafProposition implements Proposition {

    @Override
    public List<Proposition> getChildren() {
        return new ArrayList<Proposition>();
    }

    @Override
    public boolean isCompound() {
        return false;
    }

    public PropositionResult recordResultWithNoDetails(ExecutionEnvironment environment, boolean value) {
        PropositionResult result = new PropositionResult(value);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }
}
