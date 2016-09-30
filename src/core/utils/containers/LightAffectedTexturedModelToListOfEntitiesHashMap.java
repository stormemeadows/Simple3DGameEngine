package core.utils.containers;


import core.ifaces.ILightAffectedTexturedModelable;
import entities.Entity;

/**
 * @author storm
 *
 */
public class LightAffectedTexturedModelToListOfEntitiesHashMap<TM extends ILightAffectedTexturedModelable<?, ?>>
		extends XToListOfYHashMap<TM, Entity<TM>> {
	private static final long serialVersionUID = 1L;
}

// private class TexdModelToEntityListHashMap<TM extends
// LightAffectedTexturedModel<?,
// ?>> extends HashMap<TM, List<Entity<TM>>> {
// private static final long serialVersionUID = 1920597803218097199L;
// };

// private <TM extends LightAffectedTexturedModel<?, ?>> Map<TM,
// List<Entity<TM>>>
// createTexdModelToEntityMap2() {
// return new HashMap<TM, List<Entity<TM>>>();
// }
