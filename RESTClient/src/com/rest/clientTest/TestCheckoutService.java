package com.rest.clientTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rest.client.helper.ApiClientHelper;
import com.rest.client.helper.ConfigHelper;

public class TestCheckoutService {

	public void testCheckoutService() throws IOException {
		String hostName = ConfigHelper.getInstance().getHostName();
		String serviceName = ConfigHelper.getInstance()
				.getCheckoutServiceName();

		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"testCases");

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			Map<String, String> params = new HashMap<String, String>();

			params.put("items", line);

			ApiClientHelper api = new ApiClientHelper(hostName, serviceName,
					params);
			String output = api.callService();
			System.out.println(output);
			System.out.println("==========================================");
		}

		br.close();

	}

	public static void main(String[] args) throws IOException {
		TestCheckoutService client = new TestCheckoutService();
		client.testCheckoutService();
	}

	/**
	 * 
	 * @return
	 */
	public static String prepareItems() {
		List<String> list = new ArrayList<String>();
		list = Arrays.asList(new String[] { "apple,apple,orange,apple" });

		String lst = (list.toString()).substring(1,
				list.toString().length() - 1);

		return lst;
	}
}