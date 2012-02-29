/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.core.room.infc;

import org.kuali.student.common.infc.HasAttributesAndMeta;
import org.kuali.student.common.infc.HasId;

/**
 *
 * Resource in a room (computer, projector, whiteboard etc)
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface RoomFixedResource extends HasId, HasAttributesAndMeta {

    /**
     * Quantity of this resource
     *
     * @name Quantity
     * @required
     */
    public Integer getQuantity();

    /**
     * Type of the Resource
     *
     * @name Resource Type key
     * @required
     */
    public String getResourceTypeKey();
}
