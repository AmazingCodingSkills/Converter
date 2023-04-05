package com.currency.converter

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.converter.core.R
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTests {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun convert_currency() {
        Thread.sleep(2000L)

        onView(withId(R.id.ic_convert)).perform(click())

        onView(withId(com.example.calculator.R.id.firstEditText)).check(matches(isDisplayed()))
            .perform(typeText("1"))
        Thread.sleep(2000L)

        onView(withId(com.example.calculator.R.id.secondEditText)).check(matches(isDisplayed()))
            .perform(clearText(), typeText("1"))
        Thread.sleep(2000L)

        onView(withId(com.example.calculator.R.id.firstCurrency)).check(matches(isDisplayed()))
            .perform(click())

        onView(withId(com.example.calculator.R.id.recyclerAllCurrencies)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                ViewMatchers.hasDescendant(ViewMatchers.withText("GEL"))
            )
        )
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    CoreMatchers.allOf(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("DKK"))
                    ),
                    click()
                )
            )
        Thread.sleep(2000L)

        onView(withId(com.example.calculator.R.id.secondCurrency)).check(matches(isDisplayed()))
            .perform(click())

        onView(withId(com.example.calculator.R.id.recyclerAllCurrencies)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                ViewMatchers.hasDescendant(ViewMatchers.withText("SAR"))
            )
        )
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    CoreMatchers.allOf(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("PKR"))
                    ),
                    click()
                )
            )
        Thread.sleep(2000L)
    }
}