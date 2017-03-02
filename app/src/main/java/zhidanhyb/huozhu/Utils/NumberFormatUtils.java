/**
 * 
 */
package zhidanhyb.huozhu.Utils;

/**
 * 数字格式
 * @author lxj
 *
 */
public class NumberFormatUtils {

	
	/**
	 * 输入数字转成三位一个逗号如  1000 -> 1,000
	 * @return
	 */
	@SuppressWarnings("null")
	public static String NumberType(String number){
		String Num = null;
		if(number == null || number.equals("")){
			return Num = "";
		}
		String firstNum = null;
        if(number.length() >= 1 && number.length() <=3){
        	Num = number;
		}else if(number.length() == 4){
			firstNum = number.substring(0, 1);
			Num = firstNum+","+number.substring(1, number.length());
		}else if(number.length() == 5){
			firstNum = number.substring(0, 2);
			Num = firstNum+","+number.substring(2, number.length());
		}else if(number.length() == 6){
			firstNum = number.substring(0, 3);
			Num = firstNum+","+number.substring(3, number.length());
		}else if(number.length() == 7){
			firstNum = number.substring(0, 1);
			Num = firstNum+","+number.substring(1, 4)+","+number.substring(4, number.length());
		}else if(number.length() == 8){
			firstNum = number.substring(0, 2);
			Num = firstNum+","+number.substring(2, 5)+","+number.substring(5, number.length());
		}
		return Num;
	}
}
