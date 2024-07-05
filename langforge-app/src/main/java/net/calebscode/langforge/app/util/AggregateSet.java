package net.calebscode.langforge.app.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

public class AggregateSet<T> {

	private ObservableSet<T> aggregate;

	private List<ObservableList<T>> includedLists = new ArrayList<>();
	private List<ObservableSet<T>> includedSets = new ArrayList<>();

	private final ListChangeListener<T> listListener = (change) -> {
		while(change.next()) {
			// Add any added elements.
			if (change.wasAdded()) {
				aggregate.addAll(change.getAddedSubList());
			}

			// Only remove elements if they are now present in no lists.
			if (change.wasRemoved()) {
				for (var removed : change.getRemoved()) {
					if (!anyContains(removed)) {
						aggregate.remove(removed);
					}
				}
			}
		}
	};

	private final SetChangeListener<T> setListener = (change) -> {
		if (change.wasAdded()) {
			aggregate.add(change.getElementAdded());
		}

		if (change.wasRemoved()) {
			tryRemove(change.getElementRemoved());
		}
	};

	public AggregateSet() {
		aggregate = FXCollections.observableSet(new HashSet<>());
	}

	public AggregateSet(Set<T> backingSet) {
		aggregate = FXCollections.observableSet(backingSet);
	}

	public ObservableSet<T> getAggregate() {
		return aggregate;
	}

	public void aggregate(ObservableList<T> includedList) {
		includedLists.add(includedList);
		aggregate.addAll(includedList);

		includedList.addListener(listListener);
	}

	public void aggregate(ObservableSet<T> includedSet) {
		includedSets.add(includedSet);
		aggregate.addAll(includedSet);

		includedSet.addListener(setListener);
	}

	public void remove(ObservableList<T> includedList) {
		includedLists.remove(includedList);

		includedList.removeListener(listListener);
		includedList.forEach(this::tryRemove);
	}

	public void remove(ObservableSet<T> includedSet) {
		includedSets.remove(includedSet);

		includedSet.removeListener(setListener);
		includedSet.forEach(this::tryRemove);
	}

	private void tryRemove(T value) {
		if (!anyContains(value)) {
			aggregate.remove(value);
		}
	}

	private boolean anyContains(T value) {
		for (var includedSet : includedSets) {
			if (includedSet.contains(value)) {
				return true;
			}
		}

		for (var includedList : includedLists) {
			if (includedList.contains(value)) {
				return true;
			}
		}

		return false;
	}

}
