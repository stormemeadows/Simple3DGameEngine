package gameEngine.loaders;


import java.io.FileInputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * @author storm
 *
 */
class PNGFileTextureReader {

	private int			width;
	private int			height;
	private ByteBuffer	buffer;


	public PNGFileTextureReader(String fileName) {
		readInDataFromPNGFile(fileName);
	}


	private void readInDataFromPNGFile(String fileName) {
		try {
			final FileInputStream pngFile = new FileInputStream(fileName);
			final PNGDecoder decoder = new PNGDecoder(pngFile);

			width = decoder.getWidth();
			height = decoder.getHeight();

			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();

			pngFile.close();
		} catch (final Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
			System.exit(-1);
		}
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public ByteBuffer getBuffer() {
		return buffer;
	}

}
