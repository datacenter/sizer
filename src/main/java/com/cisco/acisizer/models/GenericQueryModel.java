package com.cisco.acisizer.models;

import java.util.ArrayList;


import javax.xml.bind.annotation.XmlRootElement;

import com.cisco.acisizer.generic.GenericRequestBody;
@XmlRootElement
public class GenericQueryModel {

	private ArrayList<GenericRequestBody> queryList = new ArrayList<GenericRequestBody>();

	private String responseType;
	
	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public ArrayList<GenericRequestBody> getQueryList() {
		return queryList;
	}

	public void setQueryList(ArrayList<GenericRequestBody> queryList) {
		this.queryList = queryList;
	}

	
}
