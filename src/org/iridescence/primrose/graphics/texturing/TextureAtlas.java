package org.iridescence.primrose.graphics.texturing;


import org.joml.Vector2i;

/**
 * Defines the idea of a texture atlas, a texture with packed data in square texture sections.
 */
public class TextureAtlas extends Texture {

  protected int imageSize;
  protected int textureSize;

  /**
   * Creates a Texture Atlas
   * @param path - The path of the atlas file
   * @param minFilter - The mipmap min filter
   * @param magFilter - The mipmap mag filter
   * @param textureSquareSize - The size of each image in pixels
   * @param imageSquareSize - The size of the entire atlas in pixels
   */
  public TextureAtlas(String path, int minFilter, int magFilter, int textureSquareSize, int imageSquareSize){
    super(path, minFilter, magFilter);

    imageSize = imageSquareSize;
    textureSize = textureSquareSize;

  }

  /**
   * Generates a list of 4 texture coordinates of the selected texture
   * @param coords - What texture to select (starts from 0,0)
   * @return - Returns texture coordinates in (x,y) format.
   */
  public float[] getTexture(Vector2i coords){
    float TEX_PER_ROW      = (float)imageSize / (float)textureSize;
    float INDV_TEX_SIZE    = 1.0f / TEX_PER_ROW;
    float PIXEL_SIZE       = 1.0f / (float)imageSize;

    float xMin = ((float)coords.x * INDV_TEX_SIZE) + 0.5f * PIXEL_SIZE;
    float yMin = ((float)coords.y * INDV_TEX_SIZE) + 0.5f * PIXEL_SIZE;

    float xMax = (xMin + INDV_TEX_SIZE) - 0.5f * PIXEL_SIZE;
    float yMax = (yMin + INDV_TEX_SIZE) - 0.5f * PIXEL_SIZE;

    return new float[]{
        xMax, yMin,
        xMax, yMax,
        xMin, yMax,
        xMin, yMin,
    };

  }
}

