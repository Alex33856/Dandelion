package net.azureaaron.dandelion_bp.impl;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.azureaaron.dandelion_bp.api.ConfigManager;
import net.azureaaron.dandelion_bp.api.ConfigSerializer;
import net.azureaaron.dandelion_bp.api.patching.ConfigPatch;
import net.azureaaron.dandelion_bp.impl.patching.ConfigPatcher;
import net.azureaaron.dandelion_bp.impl.utils.ReflectionUtils;

public class ConfigManagerImpl<T> implements ConfigManager<T> {
	private final Class<T> configClass;
	private final Path path;
	private final Gson gson;
	private final ConfigSerializer<T> serializer;
	private List<ConfigPatch> patches = List.of();
	private T instance;
	private T unpatchedInstance;

	public ConfigManagerImpl(Class<T> configClass, Path path, UnaryOperator<GsonBuilder> gsonBuilder) {
		this.configClass = Objects.requireNonNull(configClass, "configClass must not be null");
		this.path = Objects.requireNonNull(path, "path must not be null");
		this.gson = Objects.requireNonNull(gsonBuilder.apply(GsonConfigSerializer.createDefaultGsonBuilder()).create(), "gsonBuilder must not be null");
		this.serializer = new GsonConfigSerializer<>(this, this.path, this.gson);
		this.instance = this.createNewConfigInstance();
		this.unpatchedInstance = this.createNewConfigInstance();
	}

	protected T createNewConfigInstance() {
		return ReflectionUtils.createNewDefaultInstance(this.configClass);
	}

	protected void setUnpatchedInstance(T newInstance) {
		this.unpatchedInstance = newInstance;
		this.updatePatchedInstance();
	}

	private void updatePatchedInstance() {
		this.instance = this.serializer.copyObject(this.unpatchedInstance);
		ConfigPatcher.applyPatches(this.instance, this.patches);
	}

	@Override
	public void setPatches(List<ConfigPatch> patches) {
		this.patches = Objects.requireNonNull(patches, "patches must not be null");
		this.updatePatchedInstance();
	}

	@Override
	public Class<T> configClass() {
		return this.configClass;
	}

	@Override
	public T instance() {
		return this.instance;
	}

	@Override
	public T unpatchedInstance() {
		return this.unpatchedInstance;
	}

	@Override
	public T defaults() {
		return this.createNewConfigInstance();
	}

	@Override
	public boolean save() {
		boolean result = this.serializer.save();
		this.updatePatchedInstance();

		return result;
	}

	@Override
	public boolean load() {
		// No need to update the patched instance since that will be done automatically by the serializer's call to setUnpatchedInstance
		return this.serializer.load();
	}
}
