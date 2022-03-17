package com.mobiledrivetech.arhud.sinewave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.ActivityManager
import android.content.pm.ConfigurationInfo
import android.opengl.GLSurfaceView
import android.os.Build
import android.util.Log


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var glSurfaceView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        glSurfaceView = findViewById(R.id.glsurface)
        initData()
    }

    private fun initData() {
        if (supportsEs2()) {
            val myGlRender = SineWaveRenderer(this, 500, -360, 360, 5)
            glSurfaceView!!.setEGLContextClientVersion(2)
            glSurfaceView!!.setRenderer(myGlRender)
            glSurfaceView!!.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        } else {
            Log.d(TAG, "This device does not support OpenGL ES 2.0.")
        }
    }

    private fun supportsEs2(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo: ConfigurationInfo = activityManager.deviceConfigurationInfo
        return (configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"))))
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        glSurfaceView!!.onPause()
    }
}