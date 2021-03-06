package techreborn.items;

import java.security.InvalidParameterException;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.lib.ModInfo;

public class ItemPlates extends ItemTextureBase
{

	public static final String[] types = new String[] { "aluminum", "batteryAlloy", "brass", "bronze", "carbon",
			"chrome", "coal", "copper", "diamond", "electrum", "emerald", "gold", "invar", "iridium", "iron", "lapis",
			"lead", "magnalium", "nickel", "obsidian", "osmium", "peridot", "platinum", "redGarnet", "redstone", "ruby",
			"sapphire", "silicon", "silver", "steel", "teslatite", "tin", "titanium", "tungsten", "tungstensteel",
			"yellowGarnet", "zinc" };

	public ItemPlates()
	{
		setUnlocalizedName("techreborn.plate");
		setHasSubtypes(true);
		setCreativeTab(TechRebornCreativeTabMisc.instance);
	}

	public static ItemStack getPlateByName(String name, int count)
	{
		for (int i = 0; i < types.length; i++)
		{
			if (types[i].equalsIgnoreCase(name))
			{
				return new ItemStack(ModItems.plate, count, i);
			}
		}
		throw new InvalidParameterException("The plate " + name + " could not be found.");
	}

	public static ItemStack getPlateByName(String name)
	{
		return getPlateByName(name, 1);
	}

	@Override
	// gets Unlocalized Name depending on meta data
	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= types.length)
		{
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + types[meta];
	}

	// Adds Dusts SubItems To Creative Tab
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int meta = 0; meta < types.length; ++meta)
		{
			list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public String getTextureName(int damage)
	{
		return ModInfo.MOD_ID + ":items/plate/" + types[damage] + "Plate";
	}

	@Override
	public int getMaxMeta()
	{
		return types.length;
	}

}
