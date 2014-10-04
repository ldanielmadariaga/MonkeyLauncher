package com.example.monkeylauncher.activities;

import android.support.v4.app.Fragment;

import com.example.monkeylauncher.fragments.MonkeyLauncherFragment;

public class MonkeyLauncherActivity extends SingleFragmentActtivity {

	@Override
	protected Fragment createFragment() {
		return new MonkeyLauncherFragment();
	}
}
