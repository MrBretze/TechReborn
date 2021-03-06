package techreborn.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import reborncore.api.power.EnumPowerTier;
import reborncore.common.powerSystem.TilePowerAcceptor;
import reborncore.common.util.FluidUtils;
import reborncore.common.util.Inventory;
import reborncore.common.util.Tank;
import techreborn.api.recipe.RecipeCrafter;
import techreborn.config.ConfigTechReborn;
import techreborn.init.ModBlocks;
import techreborn.init.ModFluids;
import techreborn.lib.Reference;
import ic2.api.tile.IWrenchable;

public class TileIndustrialGrinder extends TilePowerAcceptor
		implements IWrenchable, IFluidHandler, IInventory, ISidedInventory
{
	public static final int TANK_CAPACITY = 16000;

	public int tickTime;
	public Inventory inventory = new Inventory(6, "TileGrinder", 64, this);
	public Tank tank = new Tank("TileGrinder", TANK_CAPACITY, this);
	public RecipeCrafter crafter;
	public int connectionStatus;

	public TileIndustrialGrinder()
	{
		super(ConfigTechReborn.CentrifugeTier);
		// TODO configs

		int[] inputs = new int[2];
		inputs[0] = 0;
		inputs[1] = 1;
		int[] outputs = new int[4];
		outputs[0] = 2;
		outputs[1] = 3;
		outputs[2] = 4;
		outputs[3] = 5;
		crafter = new RecipeCrafter(Reference.industrialGrinderRecipe, this, 1, 4, inventory, inputs, outputs);
	}

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, EnumFacing side)
	{
		return false;
	}

	@Override
	public EnumFacing getFacing()
	{
		return getFacingEnum();
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer)
	{
		if (entityPlayer.isSneaking())
		{
			return true;
		}
		return false;
	}

	@Override
	public float getWrenchDropRate()
	{
		return 1.0F;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer)
	{
		return new ItemStack(ModBlocks.IndustrialGrinder, 1);
	}

	public boolean isComplete()
	{
		return false;
	}

	public boolean getMutliBlock()
	{
		for (EnumFacing direction : EnumFacing.values())
		{
			TileEntity tileEntity = worldObj.getTileEntity(new BlockPos(getPos().getX() + direction.getFrontOffsetX(),
					getPos().getY() + direction.getFrontOffsetY(), getPos().getZ() + direction.getFrontOffsetZ()));
			if (tileEntity instanceof TileMachineCasing)
			{
				if (((TileMachineCasing) tileEntity).isConnected()
						&& ((TileMachineCasing) tileEntity).getMultiblockController().isAssembled()
						&& ((TileMachineCasing) tileEntity).getMultiblockController().height == 3)
				{
					connectionStatus = 1;
					return true;
				}
			}
		}
		connectionStatus = 0;
		return false;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (getMutliBlock())
		{
			crafter.updateEntity();
		}
		FluidUtils.drainContainers(this, inventory, 0, 5);
		FluidUtils.drainContainers(this, inventory, 1, 5);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		inventory.readFromNBT(tagCompound);
		tank.readFromNBT(tagCompound);
		crafter.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		inventory.writeToNBT(tagCompound);
		tank.writeToNBT(tagCompound);
		crafter.writeToNBT(tagCompound);
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
	}

	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
	}

	/* IFluidHandler */
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill)
	{
		if (resource.getFluid() == FluidRegistry.WATER || resource.getFluid() == ModFluids.fluidMercury
				|| resource.getFluid() == ModFluids.fluidSodiumpersulfate)
		{
			int filled = tank.fill(resource, doFill);
			tank.compareAndUpdate();
			return filled;
		}
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
	{
		if (resource == null || !resource.isFluidEqual(tank.getFluid()))
		{
			return null;
		}
		FluidStack fluidStack = tank.drain(resource.amount, doDrain);
		tank.compareAndUpdate();
		return fluidStack;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
	{
		FluidStack drained = tank.drain(maxDrain, doDrain);
		tank.compareAndUpdate();
		return drained;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		if (fluid == FluidRegistry.WATER || fluid == ModFluids.fluidMercury || fluid == ModFluids.fluidSodiumpersulfate)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		return new FluidTankInfo[] { tank.getInfo() };
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		return inventory.decrStackSize(slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return inventory.removeStackFromSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		inventory.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		inventory.closeInventory(player);
	}

	@Override
	public int getField(int id)
	{
		return inventory.getField(id);
	}

	@Override
	public void setField(int id, int value)
	{
		inventory.setField(id, value);
	}

	@Override
	public int getFieldCount()
	{
		return inventory.getFieldCount();
	}

	@Override
	public void clear()
	{
		inventory.clear();
	}

	@Override
	public String getName()
	{
		return inventory.getName();
	}

	@Override
	public boolean hasCustomName()
	{
		return inventory.hasCustomName();
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return inventory.getDisplayName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return inventory.isItemValidForSlot(slot, stack);
	}

	// ISidedInventory
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] { 0, 1, 2, 3, 4, 5 } : new int[] { 0, 1, 2, 3, 4, 5 };
	}

	@Override
	public boolean canInsertItem(int slotIndex, ItemStack itemStack, EnumFacing side)
	{
		if (slotIndex >= 2)
			return false;
		return isItemValidForSlot(slotIndex, itemStack);
	}

	@Override
	public boolean canExtractItem(int slotIndex, ItemStack itemStack, EnumFacing side)
	{
		return slotIndex == 2 || slotIndex == 3 || slotIndex == 4 || slotIndex == 5;
	}

	public int getProgressScaled(int scale)
	{
		if (crafter.currentTickTime != 0)
		{
			return crafter.currentTickTime * scale / crafter.currentNeededTicks;
		}
		return 0;
	}

	@Override
	public double getMaxPower()
	{
		return 10000;
	}

	@Override
	public boolean canAcceptEnergy(EnumFacing direction)
	{
		return true;
	}

	@Override
	public boolean canProvideEnergy(EnumFacing direction)
	{
		return false;
	}

	@Override
	public double getMaxOutput()
	{
		return 0;
	}

	@Override
	public double getMaxInput()
	{
		return 32;
	}

	@Override
	public EnumPowerTier getTier()
	{
		return EnumPowerTier.LOW;
	}
}
