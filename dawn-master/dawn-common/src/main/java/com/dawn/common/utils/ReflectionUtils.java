package com.dawn.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------
 * 反射相关辅助方法 (ReflectionUtils)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-08-07 16:26:00
 * ---------------------------
 */
public class ReflectionUtils {

    /**
     * 根据方法名调用指定对象的方法
     * @param object 要调用方法的对象
     * @param method 要调用的方法名
     * @param args   参数对象数组
     * @return
     */
    public static Object invoke(Object object, String method, Object... args) {
        Object result = null;
        Class<? extends Object> clazz = object.getClass();
        Method queryMethod = getMethod(clazz, method, args);
        if (queryMethod != null) {
            try {
                result = queryMethod.invoke(object, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new NoSuchMethodException(clazz.getName() + " 类中没有找到 " + method + " 方法。");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据方法名和参数对象查找方法
     * @param clazz
     * @param name
     * @param args  参数实例数据
     * @return
     */
    public static Method getMethod(Class<? extends Object> clazz, String name, Object[] args) {
        Method queryMethod = null;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == args.length) {
                    boolean isSameMethod = true;
                    for (int i = 0; i < parameterTypes.length; i++) {
                        Object arg = args[i];
                        if (arg == null) {
                            arg = "";
                        }

                        if (!isSupperClass(parameterTypes[i], args[i].getClass())) {
                            isSameMethod = false;
                        }

                    }
                    if (isSameMethod) {
                        queryMethod = method;
                        break;
                    }
                }
            }
        }
        return queryMethod;
    }

    /**
     * 判断方法参数的类型和传入的类型是否相同
     * 传入的类型实现接口类 及父及父类的接口类只要有一个相同即返回 true
     * @param paramClass
     * @param args
     * @return
     */
    public static boolean isSupperClass(Class<?> paramClass, Class<?> args) {
        boolean flag = false;
        List<Class<?>> list = new ArrayList<>();
        getAllClass(args, list);
        if (list.contains(paramClass)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 递归获取所有的父类及实现接口类
     * @param clz
     */
    public static void getAllClass(Class<?> clz, List<Class<?>> list) {
        list.add(clz);
        if (clz.getInterfaces() != null) {
            for (Class<?> c : clz.getInterfaces()) {
                list.add(c);
                getAllClass(c, list);
            }
        }
        if (clz.getSuperclass() != null) {
            getAllClass(clz.getSuperclass(), list);
        }
    }
}
