package cn.idas_ouc.server.util;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * ClassName: ExecuteShellUtil
 * Package: cn.idas_ouc.server.util
 *
 * @Author boxin
 * @Create 2023/8/13 20:05
 * @Version 1.0
 * @Description:
 */

/**
 * 执行shell命令
 */
@Slf4j
public class ExecuteShellUtil {

    private Vector<String> stdout;
    // 会话session
    private Session session;

    //输入IP、端口、用户名和密码，连接远程服务器
    public ExecuteShellUtil(final String ipAddress, final String username, final String password, int port) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, ipAddress, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int execute(final String command) {
        int returnCode = 0;
        ChannelShell channel = null;
        PrintWriter printWriter = null;
        BufferedReader input = null;
        stdout = new Vector<String>();
        log.info("The remote command is: ");
        try {
            channel = (ChannelShell) session.openChannel("shell");
            channel.connect();
            input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            printWriter = new PrintWriter(channel.getOutputStream());
            printWriter.println(command);
            printWriter.println("");
            printWriter.println("exit");
            printWriter.flush();

            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            IOUtils.closeQuietly(printWriter);
            IOUtils.closeQuietly(input);
            if (channel != null) {
                channel.disconnect();
            }
        }
        return returnCode;
    }

    public int execute(final String[] commands,Vector<String> stdout) {
        int returnCode = 0;
        ChannelShell channel = null;
        PrintWriter printWriter = null;
        BufferedReader input = null;
        // log.info("The remote command is: ");
        try {
            channel = (ChannelShell) session.openChannel("shell");
            channel.connect();
            input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            printWriter = new PrintWriter(channel.getOutputStream());
            for (String command : commands) {
                printWriter.println(command);
                // log.info(command);
                // printWriter.println("");
            }
            printWriter.println("exit");
            printWriter.flush();

            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            IOUtils.closeQuietly(printWriter);
            IOUtils.closeQuietly(input);
            if (channel != null) {
                channel.disconnect();
            }
        }
        return returnCode;
    }

    // 断开连接
    public void close(){
        if (session != null) {
            session.disconnect();
        }
    }
    // 执行命令获取执行结果
    public String executeForResult(String command) {
        execute(command);
        // executeAdduser(command);
        StringBuilder sb = new StringBuilder();
        for (String str : stdout) {
            sb.append(str+"\n");
        }
        return sb.toString();
    }
    public String executeForResult(String[] commands) {
        Vector<String> stdout = new Vector<>();
        execute(commands,stdout);
        // executeAdduser(command);
        StringBuilder sb = new StringBuilder();
        String pre = "";
        for (int i = 18; i < stdout.size(); i++) {
            String str = stdout.get(i);
            // if( str.length() > 0 && str.charAt(0) == '(' )
            if(!str.equals(pre) && !str.equals(""))
                sb.append(str+"\n");
            pre = str;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("10.140.33.99","ouc","idas9018",3908);
        // ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("10.140.33.99","ubuntu","qingzhu20da19",3909);
        // ExecuteShellUtil executeShellUtil = new ExecuteShellUtil("124.222.33.212","ubuntu","Qingzhu20da",22);
        // 执行 ls /opt/命令
        // String result = executeShellUtil.executeForResult("ls /opt/");
        // String result = executeShellUtil.executeForResult("sudo cat /etc/passwd");
        String result = executeShellUtil.executeForResult("sudo useradd -u 1035 -m guoqi");
        // String result = executeShellUtil.executeForResult("sudo cat /etc/passwd");

        System.out.println(result);
        executeShellUtil.close();
    }
}