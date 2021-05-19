package app.pointr.screenlog.model


internal data class Logs(val id: Long,
                         val priority: Int,
                         val tag: String,
                         val content: String)