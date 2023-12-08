package net.yumnumm.flutter_compose_view

import android.content.Context
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory


class ComposeViewFactory(private val methodChannel: MethodChannel) :
    PlatformViewFactory(StandardMessageCodec()) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val creationParams = args as Map<String?, Any?>?
        return MyComposeView(context, creationParams, methodChannel)
    }
}
