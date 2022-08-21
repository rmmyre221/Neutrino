package com.frostwizard4.Neutrino.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin extends EndPortalBlockEntity {
    protected EndGatewayBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
    @Inject(at = @At("HEAD"), method = "serverTick")
    private static void serverTick(World world, BlockPos pos, BlockState state, EndGatewayBlockEntity blockEntity, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            if (world.getTime() % 100L == 0L) {
                for (MobEntity e : world.getEntitiesByClass(MobEntity.class, Box.of(Vec3d.ofCenter(pos), 32, 32, 32), EndGatewayBlockEntity::canTeleport)) {
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 80, 1));
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 1));
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 80, 1));
                }
            }
        }
    }
}





