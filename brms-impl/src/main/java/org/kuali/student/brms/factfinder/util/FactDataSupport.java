package org.kuali.student.brms.factfinder.util;

import java.util.List;

import org.kuali.student.brms.factfinder.dto.FactTypeInfoDTO;

public class FactDataSupport {

    private List<FactTypeInfoDTO> factTypeInfoList;

    /**
     * @return the factTypeInfoList
     */
    public List<FactTypeInfoDTO> getFactTypeInfoList() {
        return factTypeInfoList;
    }

    /**
     * @param factTypeInfoList the factTypeInfoList to set
     */
    public void setFactTypeInfoList(List<FactTypeInfoDTO> factTypeInfoList) {
        this.factTypeInfoList = factTypeInfoList;
    }        
}
