package pl.panocha.subsense.client.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public abstract class OptionsMixin {

    /*
     * Minecraft's normal sensitivity range is 0.0 -> 1.0,
     * displayed as 0% -> 200%.
     *
     * -1/3 is the lowest useful value with Minecraft's
     * existing sensitivity formula:
     *
     * (sensitivity * 0.6 + 0.2)^3
     *
     * At -1/3, the resulting mouse movement reaches zero.
     */
    @Unique
    private static final double SUBSENSE_MIN = -1.0 / 3.0;

    @Unique
    private static final double SUBSENSE_MAX = 1.0;

    @Unique
    private static final OptionInstance.SliderableValueSet<Double> SUBSENSE_RANGE =
            OptionInstance.UnitDouble.INSTANCE.xmap(
                    slider -> SUBSENSE_MIN + slider * (SUBSENSE_MAX - SUBSENSE_MIN),
                    value -> (value - SUBSENSE_MIN) / (SUBSENSE_MAX - SUBSENSE_MIN)
            );

    @Shadow
    @Final
    @Mutable
    private OptionInstance<Double> sensitivity;

    /*
     * Replace Minecraft's sensitivity option immediately before
     * options.txt is loaded.
     *
     * Doing it before load() is important because it also allows
     * negative sensitivity values to be loaded back from options.txt.
     */
    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Options;load()V"
            )
    )
    private void subsense$replaceSensitivityOption(CallbackInfo ci) {
        this.sensitivity = new OptionInstance<>(
                "options.sensitivity",
                OptionInstance.noTooltip(),
                OptionsMixin::subsense$formatSensitivity,
                SUBSENSE_RANGE,
                0.5,
                value -> {
                }
        );
    }

    @Unique
    private static Component subsense$formatSensitivity(Component caption, Double value) {
        if (value >= 1.0) {
            return Options.genericValueLabel(
                    caption,
                    Component.translatable("options.sensitivity.max")
            );
        }

        long percentage = Math.round(value * 200.0);

        return Component.translatable(
                "options.percent_value",
                caption,
                percentage
        );
    }
}