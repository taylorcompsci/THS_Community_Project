package name.modid.GUI;

import name.modid.THSCommunityProject;
import name.modid.THSCommunityProjectClient;
import name.modid.item.DisplayInfo;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.render.RenderLayer;
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

import java.util.HashMap;

public class HudGui {

    private static final Identifier LAYER = Identifier.of(THSCommunityProject.MOD_ID, "hud-layer");

    //screen info
    public static int screenWidth = 0;
    public static int screenHeight = 0;

    //ALL UNITS ARE BASED ON PERCENTAGE!!! FOR EXAMPLE (2,3) would be %2 of the screen width and %3 of the screen height.

    //scaled from the top left corner of element
    private static Vector2d DIMENSIONS = new Vector2d(25,100);
    //Positions shift from the top left corner of element

    //CONSTANTS
    private static final Vector2d SHOWN_POS = new Vector2d(0, 0);
    private static final Vector2d HIDDEN_POS = new Vector2d(-25,0);

    //DYNAMIC
    private static Vector2d TARGET_POS = SHOWN_POS;
    private static Vector2d CURRENT_POS = HIDDEN_POS;

    //in milliseconds
    private static double TWEEN_DURATION = 10000;

    private static double startTime = -1;


    //display info stuff

    //context
    private static DrawContext publicContext;
    private static  TextRenderer fontRenderer;

    //test case
    private static DisplayInfo currentDisplayed = new DisplayInfo(Identifier.of(THSCommunityProject.MOD_ID, "textures/icon.png"), "Piano", new HashMap<>(){{
        put("Am", "K");
        put("B", "B");
        put("S", "S");

    }});
    private static int padding = 2; //percent
//    private static Matrix4f identityMatrix = new Matrix4f().identity();
//    private static VertexConsumerProvider vertexConsumerProvider;


    public static void show(){TARGET_POS = SHOWN_POS;}
    public static void hide(){TARGET_POS = HIDDEN_POS;}

    public static void initialize(){
        THSCommunityProject.LOGGER.info("Initializing the GUI.");
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(IdentifiedLayer.CHAT, LAYER, HudGui::render));
    }

    public static int screenWidthPercentage(double percent){return (int) (screenWidth*(percent/100.0));}
    public static int screenHeightPercentage(double percent){return (int) (screenHeight*(percent/100.0));}

    private static void render(DrawContext context, RenderTickCounter tickCounter){

        if(startTime == -1){
            startTime = Util.getMeasuringTimeMs();
        }



        double currentTime = Util.getMeasuringTimeMs()-startTime;

        double progress = MathHelper.clamp(currentTime/TWEEN_DURATION, 0.0, 1.0);


        MinecraftClient client = MinecraftClient.getInstance();

        publicContext = context;
        fontRenderer = client.textRenderer;

        screenWidth = client.getWindow().getScaledWidth();
        screenHeight = client.getWindow().getScaledHeight();

        CURRENT_POS.x = MathHelper.lerp(progress, CURRENT_POS.x, TARGET_POS.x);
        CURRENT_POS.y = MathHelper.lerp(progress, CURRENT_POS.y, TARGET_POS.y);

//        THSCommunityProject.LOGGER.info(String.format("(%.2f, %.2f)", CURRENT_POS.x, CURRENT_POS.y));

        int posX = screenWidthPercentage(CURRENT_POS.x);
        int posY = screenHeightPercentage(CURRENT_POS.y);

        int posX2 = screenWidthPercentage(CURRENT_POS.x+DIMENSIONS.x);
        int posY2 = screenHeightPercentage(CURRENT_POS.y+DIMENSIONS.y);



        context.fill(posX,posY,posX2,posY2, 0xFFFF0000);
        int imageDimension = screenWidthPercentage(DIMENSIONS.x*(1-(2*padding/100.0)));
        context.drawTexture(RenderLayer::getGuiTextured, currentDisplayed.textureID(),posX+screenWidthPercentage(DIMENSIONS.x * (padding/100.0)), posY+screenHeightPercentage(DIMENSIONS.y * (padding/100.0)), 0,0, imageDimension,imageDimension, imageDimension, imageDimension);

        int textX = (int) (posX+screenWidthPercentage((DIMENSIONS.x) * (padding/100.0)));
        int textY = (int) (imageDimension + (screenHeight * (2*padding/100.0)));
        drawText(currentDisplayed.name(), textX, textY , Color.BLUE.getRGB(), false);

        for(String note: currentDisplayed.notes().keySet()){
            textY+=screenHeightPercentage(5);
            String text = String.format("%s - %s", note, currentDisplayed.notes().get(note));
            drawText(text, textX, textY , Color.BLUE.getRGB(), false);

        }


    }

    public static void drawText(String text, int x, int y, int color, boolean shadow){
        publicContext.drawText(
                fontRenderer,
                text,
                x,
                y,
                color,
                shadow
        );
    }
}
