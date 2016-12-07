package util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileInput {
    private static final String path = ".\\logs";
    private static final File newfile = new File(path+"\\results");
    private static final FileInput fileinput = new FileInput();
    private Map<String, File> map = new HashMap<>();
    
    private FileInput(){
        if (newfile.mkdir()){
            System.out.println("ディレクトリの作成に成功しました");
        }else{
            System.out.println("ディレクトリの作成に失敗しました");
        }
        getFileMap();
    }
    
    public static FileInput getInstance(){
        return fileinput;
    }
    
    private void getFileMap(){
        File file = new File(path);
        File folder = file.listFiles()[0];
        File[] log = folder.listFiles();
        
        System.out.println("> Logs FileList");
        
        for(File key : log){
            if(!key.toString().contains(".csv")) continue;
            System.out.println(key.toString());
            map.put(key.getName(), key);
        }
    }
    
    public File getFile(String filename){
        String csvname = filename+".csv";
        return map.get(csvname);
    }
}