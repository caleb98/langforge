package net.calebscode.langtool.app;

import static net.calebscode.langtool.phonology.phoneme.StandardPhonemes.STANDARD_IPA_PHONEMES;

import net.calebscode.langtool.Word;
import net.calebscode.langtool.phonology.phoneme.GreedyPhonemeParser;
import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeSequenceBuilder;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleCompiler;
import net.calebscode.langtool.phonology.rules.PhonologicalRuleMatcher;
import net.calebscode.langtool.phonology.syllable.Syllable;

public class TestMain {
	public static void main(String[] args) throws Exception {

		var parser = GreedyPhonemeParser.createGreedyIPAParser();
		var parsed = parser.parse("kəlɛkʃən");

		var first = new Syllable(parser.parse("k").toArray(new Phoneme[]{}), parser.parse("ə").toArray(new Phoneme[]{}), null);
		var second = new Syllable(parser.parse("l").toArray(new Phoneme[]{}), parser.parse("ɛ").toArray(new Phoneme[]{}), parser.parse("k").toArray(new Phoneme[]{}));
		var third = new Syllable(parser.parse("ʃ").toArray(new Phoneme[]{}), parser.parse("ə").toArray(new Phoneme[]{}), parser.parse("n").toArray(new Phoneme[]{}));
		var fourth = new Syllable(parser.parse("b").toArray(new Phoneme[]{}), parser.parse("a").toArray(new Phoneme[]{}), parser.parse("p").toArray(new Phoneme[]{}));


		var word = new Word(first, second, third);

		var psb = new PhonemeSequenceBuilder();
		psb.append(word);
		var seq = psb.build();

		System.out.println(seq);

		var prc = new PhonologicalRuleCompiler(STANDARD_IPA_PHONEMES);
		var rule = prc.compile("[0_place of articulation] -> [1_voicing] / [0_place of articulation, 1_voicing] [+vowel] _");

		var matcher = new PhonologicalRuleMatcher(rule);
		psb = new PhonemeSequenceBuilder();
		psb.append(fourth);
		seq = psb.build();

		System.out.println(seq);

		matcher.apply(seq);

	}
}