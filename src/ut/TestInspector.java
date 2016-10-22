package ut;

import code.Inspector;
import ut.TestClass1;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestInspector {

	@Test
	public void testRecurison() {
		Inspector testInspector = new Inspector();
		TestClass1 testObject = new TestClass1();
		testInspector.inspect(testObject, true);
	}
}
