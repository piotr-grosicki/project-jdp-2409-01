package com.kodilla.ecommercee.controller.exception;

public class ParentGroupNotFoundException extends Exception {
    private Long parentGroupId;

    public ParentGroupNotFoundException(Long parentGroupId) {
        super("Parent group with ID " + parentGroupId + " does not exist.");
        this.parentGroupId = parentGroupId;
    }
}
