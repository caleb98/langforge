package net.calebscode.langforge.app.util;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;

public class AggregateSetTest {

	@Test
	void aggregateWithList() {
		AggregateSet<String> set = new AggregateSet<String>();

		set.aggregate(FXCollections.observableList(List.of("A", "B", "C", "T")));
		set.aggregate(FXCollections.observableList(List.of("X", "Y", "Z", "T")));

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "T"));
		assertEquals(7, set.getAggregate().size());
	}

	@Test
	void aggregateWithSet() {
		AggregateSet<String> set = new AggregateSet<String>();

		set.aggregate(FXCollections.observableSet(Set.of("A", "B", "C", "T")));
		set.aggregate(FXCollections.observableSet(Set.of("X", "Y", "Z", "T")));

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "T"));
		assertEquals(7, set.getAggregate().size());
	}

	@Test
	void aggregateWithListAndSet() {
		AggregateSet<String> set = new AggregateSet<String>();

		set.aggregate(FXCollections.observableSet(Set.of("A", "B", "C", "T")));
		set.aggregate(FXCollections.observableList(List.of("X", "Y", "Z", "T")));

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "T"));
		assertEquals(7, set.getAggregate().size());
	}

	@Test
	void removeIncludedList() {
		AggregateSet<String> set = new AggregateSet<String>();
		var listA = FXCollections.observableList(List.of("A", "B", "C", "T"));
		var listB = FXCollections.observableList(List.of("X", "Y", "Z", "T"));

		set.aggregate(listA);
		set.aggregate(listB);

		set.remove(listA);

		assertThat(set.getAggregate(), hasItems("X", "Y", "Z", "T"));
		assertEquals(4, set.getAggregate().size());
	}

	@Test
	void removeIncludedSet() {
		AggregateSet<String> set = new AggregateSet<String>();
		var setA = FXCollections.observableSet(Set.of("A", "B", "C", "T"));
		var setB = FXCollections.observableSet(Set.of("X", "Y", "Z", "T"));

		set.aggregate(setA);
		set.aggregate(setB);

		set.remove(setA);

		assertThat(set.getAggregate(), hasItems("X", "Y", "Z", "T"));
		assertEquals(4, set.getAggregate().size());
	}

	@Test
	void aggregateTracksListAdd() {
		AggregateSet<String> set = new AggregateSet<String>();
		var listA = FXCollections.observableList(new ArrayList<>(List.of("A", "B", "C")));
		var listB = FXCollections.observableList(new ArrayList<>(List.of("X", "Y", "Z")));

		set.aggregate(listA);
		set.aggregate(listB);

		listA.add("Foo");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "Foo"));
		assertEquals(7, set.getAggregate().size());

		listB.add("Foo");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "Foo"));
		assertEquals(7, set.getAggregate().size());
	}

	@Test
	void aggregateTracksListRemove() {
		AggregateSet<String> set = new AggregateSet<String>();
		var listA = FXCollections.observableList(new ArrayList<>(List.of("A", "B", "C", "T")));
		var listB = FXCollections.observableList(new ArrayList<>(List.of("X", "Y", "Z", "T")));

		set.aggregate(listA);
		set.aggregate(listB);

		listA.remove("T");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "T"));
		assertEquals(7, set.getAggregate().size());

		listB.remove("T");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z"));
		assertEquals(6, set.getAggregate().size());
	}

	@Test
	void aggregateTracksSetAdd() {
		AggregateSet<String> set = new AggregateSet<String>();
		var listA = FXCollections.observableSet(new HashSet<>(Set.of("A", "B", "C")));
		var listB = FXCollections.observableSet(new HashSet<>(Set.of("X", "Y", "Z")));

		set.aggregate(listA);
		set.aggregate(listB);

		listA.add("Foo");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "Foo"));
		assertEquals(7, set.getAggregate().size());

		listB.add("Foo");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "Foo"));
		assertEquals(7, set.getAggregate().size());
	}

	@Test
	void aggregateTracksSetRemove() {
		AggregateSet<String> set = new AggregateSet<String>();
		var listA = FXCollections.observableSet(new HashSet<>(Set.of("A", "B", "C", "T")));
		var listB = FXCollections.observableSet(new HashSet<>(Set.of("X", "Y", "Z", "T")));

		set.aggregate(listA);
		set.aggregate(listB);

		listA.remove("T");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z", "T"));
		assertEquals(7, set.getAggregate().size());

		listB.remove("T");

		assertThat(set.getAggregate(), hasItems("A", "B", "C", "X", "Y", "Z"));
		assertEquals(6, set.getAggregate().size());
	}

}
