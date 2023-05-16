package my.MyJPA.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MdcTest {

    @BeforeEach
    void setUp() {
        Mdc.clear();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void put() {
        assertTrue(Mdc.getCopyOfContextMap().isEmpty());

        try (final var mdc = Mdc.put("key", "value")) {
            assertEquals(Map.of("key", "value"), Mdc.getCopyOfContextMap());

            try (final var anotherMdc = Mdc.put("key", "value2")) {
                assertEquals(Map.of("key", "value2"), Mdc.getCopyOfContextMap());
            }

            assertEquals(Map.of("key", "value"), Mdc.getCopyOfContextMap());
        }

        assertTrue(Mdc.getCopyOfContextMap().isEmpty());
    }

    @Test
    void putMap() {
        assertTrue(Mdc.getCopyOfContextMap().isEmpty());

        try (final var mdc = Mdc.put(Map.of("key1", "value1", "key2", "value2"))) {
            assertEquals(Map.of("key1", "value1", "key2", "value2"), Mdc.getCopyOfContextMap());

            try (final var anotherMdc = Mdc.put(Map.of("key1", "value2", "key3", "value3"))) {
                assertEquals(Map.of("key1", "value2", "key2", "value2", "key3", "value3"), Mdc.getCopyOfContextMap());
            }

            assertEquals(Map.of("key1", "value1", "key2", "value2"), Mdc.getCopyOfContextMap());
        }

        assertTrue(Mdc.getCopyOfContextMap().isEmpty());
    }

}