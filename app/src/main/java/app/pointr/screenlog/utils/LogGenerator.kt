package app.pointr.screenlog.utils

import java.util.*


class LogGenerator(private val onScreenLog: ScreenLog) {
    private val random = Random()

    fun logRandomMessage() {
        val priority = random.nextInt(6)

        when (priority) {
            0 -> onScreenLog.v(javaClass.simpleName,
                    "Verbose Log %s",
                    UUID.randomUUID().toString())
            1 -> onScreenLog.d(javaClass.simpleName,
                    "Debug Log %s",
                    UUID.randomUUID().toString())
            2 -> onScreenLog.i(javaClass.simpleName,
                    "Info Log%s",
                    UUID.randomUUID().toString())
            3 -> onScreenLog.w(javaClass.simpleName,
                    "Something happened %s",
                    UUID.randomUUID().toString())
            4 -> onScreenLog.e(javaClass.simpleName,
                    "Error Log %s",
                    UUID.randomUUID().toString())
            5 -> onScreenLog.wtf(javaClass.simpleName,
                    "Something happened %s",
                    UUID.randomUUID().toString())
        }
    }
}