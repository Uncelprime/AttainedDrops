package com.vapourdrive.attaineddrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class AD_Blocks
{
	public static Block BlockMobDirt;
	public static Block BlockMobPlant;
	public static Block BlockMobBulb;
	public static Block BlockConcentrateDrop;
//	public static Block BlockXPBall;
//	public static Block BlockXPObelisk;
//	public static Block BlockXPObsidian;

	public static void init()
	{
		BlockMobDirt = new BlockMobDirt();
		BlockMobPlant = new BlockMobPlant();
		BlockMobBulb = new BlockMobBulb();
		BlockConcentrateDrop = new BlockVitalizedSoil();
//		BlockXPBall = new BlockXPBall();
//		BlockXPObelisk = new BlockXPObelisk();
//		BlockXPObsidian = new BlockXPObsidian();

		GameRegistry.registerBlock(BlockMobDirt, ItemMobDirt.class, BlockInfo.BlockMobDirtName);
		GameRegistry.registerBlock(BlockMobPlant, BlockInfo.BlockMobPlantName);
		GameRegistry.registerBlock(BlockMobBulb, ItemBulb.class, BlockInfo.BlockMobBulbName);
		GameRegistry.registerBlock(BlockConcentrateDrop, ItemVitalizedSoil.class, BlockInfo.BlockConcentrateDropName);
//		GameRegistry.registerBlock(BlockXPBall, ItemXPBall.class, BlockInfo.BlockXPBallName);
//		GameRegistry.registerBlock(BlockXPObelisk, ItemXPObelisk.class, BlockInfo.BlockXPObeliskName);
//		GameRegistry.registerBlock(BlockXPObsidian, ItemXPObsidian.class, BlockInfo.BlockXPObsidianName);
	}

	public static void registerRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(BlockConcentrateDrop, 1, 0), new Object[]
		{ Items.wheat_seeds, new ItemStack(Items.dye, 0, 15), new ItemStack(Items.water_bucket, 2), Blocks.dirt });

	}

}
