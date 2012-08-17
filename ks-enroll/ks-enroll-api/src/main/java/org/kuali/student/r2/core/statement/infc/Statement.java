/*
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

package org.kuali.student.r2.core.class1.statement.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.core.class1.statement.dto.StatementOperator;

import java.util.List;

/**
 * Detailed information about a single statement
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Statement extends IdEntity {

    /**
     * Logical operator used to assemble statements. Acceptable values are
     * restricted to AND and OR. This operator applies to both the statements
     * and requirement components contained within this statement
     *
     * @name Operator
     * @required
     */
    StatementOperator getOperator();

    /**
     * List of statement identifiers
     *
     * @name Statement Ids
     * @readOnly
     * @required
     */
    List<String> getStatementIds();

    /**
     * List of requirement component identifiers
     *
     * @name Req Component Ids
     * @readOnly
     */
    List<String> getReqComponentIds();
}
