package techreborn.parts.types;

import techreborn.parts.CableMultipart;
import techreborn.parts.EnumCableType;

/**
 * Created by modmuss50 on 05/03/2016.
 */
public class CopperCable extends CableMultipart
{
	@Override
	public EnumCableType getCableType()
	{
		return EnumCableType.COPPER;
	}
}
