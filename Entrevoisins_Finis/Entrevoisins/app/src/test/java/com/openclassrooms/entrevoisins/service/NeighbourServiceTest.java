package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void verificationEmptyFav() {
        List<Neighbour> mFav = service.getFav();
        List<Neighbour> expectedFav = new ArrayList<>();
        assertThat(mFav, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedFav.toArray()));
    }

    @Test
    public void addNeighboursWithSuccess() {
        Neighbour mUse = service.getNeighbours().get(0);
        List<Neighbour> expectedNeighbour = new ArrayList<>(service.getNeighbours());
        expectedNeighbour.add(mUse);
        service.createNeighbour(mUse);
        List<Neighbour> mListNeighbours = service.getNeighbours();
        assertThat(mListNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbour.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void verificationAddFav() {
        Neighbour mUse = service.getNeighbours().get(0);
        service.addFav(mUse);
        List<Neighbour> mFav = service.getFav();
        List<Neighbour> expectedFav = new ArrayList<>();
        expectedFav.add(mUse);
        assertThat(expectedFav, IsIterableContainingInAnyOrder.containsInAnyOrder(mFav.toArray()));
    }

    @Test
    public void verificationSupFav() {
        Neighbour mUse = service.getNeighbours().get(0);
        service.addFav(mUse);
        service.SupFav(mUse);
        List<Neighbour> mFav = service.getFav();
        List<Neighbour> expectedFav = new ArrayList<>();
        assertThat(expectedFav, IsIterableContainingInAnyOrder.containsInAnyOrder(mFav.toArray()));
    }
}
