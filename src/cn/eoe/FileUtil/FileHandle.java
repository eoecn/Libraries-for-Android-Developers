package cn.eoe.FileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.eoe.util.check;
import cn.eoe.util.Observer.AbstractSubject;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * 文件浏览控制器
 * 利用这个控制器可对指定路径下的文件夹进行浏览
 * @author 梁前武 QQ 1587790525
 * www.apkplug.com
 */
public class FileHandle {

	public FileHandle(){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			rootDir = Environment.getExternalStorageDirectory();
		} else {
			rootDir = Environment.getRootDirectory();
		}
	}
	private File rootDir;
	private File copyPath;
	/**
	 * 根路径
	 */
	public void start(Vector list){
		loadFiles(list,rootDir,null);
	}
	public void start(Vector list,String dirpath){
		if(!check.checkString(dirpath))
			start(list);
		File f=new File(dirpath);
		if(f==null||!f.isDirectory()){
			start(list);
		}else{
			loadFiles(list,f,null);
		}
	}
	/**
	 * 加载当前文件夹列表
	 * */
	public void loadFiles(Vector list,File dir,FilenameFilter filter) {
		list.clear();
		if (dir != null) {
			FileItem pare = new FileItem();
			pare.setF(dir.getParentFile());
			pare.setName("上一目录");
			list.add(pare);
			File[] files = dir.listFiles();
			if (files != null) {
				sortFiles(files); //排序
				for (File f : files) {
					if(this.isAcceptFile(f, filter)){
						//过滤文件类型
					FileItem map = new FileItem();
					map.setF(f);
					map.setName(f.getName());
					list.add(map);
					}
				}
			}
			
		} else {
			FileItem pare = new FileItem();
			pare.setF(rootDir);
			pare.setName("返回根目录");
			list.add(pare);
		}
		
	}
	
	/**
	 * 加载当前文件夹列表
	 * */
	public static Vector loadFilelist(File dir,FilenameFilter filter) {
		Vector list= new Vector();
		list.clear();
		if (dir != null) {
			// 处理上级目录
			//if (!dir.getAbsolutePath().equals(rootDir.getAbsolutePath())) {
			//	FileItem map = new FileItem();
			//	map.setF( dir.getParentFile());
			//	map.setName( dir.getParentFile().getName());
				//map.put("img", R.drawable.folder);
			//	list.add(map);
			//}
			FileItem pare = new FileItem();
			pare.setF(dir.getParentFile());
			pare.setName("上一目录");
			//map.put("img", f.isDirectory() ? R.drawable.folder
			//		: (f.getName().toLowerCase().endsWith(".zip") ? R.drawable.zip : R.drawable.text));
			list.add(pare);
			//currentDir.setText(dir.getAbsolutePath());显示当前目录路径
			File[] files = dir.listFiles();
			if (files != null) {
				sortFiles(files); //排序
				for (File f : files) {
					if(isAcceptFile(f, filter)){
						//过滤文件类型
					FileItem map = new FileItem();
					map.setF(f);
					map.setName(f.getName());
					//map.put("img", f.isDirectory() ? R.drawable.folder
					//		: (f.getName().toLowerCase().endsWith(".zip") ? R.drawable.zip : R.drawable.text));
					list.add(map);
					}
				}
			}
			
		} else {
			//Toast.makeText(this, "目录不正确，请输入正确的目录!", Toast.LENGTH_LONG).show();
			
			FileItem pare = new FileItem();
			 File rootDir;
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				rootDir = Environment.getExternalStorageDirectory();
			} else {
				rootDir = Environment.getRootDirectory();
			}
			pare.setF(rootDir);
			pare.setName("返回根目录");
			//map.put("img", f.isDirectory() ? R.drawable.folder
			//		: (f.getName().toLowerCase().endsWith(".zip") ? R.drawable.zip : R.drawable.text));
			list.add(pare);
		
		}
		//ListAdapter adapter = new SimpleAdapter(this, list, R.layout.item, new String[] { "name", "img" }, new int[] {
		//		R.id.name, R.id.icon });
		// listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		//listView.setAdapter(adapter);
		return list;
	}
	
	/**
	 * 过滤文件类型，如果filter为Null则全部要
	 * @param f
	 * @param filter
	 * @return
	 */
	private static boolean isAcceptFile(File f ,FilenameFilter filter){
		if(filter==null)
			return true;
		return filter.accept(f, f.getName());
	}
	
     /**
      *判断是文件还是文件夹
      * @return
      */
	public static  boolean isFile(File dir){
		return !dir.isDirectory();
	}
	
	/**
	 * 排序文件列表
	 * */
	private static  void sortFiles(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File file1, File file2) {
				if (file1.isDirectory() && file2.isDirectory())
					return 1;
				if (file2.isDirectory())
					return 1;
				return -1;
			}
		});
	}

	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	private static void openFile(File file) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(Uri.fromFile(file), type);
		// 跳转
		//startActivity(intent);

	}
	
	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME.MIME_MapTable.length; i++) {
			if (end.equals(MIME.MIME_MapTable[i][0]))
				type = MIME.MIME_MapTable[i][1];
		}
		return type;
	}

	/**
	 * 获取文件/文件夹大小
	 * */
	private static long getLength(File file) {
		long totaLength=0;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				totaLength = totaLength + getLength(file2);
			}
		} else {
			totaLength = totaLength + file.length();
		}
		return totaLength;
	}

	/**
	 * 复制功能，startFilePath为文件源路径，desFilePath为目的路径
	 * */
	public  boolean copy(String startFilePath, String desFilePath) {
		long currentLen = 0;
		long totaLength = 0;

		// 判断是否返回成功的变量
		boolean copyFinished = false;

		File startFile = new File(startFilePath);
		File desFile = new File(desFilePath);

		// 如果源文件是个文件
		if (startFile.isFile()) {

			copyFinished = this.copySingleFile(startFile, desFile);

			// 如果源文件是个文件夹，就需要递归复制
		} else {

			// 如果目标文件夹是源文件夹的一个子目录的情况，拒绝复制，因为会造成无限循环
			if (desFilePath.startsWith(startFilePath)) {
				System.out.println("error copy");
				return false;
			} else
				copyFinished = this.copyFolder(startFile, desFile,startFilePath,desFilePath);
		}
		return copyFinished;
	}

	/**
	 * 复制单个文件，如果复制多个文件可以递归调用
	 */
	private boolean copySingleFile(File startSingleFile, File desSingleFile) {

		boolean rightCopy = false;

		// -------从源文件中输入内存入byte中，在将byte写入目标文件--------------------
		FileInputStream singleFileInputStream = null;
		DataInputStream singleDataInputStream = null;
		FileOutputStream singleFileOutputStream = null;
		DataOutputStream singleDataOutputStream = null;

		try {

			singleFileInputStream = new FileInputStream(startSingleFile);

			singleDataInputStream = new DataInputStream(new BufferedInputStream(singleFileInputStream));

			singleFileOutputStream = new FileOutputStream(desSingleFile);

			singleDataOutputStream = new DataOutputStream(new BufferedOutputStream(singleFileOutputStream));

			byte[] b = new byte[1024];
			long currentLen=0;
			int len = -1;
			while ((len = singleDataInputStream.read(b)) != -1) {
				currentLen = currentLen + len;
				singleDataOutputStream.write(b, 0, len);
				//progressDialog.setProgress(currentLen / (1024));进度条
			}
			// 刷新缓冲区
			singleDataOutputStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (singleDataInputStream != null)
					singleDataInputStream.close();
				if (singleDataOutputStream != null)
					singleDataOutputStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 判断源文件和目标文件大小是否相同，如果相同证明复制成功
		if (startSingleFile.length() == desSingleFile.length())
			rightCopy = true;
		else
			rightCopy = false;
		return rightCopy;

	}

	/**
	 * 递归复制文件夹，因为文件夹下不止一个文件，里面可能有文件或文件夹， 因此需要调用递归方法
	 * 
	 * @param startFolder
	 *            = 需要复制的文件夹
	 * @param desFolder
	 *            = 复制目的地的文件夹
	 * @return = true 表示成功，false 表示失败
	 */

	public boolean copyFolder(File startFolder, File desFolder,String startFilePath, String desFilePath) {

		boolean rightCopy = false;

		rightCopy = this.recursionCopy(startFolder, desFolder,startFilePath,desFilePath);

		return rightCopy;
	}

	/**
	 * 复制文件夹函数，此函数是个递归，会复制文件夹下的所有文件
	 * 
	 * @param recFileFolder
	 *            = 需要拷贝的文件夹或子文件夹
	 * @param recDesFolder
	 *            = 拷贝的目的文件夹或子文件夹，
	 * @return = true表示成功， false表示失败
	 */
	private boolean recursionCopy(File recFileFolder, File recDesFolder,String startFilePath, String desFilePath) {

		File desFolder = recDesFolder;

		// 因为目的文件夹或子文件夹不存在，需要创建
		desFolder.mkdir();

		// 此为需要拷贝的文件夹下的所有文件列表（其中有文件和文件夹）
		File[] files = recFileFolder.listFiles();

		// 如果是个空文件夹
		if (files.length == 0)
			return true;

		/*
		 * 将文件夹下所有文件放入for循环，如果是文件，那么调用copySingleFile()拷贝文件， 如果是文件夹，那么递归调用此函数
		 */
		for (File thisFile : files) {

			// 如果此文件是个文件，那么直接调用单个文件复制命令复制文件
			if (thisFile.isFile()) {
				// 得到此文件的新位置地址
				String desContentFilePath = this.getSubFilePath(startFilePath, desFilePath, thisFile.getAbsolutePath());

				boolean rightCopy = this.copySingleFile(thisFile, new File(desContentFilePath));

				// 如果复制失败，就跳出循环停止复制
				if (!rightCopy)
					return false;

				// 如果是个文件夹
			} else {

				/*
				 * 此函数是为了得到目的文件夹的地址， 如：源文件夹为：D:/yingzi/text (其中text文件夹下有另一个文件夹
				 * second : D:/yingzi/text/second) 目标位置为：E:/aa/text
				 * 那么此second文件夹在目标地址的位置就是 E:/aa/text/second
				 */
				String desContentFilePath = this.getSubFilePath(startFilePath, desFilePath, thisFile.getAbsolutePath());
				// 递归的调用此函数，确保函数都被复制完全
				boolean rightCopy = recursionCopy(thisFile, new File(desContentFilePath),startFilePath,desFilePath);
				if (!rightCopy)
					return false;
			}

		}
		return true;
	}

	/**
	 * 此函数是为了得到目的文件夹的地址， 如：源文件夹为：D:/yingzi/text (其中text文件夹下有另一个文件夹 second :
	 * D:/yingzi/text/second) 目标位置为：E:/aa/text 那么此second文件夹在目标地址的位置就是
	 * E:/aa/text/second 此方法中 startFolderPath = D:/yingzi/text (源文件夹) ；
	 * desFolderPath = E:/aa/text (目标位置)； currentFilePath =
	 * D:/yingzi/text/second(需要复制的子文件夹) 返回值为： E:/aa/text/second
	 */
	private String getSubFilePath(String startFolderPath, String desFolderPath, String currentFilePath) {

		String currentDesFilePath = null;

		int i = startFolderPath.length();

		// int j = desFolderPath.lastIndexOf("/");

		// String subDirPath = startFolderPath.substring(0, i);
		// String subDesPath = desFolderPath.substring(0, j);

		currentDesFilePath = desFolderPath + "/" + currentFilePath.substring(i + 1);

		return currentDesFilePath;

	}


}
