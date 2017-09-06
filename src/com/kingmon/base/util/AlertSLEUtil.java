package com.kingmon.base.util;

import com.kingmon.base.exception.ServiceLogicalException;

public class AlertSLEUtil {
	
	public static void Error(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException(message);
	}
	
	public static void isNull(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException(message);
	}

	public static void notExist(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException( message);
	}

	public static void FormatError(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException( message);
	}

	public static void SqlError(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException( message);
	}

	public static void IOError(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException(message);
	}

	public static void FileError(String message) throws ServiceLogicalException {
		throw new ServiceLogicalException(message);
	}


}
