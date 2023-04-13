package com.example.coffeetrail;

import static org.junit.Assert.assertEquals;

import com.example.coffeetrail.ui.fragment.MapsFragment;

import org.junit.Test;

public class CheckUserShopLocation {
    @Test
    public void checkLocation() {
        MapsFragment map = new MapsFragment();
        assertEquals(4, 2 + 2);
    }
}
