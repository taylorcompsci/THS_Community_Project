package name.modid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.minecraft.text.Text;

public class Keybindings implements ClientModInitializer {

    public static KeyBinding testKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                 "key.ths-community-project.test",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_N,
                    "category.ths-community-project.test"
            )
    );


    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (testKey.wasPressed())
                client.player.sendMessage(Text.literal("Testing keybind..."),false);
        });

    }
}
