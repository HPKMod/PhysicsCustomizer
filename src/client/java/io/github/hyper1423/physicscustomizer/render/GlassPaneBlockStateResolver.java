package io.github.hyper1423.physicscustomizer.render;

import net.fabricmc.fabric.api.client.model.loading.v1.BlockStateResolver;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class GlassPaneBlockStateResolver implements BlockStateResolver {
//    private final

    @Override
    public void resolveBlockStates(Context context) {
//        MinecraftClient.getInstance().getResourceManager().getResource(Identifier.ofVanilla("models/block/template_glass_pane_"))
//        UnbakedModel ubModel = JsonUnbakedModel.deserialize();
//        BlockStatesLoader
//        PaneBlock block = (PaneBlock) context.block();
//        BlockState lonePane = block.getDefaultState()
//                .with(PaneBlock.NORTH, false)
//                .with(PaneBlock.EAST, false)
//                .with(PaneBlock.SOUTH, false)
//                .with(PaneBlock.WEST, false);
//
//        context.setModel();
    }
    public enum PaneType {
        IRON_BAR,
        GLASS_PANE,
        RED_STAINED_GLASS_PANE,
        ORANGE_STAINED_GLASS_PANE,
        YELLOW_STAINED_GLASS_PANE,
        LIME_STAINED_GLASS_PANE,
        GREEN_STAINED_GLASS_PANE,
        LIGHT_BLUE_STAINED_GLASS_PANE,
        CYAN_STAINED_GLASS_PANE,
        BLUE_STAINED_GLASS_PANE,

    }
}
