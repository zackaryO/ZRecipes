Ayam Sambal, easily found by selecting asian cuisine and chicken protein crashes with the following message.

E/AndroidRuntime: FATAL EXCEPTION: main
    Process: com.tracks.zrecipes, PID: 6367
    java.lang.NullPointerException: Attempt to invoke virtual method 'byte[] com.tracks.zrecipes.RecipeCard.getImageData()' on a null object reference
        at com.tracks.zrecipes.RecipeCardFragment.onRecipeLoaded(RecipeCardFragment.java:131)
        at com.tracks.zrecipes.GetSingleRecipe.onPostExecute(GetSingleRecipe.java:80)
        at com.tracks.zrecipes.GetSingleRecipe.onPostExecute(GetSingleRecipe.java:23)
        at android.os.AsyncTask.finish(AsyncTask.java:771)
        at android.os.AsyncTask.-$$Nest$mfinish(Unknown Source:0)
        at android.os.AsyncTask$InternalHandler.handleMessage(AsyncTask.java:788)
        at android.os.Handler.dispatchMessage(Handler.java:106)
        at android.os.Looper.loopOnce(Looper.java:201)
        at android.os.Looper.loop(Looper.java:288)
        at android.app.ActivityThread.main(ActivityThread.java:7872)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:936)
I/Process: Sending signal. PID: 6367 SIG: 9
