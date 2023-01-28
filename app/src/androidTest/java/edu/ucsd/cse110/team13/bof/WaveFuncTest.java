package edu.ucsd.cse110.team13.bof;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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
public class WaveFuncTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void waveFuncTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_name_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Miles"), closeSoftKeyboard());

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
        appCompatEditText2.perform(replaceText("https://cseweb.ucsd.edu/~mej016/website_pic_m.jpg"), closeSoftKeyboard());

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

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.edit_class_year_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        materialTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.edit_class_quarter_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        materialTextView2.perform(click());

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
                .atPosition(2);
        materialTextView3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_class_subject_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.edit_class_subject_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("CSE"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_class_course_number_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("110"), closeSoftKeyboard());

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

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("110"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("21"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edit_class_course_number_textview), withText("21"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText7.perform(closeSoftKeyboard());

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

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\nBill,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2021,FA,CSE,210,Small\n2022,WI,CSE,110,Large"), closeSoftKeyboard());

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
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton13.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText10.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\nBill,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2021,FA,CSE,210,Small\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton14 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton14.perform(click());

        ViewInteraction materialButton15 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton15.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(android.R.id.title), withText("Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction materialTextView7 = onView(
                allOf(withId(R.id.debug_uid), withText("afd551b0-6f2b-4461-9a8c-d810ed8846f3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView7.perform(longClick());

        ViewInteraction materialTextView8 = onView(
                allOf(withId(R.id.debug_uid), withText("afd551b0-6f2b-4461-9a8c-d810ed8846f3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView8.perform(click());

        ViewInteraction materialTextView9 = onView(
                allOf(withId(R.id.debug_uid), withText("afd551b0-6f2b-4461-9a8c-d810ed8846f3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView9.perform(click());

        ViewInteraction materialTextView10 = onView(
                allOf(withId(R.id.debug_uid), withText("afd551b0-6f2b-4461-9a8c-d810ed8846f3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView10.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withContentDescription("Copy"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction materialButton16 = onView(
                allOf(withId(R.id.exitBtn), withText("Exit Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton16.perform(click());

        ViewInteraction materialButton17 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton17.perform(click());

        ViewInteraction materialTextView11 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView11.perform(click());

        pressBack();

        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(R.id.discover_students_view),
                        withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction materialButton18 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton18.perform(click());

        ViewInteraction materialTextView12 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView12.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\u2028Bill,,,,\u2028https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\u20282021,FA,CSE,210,Small\u20282022,WI,CSE,110,Large\nafd551b0-6f2b-4461-9a8c-d810ed8846f3,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton19 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton19.perform(click());

        ViewInteraction materialButton20 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton20.perform(scrollTo(), click());

        ViewInteraction materialButton21 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Go Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton21.perform(click());

        ViewInteraction materialButton22 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton22.perform(click());

        ViewInteraction materialTextView13 = onView(
                allOf(withId(android.R.id.title), withText("Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView13.perform(click());

        ViewInteraction materialTextView14 = onView(
                allOf(withId(R.id.debug_uid), withText("afd551b0-6f2b-4461-9a8c-d810ed8846f3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView14.perform(longClick());

        ViewInteraction linearLayout2 = onView(
                allOf(withContentDescription("Copy"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction materialButton23 = onView(
                allOf(withId(R.id.exitBtn), withText("Exit Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton23.perform(click());

        ViewInteraction materialButton24 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton24.perform(click());

        ViewInteraction materialTextView15 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView15.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\u2028Bill,,,,\u2028https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\u20282021,FA,CSE,210,Small\u20282022,WI,CSE,110,Large\u2028afd551b0-6f2b-4461-9a8c-d810ed8846f3,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton25 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton25.perform(click());

        ViewInteraction materialButton26 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton26.perform(scrollTo(), click());

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
        appCompatEditText15.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\u2028Bill,,,,\u2028https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\u20282021,FA,CSE,210,Small\u20282022,WI,CSE,110,Large\u20284b295157-ba31-4f9f-8401-5d85d9cf659,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton27 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton27.perform(click());

        ViewInteraction materialButton28 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton28.perform(scrollTo(), click());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText16.perform(click());

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
        appCompatEditText18.perform(click());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText19.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\nBill,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2021,FA,CSE,210,Small\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton29 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton29.perform(click());

        ViewInteraction materialButton30 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton30.perform(click());

        ViewInteraction materialTextView16 = onView(
                allOf(withId(android.R.id.title), withText("Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView16.perform(click());

        pressBack();

        ViewInteraction materialButton31 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton31.perform(click());

        ViewInteraction materialTextView17 = onView(
                allOf(withId(android.R.id.title), withText("Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView17.perform(click());

        ViewInteraction materialTextView18 = onView(
                allOf(withId(R.id.debug_uid), withText("afd551b0-6f2b-4461-9a8c-d810ed8846f3"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView18.perform(longClick());

        ViewInteraction linearLayout3 = onView(
                allOf(withContentDescription("Copy"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction materialButton32 = onView(
                allOf(withId(R.id.exitBtn), withText("Exit Debug"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton32.perform(click());

        ViewInteraction materialButton33 = onView(
                allOf(withId(R.id.mock_btn), withText("Menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appBarLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton33.perform(click());

        ViewInteraction materialTextView19 = onView(
                allOf(withId(android.R.id.title), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView19.perform(click());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.record_textview),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText20.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\u2028Bill,,,,\u2028https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\u20282021,FA,CSE,210,Small\u20282022,WI,CSE,110,Large\u2028afd551b0-6f2b-4461-9a8c-d810ed8846f3,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton34 = onView(
                allOf(withId(R.id.new_msg_btn), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton34.perform(click());

        ViewInteraction materialButton35 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton35.perform(scrollTo(), click());
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
