/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.infc;

import java.util.Date;

/**
 *
 * @author nwright
 */
public interface HasEffectiveDatesInfc {
   /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * Name:Effective
     * Date/time this relationship became effective. Must be less than or equal to the
     * expirationDate specified.
     */
    public void setEffectiveDate(Date effectiveDate);

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * Name:Effective
     * Date/time this relationship became effective. Must be less than or equal to the
     * expirationDate specified.
     */
    public Date getEffectiveDate();

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * Name: Expiration
     * Date/time this relationship is no longer effective. Must be greater than or
     * equal to the effectiveDate specified.
     */
    public void setExpirationDate(Date expirationDate);

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * Name: Expiration
     * Date/time this relationship is no longer effective. Must be greater than or
     * equal to the effectiveDate specified.
     */
    public Date getExpirationDate();
}
