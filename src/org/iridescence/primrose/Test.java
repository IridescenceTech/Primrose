package org.iridescence.primrose;

import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;

import org.iridescence.primrose.game.Scene;
import org.iridescence.primrose.graphics.FramebufferObject;
import org.iridescence.primrose.graphics.Mesh;
import org.iridescence.primrose.graphics.controller.Freecam;
import org.iridescence.primrose.graphics.geometry.GeometryBuilderUtil;
import org.iridescence.primrose.graphics.lights.DirectionalLight;
import org.iridescence.primrose.graphics.lights.PointLight;
import org.iridescence.primrose.graphics.materials.MaterialBlinnPhong;
import org.iridescence.primrose.graphics.materials.MaterialLambert;
import org.iridescence.primrose.graphics.materials.MaterialPhong;
import org.iridescence.primrose.graphics.materials.MaterialToon;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.iridescence.primrose.graphics.utils.OGLUtils;
import org.iridescence.primrose.utils.FPSCounter;
import org.iridescence.primrose.utils.Timer;
import org.iridescence.primrose.window.Window;
import org.joml.Vector3f;

public class Test {
  public static void main(String[] args) {
    PrimroseUtil.init(800, 540, "Test", false);
    Window.windowObject.setVsync(false);

    Timer t = new Timer();
    Freecam cam = new Freecam();
    Scene scene = new Scene();

    MaterialBlinnPhong materialToon =
        new MaterialBlinnPhong(
            new Texture(
                "./assets/container.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR), new Texture("./assets/container-s.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR), 2.0f, 0.5f);
    Mesh m = new Mesh(GeometryBuilderUtil.createCube(1), materialToon);
    scene.add(m);

    for (int i = 0; i < 100; i++) {
      Mesh cubes = new Mesh(GeometryBuilderUtil.createCube(1), materialToon);
      cubes.transform.position =
          new Vector3f(
              (float) Math.random() * 50 - 25,
              (float) Math.random() * 50 - 25,
              (float) Math.random() * 50 - 25);
      cubes.transform.rotation =
          new Vector3f(
              (float) Math.random() * 50 - 25,
              (float) Math.random() * 50 - 25,
              (float) Math.random() * 50 - 25);
      cubes.transform.UpdateTransform();

      scene.add(cubes);
    }

    for (int i = 0; i < 16; i++){
      scene.add(new PointLight(
          new Vector3f(
              (float) Math.random() * 50 - 25,
              (float) Math.random() * 50 - 25,
              (float) Math.random() * 50 - 25), new Vector3f(1.0f), 2.0f, 0.2f, 0.22f));
    }

    FramebufferObject fbo = new FramebufferObject(Window.windowObject.getResolution(), true);

    while (!Window.windowObject.shouldClose()) {
      fbo.bind();
      fbo.clear();
      Window.windowObject.Clear();
      Window.windowObject.Update();
      FPSCounter.countFPS();

      double dt = t.deltaTime();
      cam.HandleKeyUpdate(dt);
      cam.Update();

      scene.render();
      fbo.unbind();

      OGLUtils.oglResolveFrameBuffers(
          0,
          Window.windowObject.getResolution(),
          fbo.getFBO(),
          Window.windowObject.getResolution());

      Window.windowObject.Render();
    }
    PrimroseUtil.cleanup();
  }
}
