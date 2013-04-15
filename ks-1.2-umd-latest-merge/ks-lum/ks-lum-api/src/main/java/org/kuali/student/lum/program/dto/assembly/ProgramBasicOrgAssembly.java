/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.program.dto.assembly;

import java.util.List;

public interface ProgramBasicOrgAssembly extends ProgramCommonAssembly {

    public List<String> getDivisionsContentOwner() ;
    public void setDivisionsContentOwner(List<String> divisionsContentOwner);

    public List<String> getDivisionsStudentOversight();
    public void setDivisionsStudentOversight(List<String> divisionsStudentOversight) ;

    public List<String> getUnitsContentOwner() ;
    public void setUnitsContentOwner(List<String> unitsContentOwner);

    public List<String> getUnitsStudentOversight() ;
    public void setUnitsStudentOversight(List<String> unitsStudentOversight);

}
