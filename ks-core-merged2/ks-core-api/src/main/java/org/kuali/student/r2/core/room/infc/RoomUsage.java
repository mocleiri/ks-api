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

package org.kuali.student.r2.core.room.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;

import java.util.List;

/**
 * Room Usage information
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface RoomUsage extends HasId, HasAttributesAndMeta {

    /**
     * Preferred capacity of the room
     *
     * @name Preferred Capacity
     * @required
     */
    public Integer getPreferredCapacity();

    /**
     * Hard capacity of the room
     *
     * @name Hard Capacity
     * @required
     */
    public Integer getHardCapacity();

    /**
     * Usage type (classe, exam, meeting, ...)
     *
     * @name Usage Type key
     */
    public String getUsageTypeKey();

    /**
     * Layout type (class, exam, business school, ...)
     *
     * @name Layout Type key
     */
    public String getLayoutTypeKey();
}
