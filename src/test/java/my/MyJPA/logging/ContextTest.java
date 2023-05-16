package my.MyJPA.logging;

import my.MyJPA.logging.Context;
import my.MyJPA.repository.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {

    @BeforeEach
    void setUp() {
        Context.clear();
    }

    @Test
    void getValue() {
        assertFalse(Context.getValue("key").isPresent());

        Context.setStringValue("key", "value");
        assertEquals("value", Context.getValue("key").get());

        Context.setStringValue("key", "another value");
        assertEquals("another value", Context.getValue("key").get());

        Context.clear();
        assertFalse(Context.getValue("key").isPresent());

        assertNull(Context.get(UserInfo.class).getUserId());
    }

    @Test
    void setStringValue() {
        assertThrows(NullPointerException.class, () -> Context.setStringValue("key", null));
        assertThrows(NullPointerException.class, () -> Context.setStringValue(null, "value"));
    }

    @Test
    void captureState() {
        Context.setStringValue("key1", "value1");
        Context.setStringValue("key2", "value2");

        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("key1", "value1");
        expectedMap.put("key2", "value2");

        Map<String, Object> capturedState = Context.captureState();
        assertEquals(expectedMap, capturedState);

        assertThrows(UnsupportedOperationException.class, capturedState::clear);
        assertThrows(UnsupportedOperationException.class, () -> capturedState.put("key3", "value3"));
        assertThrows(UnsupportedOperationException.class, () -> capturedState.remove("key1"));
    }
}