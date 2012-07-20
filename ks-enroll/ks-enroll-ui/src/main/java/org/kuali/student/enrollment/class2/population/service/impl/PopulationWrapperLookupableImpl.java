/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by mharmath on 7/17/12
 */
package org.kuali.student.enrollment.class2.population.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperLookupableImpl extends LookupableImpl {
    private transient PopulationService populationService = getPopulationService();
    final Logger logger = Logger.getLogger(PopulationWrapperLookupableImpl.class);

    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<PopulationWrapper> populationWrappers = new ArrayList<PopulationWrapper>();

        try {
            QueryByCriteria qbc = buildQueryByCriteria(fieldValues);
            List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(qbc, getContextInfo());
            for (PopulationInfo populationInfo: populationInfoList) {
                PopulationRuleInfo populationRuleInfo = getPopulationService().getPopulationRuleForPopulation(populationInfo.getId(),getContextInfo());
                PopulationWrapper wrapper = new PopulationWrapper();
                if ( populationInfo.getStateKey().equals(fieldValues.get("populationInfo.stateKey"))) {    //filter by state
                    wrapper.setPopulationRuleInfo(populationRuleInfo);
                    wrapper.setPopulationInfo(populationInfo);
                    wrapper.setId(populationInfo.getId());
                    populationWrappers.add(wrapper);
                }
            }
        } catch (InvalidParameterException e) {
            logger.error("PopulationWrapperLookupableImpl invalid parameter. ", e);
            throw new RuntimeException("PopulationWrapperLookupableImpl invalid parameter. ", e);
        } catch (MissingParameterException e) {
            logger.error("PopulationWrapperLookupableImpl missing parameter. ", e);
            throw new RuntimeException("PopulationWrapperLookupableImpl missing parameter. ", e);
        } catch (OperationFailedException e) {
            logger.error("PopulationWrapperLookupableImpl operation failed. ", e);
            throw new RuntimeException("PopulationWrapperLookupableImpl operation failed. ", e);
        } catch (PermissionDeniedException e) {
            logger.error("PopulationWrapperLookupableImpl permission denied. ", e);
            throw new RuntimeException("PopulationWrapperLookupableImpl permission denied. ", e);
        } catch (Exception e) {
            logger.error("PopulationWrapperLookupableImpl exception. ", e);
            throw new RuntimeException("PopulationWrapperLookupableImpl exception. ", e);
        }

        return populationWrappers;
    }

    private QueryByCriteria buildQueryByCriteria(Map<String, String> fieldValues){
        String keyword = fieldValues.get("keyword");

        keyword = keyword.isEmpty()?"*":keyword; //search for all if empty

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.like("name", "%"+keyword+"%"));   //search both columns
        predicates.add(PredicateFactory.or(PredicateFactory.like("descrPlain", "%"+keyword+"%")));

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}