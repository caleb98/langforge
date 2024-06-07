package net.calebscode.langtool.app;

import net.calebscode.langtool.GreedyPhonemeParser;
import net.calebscode.langtool.Phoneme;
import net.calebscode.langtool.SyllableGenerator;

public class TestMain {
	public static void main(String[] args) throws Exception {

//		var parser = GreedyPhonemeParser.createGreedyIPAParser();
//		var parsed = parser.parse("kəlɛkʃən");
//
//		for (var p : parsed) {
//			System.out.printf("%s -> %s%n", p.getRepresentation(), p.getName());
//		}

		var gen = new SyllableGenerator("C(G)V(C)");
		var pp = GreedyPhonemeParser.createGreedyIPAParser();
		
		var vs = pp.parse("aiueo");
		var cs = pp.parse("tdpb");
		var gs = pp.parse("ljw");
		
		gen.addClassPhonemes('V', vs.toArray(new Phoneme[]{}));
		gen.addClassPhonemes('C', cs.toArray(new Phoneme[]{}));
		gen.addClassPhonemes('G', gs.toArray(new Phoneme[]{}));

		for (int i = 0; i < 10; i++) {
			System.out.printf(
				"%-6s%-6s%-6s%-6s%-6s\n",
				gen.generateSyllable(),
				gen.generateSyllable(),
				gen.generateSyllable(),
				gen.generateSyllable(),
				gen.generateSyllable()
			);
		}

		System.out.println();
		var all = gen.generateAllSyllables();
		all.forEach(System.out::println);
		System.out.printf("%d syllables\n", all.size());
	}
}