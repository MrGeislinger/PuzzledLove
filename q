[1mdiff --git a/app/src/main/java/com/madcowscientist/puzzledlove/MainActivity.java b/app/src/main/java/com/madcowscientist/puzzledlove/MainActivity.java[m
[1mindex 0572795..b730ca5 100644[m
[1m--- a/app/src/main/java/com/madcowscientist/puzzledlove/MainActivity.java[m
[1m+++ b/app/src/main/java/com/madcowscientist/puzzledlove/MainActivity.java[m
[36m@@ -20,7 +20,8 @@[m [mpublic class MainActivity extends ActionBarActivity {[m
 [m
     //Unlocks Preferences[m
     SharedPreferences UNLOCKED_LEVELS;[m
[31m-[m
[32m+[m[32m    //Setup info to be used at beginnning[m
[32m+[m[32m    SharedPreferences SETUP_INFO;[m
 [m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
[36m@@ -45,6 +46,17 @@[m [mpublic class MainActivity extends ActionBarActivity {[m
             UnlockedEditor.commit();[m
         }[m
 [m
[32m+[m[32m        //Setup checks[m
[32m+[m[32m        SETUP_INFO = getSharedPreferences("SETUP_INFO", Context.MODE_PRIVATE);[m
[32m+[m[32m        Editor SetupEditor = SETUP_INFO.edit();[m
[32m+[m[41m        [m
[32m+[m[32m        if (!SETUP_INFO.contains("User")) {[m
[32m+[m[32m            SetupEditor.putString("User", null);[m
[32m+[m[32m            SetupEditor.putString("Lover", null);[m
[32m+[m[32m            SetupEditor.putString("Hangman_Question", "Question not set");[m
[32m+[m[32m            SetupEditor.putString("Hangman_Answer", null);[m
[32m+[m
[32m+[m[32m        }[m
     }[m
 [m
 [m
[1mdiff --git a/app/src/main/res/layout/activity_hangman.xml b/app/src/main/res/layout/activity_hangman.xml[m
[1mindex 2f2421c..daf278a 100644[m
[1m--- a/app/src/main/res/layout/activity_hangman.xml[m
[1m+++ b/app/src/main/res/layout/activity_hangman.xml[m
[36m@@ -63,4 +63,19 @@[m
         android:layout_alignRight="@+id/hangman_heart"[m
         android:layout_alignEnd="@+id/hangman_heart" />[m
 [m
[32m+[m[32m    <TextView[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:textAppearance="?android:attr/textAppearanceLarge"[m
[32m+[m[32m        android:text="@string/guessProgress"[m
[32m+[m[32m        android:id="@+id/guessProgress_TextView"[m
[32m+[m[32m        android:layout_below="@+id/questionTextView"[m
[32m+[m[32m        android:layout_marginTop="36dp"[m
[32m+[m[32m        android:layout_above="@+id/button"[m
[32m+[m[32m        android:layout_alignLeft="@+id/textView_Title"[m
[32m+[m[32m        android:layout_alignStart="@+id/textView_Title"[m
[32m+[m[32m        android:layout_alignRight="@+id/textView_Title"[m
[32m+[m[32m        android:layout_alignEnd="@+id/textView_Title"[m
[32m+[m[32m        android:gravity="center" />[m
[32m+[m
 </RelativeLayout>[m
[1mdiff --git a/app/src/main/res/values/strings.xml b/app/src/main/res/values/strings.xml[m
[1mindex d2aa410..89a636a 100644[m
[1m--- a/app/src/main/res/values/strings.xml[m
[1m+++ b/app/src/main/res/values/strings.xml[m
[36m@@ -23,5 +23,6 @@[m
     <string name="title_activity_hangman">Hangman</string>[m
     <string name="hangman_title">Break My Heart</string>[m
     <string name="questionText">Question</string>[m
[32m+[m[32m    <string name="guessProgress" />[m
 [m
 </resources>[m
