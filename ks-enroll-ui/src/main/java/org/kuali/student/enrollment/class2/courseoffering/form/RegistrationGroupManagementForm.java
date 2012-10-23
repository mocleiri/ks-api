package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 10/23/12
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationGroupManagementForm extends UifFormBase {
    //For Manage Registration Groups
    private CourseOfferingInfo theCourseOffering;
    private String formatOfferingIdForViewRG;
    private String formatOfferingName;
    private List<ActivityOfferingWrapper> filteredUnassignedAOsForSelectedFO;
    private List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList;
    private boolean hasAOCluster;
    private boolean selectCreateNewFromDropDown = false;
    private String privateClusterName;
    private String publishedClusterName;
    private String privateClusterNameForLightBox;
    private String publishedClusterNameForLightBox;
    private String privateClusterNameForRename;
    private String publishedClusterNameForRename;

    private String clusterIdIdForNewFO;
    private String clusterIdForAOMove;

    private boolean withinPortal = true;

    //This is used to handle DialogGroup/Lightbox action within collection.
    //It only works within the lifecycle of each Dialog.
    private ActivityOfferingClusterWrapper selectedCluster;

    public RegistrationGroupManagementForm (){
        filteredUnassignedAOsForSelectedFO = new ArrayList<ActivityOfferingWrapper>();
        filteredAOClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        hasAOCluster = false;
        withinPortal = true;
        selectCreateNewFromDropDown = false;
    }

    public CourseOfferingInfo getTheCourseOffering() {
        return theCourseOffering;
    }

    public void setTheCourseOffering(CourseOfferingInfo theCourseOffering) {
        this.theCourseOffering = theCourseOffering;
    }

    public String getFormatOfferingIdForViewRG() {
        return formatOfferingIdForViewRG;
    }

    public void setFormatOfferingIdForViewRG(String formatOfferingIdForViewRG) {
        this.formatOfferingIdForViewRG = formatOfferingIdForViewRG;
    }

    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    public List<ActivityOfferingWrapper> getFilteredUnassignedAOsForSelectedFO() {
        return filteredUnassignedAOsForSelectedFO;
    }

    public void setFilteredUnassignedAOsForSelectedFO(List<ActivityOfferingWrapper> filteredUnassignedAOsForSelectedFO) {
        this.filteredUnassignedAOsForSelectedFO = filteredUnassignedAOsForSelectedFO;
    }

    public List<ActivityOfferingClusterWrapper> getFilteredAOClusterWrapperList() {
        return filteredAOClusterWrapperList;
    }

    public void setFilteredAOClusterWrapperList(List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList) {
        this.filteredAOClusterWrapperList = filteredAOClusterWrapperList;
    }

    public boolean isHasAOCluster() {
        return hasAOCluster;
    }

    public void setHasAOCluster(boolean hasAOCluster) {
        this.hasAOCluster = hasAOCluster;
    }

    public boolean isSelectCreateNewFromDropDown() {
        return selectCreateNewFromDropDown;
    }

    public void setSelectCreateNewFromDropDown(boolean selectCreateNewFromDropDown) {
        this.selectCreateNewFromDropDown = selectCreateNewFromDropDown;
    }

    public String getPrivateClusterName() {
        return privateClusterName;
    }

    public void setPrivateClusterName(String privateClusterName) {
        this.privateClusterName = privateClusterName;
    }

    public String getPublishedClusterName() {
        return publishedClusterName;
    }

    public void setPublishedClusterName(String publishedClusterName) {
        this.publishedClusterName = publishedClusterName;
    }

    public String getPrivateClusterNameForLightBox() {
        return privateClusterNameForLightBox;
    }

    public void setPrivateClusterNameForLightBox(String privateClusterNameForLightBox) {
        this.privateClusterNameForLightBox = privateClusterNameForLightBox;
    }

    public String getPublishedClusterNameForLightBox() {
        return publishedClusterNameForLightBox;
    }

    public void setPublishedClusterNameForLightBox(String publishedClusterNameForLightBox) {
        this.publishedClusterNameForLightBox = publishedClusterNameForLightBox;
    }

    public String getPrivateClusterNameForRename() {
        return privateClusterNameForRename;
    }

    public void setPrivateClusterNameForRename(String privateClusterNameForRename) {
        this.privateClusterNameForRename = privateClusterNameForRename;
    }

    public String getPublishedClusterNameForRename() {
        return publishedClusterNameForRename;
    }

    public void setPublishedClusterNameForRename(String publishedClusterNameForRename) {
        this.publishedClusterNameForRename = publishedClusterNameForRename;
    }

    public ActivityOfferingClusterWrapper getSelectedCluster() {
        return selectedCluster;
    }

    public void setSelectedCluster(ActivityOfferingClusterWrapper selectedCluster) {
        this.selectedCluster = selectedCluster;
    }

    public String getClusterIdIdForNewFO() {
        return clusterIdIdForNewFO;
    }

    public void setClusterIdIdForNewFO(String clusterIdIdForNewFO) {
        this.clusterIdIdForNewFO = clusterIdIdForNewFO;
    }

    public String getClusterIdForAOMove() {
        return clusterIdForAOMove;
    }

    public void setClusterIdForAOMove(String clusterIdForAOMove) {
        this.clusterIdForAOMove = clusterIdForAOMove;
    }

    public boolean isWithinPortal() {
        return withinPortal;
    }

    public void setWithinPortal(boolean withinPortal) {
        this.withinPortal = withinPortal;
    }
}
