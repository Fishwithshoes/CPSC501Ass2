package ut;

import code.Inspector;
import ut.TestClass1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

public class TestInspector {

	
	/*private final ByteArrayOutputStream output = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(output));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}*/

	
	Inspector testInspector = new Inspector();

	
	/*NOTE: All display methods are handled by testInspect(). I attempted to compare the output
	//of each display method against a ByteArrayOutputStream, but was unsuccessful in getting proper
	assertions*/
	
	@Test
	public void testInspect() {
		TestClass1 testObject = new TestClass1();
		testInspector.inspect(testObject, true);
	}
	
	/*NOTE: The following test both the primitiveHander switch method, and each String handler method 
	per type*/
	
	@Test
	public void testPrimitiveHandlerBool() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testBool");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("false", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerChar() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testChar");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("f", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerByte() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testByte");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("4", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerShort() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testShort");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("2", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerInt() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testInt");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("343", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerLong() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testLong");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("11456", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerFloat() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testFloat");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("0.357", testInspector.primitiveHandler(testField, testObject2));
	}
	
	@Test
	public void testPrimitiveHandlerDouble() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testDouble");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("5.5645", testInspector.primitiveHandler(testField, testObject2));
	}

	//this test also tests the array String generation handler
	@Test
	public void testArrayHandler() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		Object testArray = null;
		try {
			testField = testObject2.getClass().getField("testArray1");
			testArray = testField.get(testObject2);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("test this array ", testInspector.arrayHandler(testArray, testObject2));
	}
	
	/*NOTE: This test may fail due to package stubs not being present. Change below package stub to current
	package, then re-test*/
	@Test
	public void testObjectHandler() {
		TestClass5 testObject2 = new TestClass5();
		Field testField = null;
		try {
			testField = testObject2.getClass().getField("testObject");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ut.TestClass3@255316f2", testInspector.objectHandler(testField, testObject2));
		//			  ^this stub
	}
	
}
