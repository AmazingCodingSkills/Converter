package com.currency.converter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.converter.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTests {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }
    // этот метод говорит о том, во-первых, что он запускается перед каждым тестом из-за аннотации @Before
    // так же ActivityScenario запускает MainActivity
    // ля того чтобы точно убедиться, что тест работает с полностью функционирующим экраном

    @Test
    fun convert_currency() {
        Thread.sleep(2000L)

        onView(withId(R.id.firstEditText)).check(matches(isDisplayed()))
            .perform(click())
    //onView() - это метод Espresso для нахождения элементов на экране и взаимодействия с ними
    }
}