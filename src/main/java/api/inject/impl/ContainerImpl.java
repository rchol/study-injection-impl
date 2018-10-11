package api.inject.impl;

import api.inject.Container;

public class ContainerImpl implements Container {

    public ContainerImpl(String s) {

    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getInstance(String name, Class<T> requiredType) {
        return null;
    }

    @Override
    public Object getInstance(String name) {
        return null;
    }
}
