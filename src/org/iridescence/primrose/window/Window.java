package org.iridescence.primrose.window;

import static org.iridescence.primrose.graphics.utils.OGLUtils.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.iridescence.primrose.graphics.utils.OGLReporter;
import org.iridescence.primrose.input.*;
import org.iridescence.primrose.utils.Logging;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

/**
 * The default window manager and controller object. Creates a window and allows its use for OpenGL
 * drawing. Also central hub for input processing and window event handling. See the Input Device
 * Handler Classes to retrieve data obtained by this class. These can be found in the input
 * package.
 *
 * No Joystick/Gamepad support is provided by default, but external libraries and software can be
 * used.
 */
public class Window {

    /*
    //////////////////////
    / VARIABLES AND DATA /
    //////////////////////
    */

  private int width, height;
  private String title;
  private boolean vsync, fullscreen;
  private long window;

  public static GraphicsType graphicsAPI = GraphicsType.GRAPHICS_TYPE_OPENGL_45;

    /*
    ////////////////////
    / WINDOW FUNCTIONS /
    ////////////////////
    */

  /**
   * Sets up global window object
   * @param w - Width of the window
   * @param h - Height of the window
   * @param t - Widow title
   * @param f - Fullscreen
   */
    public static void setupWindow(int w, int h, String t, boolean f){
      windowObject = new Window(w, h, t, f);
    }

  /**
   * Creates a new window object. Only one window should be created for an application. Multiple
   * window support is not currently supported/implemented. The current method of creating a full
   * screen window is border-less Full Screen Mode.
   *
   * @param Width - Width of the window (Integer)
   * @param Height - Height of the window (Integer)
   * @param Title - Window title (String)
   * @param Fullscreen - Whether or not to create the window as a full screen window.
   */
  public Window(int Width, int Height, String Title, boolean Fullscreen) {
    width = Width;
    height = Height;
    title = Title;
    fullscreen = Fullscreen;

    vsync = false; //By default VSync not enabled to test engine performance.

    Logging.logger.info("Initialized New Window Object.");
    init();
  }

  private boolean createWindow() {
    Logging.logger.info("GLFW Attempting To Create Window");

    if (fullscreen) {
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      glfwWindowHint(GLFW_RED_BITS, vidmode.redBits());
      glfwWindowHint(GLFW_GREEN_BITS, vidmode.greenBits());
      glfwWindowHint(GLFW_BLUE_BITS, vidmode.blueBits());
      glfwWindowHint(GLFW_REFRESH_RATE, vidmode.refreshRate());

      height = vidmode.height();
      width = vidmode.width();

      window = glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), NULL);
    } else {
      window = glfwCreateWindow(width, height, title, NULL, NULL);
    }

    if (window != NULL) {
      Logging.logger.info("GLFW Window: " + title + " Was Successfully Created!");
      return true;
    } else {
      Logging.logger.severe("GLFW Window Could Not Be Created!");
      return false;
    }


  }

  /**
   * Creates the window with the given parameters at object initialization. If you desire to change
   * these parameters, then please set after window creation. Toggling fullscreen after window
   * creation may result in temporary application slow down / instability. Multi-sample Antialiasing
   * is not supported, instead methods such as SSAA, FXAA, and TSAA are provided.
   */
  public void init() {
    Logging.logger.info("Initializing GLFW Library!");

    if (!glfwInit()) {
      Logging.logger.severe(
          "GLFW Library Could Not Be Loaded! Please check your native library configuration!");
      Logging.logger.severe("Application Will Now Exit!");
      throw new Error("GLFW Library Not Initialized! Check window.Logging.logger!");
    }

    Logging.logger.info("GLFW Version " + GLFW_VERSION_MAJOR + "." + GLFW_VERSION_MINOR + "."
        + GLFW_VERSION_REVISION);
    Logging.logger.info("Configuring GLFW with default settings.");

    //OPENGL MODE
    if (graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_45 || graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_40
        || graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_33) {
      Logging.logger.info("GLFW Client API set to OpenGL Mode!");
      glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
      Logging.logger.info("GLFW OpenGL Profile set to Core Profile (OpenGL 3.3+)");
      glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
      Logging.logger.info("GLFW OpenGL Forward Compatibility Mode set to true.");
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, 1);
    }else{ //VULKAN MODE
      Logging.logger.info("GLFW Client API set to Vulkan Mode!");
      glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
    }

    // Don't pop it up until it's ready
    Logging.logger.info("GLFW Window Visibility set to hidden.");
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

    Logging.logger.info("GLFW Requesting SRGB Capable Window.");
    glfwWindowHint(GLFW_SRGB_CAPABLE, GLFW_TRUE); // Hopefully, we can report this in GLInitTests

    if(graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_45){
      Logging.logger.info("GLFW OpenGL API default set to 4.5.");
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
    }else if(graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_33){
      Logging.logger.info("GLFW OpenGL API default set to 3.3.");
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    }else if(graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_40){
      Logging.logger.info("GLFW OpenGL API default set to 4.0.");
      glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
      glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
    }
    //VULKAN DOES NOT NEED THIS!

    //No MSAA support... we will most likely use a deferred pipeline, where MSAA is not supported anyways
    while (!createWindow()) {
      if(graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_45){
        Logging.logger.severe("Retrying window creation with OpenGL 4.0 instead of OpenGL 4.5!");
        Logging.logger.severe("OpenGL set to version 4.5.");
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        graphicsAPI = GraphicsType.GRAPHICS_TYPE_OPENGL_40;
      }else if(graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_40){
        Logging.logger.severe("Retrying window creation with OpenGL 3.3 instead of OpenGL 4.0!");
        Logging.logger.severe("OpenGL set to version 4.5.");
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        graphicsAPI = GraphicsType.GRAPHICS_TYPE_OPENGL_33;
      }else if(graphicsAPI == GraphicsType.GRAPHICS_TYPE_OPENGL_33){
        Logging.logger.severe("FATAL: Could not create a window. Please check your drivers and graphics configuration.");
        Logging.logger.severe("FATAL: OpenGL 3.3+ may not be supported!");
        throw new Error("Window Could Not Be Created!");
      }
    }
    setVsync(vsync);


    Logging.logger.info("GLFW Creating OpenGL Context!");
    glfwMakeContextCurrent(window);
    GL.createCapabilities(true);
    OGLReporter.capabilitiesReport();
    Logging.logger.info("OpenGL Context enabled for current thread.");

    Logging.logger.info("GLFW Displaying New Window!");
    glfwShowWindow(window);

    Logging.logger.info("Creating Handlers For Window!");
    glfwSetKeyCallback(window, new Keyboard());
    Logging.logger.info("Created Keyboard Input Handler.");
    glfwSetCursorPosCallback(window, new Cursor());
    Logging.logger.info("Created Cursor Position Handler.");
    glfwSetMouseButtonCallback(window, new Mouse());
    Logging.logger.info("Created Mouse Button Handler.");
    glfwSetScrollCallback(window, new Scroll());
    Logging.logger.info("Created Scroll Wheel Handler.");

    glfwSetFramebufferSizeCallback(window, new FramebufferResizeCallback());
    Logging.logger.info("Created Framebuffer Resize Handler.");
  }

  /**
   * Destroys current window and terminates windowing library. Reading data from Callbacks/Handlers
   * of related input and windowing changes may result in a Null-Pointer Exception Error. Please use
   * these services before window destruction. Likewise, the OpenGL context will be invalidated and
   * any OpenGL calls taken after this point are considered undefined. Logging.loggerging data for this window
   * will be written to the disk and saved, and the file lock removed.
   */
  public void cleanup() {

    Logging.logger.info("Destroying Callbacks!");
    glfwFreeCallbacks(window);
    Logging.logger.info("Destroying Window: " + title);
    glfwDestroyWindow(window);
    Logging.logger.info("Cleaning up GLFW Library!");
    glfwTerminate();
  }

  /**
   *
   *  This function polls for events which have occurred such as input events, window event requests,
   * and system events. Update should also trigger callbacks for Input and Windowing changes, though
   * they may be asynchronous.
   */
  public void Update() {
    glfwPollEvents();

    if (WindowResizeCallback.needsUpdate) {
      width = WindowResizeCallback.w;
      height = WindowResizeCallback.h;
    }
  }

  /**
   * This function swaps the double buffers from front to back, displaying the results of OpenGL
   * operations. Render does not require OpenGL to process the buffer swap, and is done
   * automatically.
   */
  public void Render() {
    glfwSwapBuffers(window);

    OGLReporter.searchErrors();
  }

  public void Clear(){
    oglClear();
  }

  public void ClearColorFloat(float r, float g, float b, float a){
    oglSetClearColorFloat(r, g, b, a);
  }

  public void ClearColorInt(int r, int g, int b, int a) {
    oglSetClearColorRGB(r, g, b, a);
  }



  /**
   * This function checks whether or not the window has a close request scheduled or close event
   * scheduled.
   *
   * @return - Returns whether or not a window close request has been made.
   */
  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }


    /*
    /////////////////////
    / UTILITY FUNCTIONS /
    /////////////////////
    */


  /**
   * Retrieves the window's width.
   *
   * @return - Returns an Integer of the window's width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Changes and sets the window's width.
   *
   * @param width - The new width of the window.
   */
  public void setWidth(int width) {
    this.width = width;
    Logging.logger.info("Window resized to " + width + " pixels wide.");
    glfwSetWindowSize(window, this.width, this.height);
  }

  /**
   * Retrieves the window's height.
   *
   * @return - Returns an Integer of the window's height.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Changes and sets the window's height.
   *
   * @param height - The new height of the window.
   */
  public void setHeight(int height) {
    this.height = height;
    Logging.logger.info("Window resized to " + height + " pixels tall.");
    glfwSetWindowSize(window, this.width, this.height);
  }

  /**
   * Retrieves the window's title.
   *
   * @return - Returns a String of the window's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Changes and sets the window's title.
   *
   * @param title - The new title of the window.
   */
  public void setTitle(String title) {
    this.title = title;
    Logging.logger.info("Window title changed to " + title + ".");
    glfwSetWindowTitle(window, title);
  }

  /**
   * Retrieves the current state of Vertical Synchronization (enabled/disabled)
   *
   * @return - Returns a Boolean of the state of Vertical Synchronization
   */
  public boolean isVsync() {
    return vsync;
  }

  /**
   * Changes and sets the state of Vertical Synchronization (enabled/disabled)
   *
   * @param vsync - The new state of Vertical Synchronization
   */
  public void setVsync(boolean vsync) {
    this.vsync = vsync;
    if (vsync) {
      Logging.logger.info("Vertical Synchronization enabled.");
      glfwSwapInterval(1);
    } else {
      Logging.logger.info("Vertical Synchronization disabled.");
      glfwSwapInterval(0);
    }
  }

  /**
   * Retrieves whether or not the window is full screen.
   *
   * @return - Returns a Boolean of the window being full screen.
   */
  public boolean isFullscreen() {
    return fullscreen;
  }

  /**
   * Changes and sets whether or not the window is full screen. This causes substantial lag and may
   * throw off time sensitive activities, so be sure to use with care.
   *
   * @param fullscreen - The new state of the window.
   */
  public void setFullscreen(boolean fullscreen) {
    this.fullscreen = fullscreen;

    GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
    if (!fullscreen) {
      Logging.logger.info("Window switched from border-less full screen to windowed mode.");
      glfwSetWindowMonitor(window, NULL,
          0, 0,
          this.width, this.height, 0);

      glfwSetWindowSize(window, this.width, this.height);
      glfwHideWindow(window);
      glfwSetWindowPos(window, vid.width() / 2 - this.getWidth() / 2,
          vid.height() / 2 - this.getHeight() / 2);
      glfwShowWindow(window);
    } else {
      Logging.logger.info("Window switched from windowed mode to border-less full screen.");
      glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, vid.width(), vid.height(),
          vid.refreshRate());
    }
  }

  /**
   * Changes window from fullscreen to window and vice versa depending on the current state.
   */
  public void toggleFullscreen() {
    setFullscreen(!this.fullscreen);
  }

  /**
   * Retrieves the GLFW window pointer.
   *
   * @return - Returns the Long of the GLFW window pointer.
   */
  public long getWindow() {
    return window;
  }

  /**
   * Retrieves the clipboard contents.
   *
   * @return - Returns the String of the clipboard contents.
   */
  public String getClipboard() {
    return glfwGetClipboardString(window);
  }

  /**
   * Changes and sets the clipboard contents.
   *
   * @param data - The String which is to be set.
   */
  public void setClipboard(String data) {
    glfwSetClipboardString(window, data);
  }

  /**
   * Shows the GLFW Window on Screen
   */
  public void showWindow() {
    glfwShowWindow(window);
  }

  /**
   * Hides the GLFW Window on Screen
   */
  public void hideWindow() {
    glfwHideWindow(window);
  }

  /**
   * Requests the OS to alert about the window.
   */
  public void windowAlert() {
    glfwRequestWindowAttention(window);
  }

  /**
   * Iconifies the window on the Screen.
   */
  public void iconifyWindow() {
    glfwIconifyWindow(window);
  }

  /**
   * Restores the window back to its default state.
   */
  public void restoreWindow() {
    glfwRestoreWindow(window);
  }

  /**
   * Sets the window icon to a specified icon.
   *
   * @param path - Path to the Icon image.
   */
  public void setIcon(String path) {

    WindowIcon ico = WindowIcon.load_image(path);

    GLFWImage image = GLFWImage.malloc();
    GLFWImage.Buffer imagebf = GLFWImage.malloc(1);

    image.set(ico.get_width(), ico.get_height(), ico.get_image());
    imagebf.put(0, image);
    glfwSetWindowIcon(window, imagebf);
  }

  /**
   * Disables cursor, but still allows mouse and positional input.
   */
  public void disableCursor() {
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
  }

  /**
   * Enables cursor to be displayed.
   */
  public void enableCursor() {
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
  }


  public Vector2i getResolution(){
    return new Vector2i(width, height);
  }

  public static Window windowObject;

}

