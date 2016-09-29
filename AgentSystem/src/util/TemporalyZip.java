/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author kaeru
 */
public class TemporalyZip {
    String zipFile;
    public TemporalyZip(String inputZipFile) {
        this.zipFile = inputZipFile;
    }
    
    String tempFolder = "temp";
    public Map decompressZip() throws Exception
    {
        // zipファイルの読込
        // try-with-resource構文でファイルcloseしている
        try(    FileInputStream         fis     = new FileInputStream( zipFile );
                ZipInputStream          archive = new ZipInputStream( fis ) )
        {
            // エントリーを1つずつファイル・フォルダに復元
            ZipEntry entry   = null;
            while( ( entry = archive.getNextEntry() ) != null )
            {
                // ファイルを作成
                File    file    = new File( tempFolder + "/" + entry.getName() );
                 
                // フォルダ・エントリの場合はフォルダを作成して次へ
                if( entry.isDirectory() )
                {
                    file.mkdirs();
                    continue;
                }
 
                // ファイル出力する場合、
                // フォルダが存在しない場合は事前にフォルダ作成
                if( !file.getParentFile().exists() ){ file.getParentFile().mkdirs(); }
                 
                // ファイル出力
                try(    FileOutputStream        fos = new FileOutputStream( file ) ;
                        BufferedOutputStream    bos = new BufferedOutputStream( fos ) )
                {
                    // エントリーの中身を出力
                    int     size    = 0;
                    byte[]  buf     = new byte[ 1024 ];
                    while( ( size = archive.read( buf ) ) > 0 )
                    {
                        bos.write( buf , 0 , size );
                    }
                }
            }
        }
        
        return setFileMap(tempFolder);
    }
    
    private Map setFileMap(String rootDir){
        Map map = new HashMap();
        
        File file = new File(rootDir);
        File folder = file.listFiles()[0];
        File[] log = folder.listFiles();
        
        System.out.println("> Logs FileList");
        
        for(File key : log){
            if(!key.toString().contains(".csv")) continue;
            System.out.println(key.toString());
            map.put(key.getName(), key);
        }
        
        return map;
    }
    
    public void close(){
        delete(new File(tempFolder));
    }
    
    private void delete(File f){
        if( f.exists()==false ){
		return ;
	}

	if(f.isFile()){
		f.delete();
	}
		
	if(f.isDirectory()){
		File[] files=f.listFiles();
		for(int i=0; i<files.length; i++){
			delete( files[i] );
		}
		f.delete();
	}
    }
}
