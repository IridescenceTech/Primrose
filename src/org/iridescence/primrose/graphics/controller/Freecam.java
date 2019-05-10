package org.iridescence.primrose.graphics.controller;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.input.Keyboard;

public class Freecam {
  public Freecam() {
    Camera.camera.Update();
  }

  public void HandleKeyUpdate(double dt) {
    if (Keyboard.isKeyDown(GLFW_KEY_W)) {
      Camera.camera.position.x +=
          Math.cos(Math.toRadians((Camera.camera.rotation.y + 90))) * 4.3 * dt;
      Camera.camera.position.z +=
          Math.sin(Math.toRadians((Camera.camera.rotation.y + 90))) * 4.3 * dt;

      Camera.camera.position.y += Math.sin(Math.toRadians((Camera.camera.rotation.x))) * 4.3 * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_S)) {
      Camera.camera.position.x +=
          -Math.cos(Math.toRadians((Camera.camera.rotation.y + 90))) * 4.3 * dt;
      Camera.camera.position.z +=
          -Math.sin(Math.toRadians((Camera.camera.rotation.y + 90))) * 4.3 * dt;
      Camera.camera.position.y += -Math.sin(Math.toRadians((Camera.camera.rotation.x))) * 4.3 * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_A)) {
      Camera.camera.position.x += Math.cos(Math.toRadians((Camera.camera.rotation.y))) * 4.3 * dt;
      Camera.camera.position.z += Math.sin(Math.toRadians((Camera.camera.rotation.y))) * 4.3 * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_D)) {
      Camera.camera.position.x += -Math.cos(Math.toRadians((Camera.camera.rotation.y))) * 4.3 * dt;
      Camera.camera.position.z += -Math.sin(Math.toRadians((Camera.camera.rotation.y))) * 4.3 * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_SPACE)) {
      Camera.camera.position.y -= 4.3 * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
      Camera.camera.position.y += 4.3 * dt;
    }

    if (Keyboard.isKeyDown(GLFW_KEY_LEFT)) {
      Camera.camera.rotation.y -= 60.0f * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_RIGHT)) {
      Camera.camera.rotation.y += 60.0f * dt;
    }

    if (Camera.camera.rotation.y > 360) {
      Camera.camera.rotation.y = 0;
    } else if (Camera.camera.rotation.y < 0) {
      Camera.camera.rotation.y = 360;
    }

    if (Keyboard.isKeyDown(GLFW_KEY_UP)) {
      Camera.camera.rotation.x -= 60.0f * dt;
    }
    if (Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
      Camera.camera.rotation.x += 60.0f * dt;
    }

    if (Camera.camera.rotation.x > 90.0f) {
      Camera.camera.rotation.x = 90.0f;
    } else if (Camera.camera.rotation.x < -90.0f) {
      Camera.camera.rotation.x = -90.0f;
    }
  }

  public void Update() {
    Camera.camera.Update();
  }
}
