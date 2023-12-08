package net.yumnumm.flutter_compose_view.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


@Composable
fun Counter(
    methodChannel: MethodChannel,
    modifier: Modifier = Modifier
) {
    println("Counter Composition")
    val counter = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        println("Counter LaunchedEffect")
        fun onMethodCall(methodCall: MethodCall, result: MethodChannel.Result) {
            println("onMethodCall")
            println(methodCall.method)
            when (methodCall.method) {
                "increment" -> {
                    counter.value++
                    result.success(counter.value)
                }

                else -> {
                    result.notImplemented()
                }
            }
        }
        methodChannel.setMethodCallHandler(::onMethodCall)
    }

    fun _onCounterUpdated(): Unit {
        methodChannel.invokeMethod("update", counter.value)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "You have pushed the button this many times:"
        )
        Text(
            text = "${counter.value}",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )
        FloatingActionButton(
            onClick = {
                counter.value++
                _onCounterUpdated()
            },
        ) {
            Icon(Icons.Filled.Add, "Small floating action button.")
        }
    }
}


