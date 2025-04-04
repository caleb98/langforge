package net.calebscode.langforge.app.phonology.viewmodel;

import static net.calebscode.langforge.phonology.phoneme.StandardPhonemes.VOICED_ALVEOLAR_FRICATIVE;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.calebscode.langforge.app.phonology.model.SyllablePatternCategoryModel;

class SyllablePatternCategoryViewModelTest {

	SyllablePatternCategoryModel viewModel;

	@BeforeEach
	void initialize() {
		viewModel = new SyllablePatternCategoryModel();
	}

	@Test
	void categoryMapPropertyUnmodifiable() {
		viewModel.addPhoneme('A', VOICED_ALVEOLAR_FRICATIVE);

		var categoryMap = viewModel.categoryMapProperty();
		var categoryPhonemes = categoryMap.get('A');

		assertThrows(UnsupportedOperationException.class, () -> categoryMap.put('B', Set.of()));
		assertThrows(UnsupportedOperationException.class, () -> categoryMap.remove('A'));
		assertThrows(UnsupportedOperationException.class, () -> categoryPhonemes.remove(VOICED_ALVEOLAR_FRICATIVE));
		assertThrows(UnsupportedOperationException.class, () -> categoryPhonemes.clear());
	}

}
