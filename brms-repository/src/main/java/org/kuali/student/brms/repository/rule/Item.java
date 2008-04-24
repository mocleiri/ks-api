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

import java.util.Calendar;

/**
 * This is the base class that 
 * <code>org.kuali.student.brms.repository.rule.Rule</code> and 
 * <code>org.kuali.student.brms.repository.rule.RuleSet<code> extends.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public interface Item {
    /**
     * Gets the name of this item
     * 
     * @return Item name
     */
    public String getName();

    /**
     * Sets the description of this item.
     * 
     * @param description Item description
     */
    public void setDescription(String description);

    /**
     * Gets the description of this item.
     * 
     * @return Item name
     */
    public String getDescription();

    /**
     * Sets the checkin comments.
     * 
     * @param checkinComment Checkin comments
     */
    public void setCheckinComment(String checkinComment);

    /**
     * Gets the checkin comments
     * 
     * @return Checkin comments
     */
    public String getCheckinComment();

    /**
     * Gets the item UUID (Universally Unique Identifier).
     * 
     * @return Item UUID
     */
    public String getUUID();

    /**
     * Sets the item source format. 
     * E.g. XML (xml) or Drools DRL (drl).
     * 
     * @param format Item format
     */
    public void setFormat(String format);
    
    /**
     * Gets the item source format. 
     * E.g. XML (xml) or Drools DRL (drl).
     * 
     * @return Item source format
     */
    public String getFormat();

    /**
     * Sets the item version number.
     * 
     * @param versionNumber Version number
     */
    public void setVersionNumber(long versionNumber);
    
    /**
     * Gets the item version number.
     * 
     * @return Version number
     */
    public long getVersionNumber();

    /**
     * Sets the status of this item. 
     * E.g. Active, Inactive, etc. 
     * 
     * @param status Item status code
     */
    public void setStatus(String status);
    
    /**
     * gets the status of this item. 
     * E.g. Active, Inactive, etc. 
     * 
     * @return Item status code
     */
    public String getStatus();

    /**
     * Sets the item created date.
     * 
     * @param createdDate
     */
    public void setCreatedDate(Calendar createdDate);
    
    /**
     * Gets the date this item was created.
     * 
     * @return Item created date
     */
    public Calendar getCreatedDate();

    /**
     * Sets the date this item was last modified. 
     * 
     * @param lastModifiedDate Item last modified date
     */
    public void setLastModifiedDate(Calendar lastModifiedDate);

    /**
     * Gets the date this item was last modified.
     * 
     * @return Item last modified date
     */
    public Calendar getLastModifiedDate();

    /**
     * Determines whether this item is archived.
     * 
     * @return True if this item is archived otherwise false
     */
    public boolean isArchived();

    /**
     * Sets this item to archived if <code>archived</code> is true. 
     * 
     * @param archived True if this item is archived, otherwise false
     */
    public void setArchived(boolean archived);

    /**
     * Sets this item historical to true or false.
     * 
     * @param historical True equals historical, otherwise false
     */
    public void setHistorical(boolean historical);
    
    /**
     * Determines whether this item is a historical item.
     * 
     * @return True if this item is historical
     */
    public boolean isHistorical();

    /**
     * Sets the UUID of this version's snapshot
     * 
     * @param versionSnapshotUUID
     */
    public void setVersionSnapshotUUID(String versionSnapshotUUID);
    
    /**
     * Returns the UUID of the snapshot version. 
     * This will return null if this is the current version. 
     * Only historical version will return a UUID.
     * 
     * @return UUID of the historical version
     */
    public String getVersionSnapshotUUID();
}
