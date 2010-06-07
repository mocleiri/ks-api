package org.kuali.student.core.assembly.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.DataBeanMapper;
import org.kuali.student.core.assembly.data.Data.IntegerKey;
import org.kuali.student.core.assembly.data.Data.Key;
import org.kuali.student.core.assembly.data.Data.StringKey;


public class DefaultDataBeanMapper implements DataBeanMapper {

	/* (non-Javadoc)
	 * @see org.kuali.student.core.assembly.data.DataBeanMapper#convertFromBean(java.lang.Object)
	 */
	public Data convertFromBean(Object value) throws Exception{
		 Data result = new Data();
	        
	        if (value != null) {
                BeanInfo info = Introspector.getBeanInfo(value.getClass());
                PropertyDescriptor[] properties = info.getPropertyDescriptors();
                 
                for (PropertyDescriptor pd : properties) {
                    String propKey = pd.getName();
                    Object propValue = pd.getReadMethod().invoke(value, (Object[]) null);
                    
                    if ("attributes".equals(propKey)){
       					setDataAttributes(result, propValue);
                    } else {
	                    setDataValue(result, propKey, propValue);
                    }
                }
	        }	
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.student.core.assembly.data.DataBeanMapper#convertFromData(org.kuali.student.core.assembly.data.Data, java.lang.Class)
	 */
	public Object convertFromData(Data data, Class<?> clazz) throws Exception{
		Object result = null;
		
		result = clazz.newInstance();
        BeanInfo info = Introspector.getBeanInfo(result.getClass());
        PropertyDescriptor[] properties = info.getPropertyDescriptors();

        PropertyDescriptor attrProperty = null;
        
        Set<Key> staticProperties = new HashSet<Key>();
		for (PropertyDescriptor pd : properties) {
			if ("attributes".equals(pd.getName())){
				//Dynamic attributes will be handled later
				attrProperty = pd;
			} else {
	            StringKey propKey = new StringKey(pd.getName());            
	            Object propValue = data.get(propKey);
	
	            //Process a nested object structure or list
	            if (propValue instanceof Data){
	            	clazz.getFields();
	            	propValue = convertNestedData((Data)propValue, clazz.getDeclaredField(propKey.toString()));
	            }
	            
	    		//Set the bean property
	    		if(pd.getWriteMethod() != null & propValue != null){    
	                pd.getWriteMethod().invoke(result, new Object[] {propValue});
	            }
	            
	    		//Hold onto the property so we know it is not dynamic
	    		staticProperties.add(propKey);
			}
		}
		
		//Any fields not processed above doesn't exist as properties for the bean and 
		//will be set as dynamic attributes.
		Set<Key> keySet = (Set<Key>)data.keySet();
		if (keySet != null && attrProperty != null){
			Map<String,String> attributes = new HashMap<String,String>();
			for (Key k:keySet){
				String keyString = k.toString();
				if (!staticProperties.contains(k) && !keyString.startsWith("_run")){
					attributes.put((String)k.get(),(String)data.get(k));
				}
			}
    		if(attrProperty.getWriteMethod() != null){    
                attrProperty.getWriteMethod().invoke(result, new Object[] {attributes});
            }
		}

		return result;
	}
	
	
	/**
	 * Processes a nested data map, it checks to see if the data should be converted to 
	 * a list structure or simply be processed as a nested complex object structure.
	 * 
	 * @param data
	 * @param propField
	 * @return
	 * @throws Exception
	 */
	protected Object convertNestedData(Data data, Field propField) throws Exception{
		Object result = null;

		Class<?> propClass = propField.getType();
		if ("java.util.List".equals(propClass.getName())){
			//Get the generic type for the list
			ParameterizedType propType = (ParameterizedType)propField.getGenericType();
			Type itemType = propType.getActualTypeArguments()[0];
			
			List<Object> resultList = new ArrayList<Object>();
			@SuppressWarnings("unchecked")
			Set<IntegerKey> keySet = (Set<IntegerKey>)data.keySet();
			for (IntegerKey key:keySet){
				Object itemValue = data.get(key);
				if (itemValue instanceof Data ){
					itemValue = convertFromData((Data)itemValue, (Class<?>)itemType);
				}
				resultList.add(((Integer)key.get()).intValue(),itemValue);
			}
			result = resultList;
		} else {
			result = convertFromData(data, propClass);
		}
		
		return result;
	}
	
	/**
	 * Used to set a simple property value into the data object. 
	 * @param data
	 * @param propertyKey
	 * @param value
	 * 
	 */
	protected void setDataValue(Data data, String propertyKey, Object value) throws Exception{
		if (isPropertyValid(value)){
			if (value instanceof Boolean){
				data.set(propertyKey, (Boolean)value);
			} else if (value instanceof Date){
				data.set(propertyKey, (Date)value);
			} else if (value instanceof Integer){
				data.set(propertyKey, (Integer)value);
			} else if (value instanceof Double){
				data.set(propertyKey, (Double)value);
			} else if (value instanceof Float){
				data.set(propertyKey, (Float)value);
			} else if (value instanceof Long) {
				data.set(propertyKey, (Long)value);
			} else if (value instanceof Short){
				data.set(propertyKey, (Short)value);			
			} else if (value instanceof String){
				data.set(propertyKey, (String)value);
			} else if (value instanceof Timestamp){
				data.set(propertyKey, (Timestamp)value);
			} else if (value instanceof Time){
				data.set(propertyKey, (Time)value);
			} else if (value instanceof Collection){
				data.set(propertyKey, getCollectionData(value));
			} else {
				Data dataValue = convertFromBean(value);
				data.set(propertyKey, dataValue);
			}
		}
		
	}
	
	/**
	 * This process the attributes map and sets the attribute key/value pairs into the Data map
	 * 
	 * @param data
	 * @param value
	 */
	protected void setDataAttributes(Data data, Object value) {
		@SuppressWarnings("unchecked")
		Map<String, String> attributes = (Map<String, String>)value;
		
		for (Entry<String, String> entry:attributes.entrySet()){
			data.set(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Used to set a list item value into the data object.
	 * 
	 * @param data
	 * @param value
	 */
	protected void setDataListValue(Data data, Object value) throws Exception{
		if (isPropertyValid(value)){
			if (value instanceof Boolean){
				data.add((Boolean)value);
			} else if (value instanceof Date){
				data.add((Date)value);
			} else if (value instanceof Integer){
				data.add((Integer)value);
			} else if (value instanceof Double){
				data.add((Double)value);
			} else if (value instanceof Float){
				data.add((Float)value);
			} else if (value instanceof Long) {
				data.add((Long)value);
			} else if (value instanceof Short){
				data.add((Short)value);			
			} else if (value instanceof String){
				data.add((String)value);
			} else if (value instanceof Timestamp){
				data.add((Timestamp)value);
			} else if (value instanceof Time){
				data.add((Time)value);
			} else if (value instanceof Collection){
				data.add(getCollectionData(value));
			} else {
				Data dataValue = convertFromBean(value);
				data.add(dataValue);			
			}
		}
	}
	
	/**
	 * Returns a data map object representing a collection
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	protected Data getCollectionData(Object value) throws Exception{
		Data result = new Data();
		
		if (value instanceof List){
			List<?> valueList = (List<?>)value;
			for (int i=0;i<valueList.size();i++){
				Object itemValue = valueList.get(i); 
				setDataListValue(result, itemValue);
			}
		} else {
			Collection<?> valueCollection = (Collection<?>)value;
			for (Object o:valueCollection){
				setDataListValue(result, o);
			}
		}
		
		return result;
	}
	
	protected boolean isPropertyValid(Object value){
		boolean isValid = false;
		
		if (value != null){
			Class<?> clazz = value.getClass();
			isValid = !(clazz.isArray() || clazz.isAnnotation() || value instanceof Class);
		}
		
		return isValid;
	}
}
