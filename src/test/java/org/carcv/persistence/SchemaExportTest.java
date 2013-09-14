/**
 * 
 */
package org.carcv.persistence;

import static org.junit.Assert.*;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.Target;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oskopek
 *
 */
public class SchemaExportTest {
	
	String workspacePath;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		workspacePath = getClass().getResource("/").getPath();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void schemaExportTest() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernateHSQLDB.cfg.xml");
		
		SchemaExport export = new SchemaExport(cfg);
		export.setFormat(true);
		export.setOutputFile(workspacePath + "import.sql." + System.currentTimeMillis());
		export.create(Target.BOTH);
		
		assertTrue(true);
	}

}
