package com.vapourdrive.attaineddrops.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.vapourdrive.attaineddrops.AttainedDrops;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMobBulb extends Block
{
	private IIcon[] bulbTop;
	private IIcon[] bulbSide;

	protected BlockMobBulb()
	{
		super(Material.plants);
		setTickRandomly(true);
		setBlockName(BlockInfo.BlockMobBulbName);
		setHardness(BlockInfo.BlockMobBulbHardness);
		setStepSound(Block.soundTypeGrass);
		float f = 0.1875F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.5F, 0.5F + f);
		setCreativeTab(AttainedDrops.tabAttainedDrops);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		bulbTop = new IIcon[(BlockInfo.MobDrops.length)];
		bulbSide = new IIcon[(BlockInfo.MobDrops.length)];

		for (int i = 0; i < (BlockInfo.MobDrops.length); ++i)
		{
			bulbTop[i] = register.registerIcon(BlockInfo.BlockIconLocation + BlockInfo.BlockMobBulbTopIcon + i);
			bulbSide[i] = register.registerIcon(BlockInfo.BlockIconLocation + BlockInfo.BlockMobBulbSideIcon + i);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side != 1 && side != 0)
		{
			return bulbSide[meta];
		}

		if (side == 1 || side == 0)
		{
			return bulbTop[meta];
		}
		else
			return blockIcon;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < BlockInfo.MobDrops.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, getBlockById(0), 0, 2);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		if (world.isAirBlock(x, y - 1, z))
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	{
		return true;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		return BlockInfo.MobDrops[meta];
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		int DropNumber;
		DropNumber = BulbHelper.staticDropNumber(meta) + random.nextInt(BulbHelper.dynamicDropNumber(meta));

		if (BulbHelper.canFortuneBulb(meta) == true)
		{
			return (quantityDroppedWithBonus(fortune, random) + DropNumber);
		}
		else
			return DropNumber;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand)
	{
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, rand, fortune))
		{
			int j = rand.nextInt(fortune + 2) - 1;

			if (j < 0)
			{
				j = 0;
			}

			return this.quantityDropped(rand) * (j + 1);
		}
		else
		{
			return this.quantityDropped(rand);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return world.getBlock(x, y, z).isReplaceable(world, z, y, z) && !world.isAirBlock(x, y - 1, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		if (world.getBlock(x, y - 1, z) != null && world.getBlock(x, y - 1, z) == AD_Blocks.BlockMobPlant)
		{
			world.setBlockMetadataWithNotify(x, y - 1, z, 7, 2);
		}
	}

	@Override
	public int tickRate(World world)
	{
		return 90;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int dropNumber = world.getBlockMetadata(x, y, z);
		if (BulbHelper.canSpawnParticles(dropNumber) == true)
		{
			particles(world, x, y, z, dropNumber);
		}
	}

	private void particles(World world, int x, int y, int z, int dropNumber)
	{
		if (world.rand.nextInt(BulbHelper.particleSpawnRate(dropNumber)) == 0)
		{
			double d0 = (double) ((float) x + world.rand.nextFloat());
			double d1 = (double) ((float) y + world.rand.nextFloat());
			double d2 = (double) ((float) z + world.rand.nextFloat());
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;

			int i1 = world.rand.nextInt(2) * 2 - 1;
			d3 = ((double) world.rand.nextFloat() - 0.5D) * 0.5D;
			d5 = ((double) world.rand.nextFloat() - 0.5D) * 0.5D;

			d3 = (double) (world.rand.nextFloat() * 30.0F * (float) i1);
			d4 = (double) (world.rand.nextFloat() * 30.0F * (float) i1);
			d5 = (double) (world.rand.nextFloat() * 30.0F * (float) i1);

			world.spawnParticle("happyVillager", d1, d2, d0, d4, d5, d3);
			world.spawnParticle("happyVillager", d0, d1, d2, d3, d4, d5);
			world.spawnParticle("happyVillager", d2, d3, d1, d5, d3, d4);
		}
	}

}
