package com.example.lib.monitor.model

data class CPUGroup(
    val cores: MutableList<Int> = mutableListOf(),
    var coreType: Int = 0x0,
    var maxFreq: Int = 0,
    var minFreq: Int = 0,
    var lastFreq: Int = 0,
    var isThrottling: Boolean = false,
) {
    override fun toString(): String = "Group cores $cores, type: ${coreType.toString(16)}, Freq [$minFreq-$maxFreq]"

    fun currentFreq(): String = "$cores are at $lastFreq"
}