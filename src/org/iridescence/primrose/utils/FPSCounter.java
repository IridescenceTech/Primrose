package org.iridescence.primrose.utils;

public class FPSCounter {

    public static Timer time = new Timer();
    private static float time1s = 0.0f;
    private static int fps = 0;

    public static void countFPS(){
        time1s += time.deltaTime();
        fps++;
        if(time1s > 1.0f){
            double drawTime = time1s / (double)fps * 1000.0d;
            time1s = 0;
            Logging.logger.info(fps + " fps     "  + drawTime + " ms");
            fps = 0;
        }
    }
}
