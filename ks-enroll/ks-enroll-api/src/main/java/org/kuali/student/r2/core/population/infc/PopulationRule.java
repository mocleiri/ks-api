/**
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.population.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.rice.core.api.criteria.QueryByCriteria;


/**
 * Information about a Population Rule to set up a Population. Each
 * "rule" defined in this interface adds to the population.
 *
 * The Population Rule Type determines the "operator" of how these
 * elements are combined.
 *
 * @author tom
 * @since Thu Nov 21 14:22:34 EDT 2011
 */ 

public interface PopulationRule 
    extends IdEntity {

    /**
     * The search criteria to be used in building this
     * population. (TODO: not sure if this can be persisted).
     *
     * @name Search Criteria
     */
    public QueryByCriteria getSearchCriteria();

    /**
     * Agenda Ids to be used in building this population.
     *
     * @name Agenda Ids
     */
    public List<String> getAgendaIds();

    /**
     * A list of Group Ids to be used in building this population.
     *
     * @name Group Ids
     */
    public List<String> getGroupIds();

    /**
     * A list of Person Ids to be used in building this population.
     *
     * @name Person Ids
     */
    public List<String> getPersonIds();

    /**
     * A list of Population Ids to be used in building this
     * population. The operation is determined by the PopuationRule
     * Type.
     *
     * @name Population Ids
     */
    public List<String> getPopulationIds();

    /**
     * A Popiulation Id to be used as a reference population for the
     * PopulationRule Type.
     *
     * @name Reference Population Id
     */
    public String getReferencePopulationId();

    /**
     * Gets the valid sort order keys that can be used to sort the
     * members of the Population. The valid sort order keys correspond
     * to the underlying rule sorting capabilities.
     *
     * @name Sort Order Type Keys
     */
    public List<String> getSortOrderTypeKeys();
}
