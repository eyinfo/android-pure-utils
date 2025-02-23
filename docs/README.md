# EBus快速集成

如果涉及到跨应用通信，则需要工程AndroidManifest.xml application节点以添加以下服务配置

```xml

<service android:name="com.eyinfo.android_pure_utils.ebus.EBusService" android:exported="false"
    android:permission="com.eyinfo.android_pure_utils.ebus.ebusProcessDataService">
    <intent-filter>
        <action android:name="com.eyinfo.android_pure_utils.ebus.ebusProcessDataService" />
    </intent-filter>
</service>
```