package net.calebscode.langforge.phonology.syllable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.calebscode.langforge.util.Compiler;

class SyllablePatternCompiler extends Compiler<Set<String>> {

	@Override
	public Set<String> compile(String pattern) {
		init(pattern);

		var resolvers = rule();
		var allPatterns = Set.of("");
		for (var part : resolvers) {
			allPatterns = allPatterns
				.stream()
				.flatMap(existing -> part.resolve().stream().map(resolved -> existing + resolved))
				.collect(Collectors.toSet());
		}

		return allPatterns;
	}

	private List<SyllablePatternResolver> rule() {
		var parts = new ArrayList<SyllablePatternResolver>();
		while(!isAtEnd()) {
			parts.add(part());
		}
		return parts;
	}

	private SyllablePatternResolver part() {
		if (match('(')) {
			return group();
		}
		else if (Character.isAlphabetic(current())) {
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
		while (match('|')) {
			options.add(groupOption());
		}

		expect(')', "Expected ')' at end of group.");
		return new Group(options);
	}

	private GroupOption groupOption() {
		var parts = new ArrayList<SyllablePatternResolver>();
		parts.add(part());
		while (!isAtEnd() && current() != ')' && current() != '|') {
			parts.add(part());
		}

		if (isAtEnd()) {
			error("Unterminated pattern group.");
		}

		return new GroupOption(parts);
	}

	private Literal literal() {
		var lit = new Literal(pattern.charAt(start));
		start++;
		return lit;
	}

	private interface SyllablePatternResolver {
		public Set<String> resolve();
	}

	private record Group(List<GroupOption> options) implements SyllablePatternResolver {
		@Override
		public Set<String> resolve() {
			var all = options.stream()
					.flatMap(opt -> opt.resolve().stream())
					.collect(Collectors.toSet());

			if (options.size() == 1) {
				all.add("");
			}

			return all;
		}
	}

	private record GroupOption(List<SyllablePatternResolver> parts) implements SyllablePatternResolver {
		@Override
		public Set<String> resolve() {
			var all = Set.of("");
			for (var part : parts) {
				all = all.stream().flatMap(
					existing -> part.resolve().stream().map(resolved -> existing + resolved)
				).collect(Collectors.toSet());
			}
			return all;
		}
	}

	private record Literal(char value) implements SyllablePatternResolver {
		@Override
		public Set<String> resolve() {
			return Set.of(String.valueOf(value));
		}
	}

}
