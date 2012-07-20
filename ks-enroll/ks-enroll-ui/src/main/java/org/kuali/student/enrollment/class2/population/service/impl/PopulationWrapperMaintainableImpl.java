package org.kuali.student.enrollment.class2.population.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.enrollment.class2.population.service.PopulationWrapperMaintainable;

import javax.xml.namespace.QName;
import java.util.Map;

public class PopulationWrapperMaintainableImpl extends MaintainableImpl implements PopulationWrapperMaintainable{
    private transient PopulationService populationService;
    private ContextInfo contextInfo;
    final Logger logger = Logger.getLogger(PopulationWrapperMaintainableImpl.class);
    
    @Override
    public void saveDataObject() {
        try {
            PopulationWrapper wrapper = (PopulationWrapper)getDataObject();
            if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION )) {
                createPopulation(wrapper);
            }
            else if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)){
                updatePopulation(wrapper);
            }
     
        } catch (DataValidationErrorException e){
            logger.error("PopulationWrapperMaintainableImpl data validation error. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl data validation error. ", e);
        } catch (InvalidParameterException e) {
            logger.error("PopulationWrapperMaintainableImpl invalid parameter. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl invalid parameter. ", e);
        } catch (MissingParameterException e) {
            logger.error("PopulationWrapperMaintainableImpl missing parameter. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl missing parameter. ", e);
        } catch (OperationFailedException e) {
            logger.error("PopulationWrapperMaintainableImpl operation failed. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl operation failed. ", e);
        } catch (PermissionDeniedException e) {
            logger.error("PopulationWrapperMaintainableImpl permission denied. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl permission denied. ", e);
        } catch (ReadOnlyException e) {
            logger.error("PopulationWrapperMaintainableImpl read only exception. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl read only exception. ", e);
        }catch (Exception e) {
            logger.error("PopulationWrapperMaintainableImpl exception. ", e);
            throw new RuntimeException("PopulationWrapperMaintainableImpl exception. ", e);
        }

    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            String populationId =  dataObjectKeys.get("id");
            PopulationWrapper wrapper = getPopulation(populationId);
            wrapper.setId(populationId);
            wrapper.setInCreateMode(false);
            wrapper.setPageTitle("Edit Population");
            if (PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY.equals(wrapper.getPopulationRuleInfo().getTypeKey())){
                //core type by rule
                wrapper.setCreateByRule(true);
            }
            else {
                //constructed type by combining populations
                wrapper.setCreateByRule(false);
            }

            return wrapper;
        } catch (Exception e) {
            logger.error("Fail to retrieve Population for edit.", e);
            throw new RuntimeException("Fail to retrieve Population for edit.", e);
        }
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterEdit
     */
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription("Edit Population");

    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterNew
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription("Create a New Population");

    }

    // getting the PopulationInfo and PopulationRuleInfo and pushing them into wrapper
    public PopulationWrapper getPopulation(String populationId) {

        PopulationWrapper wrapper = new PopulationWrapper();

        try {
            wrapper.setPopulationInfo(getPopulationService().getPopulation(populationId, getContextInfo()));
            wrapper.setPopulationRuleInfo(getPopulationService().getPopulationRuleForPopulation(populationId, getContextInfo()));
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return wrapper;
    }

    // create the PopulationInfo and PopulationRuleInfo
    public PopulationWrapper createPopulation(PopulationWrapper wrapper) throws Exception {
        PopulationInfo populationInfo = wrapper.getPopulationInfo();
        getPopulationService().createPopulation(wrapper.getPopulationInfo(), getContextInfo());
        PopulationRuleInfo  populationRuleInfo = getPopulationService().createPopulationRule(wrapper.getPopulationRuleInfo(), getContextInfo());
        getPopulationService().applyPopulationRuleToPopulation(populationRuleInfo.getId(), populationInfo.getId(),getContextInfo());
        wrapper.setPopulationInfo(populationInfo);
        wrapper.setPopulationRuleInfo(populationRuleInfo);
        wrapper.setId(populationInfo.getId());
        return wrapper;
    }

    public void updatePopulation(PopulationWrapper wrapper) throws Exception {
        getPopulationService().updatePopulation(wrapper.getId(), wrapper.getPopulationInfo(), getContextInfo());
        getPopulationService().updatePopulationRule(wrapper.getPopulationRuleInfo().getId(), wrapper.getPopulationRuleInfo(), getContextInfo());
    }

    /**
     * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
            PopulationWrapper wrapper = (PopulationWrapper)getDataObject();
            PopulationInfo populationInfo = wrapper.getPopulationInfo();
            populationInfo.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
            PopulationRuleInfo populationRuleInfo = wrapper.getPopulationRuleInfo();
            populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        }
        super.prepareForSave();
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null) {
            contextInfo = ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }

}
