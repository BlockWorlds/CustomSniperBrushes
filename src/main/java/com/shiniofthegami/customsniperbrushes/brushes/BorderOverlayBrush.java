package com.shiniofthegami.customsniperbrushes.brushes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;

public class BorderOverlayBrush extends PerformBrush{
	private boolean checkTop = false;
	
	public BorderOverlayBrush(){
		this.setName("BorderOverlay");
	}
	
	public String getPermissionNode() {
		return "voxelsniper.brush.borderoverlay";
	}

	@Override
	public void info(Message vm) {
		vm.brushName(this.getName());
		vm.size();
	}
	
	@Override
    protected final void arrow(final SnipeData v)
    {
        this.bover(v, this.getTargetBlock());
    }

    @Override
    protected final void powder(final SnipeData v)
    {
        this.bover(v, this.getLastBlock());
    }
    
    private void bover(final SnipeData v, Block targetBlock){
    	final int brushSize = v.getBrushSize();
    	
    	int blockPosX = targetBlock.getX() - (brushSize/2);
    	int blockPosY = targetBlock.getY() - (brushSize/2);
    	int blockPosZ = targetBlock.getZ() - (brushSize/2);
    	List<Block> blockQueue = new ArrayList<Block>();
    	for(int z = blockPosZ ; z < blockPosZ+brushSize ; z++){
    		for(int x = blockPosX ; x < blockPosX+brushSize ; x++){
    			for(int y = blockPosY ; y < blockPosY+brushSize ; y++){
    				Block currentBlock = this.clampY(x, y, z);
    				if(!checkTop || currentBlock.getRelative(BlockFace.UP).isEmpty()){
    					if(currentBlock.getRelative(BlockFace.EAST).isEmpty()||currentBlock.getRelative(BlockFace.WEST).isEmpty()||currentBlock.getRelative(BlockFace.SOUTH).isEmpty()||currentBlock.getRelative(BlockFace.NORTH).isEmpty()){
    						blockQueue.add(currentBlock);
    					}
    				}
    			}
    		}
    	}
    	for(Block b : blockQueue){
    		this.current.perform(b);
    	}
    	v.owner().storeUndo(this.current.getUndo());
    }
    
    @Override
    public final void parameters(final String[] par, final SnipeData v){
    	for (int i = 1; i < par.length; i++)
        {
            final String parameter = par[i];

            if (parameter.equalsIgnoreCase("info"))
            {
                v.sendMessage(ChatColor.GOLD + "BorderOverlay Brush Parameters:");
                v.sendMessage(ChatColor.AQUA + "/b bover true -- will make sure blocks above current blocks are air before performing on them. /b bover false will switch back. (false is default)");
                return;
            }
            else if (parameter.startsWith("true"))
            {
                this.checkTop = true;
                v.sendMessage(ChatColor.AQUA + "Block above checking mode ON.");
            }
            else if (parameter.startsWith("false"))
            {
                this.checkTop = false;
                v.sendMessage(ChatColor.AQUA + "Block above checking mode OFF.");
            }
            else
            {
                v.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        }
    }

}
