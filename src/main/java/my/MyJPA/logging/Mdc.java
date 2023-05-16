package my.MyJPA.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.Closeable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Mdc implements Closeable {

    private final Set<String> valuesKeys;
    private final Map<String, String> originalValuesMap;

    private Mdc(Map<String, String> values, Map<String, String> originalValuesMap) {
        this.valuesKeys = values.keySet();
        this.originalValuesMap = originalValuesMap;
        values.forEach(MDC::put);
    }

    @Override
    public void close() {
        valuesKeys.forEach(MDC::remove);
        originalValuesMap.forEach(MDC::put);
    }

    public static Mdc put(Map<String, String> valuesMap) {
        Map<String, String> originalValuesMap = null;
        for (String key : valuesMap.keySet()) {
            String value = MDC.get(key);
            if (value != null) {
                if (originalValuesMap == null) {
                    originalValuesMap = new HashMap<>();
                }
                originalValuesMap.put(key, value);
            }
        }

        return new Mdc(valuesMap, originalValuesMap == null ? Collections.emptyMap() : originalValuesMap);
    }

    public static Mdc put(String key, String value) {
        return put(Map.of(key, value));
    }

    public static Map<String, String> getCopyOfContextMap() {
        Map<String, String> mdcMap = MDC.getCopyOfContextMap();
        return mdcMap == null ? Collections.emptyMap() : mdcMap;
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static void clear() {
        MDC.clear();
    }

}
