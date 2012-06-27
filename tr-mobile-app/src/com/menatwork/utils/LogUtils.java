package com.menatwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.http.client.methods.HttpPost;

import android.os.Bundle;
import android.util.Log;

public class LogUtils {

	public static <T> void d(T caller, String message, HttpPost postObject) {
		Log.d(caller.getClass().getSimpleName(), message);
		Log.d(caller.getClass().getSimpleName(), postObject.getURI().toString());

		ByteArrayOutputStream entityDescription = new ByteArrayOutputStream();
		try {
			postObject.getEntity().writeTo(entityDescription);
		} catch (IOException e) {
			// do nothing
		}
		Log.d(caller.getClass().getSimpleName(), entityDescription.toString());
	}

	public static <T> void d(T caller, String message, Bundle bundle) {
		Log.d(caller.getClass().getSimpleName(), message);
		Set<String> keys = bundle.keySet();
		for (String key : keys) {
			Log.d(caller.getClass().getSimpleName(),
					key + " = " + bundle.get(key));
		}
	}

}