package com.zz.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

public class BaseException extends GenericException {
	private static final long serialVersionUID = 1L;

	public BaseException(String message) {
		super(message);
	}

	public static String setMessage(ExceptionCodes code, Object[] args) {
		String exceptionMessage = code.getContent();
		String exceptionCode = code.getCode();
		if (args != null && args.length > 0) {
			exceptionMessage = MessageFormat.format(exceptionMessage, args);
		}
		String exceptionContent = "{code:" + exceptionCode + ",message:" + exceptionMessage + "}";
		return exceptionContent;
	}

	public static String setMessage(ExceptionCodes code) {
		String exceptionMessage = code.getContent();
		String exceptionCode = code.getCode();
		String exceptionContent = "{code:" + exceptionCode + ",message:" + exceptionMessage + "}";
		return exceptionContent;
	}

	public static String setStackTraces(Exception e) {
		StringWriter out = new StringWriter();
		PrintWriter p = new PrintWriter(out);
		e.printStackTrace(p);
		String exceptionMessage = out.toString();
		String exceptionContent = "{code:'00000',message:" + exceptionMessage + "}";
		return exceptionContent;
	}
}
