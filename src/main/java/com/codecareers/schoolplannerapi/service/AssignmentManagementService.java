package com.codecareers.schoolplannerapi.service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codecareers.schoolplannerapi.datalayer.SimpleTextDBImp;
import com.codecareers.schoolplannerapi.model.Assignment;

@Service
public class AssignmentManagementService {

    @Autowired
    SimpleTextDBImp simpleTextDBImp;

    public int addAssignment(Assignment assignment) {
        // create a SimpleDateFormat object with the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        
        // create a Date object with the current time
        Date currentDate = new Date();
        
        // format the date object using the SimpleDateFormat object
        String formattedDate = dateFormat.format(currentDate);

        // Assign the current date to the created date of the assignment
        assignment.setCreateDate(formattedDate);

        return simpleTextDBImp.save(assignment);
    }

    public Assignment getAssignment(int id) {
        return simpleTextDBImp.findByID(id);
    }

    public List<Assignment> getAssignmentByClass(String classCategory) {
        return simpleTextDBImp.findByClass(classCategory);
    }

    public List<Assignment> getAssignmentByCurrentlyDue() {
        return simpleTextDBImp.findByCurrentlyDue();
    }

    public boolean updateAssignment(Assignment updatedAssignment) {
        return simpleTextDBImp.update(updatedAssignment);
    }

    public boolean deleteAssignment(int id) {
        return simpleTextDBImp.delete(id);
    }

    public List<Assignment> listIncompleteAssignments() {

        return null;
    }

    public List<Assignment> listCompleteAssignments() {

        return null;
    }

}
