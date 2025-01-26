package io.github.hyper1423.physicscustomizer.mixin.client;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(BlockStatesLoader.class)
public class BlockStatesLoaderMixin {
    @Unique private static final String[] PANE_CHECKLIST = new String[] {
            "iron_bars.json",
            "glass_pane.json",
            "white_stained_glass_pane.json",
            "light_gray_stained_glass_pane.json",
            "gray_stained_glass_pane.json",
            "black_stained_glass_pane.json",
            "brown_stained_glass_pane.json",
            "red_stained_glass_pane.json",
            "orange_stained_glass_pane.json",
            "yellow_stained_glass_pane.json",
            "lime_stained_glass_pane.json",
            "green_stained_glass_pane.json",
            "cyan_stained_glass_pane.json",
            "light_blue_stained_glass_pane.json",
            "blue_stained_glass_pane.json",
            "purple_stained_glass_pane.json",
            "magenta_stained_glass_pane.json",
            "pink_stained_glass_pane.json",
    };

    @Redirect(
            method = "method_65720",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/Resource;getReader()Ljava/io/BufferedReader;"
            )
    )
    private static BufferedReader interceptPaneStates(Resource resource, Map.Entry<Identifier, List<Resource>> entry) throws IOException {
        if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.USE_OLD_PANE_HITBOX).getValue()) {
            Identifier k = entry.getKey();
            for (String path: PANE_CHECKLIST) {
                if (k.equals(Identifier.ofVanilla("blockstates/" + path))) {
                    Identifier legacyPaneStates = Identifier.of("physicscustomizer", "blockstates_legacy/" + path);
                    Optional<Resource> optResource = MinecraftClient.getInstance().getResourceManager().getResource(legacyPaneStates);
                    if (optResource.isEmpty()) {
                        throw new IllegalStateException("Failed to load pane blockstate: %s".formatted(path));
//                        return resource.getReader();
                    }

                    return optResource.get().getReader();
                }
            }
        }
        return resource.getReader();
    }
}
