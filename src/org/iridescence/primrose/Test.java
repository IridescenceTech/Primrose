package org.iridescence.primrose;

import static org.lwjgl.opengl.GL11.*;
import org.iridescence.primrose.game.Scene;
import org.iridescence.primrose.graphics.Mesh;
import org.iridescence.primrose.graphics.controller.Freecam;
import org.iridescence.primrose.graphics.geometry.GeometryBuilderUtil;
import org.iridescence.primrose.graphics.lights.DirectionalLight;
import org.iridescence.primrose.graphics.materials.MaterialPhong;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.iridescence.primrose.utils.FPSCounter;
import org.iridescence.primrose.utils.Timer;
import org.iridescence.primrose.window.Window;
import org.joml.Vector3f;

public class Test {
  public static void main(String[] args){
    PrimroseUtil.init(800, 540, "Test", false);

    Timer t = new Timer();
    Freecam cam = new Freecam();
    Scene scene = new Scene();

    DirectionalLight dirLight = new DirectionalLight(new Vector3f(7.5f, 7.5f, -1.0f), new Vector3f(1.0f, 0.9333f, 0.6901f), 1.0f);
    scene.add(dirLight);

    MaterialPhong materialPhong = new MaterialPhong(new Texture("./assets/container.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR), new Texture("./assets/container-s.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR), 1.5f, 0.5f);
    Mesh m = new Mesh(GeometryBuilderUtil.createCube(15), materialPhong);
    scene.add(m);

    while(!Window.windowObject.shouldClose()){
      Window.windowObject.Clear();
      Window.windowObject.Update();
      FPSCounter.countFPS();

      double dt = t.deltaTime();
      cam.HandleKeyUpdate(dt);
      cam.Update();

      scene.render();
      Window.windowObject.Render();
    }
    PrimroseUtil.cleanup();
  }
}