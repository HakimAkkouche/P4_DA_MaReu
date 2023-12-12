package com.lamzone.mareu;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.service.DummyMeetingApiService;
import com.lamzone.mareu.utils.DeleteViewAction;
import com.lamzone.mareu.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyMeetingsTest {

    private final static int ITEMS_COUNT = 7;

    @Rule
    public ActivityScenarioRule<MyMainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MyMainActivity.class);

    /**
     * Tests Filter by room and by date
     * @throws InterruptedException InterruptedException
     */
    @Test
    public void a_meetingFilterByRoomAndByDateTest() throws InterruptedException {
        onView(withId(R.id.action_filter_by_room)).perform(click());

        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT));

        onView(withId(R.id.meeting_rooms_recyclerview)).perform(actionOnItemAtPosition(1, click()));


        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(1));

        onView(withId(R.id.meeting_rooms_recyclerview)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(2));

        onView(withId(R.id.action_filter_by_date)).perform(click());


        onView(withId(com.google.android.material.R.id.mtrl_picker_header_toggle)).perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.mtrl_picker_text_input_date),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("12/14/23"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withText("12/14/23"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.mtrl_picker_text_input_date),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(pressImeActionButton());

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click());

        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(0));

        onView(withId(R.id.meeting_rooms_recyclerview)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.meeting_rooms_recyclerview)).perform(actionOnItemAtPosition(1, click()));


        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(2));

        onView(withId(R.id.action_filter_by_date)).perform(click());

        onView(withId(com.google.android.material.R.id.cancel_button)).perform(click());

        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT));
    }

    /**
     * Test removing a meeting button
     */
    @Test
    public void b_myMeetingsList_deleteAction_shouldRemoveItem() {
        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(7));
        onView(allOf(withId(R.id.meeting_list_view), isDisplayed()))
                .perform(actionOnItemAtPosition(6, new DeleteViewAction()));
        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(6));
    }


    /**
     * test adding meeting
     */
    @Test
    public void c_addMeetingTest() {
        onView(withId(R.id.fab_add_meeting)).perform(click());

        onView(withId(R.id.add_meeting_name)).perform(replaceText("test"), closeSoftKeyboard());

        onView(withId(R.id.add_date_textview)).perform(click());
        onView(withId(com.google.android.material.R.id.mtrl_picker_header_toggle)).perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.mtrl_picker_text_input_date),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("12/15/23"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withText("12/15/23"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.mtrl_picker_text_input_date),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(com.google.android.material.R.id.confirm_button), withText("Valider"),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.date_picker_actions),
                                        childAtPosition(
                                                withId(com.google.android.material.R.id.mtrl_calendar_main_pane),
                                                1)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(withId(com.google.android.material.R.id.material_timepicker_mode_button)).perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("16"), closeSoftKeyboard());


        ViewInteraction textInputEditText5 = onView(
                allOf(withText("16"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(pressImeActionButton());

        ViewInteraction materialButton3 = onView(
                allOf(withId(com.google.android.material.R.id.material_timepicker_ok_button), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        Room room = DummyMeetingApiService.getInstance().getRooms().get(7);
        onView(withId(R.id.select_room_autocomplete_tv)).perform(click());

        onData(is(room)).inRoot(isPlatformPopup()).perform(scrollTo(), click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.add_email_edittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_input_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("test@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.addEmailButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.email_recyclerview),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withId(R.id.email_recyclerview)).check(new RecyclerViewItemCountAssertion(2));

        onView(withId(R.id.add_save_button)).perform(click());


        onView(withId(R.id.meeting_list_view)).check(new RecyclerViewItemCountAssertion(ITEMS_COUNT));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
