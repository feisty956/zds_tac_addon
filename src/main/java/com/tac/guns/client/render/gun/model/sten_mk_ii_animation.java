package com.tac.guns.client.render.gun.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.Config;
import com.tac.guns.client.SpecialModels;
import com.tac.guns.client.handler.ShootingHandler;
import com.tac.guns.client.render.gun.IOverrideModel;
import com.tac.guns.client.render.gun.ModelOverrides;
import com.tac.guns.client.util.RenderUtil;
import com.tac.guns.common.Gun;
import com.tac.guns.init.ModEnchantments;
import com.tac.guns.item.GunItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import com.tac.guns.util.GunModifierHelper;

/*
 * Because the revolver has a rotating chamber, we need to render it in a
 * different way than normal items. In this case we are overriding the model.
 */

/**
 * Author: Timeless Development, and associates.
 */
public class sten_mk_ii_animation implements IOverrideModel {

    @Override
    public void render(float v, ItemCameraTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, MatrixStack matrices, IRenderTypeBuffer renderBuffer, int light, int overlay) {

        
        if(GunModifierHelper.getAmmoCapacity(stack) > -1)
        {
            if(Gun.hasAmmo(stack))
                RenderUtil.renderModel(SpecialModels.STEN_MK_II_EXTENDED_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            else
                RenderUtil.renderModel(SpecialModels.STEN_MK_II_EXTENDED_MAG_EMPTY.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        else
        {
            if(Gun.hasAmmo(stack))
                RenderUtil.renderModel(SpecialModels.STEN_MK_II_STANDARD_MAG.getModel(), stack, matrices, renderBuffer, light, overlay);
            else
                RenderUtil.renderModel(SpecialModels.STEN_MK_II_STANDARD_MAG_EMPTY.getModel(), stack, matrices, renderBuffer, light, overlay);
        }
        RenderUtil.renderModel(SpecialModels.STEN_MK_II_BODY.getModel(), stack, matrices, renderBuffer, light, overlay);

        //We're getting the cooldown tracker for the item - items like the sword, ender pearl, and chorus fruit all have this too.
        Gun gun = ((GunItem) stack.getItem()).getGun();
        float cooldownOg = ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate()) < 0 ? 1 : ShootingHandler.get().getshootMsGap() / ShootingHandler.calcShootTickGap(gun.getGeneral().getRate());
        //Always push
        matrices.push();
        if(Gun.hasAmmo(stack))
        {
            // Math provided by Bomb787 on GitHub and Curseforge!!!
            matrices.scale(0, 0, 1+(float)(cooldownOg));
        }
        else if(!Gun.hasAmmo(stack))
        {
            if(cooldownOg > 0.5){
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.scale(0, 0, 1+(float)(cooldownOg));
            }
            else
            {
                matrices.scale(0, 0, 1+(float)(cooldownOg));
            }
        }
        RenderUtil.renderModel(SpecialModels.STEN_MK_II_BOLT_SPRING.getModel(), stack, matrices, renderBuffer, light, overlay);
        matrices.pop();
        matrices.push();
        //matrices.translate(0, 0, 0.22f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
        if(Gun.hasAmmo(stack))
        {
            // Math provided by Bomb787 on GitHub and Curseforge!!!
            matrices.translate(0, 0, -0.22f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
        }
        else if(!Gun.hasAmmo(stack))
        {
            if(cooldownOg > 0.5){
                // Math provided by Bomb787 on GitHub and Curseforge!!!
                matrices.translate(0, 0, -0.22f * (-4.5 * Math.pow(cooldownOg-0.5, 2) + 1.0));
            }
            else
            {
                matrices.translate(0, 0, -0.22f * (-4.5 * Math.pow(0.5-0.5, 2) + 1.0));
            }
        }
        RenderUtil.renderModel(SpecialModels.STEN_MK_II_BOLT.getModel(), stack, matrices, renderBuffer, light, overlay);

        //Always pop
        matrices.pop();
    }

     

    //TODO comments
}
