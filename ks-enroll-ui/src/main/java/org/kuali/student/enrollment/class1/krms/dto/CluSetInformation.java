/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.student.enrollment.class1.krms.util.CluSetRangeInformation;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CluSetInformation implements Serializable {

    private static final long serialVersionUID = 1123124L;
    private CluSetInfo cluSetInfo;
    private List<CluInformation> clus;

    private List<CluSetInformation> cluSets;

    private MembershipQueryInfo membershipQueryInfo;
    private List<CluInformation> clusInRange;
    private CluSetRangeInformation cluSetRange = new CluSetRangeInformation();

    private Map<String, CluSetInformation> subCluSetInformations;
    private CluSetInformation parent;

    public CluSetInformation(){
        super();
        this.cluSetInfo = new CluSetInfo();
    }

    public CluSetInformation(CluSetInfo cluSetInfo){
        super();
        this.cluSetInfo = cluSetInfo;
    }

    public CluSetInfo getCluSetInfo() {
        return cluSetInfo;
    }

    public void setCluSetInfo(CluSetInfo cluSetInfo) {
        this.cluSetInfo = cluSetInfo;
    }

    public String getName() {
        return cluSetInfo.getName();
    }

    public void setName(String name) {
        this.cluSetInfo.setName(name);
    }

    public List<CluInformation> getClus() {
        if (clus == null) {
            this.clus = new ArrayList<CluInformation>();
        }
        return this.clus;
    }

    public void setClus(List<CluInformation> clus) {
        this.clus = clus;
    }

    public List<CluSetInformation> getCluSets() {
        if (this.cluSets == null) {
            this.cluSets = new ArrayList<CluSetInformation>();
        }
        return this.cluSets;
    }

    public void setCluSets(List<CluSetInformation> cluSets) {
        this.cluSets = cluSets;
    }

    public MembershipQueryInfo getMembershipQueryInfo() {
        return membershipQueryInfo;
    }

    public void setMembershipQueryInfo(MembershipQueryInfo membershipQueryInfo) {
        this.membershipQueryInfo = membershipQueryInfo;
    }

    public List<CluInformation> getClusInRange() {
        if (clusInRange == null) {
            this.clusInRange = new ArrayList<CluInformation>();
        }
        return this.clusInRange;
    }

    public void setClusInRange(List<CluInformation> clusInRange) {
        this.clusInRange = clusInRange;
    }

    public CluSetRangeInformation getCluSetRange() {
        return cluSetRange;
    }

    public void setCluSetRange(CluSetRangeInformation cluSetRange) {
        this.cluSetRange = cluSetRange;
    }

    public Map<String, CluSetInformation> getSubCluSetInformations() {
        if (subCluSetInformations == null) {
            subCluSetInformations = new HashMap<String, CluSetInformation>();
        }
        return subCluSetInformations;
    }

    public void setSubCluSetInformations(Map<String, CluSetInformation> subCluSetInformations) {
        this.subCluSetInformations = subCluSetInformations;
    }

    public CluSetInformation getParent() {
        return parent;
    }

    public void setParent(CluSetInformation parent) {
        this.parent = parent;
    }

    /**
     * Returns a list of all the clus and the clusInRange.
     *
     * @return
     */
    public List<CluInformation> getClusAndClusInRange(){
        List<CluInformation> clus = new ArrayList<CluInformation>();
        clus.addAll(this.getClus());
        clus.addAll(this.getClusInRange());
        return clus;
    }

    /**
     * Returns a comma delimited list of clu ids including the
     * clusInRange to be passed to the service for the natural
     * lantuage translation.
     *
     * @return
     */
    public String getCluDelimitedString() {

        List<String> cluIds = this.getCluIds();
        if (this.getClusInRange() != null) {
            for (CluInformation clu : this.getClusInRange()) {
                cluIds.add(clu.getVerIndependentId());
            }
        }

        Collections.sort(cluIds);
        return StringUtils.collectionToCommaDelimitedString(cluIds);
    }

    /**
     * Returns a comma delimited list of cluset ids to be passed to
     * the service for the natural lantuage translation.
     *
     * @return
     */
    public String getCluSetDelimitedString() {

        List<String> cluSetIds = new ArrayList<String>();
        for (CluSetInformation cluSet : this.getCluSets()) {
            cluSetIds.add(cluSet.getCluSetInfo().getId());
        }

        Collections.sort(cluSetIds);
        return StringUtils.collectionToCommaDelimitedString(cluSetIds);
    }

    /**
     * Check if there are any simple clus linked to this cluset wrapper.
     * Does not include clusInRAnge(membershipQuery) or other cluset.
     *
     * @return
     */
    public boolean hasClus() {
        if ((this.getClus() != null) && (!this.getClus().isEmpty())) {
            return true;
        }
        return false;
    }

    public boolean hasMembershipQuery() {
        MembershipQueryInfo mqInfo = this.getMembershipQueryInfo();
        if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()) {
            return true;
        }
        return false;
    }

    public List<String> getCluIds(){
        List<String> cluIds = new ArrayList<String>();
        for(CluInformation clu : this.getClus()){
            cluIds.add(clu.getVerIndependentId());
        }
        return cluIds;
    }

    public List<Object> getCluViewers(){
        List<Object> cluGroups = new ArrayList<Object>();
        //Individual Clus
        if(this.getClus().size()>0){
            CluGroup indCourses = new CluGroup("INDIVIDUAL COURSE(S)");
            indCourses.setClus(this.getClus());

            //Only display the Indivdual Courses heading when clusets or queries exist.
            if((this.getCluSets().size()==0)&&(this.getClusInRange().size()==0)){
                indCourses.setShowTitle(false);
            }
            cluGroups.add(indCourses);
        }

        //Course sets.
        for (CluSetInformation cluSet : this.getCluSets()) {
            CluGroup cluSetCourses = new CluGroup(cluSet.getCluSetInfo().getName());
            cluSetCourses.setClus(cluSet.getClus());
            cluGroups.add(cluSetCourses);
        }

        //CourseRange
        if(this.getClusInRange().size()>0){
            CluGroup cluRangeCourses = new CluGroup(this.getCluSetRange().getCluSetRangeLabel());
            cluRangeCourses.setClus(this.getClusInRange());
            cluGroups.add(cluRangeCourses);
        }
        return cluGroups;
    }

}
