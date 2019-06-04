package org.iridescence.primrose;

import static org.lwjgl.opengl.GL11.*;

import org.iridescence.primrose.game.Scene;
import org.iridescence.primrose.graphics.Mesh;
import org.iridescence.primrose.graphics.controller.Freecam;
import org.iridescence.primrose.graphics.geometry.GeometryBuilderUtil;
import org.iridescence.primrose.graphics.lights.DirectionalLight;
import org.iridescence.primrose.graphics.materials.MaterialBlinnPhong;
import org.iridescence.primrose.graphics.materials.MaterialLambert;
import org.iridescence.primrose.graphics.rendering.Renderer;
import org.iridescence.primrose.graphics.sprite.Sprite;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.iridescence.primrose.utils.FPSCounter;
import org.iridescence.primrose.utils.Timer;
import org.iridescence.primrose.window.Window;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Test {
  public static void main(String[] args) {
    PrimroseUtil.init(800, 540, "Test", false);
    Timer t = new Timer();
    Freecam cam = new Freecam();
    Scene scene = new Scene();
    MaterialBlinnPhong materialBlinnPhong =
        new MaterialBlinnPhong(
            new Texture(
                "./assets/container.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR),
            new Texture(
                "./assets/container-s.png", GL_NEAREST_MIPMAP_LINEAR, GL_NEAREST_MIPMAP_LINEAR), 1.0f, 0.8f);
    Mesh m = new Mesh(GeometryBuilderUtil.createCube(1), materialBlinnPhong, "default", "myCube");
    scene.add(m);

    DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0.0f), new Vector3f(1.0f), 1.5f);
    directionalLight.transform.rotation.x = 45;
    directionalLight.transform.rotation.y = 40;
    scene.add(directionalLight);

    MaterialLambert materialLambert = new MaterialLambert(new Texture("./assets/cobblestone.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR));

    Mesh f = new Mesh(GeometryBuilderUtil.createPlane(10, 10), materialLambert);
    f.transform.rotation.x = -90.0f;
    f.transform.position.x -= 5.0f;
    f.transform.position.z -= 2.0f;
    f.transform.position.y -= 5.0f;
    scene.add(f);

    Renderer renderer = new Renderer();

    while (!Window.windowObject.shouldClose()) {
      Window.windowObject.Clear();
      Window.windowObject.Update();
      FPSCounter.countFPS();

      double dt = t.deltaTime();
      cam.HandleKeyUpdate(dt);
      cam.Update();

      scene.getGameObjectByName("myCube").transform.rotation.y += 50.0f * (float)dt;


      renderer.render(scene);
      Window.windowObject.Render();
    }
    PrimroseUtil.cleanup();
  }
}
