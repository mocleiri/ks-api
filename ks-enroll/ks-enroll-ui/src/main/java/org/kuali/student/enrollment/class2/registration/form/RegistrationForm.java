package org.kuali.student.enrollment.class2.registration.form;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.kuali.rice.kns.web.spring.form.UifFormBase;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.util.List;

public class RegistrationForm extends UifFormBase {

    private static final long serialVersionUID = 2554632701931313545L;

    private String programs;
    private String courseNameOrNumber;
    private List<CourseOfferingInfo> courseOfferings;

    public RegistrationForm(){
        super();
    }

    public String getCourseNameOrNumber() {
        return courseNameOrNumber;
    }

    public void setCourseNameOrNumber(String courseNameOrNumber) {
        this.courseNameOrNumber = courseNameOrNumber;
    }

    public String getPrograms() {
        return programs;
    }

    public void setPrograms(String programs) {
        this.programs = programs;
    }
}