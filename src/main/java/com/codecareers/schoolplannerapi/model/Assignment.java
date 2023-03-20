package com.codecareers.schoolplannerapi.model;

/* A model class to represent an Assignment object */
public class Assignment {

    // Here are the fields we want our Assignment object to have
    private int assigmentID;
    
    private String name;

    private String description;

    private String classCategory;

    private boolean isComplete;

    private String createDate;

    private String dueDate;

    /**
     * Empty constructor for an empty Assignment object
     */
    public Assignment() {

    }

    /**
     * A constructor that is used to create an Assignment object.
     * @param assigmentID
     * @param name
     * @param description
     * @param classCategory
     * @param isComplete
     * @param createDate
     * @param dueDate
     */
    public Assignment(int assigmentID, String name, String description, String classCategory, boolean isComplete, String createDate, String dueDate) {
        this.assigmentID = assigmentID;
        this.name = name;
        this.description = description;
        this.classCategory = classCategory;
        this.isComplete = isComplete;
        this.createDate = createDate;
        this.dueDate = dueDate;
    }
//redump.org dat files
    // Below here are all the getters and setters for the data above

    public int getAssignmentID() {
        return this.assigmentID;
    }

    public void setAssignmentID(int assigmentID) {
        this.assigmentID = assigmentID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassCategory() {
        return this.classCategory;
    }

    public void setClassCategory(String classCategory) {
        this.classCategory = classCategory;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
