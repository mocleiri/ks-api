package org.kuali.student.core.organization.assembly;

import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isUpdated;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.assembly.data.client.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgPositionHelper;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgPositionRestrictionAssembler implements Assembler<Data, OrgPositionHelper>{
    private OrganizationService orgService;
    @Override
    public Data assemble(OrgPositionHelper input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgPositionHelper disassemble(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Data get(String id) throws AssemblyException {
        List<OrgPositionRestrictionInfo> positions = new ArrayList<OrgPositionRestrictionInfo>();
        Data orgPositionMap = null;
        try{
            positions = orgService.getPositionRestrictionsByOrg(id);
            orgPositionMap = buildOrgPositionMap(positions);
        }
        catch(DoesNotExistException dnee){
            return null;
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return orgPositionMap;
    }

    @Override
    public Metadata getMetadata(String id, String type, String state) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public void setOrgService(OrganizationService service){
        orgService = service;
    }
    
    @Override
    public SaveResult<Data> save(Data input) throws AssemblyException {
        addPositionRestriction(input);
        SaveResult<Data> result = new SaveResult<Data>();
        List<ValidationResultInfo> validationResults = validate(input);
        result.setValue(input);
        return result;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validate(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    private void addPositionRestriction(Data input) throws AssemblyException{
        if (input == null) {
            return;
        }
        for (Property p : (Data)input.get("OrgPositionRestrictionInfo")) {
            OrgPositionHelper orgPositionHelper=  OrgPositionHelper.wrap((Data)p.getValue());
            if(isUpdated(orgPositionHelper.getData())){
                OrgPositionRestrictionInfo orgPositionRestrictionInfo = buildOrgPositionRestrictionInfo(orgPositionHelper);
                orgPositionRestrictionInfo.setId(orgPositionHelper.getId());
                try{
                    OrgPositionRestrictionInfo  result = orgService.updatePositionRestrictionForOrg(orgPositionRestrictionInfo.getOrgId(), 
                            orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
                    addVersionIndicator(orgPositionHelper.getData(),OrgPositionRestrictionInfo.class.getName(),result.getId(),result.getMetaInfo().getVersionInd());
                }
                catch(Exception e ){
                    throw new AssemblyException();
                }
            }
            else if(isDeleted(orgPositionHelper.getData())){
                try{
                    if(orgPositionHelper.getId()!=null){
                        StatusInfo  result = orgService.removePositionRestrictionFromOrg(orgPositionHelper.getOrgId(), orgPositionHelper.getPersonRelationType());
                    }
                }
                catch(Exception e ){
                    e.printStackTrace();
                    throw(new AssemblyException());
                }
            }
            else if(isCreated(orgPositionHelper.getData())){
                orgPositionHelper.setOrgId((OrgHelper.wrap((Data)input.get("orgInfo")).getId()));
                OrgPositionRestrictionInfo orgPositionRestrictionInfo = buildOrgPositionRestrictionInfo(orgPositionHelper);
                try{
                    OrgPositionRestrictionInfo  result = orgService.addPositionRestrictionToOrg(orgPositionHelper.getOrgId(), 
                            orgPositionHelper.getPersonRelationType(), orgPositionRestrictionInfo);
                    orgPositionHelper.setId(result.getId());
                    addVersionIndicator(orgPositionHelper.getData(),OrgPositionRestrictionInfo.class.getName(),result.getId(),result.getMetaInfo().getVersionInd());
                }
                catch(Exception e ){
                    e.printStackTrace();
                    throw new AssemblyException();
                }
            }
           
          
        }
        
    }
    
    
    private Data buildOrgPositionMap( List<OrgPositionRestrictionInfo> positions){
        Data orgPositions = new Data();
        int count =0;
        for(OrgPositionRestrictionInfo position:positions){
            Data positionMap = new Data();
            OrgPositionHelper orgPositionHelper = OrgPositionHelper.wrap(positionMap);
            orgPositionHelper.setId(position.getId());
            orgPositionHelper.setOrgId(position.getOrgId());
            orgPositionHelper.setPersonRelationType(position.getOrgPersonRelationTypeKey());
            orgPositionHelper.setTitle(position.getTitle());
            orgPositionHelper.setDesc(position.getTitle());
            orgPositionHelper.setMinNumRelations(position.getMinNumRelations());
            orgPositionHelper.setMaxNumRelations(position.getMaxNumRelations());
            addVersionIndicator(orgPositionHelper.getData(),OrgPositionRestrictionInfo.class.getName(),position.getId(),position.getMetaInfo().getVersionInd());
            orgPositions.set(count,orgPositionHelper.getData());
            count = count +1;
        }
        return orgPositions;
    }
    
    private OrgPositionRestrictionInfo buildOrgPositionRestrictionInfo(OrgPositionHelper orgPositionHelper){
        OrgPositionRestrictionInfo orgPositionRestrictionInfo = new OrgPositionRestrictionInfo();
        orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPositionHelper.getPersonRelationType());
        orgPositionRestrictionInfo.setTitle(orgPositionHelper.getTitle());
        orgPositionRestrictionInfo.setDesc(orgPositionHelper.getDesc());
        orgPositionRestrictionInfo.setMinNumRelations(orgPositionHelper.getMinNumRelations());
        orgPositionRestrictionInfo.setMaxNumRelations(orgPositionHelper.getMaxNumRelations());
        orgPositionRestrictionInfo.setOrgId(orgPositionHelper.getOrgId());
        if (isModified(orgPositionHelper.getData())) {
            if (isUpdated(orgPositionHelper.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgPositionRestrictionInfo.setMetaInfo(metaInfo);
                orgPositionRestrictionInfo.setId(orgPositionHelper.getId());
            }
            else if (isDeleted(orgPositionHelper.getData())) {
            }
            else if (isCreated(orgPositionHelper.getData())) {
            } 
        }
        if(orgPositionRestrictionInfo.getMetaInfo()!=null){
            orgPositionRestrictionInfo.getMetaInfo().setVersionInd(getVersionIndicator(orgPositionHelper.getData()));
        }
        return orgPositionRestrictionInfo;
    }

}
