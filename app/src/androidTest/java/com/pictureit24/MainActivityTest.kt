package com.pictureit24


import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.whenever
import com.pictureit24.main.MainActivity
import com.pictureit24.main.MainActivityViewModel
import com.pictureit24.main.MainRepository
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainActivityTest {

    //mockito
    //espresso
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)


    val repository = mock(MainRepository::class.java)
    @Before
    fun setup(){

        whenever(repository.getList()).thenReturn(Observable.empty())

        activityRule.launchActivity(Intent())

    }
    @Test
    fun `when_empty_list_then_error_message_should_shown`(){


    }
}