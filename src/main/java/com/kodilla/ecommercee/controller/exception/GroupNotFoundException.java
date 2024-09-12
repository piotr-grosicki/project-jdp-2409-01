package com.kodilla.ecommercee.controller.exception;

public class GroupNotFoundException extends Exception {
    private Long groupId;

    public GroupNotFoundException(Long groupId) {
        super("Group with ID " + groupId + " does not exist.");
        this.groupId = groupId;
    }
}
