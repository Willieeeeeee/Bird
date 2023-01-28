package edu.ucsd.cse110.team13.bof;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MockingWithFavoritesTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mockingWithFavoritesTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_name_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Devin"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.edit_name_enter_btn), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.url_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("https://i.kym-cdn.com/entries/icons/mobile/000/024/719/Screen_Shot_2017-11-21_at_4.07.30_PM.jpg"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.Submit_button), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.back_button), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_class_subject_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("CSE"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.edit_class_course_number_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("140"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.edit_class_quarter_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        materialTextView.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("140"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("110"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.edit_class_size_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.edit_class_enter_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.edit_class_size_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction materialTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        materialTextView3.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText7.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("101"));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("101"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText9.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.edit_class_enter_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.edit_class_done_btn), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.scan_btn), withText("Start"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                0),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton11.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("ben,,,\n,,,\n2022,WI,CSE,110"), closeSoftKeyboard());

        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton12.perform(click());

        ViewInteraction materialButton13 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton13.perform(scrollTo(), click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText14.perform(click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\nBill,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2021,FA,CSE,210,Small\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton14 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton14.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.student_row_name), withText("Bill"),
                        withParent(withParent(withId(R.id.discover_students_view))),
                        isDisplayed()));
        textView.check(matches(withText("Bill")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.student_row_headshot),
                        withParent(allOf(withId(R.id.student_row_headshot_container),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.discover_students_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.class_row_text), withText("2022 Winter CSE 110"),
                        withParent(withParent(withId(R.id.detail_student_common_classes_view))),
                        isDisplayed()));
        textView2.check(matches(withText("2022 Winter CSE 110")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.waving_hand_hollow),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.waving_hand_hollow),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.waving_hand_filled),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        pressBack();

        ViewInteraction materialButton15 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton15.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton16 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton16.perform(click());

        ViewInteraction materialButton17 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton17.perform(scrollTo(), click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText17.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText18.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\nBill,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2021,FA,CSE,210,Small\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton18 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton18.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.discover_students_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.discover_students_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.hollow_star_details),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        pressBack();

        ViewInteraction materialButton19 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton19.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(android.R.id.title), withText("View My Favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withText("Favorite"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("Favorite")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.student_row_name), withText("Bill"),
                        withParent(withParent(withId(R.id.session_students_view))),
                        isDisplayed()));
        textView4.check(matches(withText("Bill")));

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.filled_star),
                        withParent(withParent(withId(R.id.session_students_view))),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.filled_star),
                        withParent(withParent(withId(R.id.session_students_view))),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed()));

        pressBack();
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
