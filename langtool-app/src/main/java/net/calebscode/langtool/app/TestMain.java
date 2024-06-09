package net.calebscode.langtool.app;

import net.calebscode.langtool.phonology.phoneme.GreedyPhonemeParser;
import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCategoryMap;
import net.calebscode.langtool.phonology.syllable.SyllablePatternCompiler;

public class TestMain {
	public static void main(String[] args) throws Exception {

//		var parser = GreedyPhonemeParser.createGreedyIPAParser();
//		var parsed = parser.parse("kəlɛkʃən");
//
//		for (var p : parsed) {
//			System.out.printf("%s -> %s%n", p.getRepresentation(), p.getName());
//		}

		var pp = GreedyPhonemeParser.createGreedyIPAParser();
		var categories = new SyllablePatternCategoryMap();
		
		var vs = pp.parse("a");
		
		categories.addCategoryPhonemes('V', pp.parse("a").toArray(new Phoneme[]{}));
		categories.addCategoryPhonemes('C', pp.parse("st").toArray(new Phoneme[]{}));
		categories.addCategoryPhonemes('S', pp.parse("st").toArray(new Phoneme[]{}));
		
		var spc = new SyllablePatternCompiler();
		var sp = spc.compile("C(S)V", categories);
		
		System.out.println("All Patterns:");
		sp.allPatterns().forEach(pattern -> System.out.printf("\t%s%n", pattern));
		
		System.out.println("\nAll Syllables:");
		sp.allSyllables().forEach(syllable -> System.out.printf("\t%s%n", syllable));
	}
}