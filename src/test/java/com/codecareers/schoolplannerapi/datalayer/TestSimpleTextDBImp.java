package com.codecareers.schoolplannerapi.datalayer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/* This will be used to test the SimpleTextDBImp methods to make sure we get expected values */
// Use the @SpringBootTest annotation to define the file as a test file
@SpringBootTest
public class TestSimpleTextDBImp {

    /**
     * This will test the save() method in SimpleTextDBImp to make sure we can actually save data
     * to the "database" (text file in our case)
     */
    @Test
    public void testSave() {
        
        assertEquals(true, false);
    }

    /**
     * This will test the update() method in SimpleTextDBImp to make sure we can actually update
     * an assigment in our "database" (text file in our case)
     */
    @Test
    public void testUpdate() {

        assertEquals(true, false);
    }

    /**
     * This will test the delete() method in SimpleTextDBImp to make sure we can actually delete
     * an assigment in our "database" (text file in our case)
     */
    @Test
    public void testDelete() {

        assertEquals(true, false);
    }
    
}
