package edu.ucsd.cse110.team13.bof;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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
public class ProfileCreationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void profileCreationTest() {
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

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

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
        appCompatEditText4.perform(replaceText("110"), closeSoftKeyboard());

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

        ViewInteraction editText = onView(
                allOf(withId(R.id.edit_class_subject_textview), withText("CSE"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        editText.check(matches(withText("CSE")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        editText2.check(matches(withText("110")));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Tiny"),
                        withParent(allOf(withId(R.id.edit_class_size_spinner),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Tiny")));

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Winter"),
                        withParent(allOf(withId(R.id.edit_class_quarter_spinner),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Winter")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("2022"),
                        withParent(allOf(withId(R.id.edit_class_year_spinner),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("2022")));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText6.perform(click());

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
        appCompatEditText8.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("1"));

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText11.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("101"));

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("101"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText13.perform(closeSoftKeyboard());

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

        ViewInteraction materialTextView3 = onView(
                allOf(withId(android.R.id.title), withText("Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.debug_name), withText("Devin"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView4.check(matches(withText("Devin")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.class_row_text), withText("CSE 110 Winter 2022"),
                        withParent(withParent(withId(R.id.debug_classes))),
                        isDisplayed()));
        textView5.check(matches(withText("CSE 110 Winter 2022")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.class_row_text), withText("CSE 101 Winter 2022"),
                        withParent(withParent(withId(R.id.debug_classes))),
                        isDisplayed()));
        textView6.check(matches(withText("CSE 101 Winter 2022")));
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
