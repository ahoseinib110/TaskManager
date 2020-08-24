package com.example.taskmanager.repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {
    public void setList(List<E> list);
    public List<E> getList();
    public void insert(E element);
    public void remove(E element);
    public void remove(int index);
    public E get(UUID uuid);
    public void update(E e);
}
