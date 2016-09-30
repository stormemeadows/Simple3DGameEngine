package gameEngine.shaders;


import static settings.GameConstants.TerrainParameters.FRAGMENT_FILE;
import static settings.GameConstants.TerrainParameters.VERTEX_FILE;

import gameEngine.shaders.ifaces.ISampler2DData;

public class TerrainShader extends LightAffectedShader
		implements ISampler2DData {

	// sampler2D
	private int	location_backgroundTexture;
	private int	location_rTexture;
	private int	location_gTexture;
	private int	location_bTexture;
	private int	location_blendMap;


	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	@Override
	protected void getAllUniformLocations() {
		super.getAllUniformLocations();
		location_backgroundTexture = getUniformLocation("backgroundTexture");
		location_rTexture = getUniformLocation("rTexture");
		location_gTexture = getUniformLocation("gTexture");
		location_bTexture = getUniformLocation("bTexture");
		location_blendMap = getUniformLocation("blendMap");
	}


	@Override
	public void connectTextureUnits() {
		load1i(location_backgroundTexture, 0);
		load1i(location_rTexture, 1);
		load1i(location_gTexture, 2);
		load1i(location_bTexture, 3);
		load1i(location_blendMap, 4);
	}

}
