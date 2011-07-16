/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectionspike;

import reflectionspike.model.A;
import reflectionspike.validator.Validator;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import reflectionspike.model.B;
import reflectionspike.translator.AToBTranslator;

/**
 *
 * @author lucastorri
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //validatorExample();
        translatorExample();
    }

    private static void translatorExample() {
        AToBTranslator t = new AToBTranslator();
        A a = new A(3L, "hello", "4123123");
        System.out.println(a);
        B b;
        System.out.println(b = t.translateTo(a));
        System.out.println(t.translateFrom(b));
    }

    private static void validatorExample() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, RuntimeException, IntrospectionException {
        A a = new A(1L, "asd", "4123123");
        Validator v = new Validator();
        v.validate(a);
    }

    
}
