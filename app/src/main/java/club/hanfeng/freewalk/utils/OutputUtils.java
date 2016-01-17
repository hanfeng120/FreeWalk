package club.hanfeng.freewalk.utils;

import android.content.Context;
import android.widget.Toast;

public class OutputUtils {
	
	/**
	 * 弹出一个短时间的Toast
	 * @param context	上下文对象
	 * @param msg	输出的具体内容
	 */
	public static void toastShort(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 弹出一个长时间的Toast
	 * @param context	上下文对象
	 * @param msg	输出的具体内容
	 */
	public static void toastLong(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	
	public static void println(String msg){
		System.out.println(msg);
	}
	
	public static void println(int msg){
		System.out.println(msg);
	}
	
	public static void println(boolean msg){
		System.out.println(msg);
	}
	
	public static void println(Object o){
		System.out.println(o);
	}
	
	public static void print(String msg){
		System.out.print(msg);
	}
	
	public static void print(int msg){
		System.out.print(msg);
	}
	
	public static void print(boolean msg){
		System.out.print(msg);
	}
	
	public static void print(Object o){
		System.out.print(o);
	}

}
