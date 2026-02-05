package net.azureaaron.dandelion_bp.api.controllers;

import net.azureaaron.dandelion_bp.impl.controllers.StringControllerImpl;

public non-sealed interface StringController extends Controller<String> {

	static Builder createBuilder() {
		return new StringControllerImpl.StringControllerBuilderImpl();
	}

	interface Builder extends Controller.Builder<String, StringController> {}
}
