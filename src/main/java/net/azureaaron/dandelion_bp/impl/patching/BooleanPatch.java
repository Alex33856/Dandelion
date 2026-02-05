package net.azureaaron.dandelion_bp.impl.patching;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.azureaaron.dandelion_bp.api.patching.ConfigPatch;
import net.azureaaron.dandelion_bp.api.patching.PatchPredicate;

public record BooleanPatch(String path, boolean value, List<PatchPredicate> predicates) implements ConfigPatch.OfBoolean {
	protected static final MapCodec<BooleanPatch> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.fieldOf("path").forGetter(BooleanPatch::path),
			Codec.BOOL.fieldOf("value").forGetter(BooleanPatch::value),
			ConfigPatcher.PATCH_PREDICATES_CODEC.optionalFieldOf("predicates", List.of()).forGetter(BooleanPatch::predicates))
			.apply(instance, BooleanPatch::new));

	@Override
	public MapCodec<? extends ConfigPatch> codec() {
		return MAP_CODEC;
	}
}
