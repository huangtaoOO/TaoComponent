package com.example.lib.monitor.cpu

import android.system.Os
import android.system.OsConstants
import com.example.lib.monitor.model.CPUTime
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Author: huangtao
 * Date: 2023/6/13
 * Desc: CPU使用率计算
 */
abstract class CpuUsageRate {

    companion object {
        internal const val TAG = "CpuUsageRate"

        internal const val TOTAL_STAT = "/proc/stat"

        const val CPU_INTERVAL = 1000L
    }

    private val myPidStat = "/proc/${android.os.Process.myPid()}/stat"

    //一个jiffies占用的毫秒数
    protected val jiffies = 1000 / Os.sysconf(OsConstants._SC_CLK_TCK)

    protected val failRetryNum = 3

    protected var currentFailTimes = 0

    /**
     * 获取CPU的使用率
     * @return [总体使用率,APP使用率] 无法获取返回-1
     */
    abstract suspend fun obtainCPUWrap(): Pair<Float, Float>


    /**
     * 计算APP的使用率
     * @param old 开始时间段CPU数据
     * @param new 结束时间段CPU数据
     * @return [总体使用率,APP使用率] 无法获取返回-1
     */
    protected fun obtainCPUOccupancyRate(old: CPUTime, new: CPUTime): Pair<Float, Float> {
        val time = new.cpuTime - old.cpuTime
        //总的
        val occupy = if (0L != time) {
            val result = 100 - ((new.idleTime - old.idleTime) / time.toDouble()) * 100
            BigDecimal(result).setScale(2, RoundingMode.DOWN).toFloat()
        } else {
            -1.0F
        }
        //我的进程
        val myOccupy = if (0L != time) {
            val result = ((new.myCpuTime - old.myCpuTime) / time.toDouble()) * 100
            BigDecimal(result).setScale(2, RoundingMode.DOWN).toFloat()
        } else {
            -1.0F
        }
        return Pair(occupy, myOccupy)
    }

    /**
     * 读取当前APP进程的CPU使用时间
     * @return CPU使用时间 无法获取返回-1
     */
    protected fun readMyPidCpuTimeFile(): Long {
        kotlin.runCatching {
            val statInfo = File(myPidStat).readText()
            val segments = statInfo.split(" ")
            val time = segments[13].toLong() +
                    segments[14].toLong() +
                    segments[15].toLong() +
                    segments[16].toLong()
            return time * jiffies
        }
        return 0L
    }
}