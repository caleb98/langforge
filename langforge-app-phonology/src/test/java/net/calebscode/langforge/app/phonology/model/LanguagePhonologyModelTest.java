package net.calebscode.langforge.app.phonology.model;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.phonology.phoneme.Phoneme;

class LanguagePhonologyModelTest {

	LanguagePhonologyModel model;

	@BeforeEach
	void initialize() {
		model = new LanguagePhonologyModel();
	}

	@Test
	void addFeature() {
		var feature = model.addFeature("foo");

		var maybeFeature = model.getFeature("foo");

		assertTrue(maybeFeature.isPresent());

		PhonemeFeatureModel actualFeature = maybeFeature.get();

		assertEquals("foo", actualFeature.getName());
		assertEquals(0, actualFeature.valuesProperty().size());
		assertSame(feature, actualFeature);
	}

	@Test
	void addFeatureAlreadyPresent() {
		var add1 = model.addFeature("foo");
		var add2 = model.addFeature("foo");

		add1.valuesProperty().add("bar");

		assertEquals(1, model.featuresProperty().size());
		assertSame(add1, add2);
		assertThat(add2.valuesProperty(), hasItem("bar"));
	}

	@Test
	void removeFeature() {
		var feature = model.addFeature("foo");

		var removed = model.removeFeature("foo");

		assertTrue(removed);
		assertTrue(feature.isDeleted());
		assertEquals(0, model.featuresProperty().size());
	}

	@Test
	void removeFeatureNotPresent() {
		var removed = model.removeFeature("foo");

		assertFalse(removed);
	}

	@Test
	void removeFeatureUpdatesPhonemes() {
		var fooFeature = model.addFeature("foo");
		var barFeature = model.addFeature("bar");

		fooFeature.valuesProperty().add("faz");
		barFeature.valuesProperty().add("baz");

		model.phonemesProperty().add(new Phoneme(Map.of(
			"foo", "faz",
			"bar", "baz"
		)));

		model.removeFeature("foo");

		var phonemes = model.phonemesProperty();
		assertEquals(1, phonemes.size());

		var phoneme = phonemes.get(0);
		assertThat(phoneme.features(), aMapWithSize(1));
		assertThat(phoneme.features(), hasEntry("bar", "baz"));
	}

	@Test
	void removeFeatureValueUpdatesPhonemes() {
		var fooFeature = model.addFeature("foo");
		var barFeature = model.addFeature("bar");

		fooFeature.valuesProperty().addAll(List.of("fooA", "fooB"));
		barFeature.valuesProperty().addAll(List.of("barA", "barB"));

		model.phonemesProperty().add(new Phoneme(Map.of(
			"foo", "fooA",
			"bar", "barA"
		)));

		model.phonemesProperty().add(new Phoneme(Map.of(
			"foo", "fooA",
			"bar", "barB"
		)));

		fooFeature.valuesProperty().remove("fooA");
		barFeature.valuesProperty().remove("barB");

		var phonemes = model.phonemesProperty();
		assertEquals(2, phonemes.size());

		var phoneme1 = phonemes.get(0);
		assertThat(phoneme1.features(), aMapWithSize(1));
		assertThat(phoneme1.features(), hasEntry("bar", "barA"));

		var phoneme2 = phonemes.get(1);
		assertThat(phoneme2.features(), aMapWithSize(0));
	}

	@Test
	void featuresRestrictValidPhonemes() {
		var fooFeature = model.addFeature("foo");
		fooFeature.valuesProperty().add("faz");

		model.phonemesProperty().add(new Phoneme(Map.of("foo", "nope")));
		model.phonemesProperty().add(new Phoneme(Map.of("bar", "baz")));

		var phonemes = model.phonemesProperty();
		assertEquals(0, phonemes.size());
	}

}
