package techreborn.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.client.gui.SlotOutput;
import techreborn.tiles.TileIndustrialGrinder;

public class ContainerIndustrialGrinder extends ContainerCrafting
{

	public int connectionStatus;
	EntityPlayer player;
	TileIndustrialGrinder tile;

	public ContainerIndustrialGrinder(TileIndustrialGrinder tileGrinder, EntityPlayer player)
	{
		super(tileGrinder.crafter);
		tile = tileGrinder;
		this.player = player;

		// input
		this.addSlotToContainer(new Slot(tileGrinder.inventory, 0, 32, 26));
		this.addSlotToContainer(new Slot(tileGrinder.inventory, 1, 32, 44));

		// outputs
		this.addSlotToContainer(new SlotOutput(tileGrinder.inventory, 2, 77, 35));
		this.addSlotToContainer(new SlotOutput(tileGrinder.inventory, 3, 95, 35));
		this.addSlotToContainer(new SlotOutput(tileGrinder.inventory, 4, 113, 35));
		this.addSlotToContainer(new SlotOutput(tileGrinder.inventory, 5, 131, 35));

		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return true;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if (this.connectionStatus != tile.connectionStatus)
			{
				icrafting.sendProgressBarUpdate(this, 10, tile.connectionStatus);
			}
		}
	}

	@Override
	public void onCraftGuiOpened(ICrafting crafting)
	{
		super.onCraftGuiOpened(crafting);
		crafting.sendProgressBarUpdate(this, 10, tile.connectionStatus);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int value)
	{
		if (id == 10)
		{
			this.connectionStatus = value;
		}
	}

}
