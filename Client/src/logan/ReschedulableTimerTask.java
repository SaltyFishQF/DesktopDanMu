package logan;

import java.lang.reflect.Field;
import java.util.TimerTask;

public abstract class ReschedulableTimerTask extends TimerTask {
    public void setPeriod(long period) {
        //缩短周期，执行频率就提高
        setDeclaredField(TimerTask.class, this, "period", period);
    }

    public long getPeriod(){
        return (Long) getDeclaredField(TimerTask.class, this, "period");
    }

    //通过反射修改字段的值
    static boolean setDeclaredField(Class<?> clazz, Object obj,
                                    String name, Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //通过反射获取字段的值
    static Object getDeclaredField(Class<?> clazz, Object obj,
                                    String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}