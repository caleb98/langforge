package net.calebscode.langforge.app.util;

import java.util.Optional;
import java.util.regex.Pattern;

public record VersionNumber(int major, int minor, int patch) implements Comparable<VersionNumber> {

	private static final Pattern VERSION_STRING_PATTERN = Pattern.compile(
		"^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+))?)?$"
	);

	public static VersionNumber parse(String versionString) {
		var matcher = VERSION_STRING_PATTERN.matcher(versionString.strip());

		if (!matcher.matches()) {
			throw new IllegalArgumentException(versionString + " is not a valid version string.");
		}

		int major = Integer.parseInt(matcher.group(1));
		int minor = Optional.ofNullable(matcher.group(2)).map(Integer::parseInt).orElse(0);
		int patch = Optional.ofNullable(matcher.group(3)).map(Integer::parseInt).orElse(0);

		return new VersionNumber(major, minor, patch);
	}

	@Override
	public int compareTo(VersionNumber other) {
		int result = Integer.compare(major, other.major);
		if (result != 0) {
			return result;
		}

		result = Integer.compare(minor, other.minor);
		if (result != 0) {
			return result;
		}

		return Integer.compare(minor, other.minor);
	}

	@Override
	public final String toString() {
		return String.format("%d.%d.%d", major, minor, patch);
	}

}
