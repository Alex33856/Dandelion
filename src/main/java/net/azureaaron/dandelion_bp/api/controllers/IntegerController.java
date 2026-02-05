package net.azureaaron.dandelion_bp.api.controllers;

import net.azureaaron.dandelion_bp.impl.controllers.IntegerControllerImpl;

public non-sealed interface IntegerController extends NumberController<Integer> {

	static Builder createBuilder() {
		return new IntegerControllerImpl.IntegerControllerBuilderImpl();
	}

	interface Builder extends NumberController.Builder<Integer> {}
}
