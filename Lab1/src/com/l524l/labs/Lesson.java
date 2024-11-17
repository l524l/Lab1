package com.l524l.labs;

public record Lesson(String name, int grade) {

	public String name() {
		return name;
	}

	public int grade() {
		return grade;
	}
	
}
