package in.co.rays.proj4.exception;

/**
 * RecordNotFoundException thrown when a record not found occurred
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class RecordNotFoundException extends Exception {

    /**
     * @param msg
     *            error message
     */
	public RecordNotFoundException(String msg){
		super(msg);
	}
}
