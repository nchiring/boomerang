package org.boomerang.exception;

public class TimeOutException extends RuntimeException {

	    public TimeOutException() {
		  super ("Timeout while waiting for a free poolable item.");
		}
	
}
