package org.kuali.student.lum.lrc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.dto.CredentialInfo;
import org.kuali.student.lum.lrc.dto.CredentialTypeInfo;
import org.kuali.student.lum.lrc.dto.CreditInfo;
import org.kuali.student.lum.lrc.dto.CreditTypeInfo;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
import org.kuali.student.lum.lrc.entity.Credential;
import org.kuali.student.lum.lrc.entity.CredentialType;
import org.kuali.student.lum.lrc.entity.Credit;
import org.kuali.student.lum.lrc.entity.CreditType;
import org.kuali.student.lum.lrc.entity.Grade;
import org.kuali.student.lum.lrc.entity.GradeType;
import org.kuali.student.lum.lrc.entity.ResultComponent;
import org.kuali.student.lum.lrc.entity.ResultComponentAttribute;
import org.kuali.student.lum.lrc.entity.ResultComponentType;
import org.kuali.student.lum.lrc.entity.ResultValue;
import org.kuali.student.lum.lrc.entity.Scale;
import org.springframework.beans.BeanUtils;

public class LrcServiceAssembler extends BaseAssembler {
    public static CredentialInfo toCredentialInfo(Credential entity) {
        CredentialInfo dto = new CredentialInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type" });

        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        return dto;
    }

    public static List<CredentialInfo> toCredentialInfos(List<Credential> entities) {
        List<CredentialInfo> dtos = new ArrayList<CredentialInfo>(entities.size());
        for (Credential entity : entities) {
            dtos.add(toCredentialInfo(entity));
        }
        return dtos;
    }


    public static CredentialTypeInfo toCredentialTypeInfo(CredentialType entity) {
        CredentialTypeInfo dto = new CredentialTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<CredentialTypeInfo> toCredentialTypeInfos(List<CredentialType> entities) {
        List<CredentialTypeInfo> dtos = new ArrayList<CredentialTypeInfo>(entities.size());
        for (CredentialType entity : entities) {
            dtos.add(toCredentialTypeInfo(entity));
        }
        return dtos;
    }


    public static CreditInfo toCreditInfo(Credit entity) {
        CreditInfo dto = new CreditInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        return dto;
    }

    public static List<CreditInfo> toCreditInfos(List<Credit> entities) {
        List<CreditInfo> dtos = new ArrayList<CreditInfo>(entities.size());
        for (Credit entity : entities) {
            dtos.add(toCreditInfo(entity));
        }
        return dtos;
    }


    public static RichTextInfo toRichTextInfo(RichText entity) {
        if(entity==null){
            return null;
        }

        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id" });

        return dto;

    }

    public static CreditTypeInfo toCreditTypeInfo(CreditType entity) {
        CreditTypeInfo dto = new CreditTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<CreditTypeInfo> toCreditTypeInfos(List<CreditType> entities) {
        List<CreditTypeInfo> dtos = new ArrayList<CreditTypeInfo>(entities.size());
        for (CreditType entity : entities) {
            dtos.add(toCreditTypeInfo(entity));
        }
        return dtos;
    }

    public static GradeInfo toGradeInfo(Grade entity) {
        GradeInfo dto = new GradeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes", "type" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        return dto;
    }

    public static List<GradeInfo> toGradeInfos(List<Grade> entities) {
        List<GradeInfo> dtos = new ArrayList<GradeInfo>(entities.size());
        for (Grade entity : entities) {
            dtos.add(toGradeInfo(entity));
        }
        return dtos;
    }

    public static GradeTypeInfo toGradeTypeInfo(GradeType entity) {
        GradeTypeInfo dto = new GradeTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<GradeTypeInfo> toGradeTypeInfos(List<GradeType> entities) {
        List<GradeTypeInfo> dtos = new ArrayList<GradeTypeInfo>(entities.size());
        for (GradeType entity : entities) {
            dtos.add(toGradeTypeInfo(entity));
        }
        return dtos;
    }

    public static ResultComponentInfo toResultComponentInfo(ResultComponent entity) {
        ResultComponentInfo dto = new ResultComponentInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "resultValueIds", "desc", "attributes", "type" });
        List<String> ids = new ArrayList<String>(entity.getResultValues().size());
        for (ResultValue rv : entity.getResultValues()) {
            ids.add(rv.getId());
        }
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setResultValueIds(ids);
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        return dto;
    }

    public static List<ResultComponentInfo> toReListComonentInfos(List<ResultComponent> entities) {
        List<ResultComponentInfo> dtos = new ArrayList<ResultComponentInfo>(entities.size());
        for (ResultComponent entity : entities) {
            dtos.add(toResultComponentInfo(entity));
        }
        return dtos;
    }

    public static ResultComponentTypeInfo toResultComponentTypeInfo(ResultComponentType entity) {
        ResultComponentTypeInfo dto = new ResultComponentTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] {"attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }
    public static List<ResultComponentTypeInfo> toResultComponentTypeInfos(List<ResultComponentType> entites) {
        List<ResultComponentTypeInfo> dtos = new ArrayList<ResultComponentTypeInfo>(entites.size());
        for (ResultComponentType entity : entites) {
            dtos.add(toResultComponentTypeInfo(entity));
        }
        return dtos;
    }


    public static ScaleInfo toScaleInfo(Scale entity) {
       ScaleInfo dto = new ScaleInfo();
       BeanUtils.copyProperties(entity, dto,
               new String[] { "desc", "attributes" });
       dto.setDesc(toRichTextInfo(entity.getDesc()));
       dto.setAttributes(toAttributeMap(entity.getAttributes()));
       return dto;
    }

    public static ResultComponent toResultComponent(String resultComponentTypeKey, ResultComponentInfo dto, LrcDao lrcDao) throws DoesNotExistException, InvalidParameterException {
        ResultComponent entity = new ResultComponent();
        toResultComponent(entity, dto, lrcDao);
        ResultComponentType type = lrcDao.fetch(ResultComponentType.class, resultComponentTypeKey);
        entity.setType(type);
        return entity;
    }

    public static void toResultComponent(ResultComponent entity, ResultComponentInfo dto, LrcDao lrcDao) throws DoesNotExistException, InvalidParameterException {
        BeanUtils.copyProperties(dto, entity,
                new String[] { "desc", "resultValueIds", "attributes", "metaInfo", "type", "id" });
        entity.setDesc(toRichText(dto.getDesc()));
        List<ResultValue> resultValues =new ArrayList<ResultValue>(dto.getResultValueIds().size());
        for (String resultValueId : dto.getResultValueIds()) {
            ResultValue resultValue = lrcDao.fetch(ResultValue.class, resultValueId);
            resultValues.add(resultValue);
        }
        entity.setResultValues(resultValues);
        entity.setAttributes(toGenericAttributes(ResultComponentAttribute.class, dto.getAttributes(), entity, lrcDao));
    }
}
