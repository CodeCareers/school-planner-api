package com.codecareers.schoolplannerapi.datalayer;

import com.codecareers.schoolplannerapi.model.Assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/* An implementation file for the SimpleTextDB interface */
@Component
@Slf4j
public class SimpleTextDBImp implements SimpleTextDB<Assignment> {

    // Constant that holds our "database" file name
    private final String FILENAME = "SimpleTextDB.xlsx";

    private final String TABLE_ASSIGNMENTS = "Assignments";

    /**
     * Private class used to create the "database" file
     * @return true if the "database" was succefully made or false if it wasn't
     */
    private boolean createTextDB() {
        // boolean to send back saying if creation was successful
        boolean pass = false;

        // Check if file already exists, if it does exit the function
        File file = new File(FILENAME);
        boolean fileExists = file.exists();
        if(fileExists) {
            log.info("File already exits.");
            return pass;
        }

        // Create new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create new sheet
        XSSFSheet sheet = workbook.createSheet(TABLE_ASSIGNMENTS);

        // Create a Cell Style to set text wrapping on cells
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        // Create Table Name row
        Row tableHeaderRow = sheet.createRow(0);
        Cell tableHeaderCell = tableHeaderRow.createCell(0);
        tableHeaderCell.setCellStyle(style);
        tableHeaderCell.setCellValue("Table:");
        tableHeaderCell = tableHeaderRow.createCell(1);
        tableHeaderCell.setCellValue("Assignments");

        // Create Table Column Names row
        Row columnNameRow = sheet.createRow(1);

        // Keep track of current cell
        int cellNum = 0;

        // Set name Column
        Cell columnNameCell = columnNameRow.createCell(cellNum);
        columnNameCell.setCellStyle(style);
        columnNameCell.setCellValue("name");

        // Set description Column
        cellNum++;
        columnNameCell = columnNameRow.createCell(cellNum);
        columnNameCell.setCellStyle(style);
        columnNameCell.setCellValue("description");

        // Set class Column
        cellNum++;
        columnNameCell = columnNameRow.createCell(cellNum);
        columnNameCell.setCellStyle(style);
        columnNameCell.setCellValue("class");

        // Set isComplete Column
        cellNum++;
        columnNameCell = columnNameRow.createCell(cellNum);
        columnNameCell.setCellStyle(style);
        columnNameCell.setCellValue("isComplete");

        // Set createDate Column
        cellNum++;
        columnNameCell = columnNameRow.createCell(cellNum);
        columnNameCell.setCellStyle(style);
        columnNameCell.setCellValue("createDate");

        // Set dueDate Column
        cellNum++;
        columnNameCell = columnNameRow.createCell(cellNum);
        columnNameCell.setCellStyle(style);
        columnNameCell.setCellValue("dueDate");

        // Set column size
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(1, 40 * 256);
        sheet.setColumnWidth(2, 30 * 256);
        sheet.autoSizeColumn(3);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);

        // Write workbook to file
        try (FileOutputStream outputStream = new FileOutputStream(FILENAME)) {
            workbook.write(outputStream);
            workbook.close();
            pass = true;
            log.info("SimpleTextDB created");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pass;
    }

    /**
     * Passes back a boolean indicating if the "database" file exists
     * @return true if the "database" file exists, or false if it doesn't
     */
    private boolean doesDBExist() {
        File file = new File(FILENAME);
        return file.exists();
    }

    /**
     * Implementation of save() function used to add an assignment to a text file.
     * @param assignment
     * @return ID of new row created or -1 if no assignment row was created
     */
    @Override
    public int save(Assignment assignment) {
        // Check if "database" file exists, if not create one
        if(!doesDBExist()) {
            createTextDB();
        }

        // The assignment ID we will return. 
        // If creation is successful it will be changed to the actual ID
        int assigmentID = -1;

        try {
            // Open the "database" workbook
            FileInputStream file = new FileInputStream(FILENAME);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Grab the Assignments sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(TABLE_ASSIGNMENTS);

            // Create a Cell Style to set text wrapping on cells
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            // Check for next empty row to add an assignment in
            int rowNum = 0;
            Row row = sheet.getRow(rowNum);
            while(row != null) {
                rowNum = row.getRowNum();
                row = sheet.getRow(rowNum + 1);
            }

            // Add one to row number to create information on next available row in sheet
            rowNum++;

            // Set assignment ID to the new row number
            assigmentID = rowNum + 1;

            // Create row
            Row tableRow = sheet.createRow(rowNum);

            // Keep track of current cell
            int cellNum = 0;
            
            // Set name
            Cell tableRowCell = tableRow.createCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getName());

            // Set description
            cellNum++;
            tableRowCell = tableRow.createCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getDescription());

            // Set class
            cellNum++;
            tableRowCell = tableRow.createCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getClassCategory());

            // Set isComplete
            cellNum++;
            tableRowCell = tableRow.createCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getIsComplete());

            // Set createDate
            cellNum++;
            tableRowCell = tableRow.createCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getCreateDate());

            // Set dueDate
            cellNum++;
            tableRowCell = tableRow.createCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getDueDate());

            // Write workbook to file
            FileOutputStream outputStream = new FileOutputStream(FILENAME);
            workbook.write(outputStream);
            workbook.close();

            log.info("Assignment added with id: " + rowNum);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return assigmentID;
    }

    /**
     * Implementation of update() function used to update an assignment in a text file.
     * @param assignment
     * @return true or false - shows status of whether function call passed or failed
     */
    @Override
    public boolean update(Assignment assignment) {
        boolean successful = false;

        // Check if "database" file exists, if not update was not successful
        if(!doesDBExist()) {
            log.info("Could not update since no Database Exists");
            return successful;
        }

        try {
            // Open the "database" workbook
            FileInputStream file = new FileInputStream(FILENAME);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Grab the Assignments sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(TABLE_ASSIGNMENTS);

            // Create a Cell Style to set text wrapping on cells
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            // Create row
            Row tableRow = sheet.getRow(assignment.getAssignmentID() - 1);

            // Keep track of current cell
            int cellNum = 0;
            
            // Set name
            Cell tableRowCell = tableRow.getCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getName());

            // Set description
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getDescription());

            // Set class
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getClassCategory());

            // Set isComplete
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getIsComplete());

            // Skip createDate since it shouldn't change
            cellNum++;
            // tableRowCell = tableRow.getCell(cellNum);
            // tableRowCell.setCellStyle(style);
            // tableRowCell.setCellValue(assignment.getCreateDate());

            // Set dueDate
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            tableRowCell.setCellStyle(style);
            tableRowCell.setCellValue(assignment.getDueDate());

            // Write workbook to file
            FileOutputStream outputStream = new FileOutputStream(FILENAME);
            workbook.write(outputStream);
            workbook.close();

            log.info("Assignment with ID " + assignment.getAssignmentID() + " updated");
            successful = true;
        
        } catch(Exception e) {
            e.printStackTrace();
        }

        return successful;
    }

    /**
     * Implementation of delete() function used to delete an assigment in a text file
     * @param id
     * @return true or false - shows status of whether function call passed or failed
     */
    @Override
    public boolean delete(int id) {
        boolean successful = false;

        // Check if "database" file exists, if not delete was not successful
        if(!doesDBExist()) {
            log.info("Could not delete since no Database Exists");
            return successful;
        }

        try {
            // Open the "database" workbook
            FileInputStream file = new FileInputStream(FILENAME);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Grab the Assignments sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(TABLE_ASSIGNMENTS);

            // Delete the row from the sheet
            sheet.removeRow(sheet.getRow(id - 1));

            // Shift rows up by one
            sheet.shiftRows(id, sheet.getLastRowNum(), -1);

            // Write workbook to file
            FileOutputStream outputStream = new FileOutputStream(FILENAME);
            workbook.write(outputStream);
            workbook.close();

            log.info("Assignment was deleted");
            successful = true;
        
        } catch(Exception e) {
            e.printStackTrace();
        }

        return successful;
    }

    /**
     * Implementation of findByID() function used to find an assigment by ID
     * @param id
     * @return the Assignment found with the ID or null if no assigment could be found
     */
    @Override
    public Assignment findByID(int id) {
        // Check if "database" file exists, if not update was not successful
        if(!doesDBExist()) {
            log.info("Could not update since no Database Exists");
            return null;
        }

        // Assigment Instance to fill up and return to show the
        // data from the found Assignment in the "database"
        Assignment foundAssign = new Assignment();
        foundAssign.setAssignmentID(id);

        try {
            // Open the "database" workbook
            FileInputStream file = new FileInputStream(FILENAME);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Grab the Assignments sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(TABLE_ASSIGNMENTS);

            // Create row
            Row tableRow = sheet.getRow(id - 1);

            // Keep track of current cell
            int cellNum = 0;
            
            // Set name
            Cell tableRowCell = tableRow.getCell(cellNum);
            foundAssign.setName(tableRowCell.getStringCellValue());

            // Set description
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            foundAssign.setDescription(tableRowCell.getStringCellValue());

            // Set class
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            foundAssign.setClassCategory(tableRowCell.getStringCellValue());

            // Set isComplete
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            foundAssign.setIsComplete(tableRowCell.getBooleanCellValue());

            // Skip createDate since it shouldn't change
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            foundAssign.setCreateDate(tableRowCell.getStringCellValue());

            // Set dueDate
            cellNum++;
            tableRowCell = tableRow.getCell(cellNum);
            foundAssign.setDueDate(tableRowCell.getStringCellValue());

            workbook.close();

            log.info("Assignment with ID " + id + " found");
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return foundAssign;
    }

    /**
     * Implementation of findByID() function used to find an assigment by ID
     * @param id
     * @return the Assignment found with the ID or null if no assigment could be found
     */
    @Override
    public List<Assignment> findByClass(String classCategory) {
        // Check if "database" file exists, if not update was not successful
        if(!doesDBExist()) {
            log.info("Could not update since no Database Exists");
            return null;
        }

        // Assigment Instance to fill up and return to show the
        // data from the found Assignment in the "database"
        List<Assignment> assignmentList = new ArrayList<>();

        try {
            // Open the "database" workbook
            FileInputStream file = new FileInputStream(FILENAME);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Grab the Assignments sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(TABLE_ASSIGNMENTS);

            // Grab Row where assignments start
            int rowNum = 2;
            Row tableRow = sheet.getRow(rowNum);

            // Loop through the entire sheet row by row to find the Assignments we want to add
            while(tableRow != null && tableRow.getLastCellNum() > 0) {
                // Check if the class of the assignment matches, if not skip to the next row iteration using continue keyword
                log.info("Do classes (" + tableRow.getCell(2) + ", " + classCategory + ") match: " + (tableRow.getCell(2).toString().toLowerCase().equals(classCategory.toLowerCase())));
                if(!tableRow.getCell(2).toString().toLowerCase().equals(classCategory.toLowerCase())) {
                    rowNum++;
                    tableRow = sheet.getRow(rowNum);
                    continue;
                }

                // Create a new Assignment Object to save info into
                Assignment foundAssign = new Assignment();
                foundAssign.setAssignmentID(rowNum);
                
                // Keep track of current cell
                int cellNum = 0;
                
                // Set name
                Cell tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setName(tableRowCell.getStringCellValue());

                // Set description
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setDescription(tableRowCell.getStringCellValue());

                // Set class
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setClassCategory(tableRowCell.getStringCellValue());

                // Set isComplete
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setIsComplete(tableRowCell.getBooleanCellValue());

                // Skip createDate since it shouldn't change
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setCreateDate(tableRowCell.getStringCellValue());

                // Set dueDate
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setDueDate(tableRowCell.getStringCellValue());

                // Add Assignment to to the List
                assignmentList.add(foundAssign);

                // Increase Row number by one and grab the next Row
                rowNum++;
                tableRow = sheet.getRow(rowNum);
            }

            workbook.close();

            log.info("Getting list of assignments under class: " + classCategory);
        
        } catch(Exception e) {
            e.printStackTrace();
        }

        log.info("AssignmentList size: " + assignmentList.size());
        
        return assignmentList;
    }

    /**
     * Implementation of findByID() function used to find an assigment by ID
     * @param id
     * @return the Assignment found with the ID or null if no assigment could be found
     */
    @Override
    public List<Assignment> findByCurrentlyDue() {
        // Check if "database" file exists, if not update was not successful
        if(!doesDBExist()) {
            log.info("Could not update since no Database Exists");
            return null;
        }

        // Assigment Instance to fill up and return to show the
        // data from the found Assignment in the "database"
        List<Assignment> assignmentList = new ArrayList<>();

        try {
            // Open the "database" workbook
            FileInputStream file = new FileInputStream(FILENAME);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Grab the Assignments sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(TABLE_ASSIGNMENTS);

            // Grab Row where assignments start
            int rowNum = 2;
            Row tableRow = sheet.getRow(rowNum);

            // Loop through the entire sheet row by row to find the Assignments we want to add
            while(tableRow != null && tableRow.getLastCellNum() > 0) {
                // Check if the class of the assignment matches, if not skip to the next row iteration using continue keyword
                if(!tableRow.getCell(3).toString().toLowerCase().equals("false")) {
                    rowNum++;
                    tableRow = sheet.getRow(rowNum);
                    continue;
                }

                // Create a new Assignment Object to save info into
                Assignment foundAssign = new Assignment();
                foundAssign.setAssignmentID(rowNum);
                
                // Keep track of current cell
                int cellNum = 0;
                
                // Set name
                Cell tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setName(tableRowCell.getStringCellValue());

                // Set description
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setDescription(tableRowCell.getStringCellValue());

                // Set class
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setClassCategory(tableRowCell.getStringCellValue());

                // Set isComplete
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setIsComplete(tableRowCell.getBooleanCellValue());

                // Skip createDate since it shouldn't change
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setCreateDate(tableRowCell.getStringCellValue());

                // Set dueDate
                cellNum++;
                tableRowCell = tableRow.getCell(cellNum);
                foundAssign.setDueDate(tableRowCell.getStringCellValue());

                // Add Assignment to to the List
                assignmentList.add(foundAssign);

                // Increase Row number by one and grab the next Row
                rowNum++;
                tableRow = sheet.getRow(rowNum);
            }

            workbook.close();

            log.info("Getting list of assignments due");
        
        } catch(Exception e) {
            e.printStackTrace();
        }

        log.info("AssignmentList size: " + assignmentList.size());
        
        return assignmentList;
    }
    
}
