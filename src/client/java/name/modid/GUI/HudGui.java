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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import org.joml.Matrix4f;
import org.joml.Vector2d;


public class HudGui {

    private static final Identifier LAYER = Identifier.of(THSCommunityProject.MOD_ID, "hud-layer");


    //ALL UNITS ARE BASED ON PERCENTAGE!!! FOR EXAMPLE (2,3) would be %2 of the screen width and %3 of the screen height.

    //scaled from the top left corner of element
    private static Vector2d DIMENSIONS = new Vector2d(100,25);
    //Positions shift from the top left corner of element
    private static Vector2d TARGET_POS = new Vector2d(0,75);
    private static Vector2d CURRENT_POS = new Vector2d(0,75);

    public static void initialize(){
        THSCommunityProject.LOGGER.info("Initializing the GUI.");
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, LAYER, HudGui::render));
    }


    private static void render(DrawContext context, RenderTickCounter tickCounter){

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer fontRenderer = client.textRenderer;

        Matrix4f identityMatrix = new Matrix4f().identity();

        VertexConsumerProvider vertexConsumerProvider = client.getBufferBuilders().getEntityVertexConsumers();

        double currentTime = Util.getMeasuringTimeMs() / 1000.0;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();



        int posX = (int) (screenWidth * MathHelper.lerp(currentTime, CURRENT_POS.x, TARGET_POS.x));
        int posY = (int) (screenWidth * MathHelper.lerp(currentTime, CURRENT_POS.x, TARGET_POS.x));

        CURRENT_POS.x = posX;
        CURRENT_POS.y = posY;

        int posX2 = (int) (((double) posX /screenWidth+DIMENSIONS.x)*screenWidth);
        int posY2 = (int) (((double) posY /screenHeight+DIMENSIONS.y)*screenHeight);

        context.fill(posX/100,posY/100,posX2/100,posY2/100, 0xFFFF0000);

        fontRenderer.draw(
                "I hate fabric!!",
                posX+5,
                   posY+5,
                    0xFF404040,
                true,
                identityMatrix,
                vertexConsumerProvider,
                TextRenderer.TextLayerType.NORMAL,
                Integer.MAX_VALUE,
                0

        );

    }
}
