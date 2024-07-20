package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

import project.Models.Group;
import project.Models.Student;
import project.Models.Subject;

/**
 * @author Anna Samsel
 * 
 */

public class SubjectTest{
	
	@Test
    public void createSubjectTest() {
		String subjectName = "Test";
		int maxGrade = 123;
		String desc = "lorem ipsum";
		Subject testSubject1 = null;
		Subject testSubject2 = null;
		
		try {
			testSubject1 = Subject.createSubject(subjectName, maxGrade);
			testSubject2 = Subject.createSubject(subjectName, maxGrade, desc);
		}catch(Exception e) {
			fail("Error while creating subject: " + e.getMessage());
		}
		
		
		assertFalse(testSubject1==null);
		assertTrue(testSubject1.getSubjectName()==subjectName);
		assertTrue(testSubject1.getMaxGrade()==maxGrade);
		
		assertFalse(testSubject2==null);
		assertTrue(testSubject2.getSubjectName()==subjectName);
		assertTrue(testSubject2.getMaxGrade()==maxGrade);
		assertTrue(testSubject2.getDescription()==desc);
		
    }
	
	@Test
    public void addPointsTest() {
	    int pointsGood = 50;
	    int pointsBad = 9999999;
	    int limit = 100;
	    String groupName = "testSubject1";
	    String specialization = "spec";
	    Group  group = Group.createGroup(groupName, specialization);
	    Subject subject1 = Subject.createSubject("Test", limit) ;
	    assertNotNull(group);
	    group.addSubject(subject1);
	    Subject subject2 = Subject.createSubject("Test", limit) ; 
	    group.addSubject(subject2);
	    Student student = Student.createStudent("Anna","Samsel");
	    group.addStudent(student);
	
	    try {
	      subject1.addPoints(student,pointsGood);
	      subject2.addPoints(student,pointsBad);
	    }
	    catch(Exception e) {
	      fail("Error while adding points: " + e.getMessage());
	    }
	    
	    assertTrue(subject1.getPoints(student).intValue()==pointsGood);
	    assertTrue(subject2.getPoints(student).intValue()==limit);
    }
	
	@Test
    public void addStudentTest() {
		Student student = Student.createStudent("Anna","Samsel");
		String subjectName = "Test";
		int maxGrade = 123;
		Subject subject = Subject.createSubject(subjectName, maxGrade);
		
		try {
			subject.addStudent(student);
		}
		catch(Exception e) {
			fail("Error while adding student: " + e.getMessage());
		}
		
		assertTrue(subject.getStudents().contains(student));
    }
	
	@Test
    public void deleteSubjectTest() {
		String subjectName = "Test";
		int maxGrade = 123;
		Subject subject = Subject.createSubject(subjectName, maxGrade);
		Group group = Group.createGroup("testSubject2", "spec");
		group.addSubject(subject);
		
		assertDoesNotThrow(()->subject.deleteSubject());
    }
	
	@Test
    public void removeStudentTest() {
		Student student = Student.createStudent("Anna","Samsel");
		String subjectName = "Test";
		int maxGrade = 123;
		Subject subject = Subject.createSubject(subjectName, maxGrade);
		subject.addStudent(student);
		
		try {
			subject.removeStudent(student);
		}
		catch(Exception e) {
			fail("Error while removing student: " + e.getMessage());
		}
		
		assertFalse(subject.getStudents().contains(student));
    }
}
