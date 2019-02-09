package com.example.logonrmlocal.githubaac.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.logonrmlocal.githubaac.R
import com.example.logonrmlocal.githubaac.ui.userprofile.UserProfileFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDagger()
        setUpFragment()
    }
    private fun setUpDagger(){
        AndroidInjection.inject(this)
    }

    fun setUpFragment(){
  supportFragmentManager
                .beginTransaction()
                .add(R.id.container,UserProfileFragment(),null)
                .commit()
    }

    }
