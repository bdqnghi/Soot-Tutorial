package soot.tutorial.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
	// Android version
//	public static final String ANDROID_VERSION = "-4.1.1";
//	public static final String ANDROID_API_LEVEL = "-16";
	public static final String ANDROID_VERSION = "-4.4.4";
	public static final String ANDROID_API_LEVEL = "-19";
//	public static final String ANDROID_VERSION = "-5.1.0";
//	public static final String ANDROID_API_LEVEL = "-22";
//	public static final String ANDROID_VERSION = "-6.0.1";
//	public static final String ANDROID_API_LEVEL = "-23";
	
	// Android comparison version
	public static final String ANDROID_COMPARISON_VERSION = "-5.1.0";
	public static final String ANDROID_COMPARISON_API_LEVEL = "-22";
	
	// Entry point
	public static final String ENTRY_CLASS = "com.android.server.SystemServer";
	public static final String ENTRY_METHOD = "main";
//	public static final String ENTRY_CLASS = "com.android.internal.os.ZygoteInit";
//	public static final String ENTRY_METHOD = "main";
//	public static final String ENTRY_CLASS = "android.os.Messenger";
//	public static final String ENTRY_METHOD = "send";
//	public static final String ENTRY_CLASS = "android.content.IntentSender";
//	public static final String ENTRY_METHOD = "writeToParcel";
//	public static final String ENTRY_CLASS = "android.content.pm.ParceledListSlice$1";
//	public static final String ENTRY_METHOD = "onTransact";
	
	
	// Path of Android Home
//	public static final Path ANDROID_HOME_FOLDER = Paths.get("/Users/eric-mac/Library/Android/sdk");
//	public static final Path ANDROID_HOME_FOLDER = Paths.get("/home/eric/Android/Sdk");
//	public static final Path ANDROID_HOME_FOLDER = Paths.get("N:/erichoang/Android/sdk");
//	public static final Path ANDROID_HOME_PLATFORM_FOLDER = ANDROID_HOME_FOLDER.resolve("platforms");
	
	// Path of resource folder
//	public static final Path RESOURCE_FOLDER = Paths.get("/Users/eric-mac/Downloads/apk/test-soot");
//	public static final Path RESOURCE_FOLDER = Paths.get("/home/eric/Downloads/resource");
	public static final Path RESOURCE_FOLDER = Paths.get("resource");
	public static final Path RESOURCE_FRAMEWORK_FOLDER = RESOURCE_FOLDER.resolve("framework" + ANDROID_VERSION);
	public static final Path RESOURCE_ANDROID_BINARY_FOLDER = RESOURCE_FOLDER.resolve("AndroidBinary");
	
	// Path of Desktop folders
//	public static final Path DESKTOP_FOLDER = Paths.get("/Users/eric-mac/Desktop");
//	public static final Path DESKTOP_FOLDER = Paths.get("/home/eric/Desktop");
//	public static final Path DESKTOP_FOLDER = Paths.get("C:/Users/hhnguyen/Desktop");
	public static final Path OUTPUT_FOLDER = Paths.get("sootOutput");
	
	// Path of current project folder
	public static final Path CURRENT_PROJECT_FOLDER = Paths.get("");

	// Path of Android Jar files
	public static final Path ANDROID_JAR = Paths.get("lib/android.jar");
	
	// Path of resource files
	public static final Path JAR_FILE = RESOURCE_FOLDER.resolve("js.jar");
	public static final Path APK_FABRICA_WORDCARD = RESOURCE_FOLDER.resolve("flappybird.apk");
	public static final Path APK_SYSTEM_ANDROID_SERVICES = RESOURCE_FRAMEWORK_FOLDER.resolve("services.apk");
	public static final Path APK_SYSTEM_ANDROID_CORE = RESOURCE_FRAMEWORK_FOLDER.resolve("core.apk");
	public static final Path APK_SYSTEM_ANDROID_AM = RESOURCE_FRAMEWORK_FOLDER.resolve("am.apk");
	public static final Path APK_SYSTEM_ANDROID_WM = RESOURCE_FRAMEWORK_FOLDER.resolve("wm.apk");
	public static final Path APK_SYSTEM_ANDROID_FRAMEWORK = RESOURCE_FRAMEWORK_FOLDER.resolve("framework.apk");
	public static final Path APK_SYSTEM_ANDROID_FRAMEWORK_2 = RESOURCE_FRAMEWORK_FOLDER.resolve("framework2.apk");
	
	// Path of Android binary files
	public static final Path ANDROID_BINARY = RESOURCE_ANDROID_BINARY_FOLDER.resolve("android" + ANDROID_VERSION + "_r1.jar");
	
}
