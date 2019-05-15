package org.iridescence.primrose.graphics.rendering;

import org.iridescence.primrose.game.Scene;
import org.iridescence.primrose.window.Window;

public class RenderPass {

  private Scene m_scene;
  public RenderTexture tex;

  public RenderPass(Scene scene, boolean hdr, int renderFlags){
    m_scene = scene;
    tex = new RenderTexture(Window.windowObject.getResolution(), hdr, renderFlags);
  }

  public void render(){
    //Bind our framebuffer to render the scene to.
    tex.bind();
    tex.clear();

    m_scene.render();
    //Tex contains the content of the buffers.
    tex.unbind();
  }
}
