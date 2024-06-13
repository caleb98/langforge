package net.calebscode.langtool.phonology.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langtool.phonology.phoneme.IpaPhonemeMapper;
import net.calebscode.langtool.phonology.phoneme.Phoneme;
import net.calebscode.langtool.phonology.phoneme.PhonemeFeature;
import net.calebscode.langtool.util.Compiler;

public class PhonologicalRuleCompiler extends Compiler<PhonologicalRule> {
	
	private IpaPhonemeMapper ipaMapper;
	
	public PhonologicalRuleCompiler(IpaPhonemeMapper ipaMapper) {
		this.ipaMapper = ipaMapper;
	}
	
	public PhonologicalRule compile(String pattern) {
		init(pattern);
		return rule();
	}
	
	private PhonologicalRule rule() {
		var match = phoneme();
		
		skipWhitespace();
		expect('-', "Expected '->' after rule match.");
		expect('>', "Expected '->' after rule match.");
		skipWhitespace();
		
		expect('[', "Expected '[' before replacement phone.");
		var replacement = phone();
		
		skipWhitespace();
		expect('/', "Expected '/' after replacement phone.");
		skipWhitespace();
		
		//error("Expected phoneme literal or phoneme matcher.");
		return new PhonologicalRule(match, replacement);
	}

	private Phoneme phone() {
		String ipa = ipa();
		expect(']', "Expected ']' after phone.");
		return ipaMapper.getPhoneme(ipa);
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
			return boundFeature();
		}
		
		return literalFeature();
	}

	private Feature literalFeature() {
		expect('+', "Expected '+' before feature.");
		return featureDefinition(-1);
	}

	private Feature boundFeature() {
		var sb = new StringBuilder();
		while (Character.isDigit(current())) {
			sb.append(current());
			next();
		}
		int bindNumber = Integer.parseInt(sb.toString());
		
		expect('_', "Expected underscore after feature bind number.");
		
		return featureDefinition(bindNumber);
	}
	
	private Feature featureDefinition(int bindNumber) {
		String first = literal();
		String second = match(':') ? literal() : "";
		
		if (second.equals("")) {
			return new Feature("", first, bindNumber);
		}
		else {
			return new Feature(first, second, bindNumber);
		}
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
	
	public interface PhonemeRepresentation {
		public boolean matches(Phoneme phoneme);
	}
	
	record PhonemeLiteral(Phoneme phoneme) implements PhonemeRepresentation {
		@Override
		public boolean matches(Phoneme phoneme) {
			return this.phoneme.equals(phoneme);
		}
	}
	
	record PhonemeFeatureset(List<Feature> features) implements PhonemeRepresentation {
		@Override
		public boolean matches(Phoneme phoneme) {
			var phonemeFeatureValues = phoneme.features().values().stream()
					.map(PhonemeFeature::featureValue)
					.collect(Collectors.toSet());
			
			boolean matches = true;
			for (var feature : features) {
				if (!matchesAnyFeatureValue(phonemeFeatureValues, feature)
					&& !matchesExactFeatureValue(phoneme, feature)) {
					matches = false;
					break;
				}
			}
			
			return matches;
		}	
		
		private static boolean matchesExactFeatureValue(Phoneme phoneme, Feature feature) {
			return !feature.featureType().isEmpty() 
					&& Objects.equals(
						phoneme.getFeature(feature.featureType()).featureValue(), 
						feature.featureValue()
					);
		}

		private static boolean matchesAnyFeatureValue(Set<String> phonemeFeatureValues, Feature feature) {
			return feature.featureType().isEmpty() 
					&& phonemeFeatureValues.contains(feature.featureValue());
		}
	}
	
	record Feature(String featureType, String featureValue, int bindNumber) {}
	
}
