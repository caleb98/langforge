package net.calebscode.langforge.app.data;

public sealed interface SaveLoadValue
permits SaveLoadObject, SaveLoadInteger, SaveLoadDouble, SaveLoadString, SaveLoadList {

	default boolean isObject() {
		throw new SaveLoadValueException("SaveLoadValue is not an Object.");
	}

	default boolean isInteger() {
		throw new SaveLoadValueException("SaveLoadValue is not an Integer.");
	}

	default boolean isDouble() {
		throw new SaveLoadValueException("SaveLoadValue is not a Float.");
	}

	default boolean isString() {
		throw new SaveLoadValueException("SaveLoadValue is not a String.");
	}

	default boolean isList() {
		throw new SaveLoadValueException("SaveLoadValue is not a List.");
	}

	default SaveLoadObject asObject() {
		throw new SaveLoadValueException("SaveLoadValue is not an Object.");
	}

	default SaveLoadInteger asInteger() {
		throw new SaveLoadValueException("SaveLoadValue is not an Integer.");
	}

	default SaveLoadDouble asDouble() {
		throw new SaveLoadValueException("SaveLoadValue is not a Float.");
	}

	default SaveLoadString asString() {
		throw new SaveLoadValueException("SaveLoadValue is not a String.");
	}

	default SaveLoadList asList() {
		throw new SaveLoadValueException("SaveLoadValue is not a List.");
	}

	default long asIntegerValue() {
		return asInteger().value();
	}

	default double asDoubleValue() {
		return asDouble().value();
	}

	default String asStringValue() {
		return asString().value();
	}

}
