package net.calebscode.langtool.phonology.syllable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SyllablePatternCompiler {

	private String pattern;
	
	private int start;
	
	public SyllablePattern compile(String pattern, SyllablePatternCategoryMap categoryMap) {
		this.pattern = pattern;
		start = 0;
		
		return new SyllablePattern(categoryMap, rule());
	}
	
	private List<LiteralResolver> rule() {
		var parts = new ArrayList<LiteralResolver>();
		while(!isAtEnd()) {
			parts.add(part());
		}
		return parts;
	}
	
	private LiteralResolver part() {
		char current = pattern.charAt(start);
		if (consumeIf('(')) {
			return group();
		}
		else if (Character.isAlphabetic(current)) {
			return literal();
		}
		else {
			// TODO: handle this gracefully
			error("Expected letter for phoneme group.");
			return null;
		}
	}
	
	private Group group() {
		var options = new ArrayList<GroupOption>();
		options.add(groupOption());
		while (consumeIf('|')) {
			options.add(groupOption());
		}
		
		consumeExact(')', "Expected ')' at end of group.");
		int totalWeight = options.stream().mapToInt(opt -> opt.weight).sum();
		return new Group(options, totalWeight);
	}
	
	private GroupOption groupOption() {
		var parts = new ArrayList<LiteralResolver>();
		parts.add(part());
		while (current() != ':' && current() != ')' && current() != '|') {
			parts.add(part());
		}
		
		int weight = 1;
		if (consumeIf(':')) {
			weight = number();
		}
		
		return new GroupOption(parts, weight);
	}
	
	private Literal literal() {
		var lit = new Literal(pattern.charAt(start));
		start++;
		return lit;
	}
	
	private int number() {
		if (!Character.isDigit(current())) {
			error("Expected integer.");
		}
		
		int end = start;
		while (end < pattern.length() && Character.isDigit(pattern.charAt(end))) {
			end++;
		}
		
		int number = Integer.parseInt(pattern.substring(start, end));
		start = end;
		return number;
	}
	
	private boolean isAtEnd() {
		return start == pattern.length();
	}
	
	private char current() {
		return pattern.charAt(start);
	}
	
	private boolean consumeIf(char match) {
		if (current() == match) {
			start++;
			return true;
		}
		
		return false;
	}
	
	private void consumeExact(char match, String errorMessage) {
		if (current() != match) {
			error(errorMessage);
		}
		
		start++;
	}
	
	private void error(String message) {
		throw new RuntimeException(String.format(
				"Error parsing rule at character '%c' (position %d): %s",
				pattern.charAt(start),
				start,
				message
		));
	}
	
	public interface LiteralResolver {		
		public String resolve();
		public Set<String> resolveAll();
	}
	
	private static Random resolverRandom = new Random();
	
	private record Group(List<GroupOption> options, int totalWeight) implements LiteralResolver {
		@Override
		public String resolve() {
			if (options.size() == 1) {
				return resolverRandom.nextBoolean() ? options.get(0).resolve() : "";
			}
			
			int select = resolverRandom.nextInt(totalWeight);
			int current = 0;
			for (var opt : options) {
				if (select < current + opt.weight) {
					return opt.resolve();
				}
				current += opt.weight;
			}
			
			throw new RuntimeException("Invalid group option weights.");
		}

		@Override
		public Set<String> resolveAll() {
			var all = options.stream()
					.flatMap(opt -> opt.resolveAll().stream())
					.collect(Collectors.toSet());
			
			if (options.size() == 1) all.add("");
			
			return all;
		}
	}
	
	private record GroupOption(List<LiteralResolver> parts, int weight) implements LiteralResolver {
		@Override
		public String resolve() {
			StringBuilder sb = new StringBuilder();
			for (var part : parts) {
				sb.append(part.resolve());
			}
			return sb.toString();
		}

		@Override
		public Set<String> resolveAll() {
			var all = Set.of("");
			for (var part : parts) {
				all = all.stream().flatMap(
					existing -> part.resolveAll().stream().map(resolved -> existing + resolved)
				).collect(Collectors.toSet());
			}
			return all;
		}
	}
	
	private record Literal(char value) implements LiteralResolver {
		@Override
		public String resolve() {
			return String.valueOf(value);
		}

		@Override
		public Set<String> resolveAll() {
			return Set.of(resolve());
		}
	}
	
}
