package com.las.common.utils.reflect;

import com.las.common.utils.reflect.exception.ClassNotPreparedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class ReflectionUtil {
    private final static String GET_PREFIX = "get";

    //TODO: not tested
    public static <T> T copy(T t) {
        try {
            Constructor noArgsConstructor = getNoArgsConstructor(t);
            if (noArgsConstructor != null) {
                boolean accessChanged = openAccessIfNeeded(noArgsConstructor);
                T instance = (T) noArgsConstructor.newInstance();
                setValuesFromTo(t, instance);
                if (accessChanged) {
                    noArgsConstructor.setAccessible(false);
                }
                return instance;
            } else {
                throw new ClassNotPreparedException("Unable to create " + t.getClass() + " instance. Class does not have constructor with 0 parameters.");
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ClassNotPreparedException(e.getMessage(), e);
        }
    }

    public static <T> Constructor getNoArgsConstructor(T t) throws ClassNotFoundException {
        String className = t.getClass().getName();
        Constructor<?>[] declaredConstructors = Class.forName(className).getDeclaredConstructors();
        Constructor noParamConstructor = null;
        for (Constructor constructor : declaredConstructors) {
            int parameterCount = constructor.getParameterCount();
            if (parameterCount == 0) {
                noParamConstructor = constructor;
                break;
            }
        }
        return noParamConstructor;
    }

    public static String generateGetterName(String fieldName) {
        StringBuilder builder = new StringBuilder(fieldName);
        builder
                .replace(0, 1, builder.substring(0, 1).toUpperCase())
                .insert(0, GET_PREFIX);
        return builder.toString();
    }

    public static void setValuesFromTo(Object from, Object to) {
        try {
            Class<?> toClass = to.getClass();
            Field[] fields = toClass.getDeclaredFields();
            for (Field field : fields) {

                Annotation ignore = field.getAnnotation(IgnoreDump.class);
                if (ignore == null) {
                    setFieldValueFromTo(field, from, to);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void setFieldValueFromTo(Field field, Object from, Object to) throws NoSuchFieldException, IllegalAccessException {
        boolean accessChanged = openAccessIfNeeded(field);

        Class<? extends Object> fromClass = from.getClass();

        String name = field.getName();
        Field sourceField = fromClass.getDeclaredField(name);
        boolean sourceFieldAccessChanged = openAccessIfNeeded(sourceField);
        Object backupValue = sourceField.get(from);

        int modifiers = field.getModifiers();
        String modifiersStr = Modifier.toString(modifiers);
        if (!modifiersStr.contains("final")) {

            field.set(to, backupValue);
            if (sourceFieldAccessChanged) {
                changeAccessToOpposite(sourceField);
            }
            if (accessChanged) {
                changeAccessToOpposite(field);
            }
        }
    }


    private static boolean openAccessIfNeeded(AccessibleObject f) {
        boolean changed = false;
        if (!f.isAccessible()) {
            f.setAccessible(changed = true);
        }
        return changed;
    }

    private static void changeAccessToOpposite(Field field) {
        boolean oppositeAccess = !field.isAccessible();
        field.setAccessible(oppositeAccess);
    }

}
