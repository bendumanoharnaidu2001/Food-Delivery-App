package org.swiggy.catalogservice.execptions;

public class MenuItemNotFoundException extends Exception{
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
