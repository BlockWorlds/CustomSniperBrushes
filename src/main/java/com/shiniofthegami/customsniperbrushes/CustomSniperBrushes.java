package com.shiniofthegami.customsniperbrushes;
import org.bukkit.plugin.java.JavaPlugin;

import com.shiniofthegami.customsniperbrushes.brushes.BorderOverlayBrush;
import com.thevoxelbox.voxelsniper.VoxelSniper;


public class CustomSniperBrushes extends JavaPlugin{
	private VoxelSniper sniperPlugin;
	public void onEnable(){
		sniperPlugin = VoxelSniper.getInstance();
		sniperPlugin.getBrushManager().registerSniperBrush(BorderOverlayBrush.class, "bover", "borderoverlay");
	}
}
