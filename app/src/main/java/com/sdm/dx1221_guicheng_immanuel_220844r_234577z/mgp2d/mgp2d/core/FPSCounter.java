package com.sdm.dx1221_guicheng_immanuel_220844r_234577z.mgp2d.mgp2d.core;

import java.util.concurrent.TimeUnit;

public class FPSCounter {

    private double dDeltaTime;
    private final double dIdealDeltaTime;
    private final double dWorstIdealDeltaTime;
    private int nFrames;
    private int iFrameRate ;
    private double dFrameTime;
    private double dDelayTime = 0.0;
    private final boolean bLimitFrameRate;

    long prevTime = System.nanoTime();

    FPSCounter(final boolean bLimitFrameRate, final double dIdealDeltaTime, final double dWorstIdealDeltaTime) {
        dDeltaTime = 0.0;
        nFrames = 0;
        iFrameRate = 0;
        this.dIdealDeltaTime = dIdealDeltaTime;
        this.dWorstIdealDeltaTime = dWorstIdealDeltaTime;
        dFrameTime = 1.0;
        this.bLimitFrameRate = bLimitFrameRate;
    }
    public void Update() throws InterruptedException {
        long currentTime = System.nanoTime();
        dDeltaTime = (currentTime - prevTime)/1000000000.0f;
        prevTime = currentTime;

        if (dDeltaTime > dWorstIdealDeltaTime)
        {
            dDeltaTime = dWorstIdealDeltaTime;
        }
        if (bLimitFrameRate)
        {
            // Frame rate limiter. Limits each frame to a specified time in ms by sleeping.
            dDelayTime = dIdealDeltaTime - dDeltaTime;
            if (dDelayTime < 0.0)
                dDelayTime = 0.0;

            long lDelayTime = (long) (dDelayTime * 350);
            // Wait until the delay time is up
            TimeUnit.MILLISECONDS.sleep(lDelayTime);
//            cStopWatch.WaitUntil((const long long)dDelayTime);

            // Update the FPS Counter
            dFrameTime += dDeltaTime + dDelayTime;
        }
        else
        {
            // Update the FPS Counter
            dFrameTime += dDeltaTime;
        }

        // Update the frame count
        nFrames++;

        if (dFrameTime >= 1.0){ // If last update was more than 1 sec ago...
            // Calculate the current frame rate
            dFrameTime = 1000.0 / (double) nFrames;

            // Update the frame count for the last 1 second
            iFrameRate = nFrames;

            // Reset timer and update the lastTime
            nFrames = 0;
            dFrameTime = 0.0;
        }
    }

    int GetFrameRate() {
        return iFrameRate;
    }

    double GetDeltaTime() {
        return dDeltaTime;
    }
}
