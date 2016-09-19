package com.zz.core.tools;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class JSONHelper {

	public static <T> T getObject(String json, Class<T> clazz) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
				Map<String, String> patternMap = new HashMap<String, String>();
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{4}Z",
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ'Z'");
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}Z",
						"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z", "yyyy-MM-dd'T'HH:mm:ss'Z'");
				patternMap.put("[0-9]{13}", "time");
				String dateStr = json.getAsString();
				for (Map.Entry<String, String> entry : patternMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					Pattern compile = Pattern.compile(key);
					Matcher matcher = compile.matcher(dateStr);
					if (matcher.matches()) {
						if (value.equals("time")) {
							Date date = new Date();
							date.setTime(Long.valueOf(dateStr));
							return date;
						} else {
							SimpleDateFormat format = new SimpleDateFormat(value);
							try {
								Date date = format.parse(dateStr);
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);// date 换成已经已知的Date对象
								cal.add(Calendar.HOUR_OF_DAY, +8);// after 8
																	// hour
								return cal.getTime();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
				return null;
			}
		}).create();
		return (T) gson.fromJson(json, clazz);
	}

	/**
	 * 将传入的json字符串解析为List集合 List<User> jsonToLists =
	 * JSONHelper.jsonToList(json,new TypeToken<List<User>>(){});
	 * 
	 * @param json
	 * @param token
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, TypeToken<List<T>> token) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				Map<String, String> patternMap = new HashMap<String, String>();
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{4}Z",
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ'Z'");
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}Z",
						"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z", "yyyy-MM-dd'T'HH:mm:ss'Z'");
				patternMap.put("[0-9]{13}", "time");
				String dateStr = json.getAsString();
				for (Map.Entry<String, String> entry : patternMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					Pattern compile = Pattern.compile(key);
					Matcher matcher = compile.matcher(dateStr);
					if (matcher.matches()) {
						if (value.equals("time")) {
							Date date = new Date();
							date.setTime(Long.valueOf(dateStr));
							return date;
						} else {
							SimpleDateFormat format = new SimpleDateFormat(value);
							try {
								Date date = format.parse(dateStr);
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);// date 换成已经已知的Date对象
								cal.add(Calendar.HOUR_OF_DAY, +8);// after 8
																	// hour
								return cal.getTime();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
				return null;
			}
		}).create();
		return gson.fromJson(json, token.getType());
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, TypeToken<T> token) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				Map<String, String> patternMap = new HashMap<String, String>();
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{4}Z",
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ'Z'");
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}Z",
						"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z", "yyyy-MM-dd'T'HH:mm:ss'Z'");
				patternMap.put("[0-9]{13}", "time");
				String dateStr = json.getAsString();
				for (Map.Entry<String, String> entry : patternMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					Pattern compile = Pattern.compile(key);
					Matcher matcher = compile.matcher(dateStr);
					if (matcher.matches()) {
						if (value.equals("time")) {
							Date date = new Date();
							date.setTime(Long.valueOf(dateStr));
							return date;
						} else {
							SimpleDateFormat format = new SimpleDateFormat(value);
							try {
								Date date = format.parse(dateStr);
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);// date 换成已经已知的Date对象
								cal.add(Calendar.HOUR_OF_DAY, +8);// after 8
																	// hour
								return cal.getTime();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
				return null;
			}
		}).create();
		return (T) gson.fromJson(json, token.getType());
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, TypeToken<T> token, final String dateformat) {
		Gson gson = new GsonBuilder().setDateFormat(dateformat).create();
		return (T) gson.fromJson(json, token.getType());
	}

	/**
	 * 将对象转换成json格式(并自定义日期格式)
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJsonDateSerializer(Object ts, final String dateformat) {
		String jsonStr = null;
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				SimpleDateFormat format = new SimpleDateFormat(dateformat);
				return new JsonPrimitive(format.format(src));
			}
		}).setDateFormat(dateformat).create();
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

}
