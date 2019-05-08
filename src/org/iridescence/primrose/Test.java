package org.iridescence.primrose;

import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;

import org.iridescence.primrose.game.Scene;
import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.Mesh;
import org.iridescence.primrose.graphics.controller.Freecam;
import org.iridescence.primrose.graphics.geometry.GeometryBuilderUtil;
import org.iridescence.primrose.graphics.lights.DirectionalLight;
import org.iridescence.primrose.graphics.lights.PointLight;
import org.iridescence.primrose.graphics.lights.SpotLight;
import org.iridescence.primrose.graphics.materials.MaterialPhong;
import org.iridescence.primrose.graphics.shaders.PhongShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.iridescence.primrose.input.Keyboard;
import org.iridescence.primrose.utils.FPSCounter;
import org.iridescence.primrose.utils.Logging;
import org.iridescence.primrose.utils.Timer;
import org.iridescence.primrose.window.Window;
import org.joml.Vector3f;
public class Test {
  public static void main(String[] args){
    PrimroseUtil.init(800, 540, "Test", false);

    Timer t = new Timer();
    Freecam cam = new Freecam();

    Scene scene = new Scene();
    SpotLight spotLight = new SpotLight(new Vector3f(0, 0, -5), new Vector3f(1.0f, 0.9333f, 0.6901f), 0.7f, (float)toRadians(12.5f), (float)toRadians(17.5f), 0.22f, 0.20f);
    scene.add(spotLight);


    MaterialPhong materialPhong = new MaterialPhong(new Texture("./assets/container.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR), new Texture("./assets/container-s.png", GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST_MIPMAP_LINEAR), 1.5f, 0.5f);
    Mesh m = new Mesh(GeometryBuilderUtil.createCube(1), materialPhong);
    scene.add(m);

    double elapsed = 0;
    while(!Window.windowObject.shouldClose()){
      Window.windowObject.Clear();
      Window.windowObject.Update();

      FPSCounter.countFPS();

      double dt = t.deltaTime();

      elapsed += dt;



      cam.HandleKeyUpdate(dt);
      cam.Update();

      scene.render();

      Window.windowObject.Render();

      if(Keyboard.isKeyDown(GLFW_KEY_ESCAPE)){
        glfwSetWindowShouldClose(Window.windowObject.getWindow(), true);
      }
    }
    PrimroseUtil.cleanup();
  }
}