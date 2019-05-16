package org.iridescence.primrose.graphics.rendering;

import org.iridescence.primrose.game.Scene;
import org.iridescence.primrose.graphics.utils.OGLUtils;
import org.iridescence.primrose.window.Window;

public class Renderer {

  public RenderPass colorOutput;

  public Renderer(){

  }

  public void render(Scene scene){
    //SHADOW PASS FIRST;


    //Then whatever render pass you want
    if(colorOutput == null || colorOutput.m_scene != scene){
      colorOutput = new RenderPass(scene, RendererConfig.rendererConfig.hdr, RenderTargets.COLOR_TARGET);
    }

    colorOutput.render();

    //TODO: POSTPROCESS AND/OR DEFFERED SHADING
    OGLUtils.oglResolveFrameBuffers(0, Window.windowObject.getResolution(), colorOutput.tex.getFBO(), colorOutput.tex.getBufferResolution());

  }

}
