package ru.drsk.progserega.inspectionsheet.services;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import ru.drsk.progserega.inspectionsheet.storages.ILogStorage;

import static android.text.TextUtils.isEmpty;

/**
 * https://github.com/droidparts/droidparts/blob/e4f782f136e349feeda518fa15d501fd0fec70c0/droidparts/src/org/droidparts/util/L.java#L119
 */
public class DBLog {

    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;
    public static final int ASSERT = Log.ASSERT;
    private static final int DISABLE = 1024;

    private static final String DEFAULT_TAG = "Inspection Sheet";

    private static int _logLevel = VERBOSE;

    private static ILogStorage logStorage = null;


    public static void setLevel(int _logLevel) {
        DBLog._logLevel = _logLevel;
    }

    public static void setLogStorage(ILogStorage logStorage) {
        DBLog.logStorage = logStorage;
    }

    public static void v(String tag, Object obj) {
        if (isLoggable(VERBOSE)) {
            log(VERBOSE, tag, obj);
        }
    }

    public static void v(String tag, String format, Object... args) {
        if (isLoggable(VERBOSE)) {
            log(VERBOSE, tag, format, args);
        }
    }

    public static void d(String tag, Object obj) {
        if (isLoggable(DEBUG)) {
            log(DEBUG, tag, obj);
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (isLoggable(DEBUG)) {
            log(DEBUG, tag, format, args);
        }
    }

    public static void i(String tag, Object obj) {
        if (isLoggable(INFO)) {
            log(INFO, tag, obj);
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (isLoggable(INFO)) {
            log(INFO, tag, format, args);
        }
    }

    public static void w(String tag, Object obj) {
        if (isLoggable(WARN)) {
            log(WARN, tag, obj);
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (isLoggable(WARN)) {
            log(WARN, tag, format, args);
        }
    }

    public static void e(String tag, Object obj) {
        if (isLoggable(ERROR)) {
            log(ERROR, tag, obj);
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (isLoggable(ERROR)) {
            log(ERROR, tag, format, args);
        }
    }


    public static boolean isLoggable(int level) {
        return (level >= getLogLevel());
    }


    private static void log(int priority, String tag, Object obj) {
        String msg = "null";
        if (obj != null) {
            Class<?> cls = obj.getClass();
            if (isString(cls)) {
                msg = (String) obj;
                if (isEmpty(msg)) {
                    msg = "\"\"";
                }
            } else if (isThrowable(cls)) {
                StringWriter sw = new StringWriter();
                ((Throwable) obj).printStackTrace(new PrintWriter(sw));
                msg = sw.toString();
            } else if (isArray(cls)) {
//                msg = Arrays.toString(Arrays2.toObjectArray(obj));
            } else {
                msg = obj.toString();
            }
        }
        log(tag, msg, priority);
    }

    private static void log(int priority, String tag, String format, Object... args) {
        String msg;
        try {
            msg = String.format(format, args);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        log(tag, msg, priority);
    }

    private static void log(String tag, String msg, int priority) {
        if (logStorage != null) {
            logStorage.add(priority, tag, msg);
        }
        Log.println(priority, tag, msg);
    }

    private static int getLogLevel() {
        //TODO читать из манифеста
//        if (_logLevel == 0) {
//            Context ctx = Injector.getApplicationContext();
//            if (ctx != null) {
//                String logLevelStr = ManifestMetaData.get(ctx, LOG_LEVEL);
//                if (LogLevel.VERBOSE.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = VERBOSE;
//                } else if (LogLevel.DEBUG.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = DEBUG;
//                } else if (LogLevel.INFO.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = INFO;
//                } else if (LogLevel.WARN.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = WARN;
//                } else if (LogLevel.ERROR.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = ERROR;
//                } else if (LogLevel.ASSERT.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = ASSERT;
//                } else if (LogLevel.DISABLE.equalsIgnoreCase(logLevelStr)) {
//                    _logLevel = DISABLE;
//                } else {
//                    _logLevel = VERBOSE;
//                    Log.i(DEFAULT_TAG, "No valid <meta-data android:name=\"" + ManifestMetaData.LOG_LEVEL
//                            + "\" android:value=\"...\"/> in AndroidManifest.xml.");
//                }
//            }
//        }
//        return (_logLevel != 0) ? _logLevel : VERBOSE;
        return _logLevel;
    }

    public static boolean isString(Class<?> cls) {
        return cls == String.class;
    }

    public static boolean isEnum(Class<?> cls) {
        return cls.isEnum();
    }

    public static boolean isUUID(Class<?> cls) {
        return UUID.class.isAssignableFrom(cls);
    }

    public static boolean isUri(Class<?> cls) {
        return Uri.class.isAssignableFrom(cls);
    }

    public static boolean isDate(Class<?> cls) {
        return Date.class.isAssignableFrom(cls);
    }

    public static boolean isThrowable(Class<?> cls) {
        return Throwable.class.isAssignableFrom(cls);
    }

    //

    public static boolean isByteArray(Class<?> cls) {
        return cls == byte[].class;
    }

    public static boolean isArray(Class<?> cls) {
        return cls.isArray();
    }

    public static boolean isCollection(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }

    public static boolean isSet(Class<?> cls) {
        return Set.class.isAssignableFrom(cls);
    }

    public static boolean isMap(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }

    //

    public static boolean isBitmap(Class<?> cls) {
        return Bitmap.class.isAssignableFrom(cls);
    }

    public static boolean isDrawable(Class<?> cls) {
        return Drawable.class.isAssignableFrom(cls);
    }

    //

    public static boolean isJSONObject(Class<?> cls) {
        return JSONObject.class.isAssignableFrom(cls);
    }

    public static boolean isJSONArray(Class<?> cls) {
        return JSONArray.class.isAssignableFrom(cls);
    }

    //

}
