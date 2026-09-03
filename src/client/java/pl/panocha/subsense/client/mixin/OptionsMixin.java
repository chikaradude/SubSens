package pl.panocha.subsense.client.mixin;

import com.mojang.serialization.Codec;
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

    @Unique
    private static final double SUBSENSE_MIN = -1.0 / 3.0;

    @Unique
    private static final double SUBSENSE_MAX = 1.0;
    
    @Unique
    private static final OptionInstance.SliderableValueSet<Double> SUBSENSE_RANGE =
            OptionInstance.UnitDouble.INSTANCE.xmap(
                    slider -> SUBSENSE_MIN
                            + slider * (SUBSENSE_MAX - SUBSENSE_MIN),

                    value -> (value - SUBSENSE_MIN)
                            / (SUBSENSE_MAX - SUBSENSE_MIN)
            );

    @Unique
    private static final Codec<Double> SUBSENSE_CODEC =
            Codec.doubleRange(SUBSENSE_MIN, SUBSENSE_MAX);

    @Shadow
    @Final
    @Mutable
    private OptionInstance<Double> sensitivity;

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
                SUBSENSE_CODEC,
                0.5,
                value -> {
                }
        );
    }

    @Unique
    private static Component subsense$formatSensitivity(
            Component caption,
            Double value
    ) {
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