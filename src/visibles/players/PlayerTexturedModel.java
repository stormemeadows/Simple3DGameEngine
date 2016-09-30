package visibles.players;


import models.textured.LightAffectedTexturedModel;

/**
 * @author storm
 *
 */
public class PlayerTexturedModel extends LightAffectedTexturedModel<PlayerTexture, PlayerModel> {


	/**
	 * @param texture
	 * @param model
	 */
	// public <T extends PlayerTexture, M extends PlayerModel>
	// PlayerTexturedModel(T texture, M model) {
	public PlayerTexturedModel(PlayerTexture texture, PlayerModel model) {
		super(texture, model);
	}


	/**
	 * @param texture
	 * @param model
	 */
	public PlayerTexturedModel(String texFileName, String modelFileName) {
		super(new PlayerTexture(texFileName), new PlayerModel(modelFileName));
	}
}
