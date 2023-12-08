package net.yumnumm.flutter_compose_view

import android.content.Context
import android.view.View
import androidx.compose.ui.platform.ComposeView
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import net.yumnumm.flutter_compose_view.ui.CounterApp


class MyComposeView(
    context: Context,
    creationParams: Map<String?, Any?>?,
    methodChannel: MethodChannel
) : PlatformView {
    private val composeView: ComposeView

    override fun getView(): View {
        return composeView
    }

    init {
        composeView = ComposeView(context).apply {
            setContent {
                CounterApp(methodChannel)
            }
        }
    }


    override fun dispose() {}
}