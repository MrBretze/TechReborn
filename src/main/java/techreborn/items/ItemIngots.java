package techreborn.items;

import java.security.InvalidParameterException;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModItems;
import techreborn.lib.ModInfo;

public class ItemIngots extends ItemTextureBase
{
	public static final String[] types = new String[] { "aluminum", "antimony", "batteryAlloy", "redAlloy", "blueAlloy",
			"brass", "bronze", "cadmium", "chrome", "copper", "cupronickel", "electrum", "indium", "invar", "iridium",
			"kanthal", "lead", "lodestone", "magnalium", "nichrome", "nickel", "osmium", "platinum", "silver", "steel",
			"tellurium", "tin", "titanium", "tungsten", "hotTungstensteel", "tungstensteel", "zinc", "refinedIron" };

	public ItemIngots()
	{
		setCreativeTab(TechRebornCreativeTabMisc.instance);
		setHasSubtypes(true);
		setUnlocalizedName("techreborn.ingot");
	}

	public static ItemStack getIngotByName(String name, int count)
	{
		for (int i = 0; i < types.length; i++)
		{
			if (types[i].equalsIgnoreCase(name))
			{
				return new ItemStack(ModItems.ingots, count, i);
			}
		}
		throw new InvalidParameterException("The ingot " + name + " could not be found.");
	}

	public static ItemStack getIngotByName(String name)
	{
		return getIngotByName(name, 1);
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
		return ModInfo.MOD_ID + ":items/ingot/" + types[damage] + "Ingot";
	}

	@Override
	public int getMaxMeta()
	{
		return types.length;
	}

}
