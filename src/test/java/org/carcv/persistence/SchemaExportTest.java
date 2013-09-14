/**
 * 
 */
package org.carcv.persistence;

import static org.junit.Assert.*;

import static org.hamcrest.core.IsEqual.*;
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
		assertNotNull(workspacePath);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void schemaExportTest() throws Exception {
		Configuration cfg = new Configuration();		
		cfg.configure("hibernate.cfg.xml");
		assertNotNull(cfg);
		
		SchemaExport export = new SchemaExport(cfg);
		assertNotNull(export);
		
		export.setFormat(true);
		export.setOutputFile(workspacePath + "import.sql." + System.currentTimeMillis());
		export.create(Target.SCRIPT);
		
		Integer size = export.getExceptions().size();
		Integer expected = 0;
		assertThat(size, equalTo(expected));		
	}

}
