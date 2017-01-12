package com.adservio.storage;

public class StorageException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8999438520805840518L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
