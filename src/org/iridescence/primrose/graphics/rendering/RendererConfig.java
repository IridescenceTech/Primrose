package org.iridescence.primrose.graphics.rendering;

public class RendererConfig {
  public boolean hdr;
  public boolean shadowPass;
  public int shadowMapResolution;
  public boolean deferred;
  public boolean postProcess;

  public RendererConfig(){
    hdr = false;
    shadowPass = false;
    shadowMapResolution = 0;
    deferred = false;
    postProcess = false;
  }

  public static RendererConfig rendererConfig = new RendererConfig();
}
