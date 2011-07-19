package reflectionspike.translator;

import static reflectionspike.typeconverter.TypeConverter.convertType;

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
        Map<String, PropertyDescriptor> originalTypeFieldDescriptors = getFieldDescriptors(original.getClass());
        Map<String, PropertyDescriptor> translatedTypeFieldDescriptors = getFieldDescriptors(translated.getClass());
        for (String field : mappingTable.keySet()) {
            final Method getter = originalTypeFieldDescriptors.get(field).getReadMethod();
            final Method setter = translatedTypeFieldDescriptors.get(mappingTable.get(field)).getWriteMethod();
            setter.invoke(translated, convertType(getter.invoke(original), getter.getReturnType(), setter.getParameterTypes()[0]));
        }
    }

    private Map<String, PropertyDescriptor> getFieldDescriptors(Class clz) throws IntrospectionException {
        final HashMap<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>();
        for (PropertyDescriptor fieldDescriptors : Introspector.getBeanInfo(clz).getPropertyDescriptors()) {
            descriptors.put(fieldDescriptors.getName(), fieldDescriptors);
        }
        return descriptors;
    }

    public abstract Map<String, String> mappingTable();

    public Map<String, String> reverseMappingTable() {
        final Map<String, String> mappingTable = mappingTable();
        final Map<String, String> reverseMappingTable = new HashMap<String, String>();
        for (String value : mappingTable.keySet()) {
            reverseMappingTable.put(mappingTable.get(value), value);
        }
        return reverseMappingTable;
    }
}
