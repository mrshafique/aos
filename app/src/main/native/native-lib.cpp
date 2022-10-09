#include <jni.h>
extern "C"
{
	JNIEXPORT jstring JNICALL
    Java_com_aos_shafik_screens_dashboard_FragDashboardViewModel_getAPIKey(JNIEnv *env, jobject instance)
	{
        return env-> NewStringUTF("8f15761ad4472689164c67202aaf3763");
    }

    JNIEXPORT jstring JNICALL
    Java_com_aos_shafik_screens_dashboard_FragDashboardViewModel_getUrl(JNIEnv *env, jobject instance)
    {
        return env-> NewStringUTF("http://ws.audioscrobbler.com/2.0/");
    }
}