package name.modid.item;

import net.minecraft.util.Identifier;

//For each instrument to carry information to the GUI
public record DisplayInfo(Identifier textureID, int textureWidth, int textureHeight){
}
