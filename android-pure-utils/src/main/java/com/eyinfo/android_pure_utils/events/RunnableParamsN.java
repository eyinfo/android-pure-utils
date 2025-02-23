package com.eyinfo.android_pure_utils.events;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2018/7/21
 * @Description:N个参数执行类
 * @Modifier:
 * @ModifyContent:
 */
public abstract class RunnableParamsN<Params> {

    private Object[] extras;

    public void setExtras(Object[] extras) {
        this.extras = extras;
    }

    public Object[] getExtras() {
        return extras;
    }

    /**
     * 回调
     *
     * @param params 参数
     */
    public abstract void run(Params... params);
}
