package my.MyJPA.repository;

public interface Info<T extends Info> extends Cloneable {

    public T copy();

}
