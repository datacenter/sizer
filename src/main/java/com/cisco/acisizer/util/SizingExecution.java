package com.cisco.acisizer.util;

import com.cisco.acisizer.exceptions.AciFileIoException;
import com.cisco.acisizer.exceptions.AciSizingException;

public interface SizingExecution {

	void writeJsonToFile(String str_FileDirectory, String str_Content, String str_FileName) throws AciFileIoException;

	String executeTool(String fileDirectory, String fileName) throws  AciSizingException;

	void deleteTheGeneratedFile(String toolDirectory, String file) throws AciFileIoException;

}