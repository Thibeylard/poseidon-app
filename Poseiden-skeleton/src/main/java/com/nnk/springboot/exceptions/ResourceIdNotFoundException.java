package com.nnk.springboot.exceptions;

public class ResourceIdNotFoundException extends Exception {
    private Integer invalidID;

    public ResourceIdNotFoundException(String s, int invalidID) {
        super(s);
        this.invalidID = invalidID;
    }

    public ResourceIdNotFoundException(int invalidID) {
        super("Requested resource with " + invalidID + " ID could not be found.");
        this.invalidID = invalidID;
    }

    public int getInvalidID() {
        return invalidID;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
