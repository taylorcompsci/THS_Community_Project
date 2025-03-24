package name.modid.item;

import net.minecraft.util.Identifier;

import java.util.HashMap;

//For each instrument to carry information to the GUI
public record DisplayInfo(Identifier textureID, String name, HashMap<String, String> notes){
}
