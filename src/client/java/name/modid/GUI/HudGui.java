package name.modid.GUI;

import name.modid.THSCommunityProject;
import name.modid.THSCommunityProjectClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import org.joml.Matrix4f;
import org.joml.Vector2d;

import java.awt.*;


public class HudGui {

    private static final Identifier LAYER = Identifier.of(THSCommunityProject.MOD_ID, "hud-layer");


    //ALL UNITS ARE BASED ON PERCENTAGE!!! FOR EXAMPLE (2,3) would be %2 of the screen width and %3 of the screen height.

    //scaled from the top left corner of element
    private static Vector2d DIMENSIONS = new Vector2d(100,25);
    //Positions shift from the top left corner of element
    private static Vector2d TARGET_POS = new Vector2d(0,75);
    private static Vector2d CURRENT_POS = new Vector2d(0,100);

    //in milliseconds
    private static double TWEEN_DURATION = 10000;

    private static double startTime = -1;

    public static void initialize(){
        THSCommunityProject.LOGGER.info("Initializing the GUI.");
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(IdentifiedLayer.CHAT, LAYER, HudGui::render));
    }


    private static void render(DrawContext context, RenderTickCounter tickCounter){

        if(startTime == -1){
            startTime = Util.getMeasuringTimeMs();
        }

        double currentTime = Util.getMeasuringTimeMs()-startTime;

        double progress = MathHelper.clamp(currentTime/TWEEN_DURATION, 0.0, 1.0);


        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer fontRenderer = client.textRenderer;

        Matrix4f identityMatrix = new Matrix4f().identity();

        VertexConsumerProvider vertexConsumerProvider = client.getBufferBuilders().getEntityVertexConsumers();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        CURRENT_POS.x = MathHelper.lerp(progress, CURRENT_POS.x, TARGET_POS.x);
        CURRENT_POS.y = MathHelper.lerp(progress, CURRENT_POS.y, TARGET_POS.y);

        int posX = (int) (screenWidth*(CURRENT_POS.x/100));
        int posY = (int) (screenHeight*(CURRENT_POS.y/100));

        int posX2 = (int) (screenWidth*((CURRENT_POS.x+DIMENSIONS.x)/100));
        int posY2 = (int) (screenHeight*((CURRENT_POS.y+DIMENSIONS.y)/100));



        context.fill(posX,posY,posX2,posY2, 0xFFFF0000);

        fontRenderer.draw(
                "I hate fabric!!",
                5,
                    5,
                Color.WHITE.getRGB(),
                false,
                identityMatrix,
                vertexConsumerProvider,
                TextRenderer.TextLayerType.NORMAL,
                Integer.MAX_VALUE,
                200

        );

    }
}
