package reflectionspike.translator;

import reflectionspike.model.A;
import org.junit.Before;
import org.junit.Test;
import reflectionspike.model.B;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AToBTranslatorTest {

    private AToBTranslator translator;
    private A a;
    private B b;

    @Before
    public void setUp() {
        translator = new AToBTranslator();

        a = new A(443151L, "ola", "213123");
        b = new B(443151L, "ola", 213123L);
    }

    @Test
    public void shouldTranslateTo() {
        assertThat(translator.translateTo(a), is(b));
    }

    @Test
    public void shouldTranslateFrom() {
        assertThat(translator.translateFrom(b), is(a));
    }

}