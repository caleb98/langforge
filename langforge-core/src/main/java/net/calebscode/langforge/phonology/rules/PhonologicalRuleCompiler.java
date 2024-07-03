package net.calebscode.langforge.phonology.rules;

import java.util.ArrayList;
import java.util.List;

import net.calebscode.langforge.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langforge.phonology.phoneme.Phoneme;
import net.calebscode.langforge.util.Compiler;

public class PhonologicalRuleCompiler extends Compiler<PhonologicalRule> {

	private IpaPhonemeMapper ipaMapper;

	public PhonologicalRuleCompiler(IpaPhonemeMapper ipaMapper) {
		this.ipaMapper = ipaMapper;
	}

	@Override
	public PhonologicalRule compile(String source) {
		init(source);
		return rule(source);
	}

	private PhonologicalRule rule(String source) {
		var match = ruleMatch();

		skipWhitespace();
		expect('-', "Expected '->' after rule match.");
		expect('>', "Expected '->' after rule match.");
		skipWhitespace();

		PhonemeRepresentation replacement = ruleReplacement();

		skipWhitespace();
		expect('/', "Expected '/' after replacement phone.");
		skipWhitespace();

		var preMatches = new ArrayList<PhonemeRepresentation>();
		var postMatches = new ArrayList<PhonemeRepresentation>();

		while (!isAtEnd() && !test('_')) {
			preMatches.add(phonemeRepresentation());
			skipWhitespace();
		}

		expect('_', "Expected '_' in rule context.");
		skipWhitespace();

		while (!isAtEnd()) {
			postMatches.add(phonemeRepresentation());
			skipWhitespace();
		}

		return new PhonologicalRule(source, match, replacement, preMatches, postMatches);
	}

	private PhonemeRepresentation ruleReplacement() {
		if (match('~')) {
			return new NullPhoneme();
		}

		expect('[', "Expected '[' before replacement.");
		if (test('+') || test('-') || Character.isDigit(current())) {
			return phonemeFeatureset();
		}
		else {
			return replacementPhone();
		}
	}

	private PhonemeRepresentation ruleMatch() {
		if (match('~')) {
			return new NullPhoneme();
		}

		return phoneme();
	}

	private PhonemeRepresentation phonemeRepresentation() {
		if (match('#')) {
			return new WordBoundary();
		}
		else if (match('.')) {
			return new SyllableBoundary();
		}
		else {
			return phoneme();
		}
	}

	private PhonemeLiteral replacementPhone() {
		String ipa = ipa();
		expect(']', "Expected ']' after replacement phone.");
		return new PhonemeLiteral(ipaMapper.getPhoneme(ipa));
	}

	private PhonemeRepresentation phoneme() {
		if (match('/')) {
			return phonemeLiteral();
		}
		else if (match('[')) {
			return phonemeFeatureset();
		}

		error("Expected phoneme literal or matcher group.");
		return null;
	}

	private PhonemeLiteral phonemeLiteral() {
		String ipaString = ipa();
		expect('/', "Expected closing '/' after phoneme literal.");
		return new PhonemeLiteral(ipaMapper.getPhoneme(ipaString));
	}

	private PhonemeFeatureset phonemeFeatureset() {
		var features = new ArrayList<Feature>();

		features.add(feature());
		skipWhitespace();

		while (match(',')) {
			skipWhitespace();
			features.add(feature());
			skipWhitespace();
		}

		expect(']', "Expected ']' after phoneme feature set.");

		return new PhonemeFeatureset(features);
	}

	private Feature feature() {
		if (Character.isDigit(current())) {
			return boundFeature(false);
		}
		else if (match('-')) {
			if (Character.isDigit(current())) {
				return boundFeature(true);
			}
			else {
				return literalFeature(true);
			}
		}
		else if (match('+')) {
			if (Character.isDigit(current())) {
				return boundFeature(false);
			}
			else {
				return literalFeature(false);
			}
		}

		error("Expected feature definition.");
		return null;
	}

	private Feature literalFeature(boolean negate) {
		String first = literal();
		String second = match(':') ? literal() : "";

		if (second.equals("")) {
			return new Feature("", first, negate, -1);
		}
		else {
			return new Feature(first, second, negate, -1);
		}
	}

	private Feature boundFeature(boolean negate) {
		var sb = new StringBuilder();
		while (Character.isDigit(current())) {
			sb.append(current());
			next();
		}
		int bindNumber = Integer.parseInt(sb.toString());

		expect('_', "Expected underscore after feature bind number.");

		String featureType = literal();

		return new Feature(featureType, "", negate, bindNumber);
	}

	private String ipa() {
		var sb = new StringBuilder();

		while (!isAtEnd() && !test('/') && !test(']')) {
			sb.append(current());
			next();
		}

		if (sb.isEmpty()) {
			error("Expected ipa phoneme inside slashes.");
		}

		return sb.toString();
	}

	private String literal() {
		var sb = new StringBuilder();
		while (Character.isAlphabetic(current()) || test(' ')) {
			sb.append(current());
			next();
		}
		return sb.toString().trim();
	}

	public interface PhonemeRepresentationMatcher {
		public boolean matchesWordBoundary(WordBoundary wordBoundary);
		public boolean matchesSyllableBoundary(SyllableBoundary syllableBoundary);
		public boolean matchesNullPhoneme(NullPhoneme nullPhoneme);
		public boolean matchesPhonemeLiteral(PhonemeLiteral phonemeLiteral);
		public boolean matchesPhonemeFeatureset(PhonemeFeatureset phonemeFeatureset);
	}

	public interface PhonemeRepresentation {
		public boolean match(PhonemeRepresentationMatcher matcher);
	}

	public record WordBoundary() implements PhonemeRepresentation {
		@Override
		public boolean match(PhonemeRepresentationMatcher matcher) {
			return matcher.matchesWordBoundary(this);
		}
	}

	public record SyllableBoundary() implements PhonemeRepresentation {
		@Override
		public boolean match(PhonemeRepresentationMatcher matcher) {
			return matcher.matchesSyllableBoundary(this);
		}
	}

	public record NullPhoneme() implements PhonemeRepresentation {
		@Override
		public boolean match(PhonemeRepresentationMatcher matcher) {
			return matcher.matchesNullPhoneme(this);
		}
	}

	record PhonemeLiteral(Phoneme phoneme) implements PhonemeRepresentation {
		@Override
		public boolean match(PhonemeRepresentationMatcher matcher) {
			return matcher.matchesPhonemeLiteral(this);
		}
	}

	record PhonemeFeatureset(List<Feature> features) implements PhonemeRepresentation {
		@Override
		public boolean match(PhonemeRepresentationMatcher matcher) {
			return matcher.matchesPhonemeFeatureset(this);
		}
	}

	record Feature(String featureName, String featureValue, boolean negate, int bindNumber) {}

}
