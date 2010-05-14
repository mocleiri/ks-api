/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.factfinder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

/**
 * Temporary place holder for LUI Person data that can be retrieved by calling fetchFact
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "LUIPerson_T")
@NamedQueries({@NamedQuery(name = "LUIPerson.findByStudentId", query = "SELECT c FROM LUIPerson c WHERE c.studentId = :studentID")})
public class LUIPerson {

    @Id
    private String id;
    
    private String studentId;
    
    private String cluId;
    
    private Double credits;

    
    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
        
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the cluId
     */
    public String getCluId() {
        return cluId;
    }

    /**
     * @param cluId the cluId to set
     */
    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    /**
     * @return the credits
     */
    public Double getCredits() {
        return credits;
    }

    /**
     * @param credits the credits to set
     */
    public void setCredits(Double cretids) {
        this.credits = cretids;
    }    
}
