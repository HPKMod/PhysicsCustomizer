package io.github.hyper1423.physicscustomizer.render;

import io.github.hyper1423.physicscustomizer.PhysicsCustomizerClient;
import io.github.hyper1423.physicscustomizer.config.PhysicsConfigKeys;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableMesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.client.texture.SpriteAtlasTexture;

public class LegacyPaneUnbakedModel implements UnbakedModel, BakedModel, FabricBakedModel {
    // for versions before 1.21, replace `Identifier.ofVanilla` with `new Identifier`.
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier[] SPRITE_IDS = new SpriteIdentifier[]{
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/furnace_front_on")),
            new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/furnace_top"))
    };
    private final Sprite[] sprites = new Sprite[SPRITE_IDS.length];

    private Mesh mesh;

    // Some constants to avoid magic numbers, these need to match the SPRITE_IDS
//    private static final int SPRITE_SIDE = 0;
//    private static final int SPRITE_TOP = 1;

    @Override
    public void resolve(Resolver resolver) {

    }

    @Override
    public BakedModel bake(ModelTextures textures, Baker baker, ModelBakeSettings settings, boolean ambientOcclusion, boolean isSideLit, ModelTransformation transformation) {

        for (int i = 0; i < 2; i++) {
            sprites[i] = baker.getSpriteGetter().get(SPRITE_IDS[i]);
        }

        Renderer renderer = Renderer.get();
        MutableMesh mMesh = renderer.mutableMesh();
        QuadEmitter emitter = mMesh.emitter();

        for (Direction dir: Direction.values()) {
            int i = dir.getAxis().isHorizontal() ? 0 : 1;
            if (PhysicsCustomizerClient.getPhysicsConfig().getEntry(PhysicsConfigKeys.USE_OLD_PANE_HITBOX).getValue())
                i = 1 - i;
            emitter.square(dir, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            emitter.spriteBake(sprites[i], MutableQuadView.BAKE_LOCK_UV);
            emitter.color(-1, -1, -1, -1);
            emitter.emit();
        }

        this.mesh = mMesh;
        return this;

//        // Get the sprites
//        for(int i = 0; i < SPRITE_IDS.length; ++i) {
//            sprites[i] = textureGetter.apply(SPRITE_IDS[i]);
//        }
//        // Build the mesh using the Renderer API
//        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
//        MeshBuilder builder = renderer.meshBuilder();
//        QuadEmitter emitter = builder.getEmitter();
//
//        for(Direction direction : Direction.values()) {
//            // UP and DOWN share the Y axis
//            int spriteIdx = direction == Direction.UP || direction == Direction.DOWN ? SPRITE_TOP : SPRITE_SIDE;
//            // Add a new face to the mesh
//            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
//            // Set the sprite of the face, must be called after .square()
//            // We haven't specified any UV coordinates, so we want to use the whole texture. BAKE_LOCK_UV does exactly that.
//            emitter.spriteBake(sprites[spriteIdx], MutableQuadView.BAKE_LOCK_UV);
//            // Enable texture usage
//            emitter.color(-1, -1, -1, -1);
//            // Add the quad to the mesh
//            emitter.emit();
//        }
//        mesh = builder.build();

//        return this;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
        // Don't need because we use FabricBakedModel instead. However, it's better to not return null in case some mod decides to call this function.
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true; // we want the block to have a shadow depending on the adjacent blocks
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return sprites[1]; // Block break particle, let's use furnace_top
    }

    @Override
    public ModelTransformation getTransformation() {
        return null;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false; // False to trigger FabricBakedModel rendering
    }

    @Override
    public void emitBlockQuads(QuadEmitter emitter, BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, Predicate<@Nullable Direction> cullTest) {
        mesh.outputTo(emitter);
    }

    @Override
    public void emitItemQuads(QuadEmitter emitter, Supplier<Random> randomSupplier) {

    }
}
