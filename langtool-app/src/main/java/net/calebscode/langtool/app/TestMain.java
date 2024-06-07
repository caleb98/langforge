package net.calebscode.langtool.app;

import net.calebscode.langtool.Word;
import net.calebscode.langtool.phonology.phoneme.GreedyPhonemeParser;
import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequence;
import net.calebscode.langtool.phonology.phoneme.PhonologicalRule;
import net.calebscode.langtool.phonology.syllable.SyllableGenerator;

public class TestMain {
	public static void main(String[] args) throws Exception {

//		var parser = GreedyPhonemeParser.createGreedyIPAParser();
//		var parsed = parser.parse("kəlɛkʃən");
//
//		for (var p : parsed) {
//			System.out.printf("%s -> %s%n", p.getRepresentation(), p.getName());
//		}

		var gen = new SyllableGenerator("(O)V");
		var pp = GreedyPhonemeParser.createGreedyIPAParser();
		
		var vs = pp.parse("a");
		
		gen.addClassPhonemes('V', vs.toArray(new Phoneme[]{}));
		gen.addClassPhonemeSequences('O',
			new PhonemeSequence(pp.parse("t")),
			new PhonemeSequence(pp.parse("v")),
			new PhonemeSequence(pp.parse("st")),
			new PhonemeSequence(pp.parse("zt"))
		);

		Word word = new Word(gen.generateSyllable(), gen.generateSyllable(), gen.generateSyllable());
		System.out.println(word);
		new PhonologicalRule("a", "", "_a").applyTo(word);
	}
}