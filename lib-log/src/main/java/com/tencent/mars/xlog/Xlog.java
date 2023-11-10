package com.tencent.mars.xlog;

import com.example.lib.log.core.LogLevel;
import com.example.lib.log.imp.LogImp;

import org.jetbrains.annotations.NotNull;

public class Xlog implements LogImp {

	public static final int AppednerModeAsync = 0;
	public static final int AppednerModeSync = 1;

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

	private static String decryptTag(String tag) {
		return tag;
	}

	@Override
	public void logV(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
		logWrite2(LogLevel.VERBOSE.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
	}

	@Override
	public void logD(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
		logWrite2(LogLevel.DEBUG.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
	}

	@Override
	public void logI(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
		logWrite2(LogLevel.INFO.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
	}

	@Override
	public void logW(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
		logWrite2(LogLevel.WARNING.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
	}

	@Override
	public void logE(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
		logWrite2(LogLevel.ERROR.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
	}

	@Override
	public void logF(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
		logWrite2(LogLevel.FATAL.getValue(), decryptTag(tag), filename, funcname, line, pid, tid, maintid, log);
	}

	@Override
	public void setLogLevel(@NotNull LogLevel level) {
		setLogLevel(level.getValue());
	}


	public static native void logWrite(XLoggerInfo logInfo, String log);

	public static native void logWrite2(int level, String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

	@Override
	public native int getLogLevel();

	public static native void setLogLevel(int logLevel);

	public static native void setAppenderMode(int mode);

	public static native void setConsoleLogOpen(boolean isOpen);	//set whether the console prints log

	public static native void setErrLogOpen(boolean isOpen);	//set whether the  prints err log into a separate file

	public static native void appenderOpen(int level, int mode, String cacheDir, String logDir, String nameprefix, int cacheDays, String pubkey);

	public static native void setMaxFileSize(long size);

	/**
	 * should be called before appenderOpen to take effect
	 * @param duration alive seconds
	 */
	public static native void setMaxAliveTime(long duration);

	@Override
	public native void appenderClose();

	@Override
	public native void appenderFlush(boolean isSync);

}
