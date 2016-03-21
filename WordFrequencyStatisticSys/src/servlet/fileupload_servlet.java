package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.FileUtil;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;


public class fileupload_servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GBK");
		//设置文件保存的路径
		Date currentData = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String cal = sdf.format(currentData);
		String filePath = getServletContext().getRealPath("/") + "upload/"+cal;
		String file_name = "";
		
		//初始化SmartUpload
        SmartUpload upload = new SmartUpload();
        upload.initialize(this.getServletConfig(), request, response);
        @SuppressWarnings("unused")
		int count = 0;
        com.jspsmart.upload.File tempFile = null;
        //开始上传
        try {
        	upload.setMaxFileSize(10*1024*1024);//设置文件大小的上限10M
        	upload.setAllowedFilesList("txt");//设置允许上传的文件格式，多个用，分开
			upload.upload();//上传文件
			
			// 如果文件夹不存在 则创建这个文件夹
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
			count = upload.save(filePath);//保存文件到指定路径
			
			for (int i = 0; i < upload.getFiles().getCount(); i++) {
				tempFile = upload.getFiles().getFile(i);//读取刚上传的文件
//				File file1=new File(filePath+tempFile.getFileName());
//				System.out.println("-------------------------------------------------");
//				System.out.println("表单项名称:" + tempFile.getFieldName());
				file_name = tempFile.getFileName();
				System.out.println("文件名：" + file_name);
//				System.out.println("文件长度：" + tempFile.getSize());
//				System.out.println("文件扩展名:" + tempFile.getFileExt());
//				System.out.println("文件全名：" + tempFile.getFilePathName());
			}
		} catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			System.out.println("格式不符合");
		}
//        System.out.println(count);
        
        //////////////////////////////读取文件///////////////////////////////
        
        Map<String,Integer> map = FileUtil.readtxtFile(filePath+"/"+tempFile.getFileName());
        System.out.println(map.toString());
        
        List<Map.Entry<String,Integer>> sort_data = FileUtil.sort(map).subList(0, 30);
        Map<String,Integer> sort_map = FileUtil.convert(sort_data);
        for(int i=0;i<30;i++)
			System.out.println("排序："+sort_data.get(i));
		

        
        request.getSession().setAttribute("filename", file_name);
        request.getSession().setAttribute("data", map);
        request.getSession().setAttribute("sort_data", sort_map);
        
        String path = request.getContextPath();
        response.sendRedirect(path+"/Chart.jsp");
	}

}
