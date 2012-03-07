package org.kuali.student.conversion.util;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.r1.common.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;

public class LumConverterTest {

    @Test
    public void testLoCategoryInfo() {
        org.kuali.student.r1.lum.lo.dto.LoCategoryInfo r1 = new org.kuali.student.r1.lum.lo.dto.LoCategoryInfo();
        r1.setLoRepository("R1 Lo Repository");
        LoCategoryInfo r2 = R1R2ConverterUtil.convert(r1, LoCategoryInfo.class);
        Assert.assertEquals(r1.getLoRepository(), r2.getLoRepositoryKey());
    }
    
    @Test
    public void testCredentialProgramInfo() {
        org.kuali.student.r1.lum.program.dto.CredentialProgramInfo r1 = new org.kuali.student.r1.lum.program.dto.CredentialProgramInfo();
        r1.setType("R1 Type");
        r1.setCredentialProgramType("R1 Credential Program Type");
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        CredentialProgramInfo r2 = R1R2ConverterUtil.convert(r1, CredentialProgramInfo.class);
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getCredentialProgramType(), r2.getCredentialProgramType());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getDescr().getFormatted(), r2.getDescr().getFormatted());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersion().getVersionIndId());
    }
    
    @Test
    public void testActivityInfo() {
        org.kuali.student.r1.lum.course.dto.ActivityInfo r1 = new org.kuali.student.r1.lum.course.dto.ActivityInfo();
        r1.setState("R1 State");
        r1.setActivityType("R1 ActivityType");
        ActivityInfo r2 = R1R2ConverterUtil.convert(r1, ActivityInfo.class);
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getActivityType(), r2.getTypeKey());
        org.kuali.student.r1.lum.course.dto.ActivityInfo r1a = R1R2ConverterUtil.convert(r2, org.kuali.student.r1.lum.course.dto.ActivityInfo.class);
        Assert.assertEquals(r2.getStateKey(), r1a.getState());
        Assert.assertEquals(r2.getTypeKey(), r1a.getActivityType());
    }
}
