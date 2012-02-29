package org.kuali.student.r1.core.util;

import java.util.ArrayList;
import java.util.List;
//import org.kuali.student.r1.common.assemble.BaseDTOAssemblyNode;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

public class R1R2ConverterUtil {

    private final static Mapper dozerMapper = DozerBeanMapperSingletonWrapper.getInstance();
    
    public static <T, X> X convert(T source, Class<X> target) {
        return dozerMapper.map(source, target);
    }

    public static <T, X> X convert(T source, X target) {
        dozerMapper.map(source, target);
        return target;
    }

    public static <T, X> List<X> convertLists(List<T> sourceList, Class<X> targetListType) {
        List<X> targetList = new ArrayList<X>();
        for (T sourceObject : sourceList) {
            X targetObject = convert(sourceObject, targetListType);
            targetList.add(targetObject);
        }
        return targetList;
    }
    
//    public static <A ,B ,C ,D >  BaseDTOAssemblyNode<A,B> convertBOasm(BaseDTOAssemblyNode<C,D> source, BaseDTOAssemblyNode<A,B> target)
//    {
//
//    	
//    	
//    	
//    	return target;
//    	
//    }
//    
}