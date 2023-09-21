package cn.idas_ouc.server.util;

import org.junit.jupiter.api.Test;

/**
 * ClassName: ExecuteShellUtilTest
 * Package: cn.idas_ouc.server.util
 *
 * @Author boxin
 * @Create 2023/9/21 16:33
 * @Version 1.0
 * @Description:
 */

class ExecuteShellUtilTest {


    @Test
    void executeForResult1() {
        // ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("10.140.33.99","ubuntu","qingzhu20da19",3909);
        // ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("124.222.33.212","ubuntu","Qingzhu20da",22);
        ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("10.140.33.99","ouc","qingzhu20da",3908);

        String result = executeShellUtil.executeForResult("ls /opt/");

        System.out.println(result);
        executeShellUtil.close();
    }
    @Test
    void executeForResult2() {
        ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("10.140.33.99","ouc","qingzhu20da",3908);
        // String[] commands = {"ls /opt/"};
        String username = "test";
        String[] commands = {"sudo useradd -u 1099 -m "+username,"sudo su root","echo \""+username+":idas123\" | chpasswd\n","exit\n"};
        String result = executeShellUtil.executeForResult(commands);

        System.out.println(result);
        executeShellUtil.close();
    }

    @Test
    void name() {

        String admin = "ouc";
        String adminPassword = "qingzhu20da";
        String ip = "124.222.33.212";
        Integer port = Integer.valueOf("9018");
        String username = "test";
        ExecuteShellUtil executeShellUtil = new ExecuteShellUtil(ip,admin,adminPassword,port);
        String[] adduserCommands = {"sudo useradd -u 1099 -m "+username};
        String result = executeShellUtil.executeForResult(adduserCommands);

        System.out.println(result);
        executeShellUtil.close();

    }
}