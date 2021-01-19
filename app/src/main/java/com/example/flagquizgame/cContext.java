/**
 * file used to get context needed. i.e to be able to use getAssets() method for AssestsManager object as seen in constructor of initializeQuiz.java
 */
package com.example.flagquizgame;

import android.app.Application;
import android.content.Context;

public class cContext extends Application {

        private static cContext instance;

        public static cContext getInstance() {
            return instance;
        }

        public static Context getContext(){
            // or return instance;
             return instance.getApplicationContext();
        }

        @Override
        public void onCreate() {
            instance = this;
            super.onCreate();
        }
}

