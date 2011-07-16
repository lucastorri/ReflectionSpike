/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reflectionspike.validator;

import reflectionspike.model.A;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lucastorri
 */
public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        validator = new Validator();
    }

    @Test(expected=RuntimeException.class)
    public void validatingNonEmptyStringsShouldThrowExceptionIfStringIsEmpty() throws Exception {
        validator.validate(new A(11L, "", "0"));
    }

    @Test(expected=RuntimeException.class)
    public void validatingBiggerThanShouldThrowExceptionIfValueIsEqualOrSmaller() throws Exception {
        validator.validate(new A(3L, null, "0"));
    }

    @Test
    public void shouldAllowValidObjects() throws Exception {
        validator.validate(new A(11L, "ola", "0"));
    }

}