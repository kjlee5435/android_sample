package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.util.*


/*
TYPE_STEP_COUNTER

added in API level 19
public static final int TYPE_STEP_COUNTER
A constant describing a step counter sensor.

A sensor of this type returns the number of steps taken
by the user since the last reboot while activated.

The value is returned as a float (with the fractional part set to zero)
and is reset to zero only on a system reboot.

The timestamp of the event is set to the time when the last step
for that event was taken.

This sensor is implemented in hardware and is expected to be low power.
If you want to continuously track the number of steps over a long period of time,
do NOT unregister for this sensor, so that it keeps counting steps in the background even
when the AP is in suspend mode and report the aggregate count when the AP is awake.

Application needs to stay registered for this sensor because step counter
does not count steps if it is not activated.

This sensor is ideal for fitness tracking applications.
It is defined as an REPORTING_MODE_ON_CHANGE sensor.
*/

class StepCounterGetter: SensorEventListener {
    val TAG = "KJKJ-StepCounterGetter"
    var type = "NONE"

    companion object {
        val instance = StepCounterGetter()
        var sensorManager:SensorManager? = null
    }


    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if (p0.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val count:Int = p0.values[0].toInt()

                Log.d(TAG, type + ":: step call - " + count)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


    fun init(_type:String, context:Context) {

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        type = _type
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Log.d(TAG, "no step sensor")
        } else {
            Log.d(TAG, "register successed-- " + type)
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun unregister(pos : Int, context:Context) {
        Log.d(TAG, "unregister")
        type = "UNREG " + pos
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.unregisterListener(this)
    }

    fun testSingleton():Int {
        return 10;
    }
}