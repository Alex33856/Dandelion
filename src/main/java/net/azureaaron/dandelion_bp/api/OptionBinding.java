package net.azureaaron.dandelion_bp.api;

public interface OptionBinding<T> {

	T defaultValue();

	T get();

	void set(T value);
}
