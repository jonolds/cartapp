package edu.uark.cartapp.models.api.services

import edu.uark.cartapp.models.api.interfaces.PathElementInterface
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

/* ==== APP BaseRemoteService.java ====*/
abstract class BaseRemoteService {

	protected fun requestSingle(pathElements: Array<PathElementInterface>): JSONObject? {
		return this.requestSingle(pathElements, StringUtils.EMPTY)
	}

	protected fun requestSingle(pathElements: Array<PathElementInterface>, id: UUID): JSONObject? {
		return this.requestSingle(pathElements, id.toString())
	}

	protected fun requestSingle(pathElements: Array<PathElementInterface>, value: String): JSONObject? {
		val connectionUrl = this.buildPath(pathElements, value)
		val rawResponse = this.performGetRequest(connectionUrl)
		return this.rawResponseToJSONObject(rawResponse)
	}

	protected fun putSingle(pathElements: Array<PathElementInterface>, jsonObject: JSONObject): JSONObject? {
		val connectionUrl = this.buildPath(pathElements)
		val rawResponse = this.performPutRequest(connectionUrl, jsonObject)
		return this.rawResponseToJSONObject(rawResponse)
	}

	private fun performGetRequest(connectionUrl: URL?): String {
		if (connectionUrl == null) {
			return StringUtils.EMPTY
		}

		var httpURLConnection: HttpURLConnection? = null
		val rawResponse = StringBuilder()

		try {
			httpURLConnection = connectionUrl.openConnection() as HttpURLConnection
			httpURLConnection.requestMethod = GET_REQUEST_METHOD
			httpURLConnection.addRequestProperty(ACCEPT_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE)

			val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))

			val buffer = CharArray(1024)
			var readCharacters = bufferedReader.read(buffer, 0, buffer.size)
			while (readCharacters > 0) {
				rawResponse.append(buffer, 0, readCharacters)
				readCharacters = bufferedReader.read(buffer, 0, buffer.size)
			}

			bufferedReader.close()
		} catch (e: IOException) {
			e.printStackTrace()
		} finally {
			httpURLConnection?.disconnect()
		}
		return rawResponse.toString()
	}

	private fun performPutRequest(connectionUrl: URL?, jsonObject: JSONObject): String {
		if (connectionUrl == null) {
			return StringUtils.EMPTY
		}

		var httpURLConnection: HttpURLConnection? = null
		val rawResponse = StringBuilder()

		try {
			httpURLConnection = connectionUrl.openConnection() as HttpURLConnection
			httpURLConnection.doInput = true
			httpURLConnection.doOutput = true
			httpURLConnection.setFixedLengthStreamingMode(jsonObject.toString().toByteArray(StandardCharsets.UTF_8).size)
			httpURLConnection.requestMethod = PUT_REQUEST_METHOD
			httpURLConnection.addRequestProperty(ACCEPT_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE)
			httpURLConnection.addRequestProperty(CONTENT_TYPE_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE)

			val outputStream = httpURLConnection.outputStream
			outputStream.write(jsonObject.toString().toByteArray(StandardCharsets.UTF_8))
			outputStream.flush()

			val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))

			val buffer = CharArray(1024)
			var readCharacters = bufferedReader.read(buffer, 0, buffer.size)
			while (readCharacters > 0) {
				rawResponse.append(buffer, 0, readCharacters)
				readCharacters = bufferedReader.read(buffer, 0, buffer.size)
			}
		} catch (e: IOException) {
			e.printStackTrace()
		} finally {
			httpURLConnection?.disconnect()
		}
		return rawResponse.toString()
	}

	private fun rawResponseToJSONObject(rawResponse: String): JSONObject? {
		var jsonObject: JSONObject? = null

		if (!StringUtils.isBlank(rawResponse)) {
			try {
				jsonObject = JSONObject(rawResponse)
			} catch (e: JSONException) {
				e.printStackTrace()
			}
		}
		return jsonObject
	}

	private fun buildPath(pathElements: Array<PathElementInterface>): URL? {
		return this.buildPath(pathElements, StringUtils.EMPTY)
	}

	private fun buildPath(pathElements: Array<PathElementInterface>, specificationEntry: String): URL? {
		val completePath = StringBuilder(BASE_URL)

		for (pathElement in pathElements) {
			val pathEntry = pathElement.pathValue

			if (!StringUtils.isBlank(pathEntry)) {
				completePath.append(pathEntry).append(URL_JOIN)
			}
		}

		if (!StringUtils.isBlank(specificationEntry)) {
			completePath.append(specificationEntry)
		}

		var connectionUrl: URL?
		try {
			connectionUrl = URL(completePath.toString())
		} catch (e: MalformedURLException) {
			e.printStackTrace()
			connectionUrl = null
		}

		return connectionUrl
	}

	companion object {

		private val URL_JOIN = "/"
		private val GET_REQUEST_METHOD = "GET"
		private val PUT_REQUEST_METHOD = "PUT"
		private val UTF8_CHARACTER_ENCODING = "UTF8"
		private val ACCEPT_REQUEST_PROPERTY = "Accept"
		private val JSON_PAYLOAD_TYPE = "application/json"
		private val CONTENT_TYPE_REQUEST_PROPERTY = "Content-Type"
		private val BASE_URL = "https://cartservice.herokuapp.com/"
	}
}