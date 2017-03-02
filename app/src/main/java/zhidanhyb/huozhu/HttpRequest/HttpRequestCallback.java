package zhidanhyb.huozhu.HttpRequest;


/**
 * @描述 网络请求回调
 * @author Administrator
 *
 */
public interface HttpRequestCallback {/**
	 * @描述 网络请求成功回调
	 * @方法�? HttpSuccess(String data)
	 * @param data
	 *            -返回结果
	 * @return void
	 */
	public void HttpSuccess(Object bean, int requestcode);
	
	/**
	 * @描述 取消网络请求的回�?
	 * @方法�? HttpCancel()
	 * @param �?
	 * @return void
	 */
	public void HttpCancel();

	/**
	 * @描述 发起网络请求失败的回�?(返回的JSON字符串为 null 或�?�为"")
	 * @方法�? HttpFail()
	 * @param �?
	 * @return void
	 */
	public void HttpFail(int code, Object responseString, int requestcode);
	/**
	 * @描述 �?始请求网�?
	 * @方法�? HttpStart()
	 * @param �?
	 * @return void
	 */
	public void HttpStart();
	
	/**
	 * @描述 重新请求网络
	 * @方法�? HttpRetry()
	 * @param �?
	 * @return void
	 */
	public void HttpRetry();
	
	/**
	 * @描述 网络请求进度
	 * @方法�? HttpProgress()
	 * @param �?
	 * @return void
	 */
	public void HttpProgress(long bytesWritten, long totalSize);
}
