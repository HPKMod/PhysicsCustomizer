package io.github.hyper1423.physicscustomizer.render;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class PhysicsCustomizerModelLoadingPlugin implements ModelLoadingPlugin {
    public static final ModelIdentifier FOUR_SIDED_FURNACE_MODEL = new ModelIdentifier(Identifier.of("minecraft", "block/template_glass_pane_post"), "");

    @Override
    public void initialize(Context pluginContext) {
//        pluginContext.registerBlockStateResolver();
//        pluginContext.modifyBlockModelOnLoad().
//        // We want to add our model when the models are loaded
//        pluginContext.registerBlockStateResolver(
//                Blocks.GLASS_PANE,
//                new GlassPaneBlockStateResolver()
//        );
//        pluginContext.modifyModelOnLoad().register((original, context) -> {
//            // This is called for every model that is loaded, so make sure we only target ours
//            final Identifier id = context.id();
//            if(id != null && id.equals(FOUR_SIDED_FURNACE_MODEL.id())) {
//                return JsonUnbakedModel
//            } else {
//                // If we don't modify the model we just return the original as-is
//                return original;
//            }
//        });
    }
}
