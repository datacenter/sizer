/**
 * 
 */
package com.cisco.acisizer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.cisco.acisizer.exceptions.AciFileIoException;
import com.cisco.acisizer.exceptions.AciSizingException;

/**
 * @author Mahesh
 *
 */

@Component
@Profile("stage")
public class SizingExecOnLinux implements SizingExecution {
	private static final Logger LOGGER = LoggerFactory.getLogger(SizingExecOnLinux.class);

	@Override
	public void writeJsonToFile(String directory, String content, String str_FileName) throws AciFileIoException {
		try {
			FileUtils.writeStringToFile(new File(getPathName(directory) + str_FileName), content);
		} catch (IOException e) {
			throw new AciFileIoException("Error writing the input Json File", e.getCause());
		}
	}

	@Override
	public String executeTool(String directory, String fileName) throws AciSizingException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.redirectErrorStream(true); // This is the important part
		// ServletContext context = getServletContext();
		// LOGGER.info(new
		// File(getClass().getClassLoader().getResource("/acitool/").toString()).getAbsolutePath());
		// LOGGER.info(ResourceLoader.class.getResource("/acitool/").toString());
		// File abPath=new File(directory);
		// LOGGER.info(abPath.getAbsolutePath());
		// LOGGER.info(Paths.get(".").toAbsolutePath().toString());
		String path = getPathName(directory);
		LOGGER.info(path);
		builder.command(path + ACISizerConstant.CISCO_SIZING_TOOL_SCALE_CALC, path + fileName);

		String line = "";
		StringBuilder resultBuilder = new StringBuilder();
		Process process;
		try {
			process = builder.start();
			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			line = reader.readLine();

			while (line != null) {
				resultBuilder.append(line);
				line = reader.readLine();
			}
			process.waitFor();
			if (process.exitValue() != 0) {
				throw new AciSizingException(
						ACISizerConstant.ERROR_IN_SIZING + resultBuilder.toString().split("at")[1].split(",")[0]);
			}
		} catch (IOException e) {
			throw new AciSizingException(ACISizerConstant.ERROR_IN_SIZING, e.getCause());
		} catch (Exception e) {
			LOGGER.info("Error in sizing Linux :" + e.getCause());
			e.printStackTrace();
			throw new AciSizingException(ACISizerConstant.ERROR_IN_SIZING + e);
		}
		return resultBuilder.toString();
	}

	private String getPathName(String directory) {
		String absolutePath = Paths.get("").toAbsolutePath().getParent().toString()+directory;
		
		LOGGER.info("full path new : "+absolutePath);
		
		return absolutePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cisco.acisizer.util.SizingExecution#deleteTheGeneratedFile(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void deleteTheGeneratedFile(String toolDirectory, String file) throws AciFileIoException {
		try {
			FileUtils.forceDelete(new File(getPathName(toolDirectory) + file));
		} catch (IOException e) {
			throw new AciFileIoException("Error deleting the input Json File", e.getCause());
		}
	}

}
