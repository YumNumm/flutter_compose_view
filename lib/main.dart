import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Compose View',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  final methodChannel = const MethodChannel(
    'net.yumnumm.flutter_compose_view.FlutterComposeView/channel',
  );
  Future<void> _incrementCounter() async {
    final value = await methodChannel.invokeMethod<int>('increment');
    if (context.mounted && value != null){
      setState(() {
        _counter = value;
      });
    }
  }

  @override
  void initState() {
    super.initState();
    methodChannel.setMethodCallHandler(
      (call) async {
        if (call.method == 'update') {
          final count = call.arguments as int;
          if (context.mounted) {
            setState(() {
              _counter = count;
            });
          }
        }
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const Expanded(
          child: _AndroidHybridComposition(),
        ),
        Expanded(
          child: Scaffold(
            appBar: AppBar(
              backgroundColor: Theme.of(context).colorScheme.inversePrimary,
              title: const Text('Flutter'),
            ),
            body: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  const Text(
                    'You have pushed the button this many times:',
                  ),
                  Text(
                    '$_counter',
                    style: Theme.of(context).textTheme.headlineMedium,
                  ),
                ],
              ),
            ),
            floatingActionButton: FloatingActionButton(
              onPressed: _incrementCounter,
              child: const Icon(Icons.add),
            ),
          ),
        ),
      ],
    );
  }
}

class _AndroidHybridComposition extends StatelessWidget {
  const _AndroidHybridComposition();

  @override
  Widget build(BuildContext context) {
    const viewType = 'FlutterComposeView';
    const creationParams = <String, dynamic>{};

    return PlatformViewLink(
      viewType: viewType,
      surfaceFactory: (context, controller) {
        return AndroidViewSurface(
          controller: controller as AndroidViewController,
          gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
          hitTestBehavior: PlatformViewHitTestBehavior.opaque,
        );
      },
      onCreatePlatformView: (params) {
        // TLHCを利用
        // - SDK 23未満 または、 SurfaceViewを含む場合は HCへフォールバック
        return PlatformViewsService.initSurfaceAndroidView(
          id: params.id,
          viewType: viewType,
          layoutDirection: TextDirection.ltr,
          creationParams: creationParams,
          creationParamsCodec: const StandardMessageCodec(),
          onFocus: () {
            params.onFocusChanged(true);
          },
        )
          ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
          ..create();
      },
    );
  }
}
