/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.basicgesturedetect;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.android.common.logger.Log;

//import scala.util.control.Breaks._

object GestureListener {
  val TAG = "GestureListener";

  private def getTouchType(e: MotionEvent):String = {

    var touchTypeDescription:String = " "
    val touchType:Int = e.getToolType(0)

    touchType match {
      case MotionEvent.TOOL_TYPE_FINGER => touchTypeDescription += "(finger)"
      case MotionEvent.TOOL_TYPE_STYLUS => touchTypeDescription += "(stylus, "

        val stylusPressure:Double = e.getPressure();
        touchTypeDescription += "pressure: " + stylusPressure;

        if(Build.VERSION.SDK_INT >= 21) {
          touchTypeDescription += ", buttons pressed: " + getButtonsPressed(e);
        }
        touchTypeDescription += ")";

      case MotionEvent.TOOL_TYPE_ERASER => touchTypeDescription += "(eraser)";

      case MotionEvent.TOOL_TYPE_MOUSE => touchTypeDescription += "(mouse)";

      case _ => touchTypeDescription += "(unknown tool)";
    }

    return touchTypeDescription;
  }

  @TargetApi(21)
  private def getButtonsPressed(e: MotionEvent):String = {
    var buttons:String = "";

    if(e.isButtonPressed(MotionEvent.BUTTON_PRIMARY)){
      buttons += " primary";
    }

    if(e.isButtonPressed(MotionEvent.BUTTON_SECONDARY)){
      buttons += " secondary";
    }

    if(e.isButtonPressed(MotionEvent.BUTTON_TERTIARY)){
      buttons += " tertiary";
    }

    if(e.isButtonPressed(MotionEvent.BUTTON_BACK)){
      buttons += " back";
    }

    if(e.isButtonPressed(MotionEvent.BUTTON_FORWARD)){
      buttons += " forward";
    }

    if (buttons.equals("")){
      buttons = "none";
    }

    return buttons;
  }

}

class GestureListener extends GestureDetector.SimpleOnGestureListener {

  // BEGIN_INCLUDE(init_gestureListener)
    @Override
    override def onSingleTapUp(e: MotionEvent):Boolean = {
        // Up motion completing a single tap occurred.
        Log.i(GestureListener.TAG, "Single Tap Up" + GestureListener.getTouchType(e));
        return false;
    }

    @Override
    override def onLongPress(e: MotionEvent) {
        // Touch has been long enough to indicate a long press.
        // Does not indicate motion is complete yet (no up event necessarily)
        Log.i(GestureListener.TAG, "Long Press" + GestureListener.getTouchType(e));
    }

    @Override
    def onScroll(e1: MotionEvent, e2: MotionEvent,distanceX: Double, distanceY: Double) {
        // User attempted to scroll
        Log.i(GestureListener.TAG, "Scroll" + GestureListener.getTouchType(e1));
        return false;
    }

    @Override
    def onFling(e1: MotionEvent, e2: MotionEvent , velocityX: Double, velocityY: Double):Boolean = {
        // Fling event occurred.  Notification of this one happens after an "up" event.
        Log.i(GestureListener.TAG, "Fling" + GestureListener.getTouchType(e1));
        return false;
    }

    @Override
    override def onShowPress(e: MotionEvent) {
        // User performed a down event, and hasn't moved yet.
        Log.i(GestureListener.TAG, "Show Press" + GestureListener.getTouchType(e));
    }

    @Override
    override def onDown(e: MotionEvent):Boolean = {
        // "Down" event - User touched the screen.
        Log.i(GestureListener.TAG, "Down" + GestureListener.getTouchType(e));
        return false;
    }

    @Override
    override def onDoubleTap(e: MotionEvent):Boolean = {
        // User tapped the screen twice.
        Log.i(GestureListener.TAG, "Double tap" + GestureListener.getTouchType(e));
        return false;
    }

    @Override
    override def onDoubleTapEvent(e: MotionEvent):Boolean = {
        // Since double-tap is actually several events which are considered one aggregate
        // gesture, there's a separate callback for an individual event within the doubletap
        // occurring.  This occurs for down, up, and move.
        Log.i(GestureListener.TAG, "Event within double tap" + GestureListener.getTouchType(e));
        return false;
    }

    @Override
    override def onSingleTapConfirmed(e: MotionEvent):Boolean = {
        // A confirmed single-tap event has occurred.  Only called when the detector has
        // determined that the first tap stands alone, and is not part of a double tap.
        Log.i(GestureListener.TAG, "Single tap confirmed" + GestureListener.getTouchType(e));
        return false;
    }
}
