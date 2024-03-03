package org.swiggy.catalogservice.execptions;

public class NoMenuItemFoundException extends Exception{
    public NoMenuItemFoundException(String message) {
        super(message);
    }
}
