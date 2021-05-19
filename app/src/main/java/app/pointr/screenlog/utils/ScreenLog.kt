package app.pointr.screenlog.utils

import android.content.Context
import android.util.Log
import app.pointr.screenlog.adapter.LogAdapter
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.atomic.AtomicLong


class ScreenLog private constructor(private val applicationContext: Context,
                                    private val capacity: Int,
                                    private val outputToLogcat: Boolean) {
    private val list = LinkedList<app.pointr.screenlog.model.Logs>()
    private val idCounter = AtomicLong(0)

    private val subject: BehaviorSubject<List<app.pointr.screenlog.model.Logs>> = BehaviorSubject.createDefault(listOf())

    val adapter: LogAdapter by lazy { LogAdapter(subject) }

    fun v(tag: String,
          message: String,
          vararg objects: Any) {
        log(Log.VERBOSE,
                tag,
                null,
                formatMessage(message, *objects))
    }

    fun d(tag: String,
          message: String,
          vararg objects: Any) {
        log(Log.DEBUG,
                tag,
                null,
                formatMessage(message, *objects))
    }

    fun i(tag: String,
          message: String,
          vararg objects: Any) {
        log(Log.INFO,
                tag,
                null,
                formatMessage(message, *objects))
    }

    fun w(tag: String,
          message: String,
          vararg objects: Any) {
        log(Log.WARN,
                tag,
                null,
                formatMessage(message, *objects))
    }

    fun e(tag: String,
          message: String,
          vararg objects: Any) {
        log(Log.ERROR,
                tag,
                null,
                formatMessage(message, *objects))
    }

    fun wtf(tag: String,
            message: String,
            vararg objects: Any) {
        log(Log.ASSERT,
                tag,
                null,
                formatMessage(message, *objects))
    }

    private fun formatMessage(message: String,
                              vararg args: Any): String {
        return String.format(message, *args)
    }

    private fun log(priority: Int,
                    tag: String,
                    t: Throwable?,
                    finalMessage: String) {
        val size = list.size

        list.offerLast(app.pointr.screenlog.model.Logs(idCounter.incrementAndGet(),
                priority,
                tag,
                finalMessage))
        if (size == capacity + 1) {
            list.pollFirst()
        }

        val clone = ArrayList(list)
        subject.onNext(clone)

        if (outputToLogcat) {
            Log.println(priority, tag, finalMessage)
        }
    }

    companion object {
        @JvmStatic
        fun builder() = Builder()

        internal var INSTANCE: ScreenLog? = null
    }

    class Builder internal constructor() {
        private var context: Context? = null
        private var capacity: Int = 500
        private var outputToLogcat: Boolean = true
        private var notificationId: Int? = null

        fun context(context: Context): Builder {
            this.context = context.applicationContext
            return this
        }

        fun notificationId(notificationId: Int): Builder {
            this.notificationId = notificationId
            return this
        }

        @Synchronized
        fun build(): ScreenLog {
            val tempContext = context
            val tempNotificationId = notificationId

            if (tempContext == null) {
                throw Exception("Must provide builder with context")
            }

            if (tempNotificationId == null) {
                throw Exception("Must provide builder with notificationId")
            }

            if (INSTANCE == null) {
                val onScreenLog = ScreenLog(tempContext,
                        capacity,
                        outputToLogcat)
                INSTANCE = onScreenLog
                return onScreenLog
            } else {
                throw Exception("OnScreenLog was already instantiated. Please reuse OnScreenLog instance.")
            }
        }
    }
}