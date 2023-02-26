package com.codecareers.schoolplannerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codecareers.schoolplannerapi.model.Assignment;
import com.codecareers.schoolplannerapi.service.AssignmentManagementService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AssignmentController {

    @Autowired
    AssignmentManagementService assignmentManagementService;

    @PostMapping("/add")
    public String add(@RequestBody Assignment assignment) {
        int id = assignmentManagementService.addAssignment(assignment);
        if(id >= 0) {
            return "Assignment added successfully with ID: " + id;
        }
        else {
            return "Assignment failed to add";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody Assignment assignment) {
        log.info("Assignment ID at start: " + assignment.getAssignmentID());
        log.info("Assignment name: " + assignment.getName());
        boolean success = assignmentManagementService.updateAssignment(assignment);
        if(success) {
            return "Assignment updated successfully";
        }
        else {
            return "Assignment failed to update";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        boolean success = assignmentManagementService.deleteAssignment(id);
        if(success) {
            return "Assignment deleted successfully";
        }
        else {
            return "Assignment failed to delete";
        }
    }

    @GetMapping("/assignment")
    public @ResponseBody Assignment assignment(@RequestParam int id) {
        return assignmentManagementService.getAssignment(id);
    }
    
}
