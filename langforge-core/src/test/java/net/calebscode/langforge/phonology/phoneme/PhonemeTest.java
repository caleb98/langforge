package net.calebscode.langforge.phonology.phoneme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class PhonemeTest {

	@Test
	void constructPhonemeWithInvalidFeatures() {
		assertThrows(
			NullPointerException.class,
			() -> new Phoneme(null)
		);
	}
	
	@Test
	void constructWithInvalidFeatureName() {
		HashMap<String, String> features = new HashMap<>();
		features.put(null, "bar");
		assertThrows(
			IllegalArgumentException.class,
			() -> new Phoneme(features)
		);
	}
	
	@Test
	void constructWithInvalidFeatureValue() {
		HashMap<String, String> features = new HashMap<>();
		features.put("foo", null);
		assertThrows(
			IllegalArgumentException.class,
			() -> new Phoneme(features)
		);
	}
	
	@Test
	void featuresNotModifiable() {
		var phoneme = new Phoneme(new HashMap<>());
		
		assertThrows(
			UnsupportedOperationException.class,
			() -> phoneme.features().put("foo", "bar")
		);
	}
	
	@Test
	void hasFeature() {
		var phoneme = new Phoneme(Map.of("foo", "bar"));
		
		assertTrue(phoneme.hasFeature("foo"));
		assertFalse(phoneme.hasFeature("bar"));
	}
	
	@Test
	void featureValueMatches() {
		var phoneme = new Phoneme(Map.of("foo", "bar"));
		
		assertTrue(phoneme.featureValueMatches("foo", "bar"));
		assertFalse(phoneme.featureValueMatches("foo", "baz"));
	}
	
	@Test
	void getFeatureValue() {
		var phoneme = new Phoneme(Map.of("foo", "bar"));
		
		var fooValue = phoneme.getFeatureValue("foo");
		var bazValue = phoneme.getFeatureValue("baz");
		
		assertTrue(fooValue.isPresent());
		assertEquals("bar", fooValue.get());
		
		assertTrue(bazValue.isEmpty());
	}
	
	@Test
	void withFeature() {
		var phoneme = new Phoneme(Map.of("foo", "bar"));
		
		var withFooBaz = phoneme.withFeature("foo", "baz");
		var withBingBang = phoneme.withFeature("bing", "bang");
		
		var originalFeatures = phoneme.features();
		var withFooBazFeatures = withFooBaz.features();
		var withBingBangFeatures = withBingBang.features();
		
		assertEquals(1, originalFeatures.size());
		assertEquals("bar", originalFeatures.get("foo"));
		
		assertEquals(1, withFooBazFeatures.size());
		assertEquals("baz", withFooBazFeatures.get("foo"));
		
		assertEquals(2, withBingBangFeatures.size());
		assertEquals("bar", withBingBangFeatures.get("foo"));
		assertEquals("bang", withBingBangFeatures.get("bing"));
	}
	
	@Test
	void withoutFeature() {
		var phoneme = new Phoneme(Map.of("foo", "bar"));
		
		var withoutFoo = phoneme.withoutFeature("foo");
		var withoutBar = phoneme.withoutFeature("bar");

		var originalFeatures = phoneme.features();
		var withoutFooFeatures = withoutFoo.features();
		var withoutBarFeatures = withoutBar.features();
		
		assertEquals(1, originalFeatures.size());
		assertEquals("bar", originalFeatures.get("foo"));
		
		assertEquals(0, withoutFooFeatures.size());
		
		assertEquals(1, withoutBarFeatures.size());
		assertEquals("bar", withoutBarFeatures.get("foo"));
	}
	
	@Test
	void getPhonemes() {
		var phoneme = new Phoneme(Map.of("foo", "bar"));
		
		assertEquals(List.of(phoneme), phoneme.getPhonemes());
	}
	
}
