package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluIdentifierInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluIdentifierInfoMetadata;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;

public class CluIdentifierInfoAssembler implements Assembler<Data, CluIdentifierInfo>{

	@Override
	public Data assemble(CluIdentifierInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluIdentifierInfoHelper result = CluIdentifierInfoHelper.wrap(new Data());
		result.setId(input.getCluId());
		result.setCode(input.getCode());
		result.setDivision(input.getDivision());
		result.setId(input.getId());
		result.setLevel(input.getLevel());
		result.setLongName(input.getLongName());
		result.setOrgId(input.getOrgId());
		result.setShortName(input.getShortName());
		result.setState(input.getState());
		result.setSuffixCode(input.getSuffixCode());
		result.setType(input.getType());
		result.setVariation(input.getVariation());
		return result.getData();
	}

	@Override
	public CluIdentifierInfo disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluIdentifierInfo result = new CluIdentifierInfo();
		CluIdentifierInfoHelper hlp = CluIdentifierInfoHelper.wrap(input);
		result.setCluId(hlp.getId());
		result.setCode(hlp.getCode());
		result.setDivision(hlp.getDivision());
		result.setId(hlp.getId());
		result.setLevel(hlp.getLevel());
		result.setLongName(hlp.getLongName());
		result.setOrgId(hlp.getOrgId());
		result.setShortName(hlp.getShortName());
		result.setState(hlp.getState());
		result.setSuffixCode(hlp.getSuffixCode());
		result.setType(hlp.getType());
		result.setVariation(hlp.getVariation());
		return result;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(CluIdentifierInfoAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String type, String state) throws AssemblyException {
		return new CluIdentifierInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(CluIdentifierInfoAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		return null;
	}
	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
