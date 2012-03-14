package org.kuali.student.r1.common.validator;

import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.validation.dto.ValidationResultInfo;

//TODO KSCM-428 Hopefully this interface isn't here after refactoring, a equivelent with Contextinfo
//class has been created in R2 packages with upgraded metods/parameters

@Deprecated
public interface Validator {

	public List<ValidationResultInfo> validateObject(Object o, ObjectStructureDefinition objStructure);
	public List<ValidationResultInfo> validateObject(FieldDefinition field,Object o, ObjectStructureDefinition objStructure,Stack<String> elementStack);
}
