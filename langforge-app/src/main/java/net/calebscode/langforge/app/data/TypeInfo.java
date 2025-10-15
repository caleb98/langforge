package net.calebscode.langforge.app.data;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public abstract class TypeInfo<T> {

	private final Type type;

	protected TypeInfo() {
		var base = getClass().getGenericSuperclass();
		if (!(base instanceof ParameterizedType paramterized)) {
			throw new IllegalStateException("Must provide generic type parameters to TypeInfo.");
		}

		if (paramterized.getRawType() != TypeInfo.class) {
			throw new IllegalStateException("TypeInfo must be created using a direct subclass.");
		}

		var typeArgument = paramterized.getActualTypeArguments()[0];
		if (typeArgument instanceof TypeVariable<?>) {
			throw new IllegalStateException("TypeInfo cannot be created with generic type variable.");
		}
		else if (typeArgument instanceof WildcardType) {
			throw new IllegalStateException("TypeInfo cannot be created with wildcard type.");
		}

		if (!isTypeTreeFullyResolved(typeArgument)) {
			throw new IllegalStateException("TypeInfo generic parameters must be fully resolvable. "
					+ "No generic or wildcard types are permitted.");
		}

		type = typeArgument;
	}

	Type getType() {
		return type;
	}

	private static boolean isTypeTreeFullyResolved(Type node) {
		return switch (node) {

		case Class<?> classNode -> true;
		case TypeVariable<?> typeVariableNode -> false;
		case WildcardType wildcardType -> false;

		case GenericArrayType genericArrayNode -> isTypeTreeFullyResolved(genericArrayNode.getGenericComponentType());

		case ParameterizedType parameterizedTypeNode -> {
			for (var typeParam : parameterizedTypeNode.getActualTypeArguments()) {
				if (!isTypeTreeFullyResolved(typeParam)) {
					yield false;
				}
			}
			yield true;
		}

		default -> throw new IllegalArgumentException("Unexpected value: " + node);

		};
	}

}
