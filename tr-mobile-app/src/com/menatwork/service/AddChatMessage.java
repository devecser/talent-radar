package com.menatwork.service;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.BaseResponse;

public class AddChatMessage extends StandardServiceCall<BaseResponse> {

	public static AddChatMessage newInstance(final Context context, final String fromId,
			final String toId, final String content) {
		return new AddChatMessage(context, fromId, toId, content);
	}

	/*
	 * Add Message to chat
	 * 
	 * http://talent-radar.com/usersMessages/app_addMessage
	 * 
	 * data[UsersMessage][user_from_id]
	 * 
	 * data[UsersMessage][user_to_id]
	 * 
	 * data[UsersMessage][content]
	 */

	private AddChatMessage(final Context context, final String fromId, final String toId,
			final String content) {
		super(context, BaseResponse.class);
		// TODO - see what wave codification - boris - 20/09/2012
		// content = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
		this.setParameter(R.string.post_key_add_chat_from_id, fromId);
		this.setParameter(R.string.post_key_add_chat_to_id, toId);
		this.setParameter(R.string.post_key_add_chat_content, content);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_add_chat);
	}

}
