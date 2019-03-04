package com.metro.bi.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class SftpUtil {
    private transient Logger log = LoggerFactory.getLogger(this.getClass());

    private ChannelSftp sftp;

    private Session session;
    /** SFTP 登录用户名*/
    private String username;
    /** SFTP 登录密码*/
    private String password;
    /** 私钥 */
    private String privateKey;
    /** SFTP 服务器地址IP地址*/
    private String host;
    /** SFTP 端口*/
    private int port;


    /**
     * 构造基于密码认证的sftp对象
     */
    public SftpUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SftpUtil(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SftpUtil(){}

    /**
     * 连接sftp服务器
     */
    public void login(){
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }

            session = jsch.getSession(username, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect(1000);

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    public String getHome() throws SftpException{
        return  sftp.getHome();
    }
    /**
     * 关闭连接 server
     */
    public void logout(){
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     * @param directory
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }
    /**
     * 下载文件。
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException,IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null)fileOutputStream.close();
        }

    }
    /**
     * 下载文件。
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     */
    public boolean download(String directory, String downloadFile,String localPath, String saveFile) throws SftpException, FileNotFoundException,IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(localPath+saveFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile, fileOutputStream);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null)fileOutputStream.close();
        }
        return false;
    }
    /**
     * 批量下载文件
     * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat：下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat：下载文件格式(文件格式)
     * @param del：下载后是否删除sftp文件
     * @return
     */
    public List<String> batchDownLoadFile(String remotePath, String localPath,
                                          String fileFormat, String fileEndFormat, boolean del)
    {
        List<String> filenames = new ArrayList<String>();
        try
        {
            // connect();
            Vector v = listFiles(remotePath);
            // sftp.cd(remotePath);
            if (v.size() > 0)
            {
                System.out.println("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
                Iterator it = v.iterator();
                while (it.hasNext())
                {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir())
                    {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        fileFormat = fileFormat == null ? "" : fileFormat
                                .trim();
                        fileEndFormat = fileEndFormat == null ? ""
                                : fileEndFormat.trim();
                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0)
                        {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat))
                            {
                                flag = download(remotePath, filename,localPath, filename);
                                if (flag)
                                {
                                    filenames.add(localFileName);
                                    if (flag && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        }
                        else if (fileFormat.length() > 0 && "".equals(fileEndFormat))
                        {
                            if (filename.startsWith(fileFormat))
                            {
                                flag = download(remotePath, filename, localPath, filename);
                                if (flag)
                                {
                                    filenames.add(localFileName);
                                    if (flag && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        }
                        else if (fileEndFormat.length() > 0 && "".equals(fileFormat))
                        {
                            if (filename.endsWith(fileEndFormat))
                            {
                                flag = download(remotePath, filename,localPath, filename);
                                if (flag)
                                {
                                    filenames.add(localFileName);
                                    if (flag && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        }
                        else
                        {
                            flag = download(remotePath, filename,localPath, filename);
                            if (flag)
                            {
                                filenames.add(localFileName);
                                if (flag && del)
                                {
                                    deleteSFTP(remotePath, filename);
                                }
                            }
                        }
                    }
                }
            }
            if (log.isInfoEnabled())
            {
                log.info("download file is success:remotePath=" + remotePath
                        + "and localPath=" + localPath + ",file size is"
                        + v.size());
            }
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e2)
        {
            e2.printStackTrace();
        }
        finally
        {
            // this.disconnect();
        }
        return filenames;
    }

    /**
     * 删除stfp文件
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     */
    public void deleteSFTP(String directory, String deleteFile)
    {
        try
        {
            // sftp.cd(directory);
            sftp.rm(directory + deleteFile);
            if (log.isInfoEnabled())
            {
                log.info("delete file success from sftp.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param basePath  服务器的基础路径
     * @param directory  上传到该目录
     * @param sftpFileName  sftp端文件名
     * @param input   输入流
     */
    public void upload(String basePath,String directory, String sftpFileName, InputStream input) throws SftpException{
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String [] dirs=directory.split("/");
            String tempPath=basePath;
            for(String dir:dirs){
                if(null== dir || "".equals(dir)) continue;
                tempPath+="/"+dir;
                try{
                    sftp.cd(tempPath);
                }catch(SftpException ex){
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        sftp.put(input, sftpFileName);  //上传文件
    }

    //上传文件测试
    public static void main(String[] args) throws SftpException, IOException {
//        SftpUtil sftp = new SftpUtil("administrator", "Qingdao2018!", "123.59.204.138", 22);
//        SftpUtil sftp = new SftpUtil("root", "2018-ovu", "47.92.212.220", 22);
        SftpUtil sftp = new SftpUtil("administrator", "touMing1Shili", "47.105.126.129", 22);
        sftp.login();
//        System.out.println(sftp.listFiles("./"));
        System.out.println(sftp.getHome());
        String fileName = "123.txt";
//        String fileName="短信路径2.png";//上传，下载时文件名保持一致
//        File file = new File("D:/图片/短信路径.png");
//        InputStream is = null;
//        try {
//            is = new FileInputStream(file);
//            sftp.upload("","", fileName, is);
//        } finally {
//            if(is!=null)is.close();
//        }

//        sftp.download("/home/metro",fileName,"E:\\api\\logs\\"+fileName);
        sftp.logout();
    }


}
