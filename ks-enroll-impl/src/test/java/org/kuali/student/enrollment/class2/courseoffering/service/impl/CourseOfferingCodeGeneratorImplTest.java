package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 12/5/12
 * Time: 9:23 AM
 *
 * This file is designed to test CourseOfferingCodeGeneratorImpl.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
public class CourseOfferingCodeGeneratorImplTest {

    private static final Logger log = Logger.getLogger(CourseOfferingCodeGeneratorImplTest.class);

    @Resource
    CourseOfferingCodeGenerator offeringCodeGenerator;


    protected List<ActivityOfferingInfo> _getBaseAOList(){
        List<ActivityOfferingInfo> aoList = new ArrayList<ActivityOfferingInfo>();

        String courseOfferingCode = "ENGL101";

        ActivityOfferingInfo ao1 = new ActivityOfferingInfo();
        ao1.setActivityCode("A");
        ao1.setCourseOfferingCode(courseOfferingCode);
        aoList.add(ao1);

        ActivityOfferingInfo ao2 = new ActivityOfferingInfo();
        ao2.setActivityCode("B");
        ao2.setCourseOfferingCode(courseOfferingCode);
        aoList.add(ao2);

        ActivityOfferingInfo ao3 = new ActivityOfferingInfo();
        ao3.setActivityCode("C");
        ao3.setCourseOfferingCode(courseOfferingCode);
        aoList.add(ao3);


        return aoList;
    }


    @Test
    public void testGenerateActivityOfferingCode(){

        String courseOfferingCode = "ENGL101";
        List<ActivityOfferingInfo> aoList = _getBaseAOList();

        String nextCode = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,aoList);

        // the list passed in above is A,B,C so the next should be D
        assertTrue("D".equals(nextCode));

        // Lets add a gap
        ActivityOfferingInfo ao1 = new ActivityOfferingInfo();
        ao1.setActivityCode("E");
        ao1.setCourseOfferingCode("ENGL101");
        aoList.add(ao1);

        nextCode = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,aoList);

        // the list passed in above is A,B,C,E so we should fill in the gap with D
        assertTrue ("D".equals(nextCode));

    }


    /**
     *     This class is set to ignore right now, but should be set to @Test once our concurrency
     *     issue is solved.
     */
    @Test
    public void testGenerateActivityOfferingCodeMultiThread(){
        try{
            test("ENGL101", 6);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void test(final String courseOfferingCode, final int threadCount) throws InterruptedException, ExecutionException {


        Callable<String> task = new Callable<String>() {

            @Override

            public String call() {

                return offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,new ArrayList<ActivityOfferingInfo>());

            }

        };

        List<Callable<String>> tasks = Collections.nCopies(threadCount, task);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Future<String>> futures = executorService.invokeAll(tasks);

        List<String> resultList = new ArrayList<String>(futures.size());

        // Check for exceptions

        for (Future<String> future : futures) {

            // Throws an exception if an exception was thrown by the task.

            resultList.add(future.get());

        }

        // Validate the IDs

        assert (futures.size() ==  threadCount);

        List<String> expectedList = new ArrayList<String>(threadCount);

        String nextCode = "";
        for (long i = 1; i <= threadCount; i++) {
            nextCode = getNextCode(nextCode);
            expectedList.add(nextCode);

        }

        Collections.sort(resultList);

        for(int i=0;i< resultList.size(); i++){
            assertTrue("\nWas expecting \n[" + expectedList.get(i) + "] but got \n[" + resultList.get(i) + "]\n" , expectedList.get(i).equals(resultList.get(i)));
        }

    }

    public String getNextCode(String source){
        if (StringUtils.isEmpty(source)){
            return "A";
        } else if (StringUtils.endsWithIgnoreCase(source,"Z")){
            return getNextCode(StringUtils.substringBeforeLast(source,"Z")) + "A";
        } else {
            char lastLetter = source.charAt(source.length()-1);
            return StringUtils.substringBeforeLast(source,""+lastLetter) + ++lastLetter;
        }
    }




}
