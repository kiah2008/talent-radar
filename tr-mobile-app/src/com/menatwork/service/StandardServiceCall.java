package com.menatwork.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public abstract class StandardServiceCall<T extends Response> extends
		ServiceCall<T> {

	private final Class<T> responseClass;
	private final Map<String, Object> parameters;

	/**
	 * Constructs a StandardServiceCall with a context, a response class (to
	 * wrap the JSONObject response after the POST method) and no parameters (to
	 * be added later by the
	 * {@link StandardServiceCall#addParameter(String, Object)} interface.
	 *
	 * @param context
	 *            Some context
	 * @param responseClass
	 *            Class of the response for this kind of service call
	 */
	public StandardServiceCall(final Context context,
			final Class<T> responseClass) {
		super(context);
		this.responseClass = responseClass;
		this.parameters = new HashMap<String, Object>();
	}

	/**
	 * Adds a parameter with a key and value corresponding to the key an value
	 * for the POST method.
	 *
	 * @param key
	 * @param value
	 */
	public void addParameter(final String key, final Object value) {
		parameters.put(key, value);
	}

	/**
	 * Gets a parameter value mapped to a certain key.
	 *
	 * @param key
	 *            Key of the parameter
	 * @return Value of the parameter
	 */
	public Object get(final String key) {
		return parameters.get(key);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>(
				parameters.size());

		// For each parameter we just create a NameValuePair and add it to the
		// parameter list
		for (final String key : parameters.keySet())
			postParams.add(new BasicNameValuePair( //
					key, //
					String.valueOf(parameters.get(key)) //
					));

		return postParams;
	}

	@Override
	protected T wrap(final JSONObject response) {
		try {
			// we get the constructor with a JSONObject parameter and call it
			// with the response parameter
			return responseClass.getConstructor(JSONObject.class).newInstance(
					response);

		} catch (final IllegalArgumentException e) {
			throw new Defect(
					"error trying to get constructor with JSONObject parameter and creating instance");
		} catch (final SecurityException e) {
			throw new Defect(
					"error trying to get constructor with JSONObject parameter and creating instance");
		} catch (final InstantiationException e) {
			throw new Defect(
					"error trying to get constructor with JSONObject parameter and creating instance");
		} catch (final IllegalAccessException e) {
			throw new Defect(
					"error trying to get constructor with JSONObject parameter and creating instance");
		} catch (final InvocationTargetException e) {
			throw new Defect(
					"error trying to get constructor with JSONObject parameter and creating instance");
		} catch (final NoSuchMethodException e) {
			throw new Defect(
					"error trying to get constructor with JSONObject parameter and creating instance");
		}
	}
}
