package tests;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import project.Models.Group;
import project.Models.Student;

/**
 * @author Anna Samsel
 * 
 */

public class StudentTest{

	@Test
    public void createStudentTest() {
        String name = "Anna";
        String surname = "Samsel";
        Group group = new Group();
        Student student1 = null;
        Student student2 = null;
        
        try {
        	student1 = Student.createStudent(name, surname);
        	student2 = Student.createStudent(name, surname, group);
		}catch(Exception e) {
			fail("Error while creating student: " + e.getMessage());
		}
        
        assertFalse(student1==null);
		assertTrue(student1.getName()==name);
		assertTrue(student1.getSurname()==surname);
		
		assertNotNull(student2);
		assertTrue(student2.getName()==name);
		assertTrue(student2.getSurname()==surname);
		assertThat(student2.getGroup().contains(group));
    }
	
	@Test
    public void addGroupTest() {
		String name = "Anna";
        String surname = "Samsel";
        Group group = new Group();
        Student student = Student.createStudent(name, surname);
        
        try {
        	student.addGroup(group);
		}catch(Exception e) {
			fail("Error while adding group: " + e.getMessage());
		}
        
        assertNotNull(group);
        assertThat(student.getGroup().contains(group));
    }
	
	@Test
    public void removeGroupTest() {
		String name = "Anna";
        String surname = "Samsel";
        Group group = new Group();
        Student student = Student.createStudent(name, surname, group);
        
        try {
        	student.removeGroup(group);
		}catch(Exception e) {
			fail("Error while removing group: " + e.getMessage());
		}
        
        assertThat(!(student.getGroup().contains(group)));
    }
}
