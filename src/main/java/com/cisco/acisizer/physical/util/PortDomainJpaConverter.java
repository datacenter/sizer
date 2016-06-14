/**
 * 
 */
package com.cisco.acisizer.physical.util;

import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cisco.acisizer.domain.PortDomain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mahesh
 *
 */
@Converter
public class PortDomainJpaConverter implements AttributeConverter<Object, String> {

	
	private Gson gson=new Gson();

	@Override
	public String convertToDatabaseColumn(Object arg0) {
		return gson.toJson(arg0);
	}

	@Override
	public Map<String, PortDomain> convertToEntityAttribute(String arg0) {

		if (arg0 != null) {
			Type type = new TypeToken<Map<String, PortDomain>>() {
			}.getType();
			Map<String, PortDomain> map = gson.fromJson(arg0, type);
			return map;
		} else {
			return null;
		}

	}

}
