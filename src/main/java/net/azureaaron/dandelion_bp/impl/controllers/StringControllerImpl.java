package net.azureaaron.dandelion_bp.impl.controllers;

import net.azureaaron.dandelion_bp.api.controllers.StringController;

public class StringControllerImpl implements StringController {
	protected StringControllerImpl() {}

	public static class StringControllerBuilderImpl implements StringController.Builder {
		@Override
		public StringController build() {
			return new StringControllerImpl();
		}
	}
}
