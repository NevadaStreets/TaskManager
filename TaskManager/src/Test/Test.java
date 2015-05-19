package Test;

import static org.junit.Assert.*;
import application.Proyecto;

public class Test {

	
	@org.junit.Test
	public void testNameProyecto() {
		
		String s = "Project1";
		Proyecto pp = new Proyecto();
		pp.setName(s);
		
		assertEquals(pp.getName(), "Project1");
		
	}
}
