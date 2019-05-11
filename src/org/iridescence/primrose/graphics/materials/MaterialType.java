package org.iridescence.primrose.graphics.materials;


public enum MaterialType {
  MATERIAL_TYPE_BASIC(0),
  MATERIAL_TYPE_LAMBERT(1),
  MATERIAL_TYPE_PHONG(2),
  MATERIAL_TYPE_BLINN_PHONG(3),
  MATERIAL_TYPE_TOON(4),
  MATERIAL_TYPE_PHYSICAL(5);

  private int value;

  public int getValue(){
    return value;
  }

  MaterialType(int v){
    this.value = v;
  }
}
