package techreborn.items.tools;

import java.util.List;

import me.modmuss50.jsonDestroyer.api.ITexturedItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.RebornCore;
import reborncore.api.power.IEnergyItemInfo;
import reborncore.common.powerSystem.PoweredItem;
import reborncore.common.util.Color;
import techreborn.client.TechRebornCreativeTab;
import techreborn.config.ConfigTechReborn;
import techreborn.lib.ModInfo;

public class ItemRockCutter extends ItemPickaxe implements IEnergyItemInfo, ITexturedItem
{

	public static final int maxCharge = ConfigTechReborn.RockCutterCharge;
	public static final int tier = ConfigTechReborn.RockCutterTier;
	public int cost = 500;

	public ItemRockCutter()
	{
		super(ToolMaterial.DIAMOND);
		setUnlocalizedName("techreborn.rockcutter");
		setCreativeTab(TechRebornCreativeTab.instance);
		setMaxStackSize(1);
		efficiencyOnProperMaterial = 16F;
		RebornCore.jsonDestroyer.registerObject(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack ist)
	{
		return true;
	}

	@Override
	public void addInformation(ItemStack iS, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (!iS.isItemEnchanted())
		{
			par3List.add(Color.WHITE + "Silk Touch I");
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockState state)
	{
		if (Items.diamond_pickaxe.canHarvestBlock(state))
		{
//			 if (PoweredItem.canUseEnergy(cost, stack)) {
//			 PoweredItem.useEnergy(cost, stack);
//			 return true;
//			 }
		}
		return false;
	}

	 @Override
	 public int getHarvestLevel(ItemStack stack, String toolClass) {
	 if (!stack.isItemEnchanted()) {
	 stack.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
	 }
	 return super.getHarvestLevel(stack, toolClass);
	 }

	 @Override
	 public float getStrVsBlock(ItemStack stack, IBlockState block) {
	 if (!stack.isItemEnchanted()) {
	 stack.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
	 }
	 return super.getStrVsBlock(stack, block);
	 }

	@Override
	public boolean isRepairable()
	{
		return false;
	}

	// public void onCreated(ItemStack par1ItemStack, World par2World,
	// EntityPlayer par3EntityPlayer) {
	// par1ItemStack.addEnchantment(Enchantment.silkTouch, 1);
	// }

	@Override
	public double getMaxPower(ItemStack stack)
	{
		return maxCharge;
	}

	@Override
	public boolean canAcceptEnergy(ItemStack stack)
	{
		return true;
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack)
	{
		return false;
	}

	@Override
	public double getMaxTransfer(ItemStack stack)
	{
		return 200;
	}

	@Override
	public int getStackTier(ItemStack stack)
	{
		return 2;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList)
	{
		ItemStack itemStack = new ItemStack(this, 1);
		itemList.add(itemStack);

		ItemStack charged = new ItemStack(this, 1);
		PoweredItem.setEnergy(getMaxPower(charged), charged);
		itemList.add(charged);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		double charge = (PoweredItem.getEnergy(stack) / getMaxPower(stack));
		return 1 - charge;

	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	@Override
	public String getTextureName(int damage)
	{
		return "techreborn:items/tool/rockcutter";
	}

	@Override
	public int getMaxMeta()
	{
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		return new ModelResourceLocation(ModInfo.MOD_ID + ":" + getUnlocalizedName(stack).substring(5), "inventory");
	}

}
