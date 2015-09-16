package info.juanmendez.android.retrolambda.interfaces;

import info.juanmendez.android.retrolambda.model.Person;

public interface PersonInterface {
	
	String getName();
	void setName(String name);
	int getAge();
	void setAge(int age);

	default String getPersonInfo(){
		return getName() + " {" + getAge() + "} ";
	}

	static String getPersonalInfo( PersonInterface p ){
		return p.getName() + " {" + p.getAge() + "} ";
	}
}
