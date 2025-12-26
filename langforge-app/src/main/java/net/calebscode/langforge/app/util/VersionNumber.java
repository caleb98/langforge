package net.calebscode.langforge.app.util;

public record VersionNumber(int major, int minor, int patch) implements Comparable<VersionNumber> {

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
