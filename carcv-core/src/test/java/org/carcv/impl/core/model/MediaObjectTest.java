/**
 * 
 */
package org.carcv.impl.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.carcv.core.model.MediaType;
import org.carcv.impl.core.model.MediaObject;
import org.junit.Test;

/**
 * @author oskopek
 * 
 */
public class MediaObjectTest {

	/**
	 * Test method for {@link org.carcv.impl.core.model.MediaObject#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		MediaObject one = new MediaObject("test", MediaType.JPEG);
		MediaObject two = new MediaObject("test", MediaType.JPEG);
		MediaObject three = new MediaObject("test", MediaType.H264);
		MediaObject four = new MediaObject("diff", MediaType.JPEG);

		assertEquals(one.hashCode(), two.hashCode());
		assertNotEquals(one.hashCode(), three.hashCode());
		assertNotEquals(one.hashCode(), four.hashCode());
		assertNotEquals(four.hashCode(), three.hashCode());
	}

	/**
	 * Test method for
	 * {@link org.carcv.impl.core.model.MediaObject#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		MediaObject one = new MediaObject("test", MediaType.JPEG);
		MediaObject two = new MediaObject("test", MediaType.JPEG);
		MediaObject three = new MediaObject("test", MediaType.H264);
		MediaObject four = new MediaObject("diff", MediaType.JPEG);

		assertTrue(one.equals(two));
		assertTrue(!one.equals(three));
		assertTrue(!one.equals(four));
		assertTrue(!four.equals(three));
	}

}
