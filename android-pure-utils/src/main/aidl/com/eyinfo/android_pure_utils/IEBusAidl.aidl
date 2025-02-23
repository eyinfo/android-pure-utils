// IEBusAidl.aidl
package com.eyinfo.android_pure_utils;

// Declare any non-default types here with import statements

interface IEBusAidl {
    /**
     * 获取进程id
     */
    int getProcessId();
    /**
     * 接收ebus数据
     * @param receiveKey key
     * @param events Object[] json形式
     */
    void receiveEBusData(String receiveKey,String events);
}