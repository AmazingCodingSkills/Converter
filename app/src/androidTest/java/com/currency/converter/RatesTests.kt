package com.currency.converter

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.converter.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RatesTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun show_favorite_currency_rates() {
        Thread.sleep(2000L)

        onView(withId(R.id.recyclerMainScreen))
            .check(matches(isDisplayed()))
            .check(matches(hasChildCount(8)))

        onView(withText("ИЗМЕНИТЬ"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Все"))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(1000L)

        onView(withId(R.id.recyclerListCurrency))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Euro"))
                )
            )
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText("Euro")),
                        hasDescendant(withId(R.id.favoriteImageButton))
                    ),
                    click()
                )
            )

        Thread.sleep(1000L)

        onView(withId(R.id.viewPagerFavouritesScreen))
            .perform(swipeRight())

        Thread.sleep(1000L)

        onView(withId(R.id.buttonBackMain))
            .check(matches(isDisplayed()))
            .perform(click())

        Thread.sleep(1000L)

        onView(withId(R.id.swipeToRefreshMainScreen)).perform(swipeDown());

        Thread.sleep(2000L)

        onView(withId(R.id.recyclerMainScreen))
            .check(matches(isDisplayed()))
            .check(
                matches(
                    allOf(
                        hasChildCount(1),
                        hasDescendant(
                            withText(
                                "EUR"
                            )
                        )
                    )
                )
            )
    }
}




