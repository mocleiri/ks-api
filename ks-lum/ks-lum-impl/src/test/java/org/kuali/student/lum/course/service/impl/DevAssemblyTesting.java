package org.kuali.student.lum.course.service.impl;

import static org.junit.Assert.assertNotNull;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:course-dev-test-context.xml"})
public class DevAssemblyTesting {

	@Autowired
	CourseService courseService;
	
	@Test
	public void testCreate() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		CourseDataGenerator courseGen = new CourseDataGenerator();
		CourseInfo course = courseGen.getCourseTestData();
		CourseInfo createdCourse = courseService.createCourse(course);
		assertNotNull(createdCourse);
	}
}
