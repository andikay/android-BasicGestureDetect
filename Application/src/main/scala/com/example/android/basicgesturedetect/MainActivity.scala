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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

object MainActivity {
  val TAG  = "MainActivity"
  val FRAGTAG  = "BasicGestureDetectFragment"
}

class MainActivity extends SampleActivityBase {

    @Override
    override protected def onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentByTag(MainActivity.FRAGTAG) == null ) {
            val transaction:FragmentTransaction = getSupportFragmentManager().beginTransaction();
            val fragment:BasicGestureDetectFragment = new BasicGestureDetectFragment();
            transaction.add(fragment, MainActivity.FRAGTAG);
            transaction.commit();
        }
    }

    @Override
    override def onCreateOptionsMenu(menu: Menu):Boolean = {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    override def initializeLogging() {
        // Wraps Android's native log framework.
        val logWrapper:LogWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        val msgFilter:MessageOnlyLogFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        val logFragment: LogFragment = getSupportFragmentManager.findFragmentById(R.id.log_fragment).asInstanceOf[LogFragment];
        msgFilter.setNext(logFragment.getLogView());
        //logFragment.getLogView().setTextAppearance(this, R.style.Log);
        logFragment.getLogView().setBackgroundColor(Color.WHITE);

        Log.i(MainActivity.TAG, "Ready");
    }
}
