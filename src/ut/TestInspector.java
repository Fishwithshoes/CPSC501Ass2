package ut;

import code.Inspector;
import ut.TestClass1;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestInspector {
	
	Inspector testInspector = new Inspector();

	@Test
	public void testRecursion() {
		TestClass1 testObject = new TestClass1();
		testInspector.inspect(testObject, true);
	}
}
