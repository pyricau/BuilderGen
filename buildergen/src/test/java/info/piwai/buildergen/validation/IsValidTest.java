package info.piwai.buildergen.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IsValidTest {

	@Test
	public void initialStateIsValid() {
		IsValid valid = new IsValid();
		assertTrue(valid.isValid());
	}
	
	@Test
	public void invalidateInvalidates() {
		IsValid valid = new IsValid();
		valid.invalidate();
		assertFalse(valid.isValid());
	}
	
	@Test
	public void doubleInvalidateInvalidates() {
		IsValid valid = new IsValid();
		valid.invalidate();
		valid.invalidate();
		assertFalse(valid.isValid());
	}
	
}
