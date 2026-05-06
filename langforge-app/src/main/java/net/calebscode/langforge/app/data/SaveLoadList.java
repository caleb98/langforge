package net.calebscode.langforge.app.data;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public final class SaveLoadList implements SaveLoadValue, List<SaveLoadValue> {

	private List<SaveLoadValue> list;

	public SaveLoadList() {
		list = new ArrayList<>();
	}

	public SaveLoadList(List<? extends SaveLoadValue> list) {
		this.list = new ArrayList<>(list);
	}

	public <T> SaveLoadList(Collection<T> collection, Function<T, SaveLoadValue> mapper) {
		list = collection.stream().map(mapper).collect(toCollection(ArrayList::new));
	}

	@Override
	public boolean isList() {
		return true;
	}

	@Override
	public SaveLoadList asList() {
		return this;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<SaveLoadValue> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(SaveLoadValue e) {
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends SaveLoadValue> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends SaveLoadValue> c) {
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public SaveLoadValue get(int index) {
		return list.get(index);
	}

	@Override
	public SaveLoadValue set(int index, SaveLoadValue element) {
		return list.set(index, element);
	}

	@Override
	public void add(int index, SaveLoadValue element) {
		list.add(index, element);
	}

	@Override
	public SaveLoadValue remove(int index) {
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<SaveLoadValue> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<SaveLoadValue> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<SaveLoadValue> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}