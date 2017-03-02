package zhidanhyb.huozhu.HttpRequest;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.Utils.NetUtils;
import zhidanhyb.huozhu.Utils.T;

/**
 * 网络请求
 *
 * @author lxj
 */
public class HttpRestClient {
    /**
     *
     */
    private static AsyncHttpClient client = null;

    public HttpRestClient() {
        if (client == null) {
            client = new AsyncHttpClient();
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i("联网===", HttpConfigSite.PostUrl + relativeUrl);
        return HttpConfigSite.PostUrl + relativeUrl;
    }

    public static void get(Context context, final HttpRequestCallback httpRequestCallback, String url,
                           RequestParams params, final int requestcode) {
        if (NetUtils.isConnected(context)) {
            client.setTimeout(HttpConfigSite.TimeOut);
            if (ZDSharedPreferences.getInstance(context).getHttpHeadToken().equals("")) {
                client.addHeader("token", "");
            } else {
                client.addHeader("token", ZDSharedPreferences.getInstance(context).getHttpHeadToken());
            }
            client.get(context, getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    if (response != null) {
                        httpRequestCallback.HttpSuccess(response, requestcode);
                        Log.e("联网===", response + "");
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    httpRequestCallback.HttpFail(statusCode, responseString, requestcode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    httpRequestCallback.HttpFail(statusCode, errorResponse, requestcode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    httpRequestCallback.HttpFail(statusCode, errorResponse, requestcode);
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    httpRequestCallback.HttpCancel();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    httpRequestCallback.HttpStart();
                }

                @Override
                public void onRetry(int retryNo) {
                    // TODO Auto-generated method stub
                    super.onRetry(retryNo);
                    httpRequestCallback.HttpRetry();
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    httpRequestCallback.HttpProgress(bytesWritten, totalSize);
                }
            });
        } else {
            T.showShort(context, "请检查网络是否连接");
        }
    }

    /**
     *
     */
    private static int count = 0;

    /**
     * @param context
     * @param httpRequestCallback
     * @param url
     * @param params
     * @param requestcode
     */
    public static void post(Context context, final HttpRequestCallback httpRequestCallback, String url,
                            RequestParams params, final int requestcode) {
        count++;
        Log.e("param", params.toString() + "," + "ReqDataTimes=" + count);
        if (NetUtils.isConnected(context)) {
            client.setTimeout(HttpConfigSite.TimeOut);
            if (ZDSharedPreferences.getInstance(context).getHttpHeadToken().equals("")) {
                client.addHeader("token", "");
            } else {
                client.addHeader("token", ZDSharedPreferences.getInstance(context).getHttpHeadToken());
            }
            client.post(getAbsoluteUrl(url), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    if (response != null) {
                        httpRequestCallback.HttpSuccess(response, requestcode);
                        Log.e("联网===", response + "");

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    httpRequestCallback.HttpFail(statusCode, responseString, requestcode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    httpRequestCallback.HttpFail(statusCode, errorResponse, requestcode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    httpRequestCallback.HttpFail(statusCode, errorResponse, requestcode);
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    httpRequestCallback.HttpCancel();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    httpRequestCallback.HttpStart();
                }

                @Override
                public void onRetry(int retryNo) {
                    // TODO Auto-generated method stub
                    super.onRetry(retryNo);
                    httpRequestCallback.HttpRetry();
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    httpRequestCallback.HttpProgress(bytesWritten, totalSize);
                }
            });
        } else {
            T.showShort(context, "请检查网络是否连接");
            Log.e("error", "请检查网络是否连接");
        }
    }

    public static void JsonPost(Context context, final HttpRequestCallback httpRequestCallback, String url,
                                StringEntity stringEntity, final int requestcode) {
        if (NetUtils.isConnected(context)) {
            client.setTimeout(HttpConfigSite.TimeOut);
            client.post(context, url, stringEntity, "application/json;charset=utf-8", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    if (response != null) {
                        Log.e("联网===", response + "");

                        httpRequestCallback.HttpSuccess(response, requestcode);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    if (response != null) {
                        Log.e("联网===", response + "");
                        httpRequestCallback.HttpSuccess(response, requestcode);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    httpRequestCallback.HttpFail(statusCode, errorResponse, requestcode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    httpRequestCallback.HttpFail(statusCode, errorResponse, requestcode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    httpRequestCallback.HttpFail(statusCode, responseString, requestcode);
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    httpRequestCallback.HttpCancel();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    httpRequestCallback.HttpStart();
                }

                @Override
                public void onRetry(int retryNo) {
                    // TODO Auto-generated method stub
                    super.onRetry(retryNo);
                    httpRequestCallback.HttpRetry();
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    httpRequestCallback.HttpProgress(bytesWritten, totalSize);
                }
            });
        } else {
            T.showShort(context, "请检查网络是否连接");
        }
    }
}
