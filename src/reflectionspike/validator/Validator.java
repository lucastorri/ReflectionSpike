/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectionspike.validator;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author lucastorri
 */
public class Validator {

    public void validate(Object o) throws RuntimeException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, IntrospectionException {
        final Class<? extends Object> clz = o.getClass();
        for (PropertyDescriptor d : Introspector.getBeanInfo(clz).getPropertyDescriptors()) {
            if (!d.getName().equals("class")) {
                for (Annotation an : clz.getDeclaredField(d.getName()).getDeclaredAnnotations()) {
                    validate(an, d, o);
                }
            }
        }
    }

    private void validate(Annotation an, PropertyDescriptor d, Object o) throws InvocationTargetException, IllegalArgumentException, IllegalAccessException {
        if (an.annotationType().equals(ValidateNotNull.class)) {
            if (d.getReadMethod().invoke(o) == null) {
                throw new RuntimeException("field '" + d.getName() + "' is null");
            }
        } else if (an.annotationType().equals(ValidadeNotEmpty.class)) {
            if (d.getReadMethod().invoke(o).toString().trim().equals("")) {
                throw new RuntimeException("field '" + d.getName() + "' is empty");
            }
        } else if (an.annotationType().equals(ValidateBiggerThan.class)) {
            ValidateBiggerThan v = (ValidateBiggerThan) an;
            long value = (Long) d.getReadMethod().invoke(o);
            if (value <= v.value()) {
                throw new RuntimeException("field '" + d.getName() + "' with value " + value + " is equal or smaller than " + v.value());
            }
        }
    }
}
