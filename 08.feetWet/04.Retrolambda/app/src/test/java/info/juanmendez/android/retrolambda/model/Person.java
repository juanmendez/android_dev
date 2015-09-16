package info.juanmendez.android.retrolambda.model;

import info.juanmendez.android.retrolambda.interfaces.PersonInterface;

public class Person implements PersonInterface {
	private String name;
	private int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return this.name + " (" + this.age + ")";
	}
}
