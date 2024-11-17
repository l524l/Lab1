package com.l524l.labs;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<Student> list = List.of(new Student("Bob",1,1,"132",List.of(new Lesson("Math",4))),new Student("Alice",1,3,"132",List.of(new Lesson("Math",4))),new Student("He",2,3,"132",List.of(new Lesson("Math",5),new Lesson("Math",1))));
		printStudents(list,1);
		transferToTheNextCourse(list).forEach(x-> System.out.println(x.getName()));;
	}
	
	public static void printStudents(List<Student> students, int course){
		students.stream().filter((x)->x.getCourse()==course).forEach((s)->System.out.println(s.getName()));
	}
	public static List<Student> transferToTheNextCourse(List<Student> students) {
		return students.stream().filter((x)->x.getAvaregeGrade()>=3).map( (s) -> {s.setCourse(s.getCourse()+1); return s;}).collect(Collectors.toList());
	}
}
