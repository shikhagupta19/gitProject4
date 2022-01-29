package in.co.rays.proj4.exception;

/**
 * ApplicationException is propogate from Service classes when an business
 * logic exception occurred.
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class ApplicationException extends Exception{


    /**
     * @param msg
     *            : Error message
     */
	public ApplicationException(String msg){
		super(msg);
	}
}
