package reflectionspike.translator;

import java.util.HashMap;
import java.util.Map;
import reflectionspike.model.A;
import reflectionspike.model.B;

public class AToBTranslator extends Translator<A, B> {

    private static final Map<String, String> mapping = new HashMap<String, String>() {

        {
            put("a", "c");
            put("b", "d");
            put("x", "y");
        }
    };

    @Override
    public Map<String, String> mappingTable() {
        return mapping;
    }
}
