package my.MyJPA.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class Context {

    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    private static final InheritableThreadLocal<Map<Class<? extends Info<?>>, Info<?>>> anotherThreadLocal = new InheritableThreadLocal<Map<Class<? extends Info<?>>, Info<?>>>() {
        @Override
        protected Map<Class<? extends Info<?>>, Info<?>> initialValue() {
            return new HashMap<>();
        }

        @Override
        protected Map<Class<? extends Info<?>>, Info<?>> childValue(Map<Class<? extends Info<?>>, Info<?>> parentValue) {
            return parentValue.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().copy()));
        }
    };

    public static <T extends Info<?>> T get(Class<T> infoType) {
        final Map<Class<? extends Info<?>>, Info<?>> infoMap = anotherThreadLocal.get();
        T info = (T) infoMap.get(infoType);

        if (info == null) {
            try {
                info = infoType.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            infoMap.put(infoType, info);
        }

        return info;
    }

    public static Optional<Object> getValue(String key) {
        Objects.requireNonNull(key);
        return Optional.ofNullable(threadLocal.get().get(key));
    }

    public static void setStringValue(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        threadLocal.get().put(key, value);
    }

    public static void remove(String key) {
        Objects.requireNonNull(key);
        threadLocal.get().remove(key);
    }

    // init by parent values
    public static void init(Map<String, Object> state) {
        threadLocal.get().putAll(state);
    }

    // capture immutable copy
    public static Map<String, Object> captureState() {
        return Map.copyOf(threadLocal.get());
    }

    public static void clear() {
        threadLocal.get().clear();
    }

}
