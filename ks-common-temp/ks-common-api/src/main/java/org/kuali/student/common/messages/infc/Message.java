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

package org.kuali.student.common.messages.infc;

import org.kuali.student.common.infc.HasKey;

/**
 *  Information about a message
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface Message extends HasKey {

    /**
     * Unique identifier for a locale
     *
     * @name Locale
     * @required
     *
     */
    String getLocale();

    /**
     * Unique identifier for a message group
     *
     * @name Group Name
     * @required
     *
     */
    String getGroupName();

    /**
     * The string representation of the message. Symbols may be included within
     * the message, but the expectation is that the caller is aware of the
     * format of these symbols.
     *
     * @name Value
     * @required
     *
     */
    String getValue();
}
