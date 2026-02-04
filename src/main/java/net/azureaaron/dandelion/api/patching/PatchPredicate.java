package net.azureaaron.dandelion.api.patching;

import java.util.OptionalLong;

import com.mojang.serialization.MapCodec;

import net.azureaaron.dandelion.Dandelion;
import net.fabricmc.loader.api.SemanticVersion;
import net.minecraft.resources.Identifier;

/**
 * A predicate that will be used in determining whether a {@link ConfigPatch} should be applied.
 */
public interface PatchPredicate {

	/**
	 * The {@link MapCodec} used to deserialize the predicate.
	 */
	MapCodec<? extends PatchPredicate> codec();

	/*
	 * Tests the predicate.
	 */
	boolean test();

	/**
	 * The built-in date predicate.
	 *
	 * @apiNote Do not extend this.
	 */
	interface OfDate extends PatchPredicate {
		Identifier ID = Dandelion.id("predicate/date");

		/**
		 * The start date.
		 */
		OptionalLong from();

		/**
		 * The end date.
		 */
		OptionalLong to();
	}

	/**
	 * The built-in Minecraft version predicate.
	 *
	 * @apiNote Do not extend this.
	 */
	interface OfMinecraftVersion extends PatchPredicate {
		Identifier ID = Dandelion.id("predicate/minecraft_version");

		/**
		 * The version of the Minecraft to compare against.
		 */
		String version();

		/**
		 * The operator used to compare the current Minecraft version against the given Minecraft version.
		 */
		ComparisonOperator operator();
	}

	/**
	 * The built-in mod version predicate. The mod must use semver version formatting.
	 *
	 * @apiNote Do not extend this.
	 */
	interface OfModVersion extends PatchPredicate {
		Identifier ID = Dandelion.id("predicate/mod_version");

		/**
		 * The id of the mod whose version will be compared.
		 */
		String modId();

		/**
		 * The version of the mod to compare against.
		 */
		SemanticVersion version();

		/**
		 * The operator used to compare the current mod version against the given mod version.
		 */
		ComparisonOperator operator();
	}
}
