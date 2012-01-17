/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.util.constants;

import org.kuali.student.common.util.constants.CommonServiceConstants;
import org.kuali.student.core.population.dto.PopulationInfo;
import org.kuali.student.core.population.dto.PopulationRuleInfo;


/**
 * This class holds the constants used by the Population service.
 *
 * @author tom
 */

public class PopulationServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "population";
    public static final String REF_OBJECT_URI_POPULATION = NAMESPACE + "/" + PopulationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_POPULATION_RULE = NAMESPACE + "/" + PopulationRuleInfo.class.getSimpleName();

    /**
     * Population types
     */
    public static final String POPULATION_TYPE_KEY = "kuali.population.type.population";

    /**
     * PopulationRule types
     */
    public static final String POPULATION_RULE_TYPE_KEY = "kuali.population.type.population.rule";

    /**
     * States for Populations
     */
    public static final String POPULATION_LIFECYCLE_KEY = "kuali.population.population.lifecycle";

    /**
     * States for Population Rule
     */
    public static final String POPULATION_RULE_LIFECYCLE_KEY = "kuali.population.population.rule.lifecycle";
    public static final String POPULATION_RULE_ACTIVE_STATE_KEY = "kuali.population.population.rule.state.active";
    public static final String POPULATION_RULE_INACTIVE_STATE_KEY = "kuali.population.population.rule.state.inactive";
    public static final String[] POPULATION_RULE_LIFECYCLE_KEYS = {
        POPULATION_RULE_ACTIVE_STATE_KEY,
        POPULATION_RULE_INACTIVE_STATE_KEY
    };
    
    /**
     * known population keys
     */
    public static final String SUMMER_ONLY_STUDENTS_POPULATION_KEY = "kuali.population.summer.only.student";
}
