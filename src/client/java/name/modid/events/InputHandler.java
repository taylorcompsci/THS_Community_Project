package name.modid.events;
import name.modid.THSCommunityProject;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InputHandler {

    public static final String CATEGORY = "key.category.ths-community-project";

    //maps all the keybinds to their respected functions. Each function must take in the keybinding and client and return an Integer!!!
    public static HashMap<KeyBinding, BiFunction<KeyBinding, MinecraftClient , Integer>> KEYBINDS = new HashMap<>();



    //registers the tick that checks each keybind every client tick
    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for(KeyBinding key : KEYBINDS.keySet()){
                if(key.wasPressed()){
                    //in the future if we want to do smth with the return value
                    int returnValue = KEYBINDS.get(key).apply(key,client);
                    int s = GLFW.GLFW_KEY_0;
                }
            }
        });
    }

    //registers each keybind. Make sure that for the keycode, you use the GLFW library or the Uppercase ASCII value of the keybind. For example 'A' would 65.
    public static KeyBinding registerKeybind(String name, int keycode, BiFunction<KeyBinding, MinecraftClient, Integer> action){
        KeyBinding key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ths-community-project."+name,
                InputUtil.Type.KEYSYM,
                keycode,
                CATEGORY
        ));
        KEYBINDS.put(key, action);
        return key;
    }

    public static void register(){
        THSCommunityProject.LOGGER.info("Registering keybinds!");

        /*KEYBINDS DEFINED HERE:*/


        registerKeybind("test", (int) 'Y', (key, client) -> {
            
            client.player.networkHandler.sendChatMessage("Hey bro!!");

            return 1;
        });


        /*END OF KEYBIND REGISTRATION*/
        registerKeyInputs();
    }






}
