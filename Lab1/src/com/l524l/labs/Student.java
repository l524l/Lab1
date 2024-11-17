package com.l524l.labs;

import java.util.List;

public class Student {
	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setGrades(List<Lesson> grades) {
		this.grades = grades;
	}

	private String name;
	private int age;
	private int course;
	private String group;
	private List<Lesson> grades;
	
	public Student(String name, int age, int course, String group, List<Lesson> grades) {
		super();
		this.name = name;
		this.age = age;
		this.course = course;
		this.group = group;
		this.grades = grades;
	}
	
	public double getAvaregeGrade() {
		return grades.stream().mapToDouble(Lesson::grade).summaryStatistics().getAverage();
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getCourse() {
		return course;
	}

	public String getGroup() {
		return group;
	}

	public List<Lesson> getGrades() {
		return grades;
	}
	
	
}
