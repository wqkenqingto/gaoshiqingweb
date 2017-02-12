package common.ip;

/**
 * 类IPtest.java的实现描述：test
 * 
 * @author sz.gong 2016年3月13日 下午5:25:38
 */
public class IPtest {
    public static void main(String[] args) {
        // 指定纯真数据库的文件名，所在文件夹
        IPSeeker ip = new IPSeeker("QQWry.Dat", "f:/");
        String temp = "169.254.111.173";
        // 测试IP 58.20.43.13
        System.out.println(ip.getIPLocation(temp).getCountry() + ":" + ip.getIPLocation(temp).getArea());
    }

}
