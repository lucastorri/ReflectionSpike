package reflectionspike.translator;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public abstract class Translator<A, B> {

    private A a;
    private B b;

    private A newA() {
        return (A) newGeneric(0);
    }

    private B newB() {
        return (B) newGeneric(1);
    }

    private Object newGeneric(int index) {
        try {
            return ((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[index]).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate type");
        }
    }

    public B translateTo(A original) {
        return translate(original, newB(), mappingTable());
    }

    public A translateFrom(B original) {
        return translate(original, newA(), reverseMappingTable());
    }

    private <T> T translate(Object original, T translated, Map<String, String> mappingTable) {
        try {
            realTranslate(original, translated, mappingTable);
        } catch (Exception e) {
            return null;
        }
        return translated;
    }

    private void realTranslate(Object original, Object translated, Map<String, String> mappingTable) throws InvocationTargetException, IllegalAccessException, IllegalArgumentException, IntrospectionException {
        Map<String, PropertyDescriptor> fromDescriptors = getPropertyDescriptors(original.getClass());
        Map<String, PropertyDescriptor> toDescriptors = getPropertyDescriptors(translated.getClass());
        for (String value : mappingTable.keySet()) {
            final Method setter = toDescriptors.get(mappingTable.get(value)).getWriteMethod();
            final Method getter = fromDescriptors.get(value).getReadMethod();
            setter.invoke(translated, convertType(getter.invoke(original), getter.getReturnType(), setter.getParameterTypes()[0]));
        }
    }

    private Object convertType(Object o, Class originalType, Class targetType) {
        if (o != null && !originalType.equals(targetType)) {
            if (originalType.equals(String.class)) {
                String s = o.toString();
                if (targetType.equals(Long.class)) {
                    o = Long.parseLong(s);
                } else if (targetType.equals(Integer.class)) {
                    o = Integer.parseInt(s);
                } else if (targetType.equals(Short.class)) {
                    o = Short.parseShort(s);
                }
            } else if (targetType.equals(String.class)) {
                o = o.toString();
            }
        }

        return o;
    }

    private Map<String, PropertyDescriptor> getPropertyDescriptors(Class clz) throws IntrospectionException {
        final HashMap<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>();
        for (PropertyDescriptor pd : Introspector.getBeanInfo(clz).getPropertyDescriptors()) {
            descriptors.put(pd.getName(), pd);
        }
        return descriptors;
    }

    public abstract Map<String, String> mappingTable();

    public Map<String, String> reverseMappingTable() {
        Map<String, String> mappingTable = mappingTable();
        Map<String, String> reverseMappingTable = new HashMap<String, String>();
        for (String value : mappingTable.keySet()) {
            reverseMappingTable.put(mappingTable.get(value), value);
        }
        return reverseMappingTable;
    }
}
