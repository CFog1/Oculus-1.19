package net.coderbot.iris.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.coderbot.iris.gl.blending.DepthColorStorage;

@Mixin(GlStateManager.class)
public class MixinGlStateManager_DepthColorOverride {
	@Inject(method = "_colorMask", at = @At("HEAD"), cancellable = true)
	private static void iris$colorMaskLock(boolean red, boolean green, boolean blue, boolean alpha, CallbackInfo ci) {
		if (DepthColorStorage.isDepthColorLocked()) {
			DepthColorStorage.deferColorMask(red, green, blue, alpha);
			ci.cancel();
		}
	}

	@Inject(method = "_depthMask", at = @At("HEAD"), cancellable = true)
	private static void iris$depthMaskLock(boolean enable, CallbackInfo ci) {
		if (DepthColorStorage.isDepthColorLocked()) {
			DepthColorStorage.deferDepthEnable(enable);
			ci.cancel();
		}
	}
}
