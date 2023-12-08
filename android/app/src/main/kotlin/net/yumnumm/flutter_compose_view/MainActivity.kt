package net.yumnumm.flutter_compose_view

import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterFragmentActivity() {

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val methodChannel = MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            "net.yumnumm.flutter_compose_view.FlutterComposeView/channel"
        )

        flutterEngine
            .platformViewsController
            .registry
            .registerViewFactory("FlutterComposeView", ComposeViewFactory(methodChannel))
    }

}


