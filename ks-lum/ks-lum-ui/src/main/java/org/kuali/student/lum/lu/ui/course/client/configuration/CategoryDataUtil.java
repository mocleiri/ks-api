package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;

public class CategoryDataUtil {
    
    public static Data toData(LoCategoryInfo loCategoryInfo) {
        LoCategoryInfoHelper catHelper = new LoCategoryInfoHelper();
        catHelper.setId(loCategoryInfo.getId());
        catHelper.setName(loCategoryInfo.getName());
        RichTextInfoHelper catRTHelper = RichTextInfoHelper.wrap(new Data());
        RichTextInfo catRT = loCategoryInfo.getDesc();
        if (null != catRT) {
            catRTHelper.setFormatted(loCategoryInfo.getDesc().getFormatted());
            catRTHelper.setPlain(loCategoryInfo.getDesc().getPlain());
        }
        catHelper.setDesc(catRTHelper.getData());
        catHelper.setLoRepository(loCategoryInfo.getLoRepository());
        catHelper.setEffectiveDate(loCategoryInfo.getEffectiveDate());
        catHelper.setExpirationDate(loCategoryInfo.getExpirationDate());
        /* TODO - doesn't work on the client; what to do?
        AttributesAssembler attAssembler = new AttributesAssembler();
        catHelper.setAttributes(attAssembler.assemble(cat.getAttributes()));
        */
        catHelper.setState(loCategoryInfo.getState());
        catHelper.setType(loCategoryInfo.getType());
        
        // TODO - do we need  MetaInfo communicated to the client, and hence a MetaInfoAssembler?
        /*
        MetaInfo mInfo = loCategory.getMetaInfo();
        MetaInfoHelper metaHelper = MetaInfoHelper.wrap(new Data());
        metaHelper.setCreateId(mInfo.getCreateId());
        ...
        */
        // TODO - LoCategoryInfoAssembler, w/ an assemble method so we can just do 
        // categoriesData.add(LoCategoryInfoAssembler.assemble(cat)) instead
        // of all the above
        return catHelper.getData();
    }
    
    public static LoCategoryInfo toLoCategoryInfo(Data categoryData) {
        LoCategoryInfo catInfo = null;
        if (categoryData != null) {
            LoCategoryInfoHelper catHelper = new LoCategoryInfoHelper(categoryData);
            catInfo = new LoCategoryInfo();
            catInfo.setId(catHelper.getId());
            RichTextHelper rtHelper = new RichTextHelper(catHelper.getDesc());
            // testing
            if (null != catHelper) {
                RichTextInfo descInfo = new RichTextInfo();
                descInfo.setFormatted(rtHelper.getFormatted());
                descInfo.setPlain(rtHelper.getPlain());
                catInfo.setDesc(descInfo);
            }
            catInfo.setEffectiveDate(catHelper.getEffectiveDate());
            catInfo.setExpirationDate(catHelper.getExpirationDate());
            catInfo.setLoRepository(catHelper.getLoRepository());
            // TODO - this should't be necessary when DOL pushed down into LOPicker
            // and its LOCategoryBuilder
            // catInfo.setAttributes(catHelper.getAttributes());
            catInfo.setName(catHelper.getName());
            catInfo.setState(catHelper.getState());
            catInfo.setType(catHelper.getType());
            // TODO - LoCategoryInfoAssembler, w/ a disassemble method so we can just do 
            // categoriesData.add(LoCategoryInfoAssembler.disassemble(catData)) instead
            // of all the above
        }
        return catInfo;
    }
    
}