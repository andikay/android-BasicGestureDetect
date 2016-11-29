/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.common.logger;

//import android.util.Log;

class LogWrapper extends LogNode {

    private var mNext:LogNode = null

    def getNext():LogNode = {
        return mNext;
    }

    def setNext(node: LogNode) {
        mNext = node;
    }

    @Override
    override def println(priority: Int, tag: String, msg: String, tr: Throwable) {

        var useMsg:String = msg
        if (useMsg == null) {
            useMsg = "";
        }

        val msgv =
            if (tr != null) {
                msg + "\n" + android.util.Log.getStackTraceString(tr);
            }
            else {
                msg
            }

        android.util.Log.println(priority, tag, useMsg);

        if (mNext != null) {
            mNext.println(priority, tag, msgv, tr);
        }
    }
}
