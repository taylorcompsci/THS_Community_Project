package name.modid;

import name.modid.events.InputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;

public class THSCommunityProjectClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		InputHandler.register();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}