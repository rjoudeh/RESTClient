package com.rest.client.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.rest.bean.CheckoutErrorResponse;
import com.rest.bean.CheckoutResponse;
import com.rest.xml.XmlUtil;

/**
 * API Client to call services and get responses
 * 
 * @author Rania
 * 
 */
public class ApiClientHelper {
	private String hostName;
	private String serviceName;
	private Map<String, String> params;
	private HttpMethod httpMethod = HttpMethod.GET;
	String responseType = "application/xml";

	/**
	 * 
	 * @param hostName
	 * @param serviceName
	 */
	public ApiClientHelper(String hostName, String serviceName) {
		this.hostName = hostName;
		this.serviceName = serviceName;
	}

	/**
	 * 
	 * @param hostName
	 * @param serviceName
	 * @param params
	 */
	public ApiClientHelper(String hostName, String serviceName,
			Map<String, String> params) {
		this.hostName = hostName;
		this.serviceName = serviceName;
		this.params = params;
	}

	/**
	 * 
	 * @param hostName
	 * @param serviceName
	 * @param params
	 * @param httpMethod
	 */
	public ApiClientHelper(String hostName, String serviceName,
			Map<String, String> params, HttpMethod httpMethod) {
		this.hostName = hostName;
		this.serviceName = serviceName;
		this.params = params;
		this.httpMethod = httpMethod;
	}

	/**
	 * Call the specified service
	 * 
	 * @return
	 */
	public String callService() {
		String total = "";
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpUriRequest httpRequest;
			String uri = hostName + "/" + serviceName;
			switch (httpMethod) {
			case GET:
				httpRequest = new HttpGet(uri);
				break;
			case POST:
				httpRequest = new HttpPost(uri);
				break;
			default:
				throw new Exception("Undefined http method!");
			}

			httpRequest.addHeader("accept", responseType);
			buildParams(httpRequest);

			// Execute your request and catch response
			HttpResponse response = httpClient.execute(httpRequest);

			// Get-Capture Complete application/xml body response
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			// System.out.println("Response from " + serviceName);

			// Simply iterate through XML response and show on console.
			String output;
			String outContent = "";
			while ((output = br.readLine()) != null) {
				outContent += output;
			}

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				// throw new Exception("Failed : HTTP error code : "
				// + response.getStatusLine().getStatusCode());

				CheckoutErrorResponse error = XmlUtil.readXml(outContent,
						CheckoutErrorResponse.class);
				
				return "Error: " + error.getMessage();

			}

			CheckoutResponse res = XmlUtil.readXml(outContent,
					CheckoutResponse.class);
			total = "Total: " + res.getTotal();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return total;
	}

	/**
	 * Build request parameters
	 * 
	 * @param httpRequest
	 * @throws Exception
	 */
	private void buildParams(HttpUriRequest httpRequest) throws Exception {
		if (httpRequest == null) {
			throw new Exception("Could not build request params!");
		}

		if (httpRequest.getMethod().equals(HttpMethod.GET.name())) {
			buildGetParams((HttpGet) httpRequest);
		} else if (httpRequest.getMethod().equals(HttpMethod.POST.name())) {
			buildPostParams((HttpPost) httpRequest);
		}
	}

	/**
	 * Build parameters for POST method
	 * 
	 * @param httpRequest
	 * @throws UnsupportedEncodingException
	 */
	private void buildPostParams(HttpPost httpRequest)
			throws UnsupportedEncodingException {
		if (params != null && params.size() > 0) {
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				postParameters
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			httpRequest.setEntity(new UrlEncodedFormEntity(postParameters));
		}
	}

	/**
	 * Build parameters for GET method
	 * 
	 * @param httpRequest
	 * @throws URISyntaxException
	 */
	private void buildGetParams(HttpGet httpRequest) throws URISyntaxException {
		if (params != null && params.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<>();
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}

			URI uri = new URIBuilder(httpRequest.getURI()).addParameters(nvps)
					.build();
			httpRequest.setURI(uri);
		}
	}

	/**
	 * 
	 * enum for the used http methods
	 * 
	 */
	public enum HttpMethod {
		GET, POST;
	}
}
