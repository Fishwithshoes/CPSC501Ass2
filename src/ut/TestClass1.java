package ut;

import java.io.IOException;
import java.lang.Exception;
import java.rmi.activation.ActivationException;

import ut.TestClass2;

public class TestClass1 {
	
	private int testIndex1 = 4;
	final char testChar1 = 'b';
	static float testFloat = 2.0f;
	//static TestClass2 testObject1 = new TestClass2();
	//TestClass2 testObject2 = new TestClass2();
	TestClass4 testObject3 = new TestClass4();
	private TestClass4[] testClassArray1 = {testObject3, testObject3};
	private String [] testStringArray1 = {"shoop", "deboop"};
	
	public TestClass1() {

	}
	
	static final void testMethod1(int index, boolean bool, String value) throws IOException, ActivationException{
		
	}
}
