package org.kuali.student.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.TypeInfo;
import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.AttributeDef;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.Meta;
import org.kuali.student.core.entity.Type;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.springframework.beans.BeanUtils;

public class BaseAssembler {
	
	protected static Map<String,String> toAttributeMap(
			List<? extends Attribute<?, ?>> attributes) {

		Map<String,String> attributeInfos = new HashMap<String,String>();

		for (Attribute<?, ?> attribute : attributes) {
			attributeInfos.put(attribute.getAttrDef().getName(), attribute.getValue());
		}

		return attributeInfos;
	}

	protected static <A extends Attribute<O, D>, O extends AttributeOwner<A>, D extends AttributeDef> List<A> toGenericAttributes(
			Class<D> attributeDefClass, Class<A> attributeClass,
			Map<String,String> attributeMap, O owner, CrudDao dao)
			throws InvalidParameterException {
		List<A> attributes = new ArrayList<A>();

		// Delete all the old attributes(if the owner is not null)
		for (A attribute : owner.getAttributes()) {
			dao.delete(attribute);
		}
		owner.getAttributes().clear();

		for (Map.Entry<String,String> attributeEntry : attributeMap.entrySet()) {
			// Look up the attribute definition
			D attributeDef = dao.fetchAttributeDefByName(attributeDefClass,
					attributeEntry.getKey());

			if (attributeDef == null) {
				throw new InvalidParameterException("Invalid Attribute : "
						+ attributeEntry.getKey());
			}

			A attribute;
			try {
				attribute = attributeClass.newInstance();
				attribute.setValue(attributeEntry.getValue());
				attribute.setAttrDef(attributeDef);
				attribute.setOwner(owner);
				attributes.add(attribute);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return attributes;
	}
	
	/**
	 * @param <T>
	 *            TypeInfo class
	 * @param <S>
	 *            Type Class
	 * @param typeInfoClass
	 *            the class of the resulting typeInfo object
	 * @param typeEntity
	 *            the typeEntity to copy from
	 * @return a new TypeInfo
	 */
	public static <T extends TypeInfo, S extends Type> T toGenericTypeInfo(
			Class<T> typeInfoClass, S typeEntity) {
		T typeInfo;
		try {
			// Create a new TypeInfo based on the <T> class and copy the
			// properties
			typeInfo = typeInfoClass.newInstance();
			BeanUtils.copyProperties(typeEntity, typeInfo,
					new String[] { "attributes" });

			// Copy the attributes
			typeInfo
					.setAttributes(toAttributeMap(typeEntity.getAttributes()));

			return typeInfo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static <T extends TypeInfo, S extends Type> List<T> toGenericTypeInfoList(
			Class<T> typeInfoClass, List<S> typeEntities) {
		List<T> typeInfoList = new ArrayList<T>();
		for (S typeEntity : typeEntities) {
			typeInfoList.add(toGenericTypeInfo(typeInfoClass, typeEntity));
		}
		return typeInfoList;
	}
	
	protected static MetaInfo toMetaInfo(Meta meta, long versionInd) {

		MetaInfo metaInfo = new MetaInfo();
		// If there was a meta passed in then copy the values
		if (meta != null) {
			BeanUtils.copyProperties(meta, metaInfo);
		}
		metaInfo.setVersionInd(String.valueOf(versionInd));

		return metaInfo;
	}
	
}
