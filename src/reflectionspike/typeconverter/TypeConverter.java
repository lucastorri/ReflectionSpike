/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectionspike.typeconverter;

/**
 *
 * @author lucastorri
 */
public class TypeConverter {

    public static Object convertType(Object o, Class originalType, Class targetType) {
        if (o != null && !originalType.equals(targetType)) {
            if (originalType.equals(String.class)) {
                o = convertStringToNumber((String) o, targetType);
            } else if (targetType.equals(String.class)) {
                o = o.toString();
            }
        }
        return o;
    }

    private static Number convertStringToNumber(String str, Class targetType) {
        if (targetType.equals(Long.class)) {
            return Long.parseLong(str);
        } else if (targetType.equals(Integer.class)) {
            return Integer.parseInt(str);
        } else if (targetType.equals(Short.class)) {
            return Short.parseShort(str);
        }
        return null;
    }
}
