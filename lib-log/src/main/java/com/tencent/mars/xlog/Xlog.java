package com.tencent.mars.xlog;

import com.example.lib.log.core.AppenderMode;
import com.example.lib.log.core.CompressLevel;
import com.example.lib.log.core.CompressMode;
import com.example.lib.log.core.LogLevel;
import com.example.lib.log.imp.LogImp;

public class Xlog implements LogImp {

    static class XLoggerInfo {
        public int level;
        public String tag;
        public String filename;
        public String funcname;
        public int line;
        public long pid;
        public long tid;
        public long maintid;
    }

    public static class XLogConfig {
        public int level = CompressLevel.LEVEL0.getValue();
        public int mode = AppenderMode.Async.getValue();
        public String logdir;
        public String nameprefix;
        public String pubkey = "";
        public int compressmode = CompressMode.ZLIB.getValue();
        public int compresslevel = 0;
        public String cachedir;
        public int cachedays = 0;
    }

    public static void open(boolean isLoadLib, int level, int mode, String cacheDir, String logDir, String nameprefix, String pubkey, int cachedays) {
        if (isLoadLib) {
            System.loadLibrary("c++_shared");
            System.loadLibrary("marsxlog");
        }

        XLogConfig logConfig = new XLogConfig();
        logConfig.level = level;
        logConfig.mode = mode;
        logConfig.logdir = logDir;
        logConfig.nameprefix = nameprefix;
        logConfig.pubkey = pubkey;
        logConfig.compressmode = CompressMode.ZLIB.getValue();
        logConfig.compresslevel = 0;
        logConfig.cachedir = cacheDir;
        logConfig.cachedays = cachedays;
        appenderOpen(logConfig);
    }

    private static String decryptTag(String tag) {
        return tag;
    }

    @Override
    public void logV(long logInstancePtr, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(logInstancePtr, LogLevel.VERBOSE.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    @Override
    public void logD(long logInstancePtr, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(logInstancePtr, LogLevel.DEBUG.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    @Override
    public void logI(long logInstancePtr, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(logInstancePtr, LogLevel.INFO.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    @Override
    public void logW(long logInstancePtr, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(logInstancePtr, LogLevel.WARNING.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    @Override
    public void logE(long logInstancePtr, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(logInstancePtr, LogLevel.ERROR.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }

    @Override
    public void logF(long logInstancePtr, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(logInstancePtr, LogLevel.FATAL.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
    }


    @Override
    public void appenderOpen(int level, int mode, String cacheDir, String logDir, String nameprefix, int cacheDays) {

        XLogConfig logConfig = new XLogConfig();
        logConfig.level = level;
        logConfig.mode = mode;
        logConfig.logdir = logDir;
        logConfig.nameprefix = nameprefix;
        logConfig.compressmode = CompressMode.ZLIB.getValue();
        logConfig.pubkey = "";
        logConfig.cachedir = cacheDir;
        logConfig.cachedays = cacheDays;

        appenderOpen(logConfig);
    }

    public static native void logWrite(XLoggerInfo logInfo, String log);

    public static void logWrite2(int level, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
        logWrite2(0, level, tag, filename, funcname, line, pid, tid, maintid, log);
    }

    public static native void logWrite2(long logInstancePtr, int level, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

    @Override
    public native int getLogLevel(long logInstancePtr);


    @Override
    public native void setAppenderMode(long logInstancePtr, int mode);

    @Override
    public long openLogInstance(int level, int mode, String cacheDir, String logDir, String nameprefix, int cacheDays) {
        XLogConfig logConfig = new XLogConfig();
        logConfig.level = level;
        logConfig.mode = mode;
        logConfig.logdir = logDir;
        logConfig.nameprefix = nameprefix;
        logConfig.compressmode = CompressMode.ZLIB.getValue();
        logConfig.pubkey = "";
        logConfig.cachedir = cacheDir;
        logConfig.cachedays = cacheDays;
        return newXlogInstance(logConfig);
    }

    @Override
    public native long getXlogInstance(String nameprefix);

    @Override
    public native void releaseXlogInstance(String nameprefix);

    public native long newXlogInstance(XLogConfig logConfig);

    @Override
    public native void setConsoleLogOpen(long logInstancePtr, boolean isOpen);    //set whether the console prints log

    private static native void appenderOpen(XLogConfig logConfig);

    @Override
    public native void appenderClose();

    @Override
    public native void appenderFlush(long logInstancePtr, boolean isSync);

    @Override
    public native void setMaxFileSize(long logInstancePtr, long aliveSeconds);

    @Override
    public native void setMaxAliveTime(long logInstancePtr, long aliveSeconds);

}