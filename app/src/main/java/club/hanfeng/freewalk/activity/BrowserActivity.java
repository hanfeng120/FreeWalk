package club.hanfeng.freewalk.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import club.hanfeng.freewalk.R;
import club.hanfeng.freewalk.mainpage.MainPageActivity;

public class BrowserActivity extends AppCompatActivity{
	
	private WebView mBrowser;
	private ProgressBar pb;
	private ActionBar actionBar;
	private ImageView img_fail;
	private String http_url;
	private String title;
	private boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);

		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("");
		
		
		mBrowser=(WebView) findViewById(R.id.browser);
		pb=(ProgressBar) findViewById(R.id.pb_browser);
		img_fail=(ImageView) findViewById(R.id.img_err_browser);
	
		WebSettings webSet=mBrowser.getSettings();
		webSet.setJavaScriptEnabled(true);
		webSet.setAllowFileAccess(true);
		webSet.setBuiltInZoomControls(true);
		webSet.setSupportZoom(true);
		
		MyWebViewClient myWebViewClient=new MyWebViewClient();
		mBrowser.setWebViewClient(myWebViewClient);		
		MyWebChromeClient myWebChromeClient=new MyWebChromeClient();
		mBrowser.setWebChromeClient(myWebChromeClient);		
		mBrowser.setDownloadListener(new MyDownload());

		
		Intent intent = getIntent();
		http_url = intent.getStringExtra("url");
		flag = intent.getBooleanExtra("flag", false);
		title = intent.getStringExtra("title");
		actionBar.setTitle(title);

		if(http_url == null){
			mBrowser.loadUrl("http://www.baidu.com");
		}else{
			if(http_url.startsWith("http://")){
				mBrowser.loadUrl(http_url);
			}else{
				http_url="http://"+http_url;
				mBrowser.loadUrl(http_url);
			}
		}
	}
	
	
	class MyDownload implements DownloadListener{

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			Uri uri=Uri.parse(url);
			Intent intent=new Intent(Intent.ACTION_VIEW,uri);
			startActivity(intent);
		}
		
	}
	
	class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			img_fail.setVisibility(View.VISIBLE);
			mBrowser.setVisibility(View.GONE);
            view.loadUrl("javascript:document.body.innerHTML=\"\"");

		}
		
	}
	
	/**
	 * 
	 */
	class MyWebChromeClient extends WebChromeClient{
		
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(view, newProgress);
			if(newProgress<100){
				pb.setVisibility(View.VISIBLE);
				pb.setProgress(newProgress);
			}else{
				pb.setVisibility(View.GONE);
			}
		}
		
		@Override
		public void onReceivedTitle(WebView view, String title) {
			// TODO Auto-generated method stub
			actionBar.setTitle(title);
			super.onReceivedTitle(view, title);
		}
			
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if((keyCode==KeyEvent.KEYCODE_BACK)&&mBrowser.canGoBack()){
			mBrowser.goBack();
			return true;
		}else if(keyCode==KeyEvent.KEYCODE_BACK && !mBrowser.canGoBack()){
			if(flag){
				Intent intent = new Intent(this, MainPageActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}else{
				finish();
			}
		}
		return false;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_browser, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				if(flag){
					Intent intent = new Intent(this, MainPageActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}else{
					finish();
				}
				break;
			case R.id.menu_browser_ref:
				pb.setVisibility(View.VISIBLE);
				mBrowser.reload();
				mBrowser.setVisibility(View.VISIBLE);
				img_fail.setVisibility(View.GONE);
				break;
			case R.id.menu_browser_share:
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/*");
				// 自动添加的分享的具体信息
				intent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(intent, "分享当前页面"));

				break;
			case R.id.menu_browser_open:
				Intent intent2 = new Intent();
				intent2.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(mBrowser.getUrl());
				intent2.setData(content_url);
				startActivity(intent2);
				break;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}
	

}


