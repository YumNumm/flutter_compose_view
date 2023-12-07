import 'package:pigeon/pigeon.dart';

class CounterState {
  int? count;
}

@ConfigurePigeon(
  PigeonOptions(
    dartOut: 'lib/src/messages.g.dart',
    dartOptions: DartOptions(),
    kotlinOut:
        'android/app/src/main/kotlin/net/yumnumm/flutter_compose_view/Messages.g.kt',
    kotlinOptions: KotlinOptions(),
  ),
)
@HostApi()
abstract class CounterApi {
  void increment();
  CounterState getState();
}
