package com.example.monkeylauncher.fragments;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MonkeyLauncherFragment extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent startupIntent = new Intent(Intent.ACTION_MAIN);
		startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		PackageManager packageManager = getActivity().getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(startupIntent, 0);

		Log.i("Activities", "Found: " + activities.size() + " activities.");

		Collections.sort(activities, new Comparator<ResolveInfo>() {

			// TODO Move
			@Override
			public int compare(ResolveInfo lhs, ResolveInfo rhs) {
				PackageManager packageManager = getActivity().getPackageManager();
				return String.CASE_INSENSITIVE_ORDER.compare(lhs.loadLabel(packageManager).toString(), rhs
						.loadLabel(packageManager).toString());
			}
		});

		// TODO Move
		ArrayAdapter<ResolveInfo> resolveInfoAdapter = new ArrayAdapter<ResolveInfo>(getActivity(),
				R.layout.simple_list_item_1, activities) {

			public View getView(int position, View convertView, ViewGroup parent) {
				PackageManager packageManager = getActivity().getPackageManager();
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView) view;
				ResolveInfo resolveInfo = getItem(position);
				textView.setText(resolveInfo.loadLabel(packageManager));
				Drawable icon = resolveInfo.loadIcon(packageManager);
				textView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
				return view;
			}
		};

		setListAdapter(resolveInfoAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ResolveInfo resolveInfo = (ResolveInfo) l.getAdapter().getItem(position);
		ActivityInfo activityInfo = resolveInfo.activityInfo;

		// TODO FFC
		if (activityInfo == null) {
			return;
		}

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
