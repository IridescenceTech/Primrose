package org.iridescence.primrose;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.utils.Logging;
import org.iridescence.primrose.window.Window;
import org.joml.Vector3f;

public class PrimroseUtil {
  public static void init(int w, int h, String t, boolean f){
    Logging.initializeLogger();
    Window.setupWindow(w, h, t, f);

    Camera.camera = new Camera(new Vector3f(0, 0, -5), new Vector3f(0, 0, 0), 90.0f, (float)Window.windowObject.getWidth() / (float)Window.windowObject.getHeight());
  }

  public static void cleanup(){
    Window.windowObject.cleanup();
    Window.windowObject = null;

    Logging.cleanupLogger();
  }
}
