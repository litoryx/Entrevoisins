
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myFavList_shouldBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        int favCount = 0;
        // Given : We montrons the element at position 0
        onView(ViewMatchers.withContentDescription("listfav")).check(withItemCount(favCount));
    }

    @Test
    public void VerifNomWithSuccess() {

        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.name)).check(matches(withText("Jack")));
    }

    @Test
    public void VerifLaunchSuccess() {

        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.name)).check(matches(withText("Jack")));
    }

    @Test
    public void should_addNeighbour() {

        onView(ViewMatchers.withContentDescription("listmyneighbour")).check(withItemCount(ITEMS_COUNT));

        onView(ViewMatchers.withId(R.id.add_neighbour))
                .perform(click());

        onView(ViewMatchers.withId(R.id.name))
                .perform(ViewActions.replaceText("Rob"));

        onView(ViewMatchers.withId(R.id.phoneNumber))
                .perform(ViewActions.replaceText("06.25.21.45.85"));

        onView(ViewMatchers.withId(R.id.address))
                .perform(ViewActions.replaceText("5 rue des champs elysee"));

        onView(ViewMatchers.withId(R.id.aboutMe))
                .perform(ViewActions.replaceText("j'aime le vin et l'argent"));

        onView(ViewMatchers.withId(R.id.create))
                .perform(click());

        onView(ViewMatchers.withContentDescription("listmyneighbour")).check(withItemCount(ITEMS_COUNT+1));

    }

    @Test
    public void malistFav_shouldaddItem() {
        int favCount = 0;
        // Given : We montrons the element at position 0
        onView(ViewMatchers.withContentDescription("listfav")).check(withItemCount(favCount));
        // When perform a click on a favoris icon
        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.favoris))
                .perform(click());

        pressBack();

        onView(ViewMatchers.withContentDescription("listfav")).check(withItemCount(favCount+1));

        onView(ViewMatchers.withId(R.id.container)).perform(swipeLeft());

        onView(ViewMatchers.withContentDescription("listfav"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.name)).check(matches(withText("Jack")));
        // Then : the number of element is 1

    }

    @Test
    public void malistFav_shouldelItem() {
        int favCount = 0;

        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.favoris))
                .perform(click());

        pressBack();

        onView(ViewMatchers.withContentDescription("listfav")).check(withItemCount(favCount+1));

        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.favoris))
                .perform(click());

        pressBack();

        onView(ViewMatchers.withContentDescription("listfav")).check(withItemCount(favCount));

    }
    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 12
        onView(ViewMatchers.withContentDescription("listmyneighbour")).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withContentDescription("listmyneighbour"))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withContentDescription("listmyneighbour")).check(withItemCount(ITEMS_COUNT-1));
    }
}