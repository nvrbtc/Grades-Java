package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;

/**
 * @author Anna Samsel
 * 
 */

public class GroupTest {
	
	@Test
    public void addSubjectTest() {
        Group group = Group.createGroup("testGroup1", "speci");
        Subject subject = Subject.createSubject("test1", 100);
        
        try {
        	group.addSubject(subject);
		}catch(Exception e) {
			fail("Error while adding subject to group: " + e.getMessage());
		}
        
        assertTrue(group.getSubjects().contains(subject));
    }
	
	@Test
    public void deleteSubjectTest() {
		Group group = Group.createGroup("testGroup2", "speci");
        Subject subject = Subject.createSubject("test2", 100);
        group.addSubject(subject);
        
        try {
        	group.deleteSubject(subject);
		}catch(Exception e) {
			fail("Error while deleting subject from group: " + e.getMessage());
		}
        
        assertFalse(group.getSubjects().contains(subject));
    }
	
	@Test
    public void createGroupTest() {
        String groupName = "TC";
        String specialization = "speci";
        Group group = null;
        
        try {
			group = Group.createGroup(groupName, specialization);
		}catch(Exception e) {
			fail("Error while creating group: " + e.getMessage());
		}
        
        assertNotNull(group);
    }
	
	@Test
    public void removeStudentTest() {
		Student student = Student.createStudent("Anna", "Samsel");
		Group group = Group.createGroup("testGroup3", "speci");
		group.addStudent(student);
		
		try {
			group.removeStudent(student);
		}catch(Exception e) {
			fail("Error while removing student from group: " + e.getMessage());
		}
		
		assertFalse(group.getStudents().contains(student));
    }
	
	@Test
    public void addStudentTest() {
		Group group = Group.createGroup("testGroup4", "speci");
		Student student = Student.createStudent("Anna", "Samsel");
		boolean check = false;
		
		try {
			check = group.addStudent(student);
		}catch(Exception e) {
			fail("Error while adding student to group: " + e.getMessage());
		}
		
		assertTrue(check);
    }
}
