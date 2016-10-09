package com.example.kashishgrover.mttn.other;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class InternetCheck {

	static AlertDialog alert;

	static {
		alert = null;
	}

	public static boolean haveInternet(Context ctx) {

		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			return true;
		}
		return true;
	}

	public static void showNoConnectionDialog(final Context ctx, int i) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		if (alert != null)
			alert.dismiss();

		builder = new AlertDialog.Builder(ctx);
		builder.setCancelable(true);
		if (i == 0) {
			builder.setMessage("No network connection found.\nPlease turn on mobile network or Wi-Fi in Settings.");
			builder.setTitle("Unable to connect");
		} else if (i == 1) {
			builder.setMessage("Lost network connection.\nPlease turn on mobile network or Wi-Fi in Settings.");
			builder.setTitle("Lost connection");
		}

		builder.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						ctx.startActivity(i);
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});

		alert = builder.create();
		alert.show();
		return;
	}
}
