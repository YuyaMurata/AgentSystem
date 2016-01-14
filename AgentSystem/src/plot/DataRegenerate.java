/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rda.data.fileout.OutputData;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class DataRegenerate implements SetProperty{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String folderName = "user10";
        if(args.length == 1)
            folderName = args[0];
        
        String path = LOG_DIR+folderName;
        HashMap<String, File> map = getFileList(path);
        setAgentMap(map);
        
        //CSVWrite
        map.put("gnuplotcsv", new File(path+"/sysdata_gnuplot.csv"));
        CSVWriter csv = new CSVWriter(new OutputStreamWriter(new FileOutputStream(map.get("gnuplotcsv"))), ',', CSVWriter.NO_QUOTE_CHARACTER);
        csvWritePlotData(map, csv);
        csv.flush();
        
        OutputData out1 = new OutputData(map.get("current")+"/splot_script.plt");
        createScriptSplot(map, out1);
        out1.close();
        
        OutputData out2 = new OutputData(map.get("current")+"/multiplot_script.plt");
        createScriptMultiPlot(map, out2);
        out2.close();
    }
    
    //UserN folder - getfile
    public static HashMap<String, File> getFileList(String path){
        File file = new File(path);
        File[] fileList = file.listFiles();
        HashMap<String, File> map = new HashMap<>();
        String key = "";
        
        System.out.println("Path::"+file.getAbsolutePath());
        map.put("current", new File(file.getAbsolutePath()));
        for(File list : fileList){
            System.out.println(list.toString());
            
            if (list.toString().contains("Summary-ALL")) key = "summary";
            else if (list.toString().contains(LOG_MQE)) key = LOG_MQE;
            else if (list.toString().contains("System-ALL")) key = "system";
            else key = "test";
            
            map.put(key, list);
        }
        
        return map;
    }
    
    private static HashMap<String, String> agentTreeMap = new HashMap<>();
    public static void setAgentMap(HashMap map) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream((File) map.get(LOG_MQE)), "UTF-8"));    
        HashMap<String, String> agMap = new HashMap<>();
        
        //Data
        String[] line;
        while((line =reader.readNext()) != null){
            if(line.length < 1) continue;
            if(line[1].contains("field")) continue;
            
            agMap.put(line[4], line[3]);
        }
        
        agentTreeMap = agMap;
    }
    
    public static void csvWritePlotData(HashMap map, CSVWriter csv) throws FileNotFoundException, IOException{
        CSVReader reader = new CSVReader(new FileReader((File) map.get("system")));
        List<List<String>> plotList = new ArrayList<>();
        
        //reject Fields
        String[] fields = reader.readNext();
        
        //Data
        Long time = -1L;
        String line[];
        while((line = reader.readNext()) != null){
            time ++;
            for(int i=0; i < line.length; i++){
                if(!fields[i].contains("Length")) continue;
                
                List<String> plot = new ArrayList<>();
                plot.add(time.toString());
                String ag = fields[i].replace("Length", "");
                Integer no = Integer.valueOf(ag.replace("R#", ""));
                plot.add(no.toString());
                plot.add(line[i]);
                
                plotList.add(plot);
            }
            
            //Gnuplot color
            plotList.add(new ArrayList<String>());
        }
        
        //CSV Write
        //CSV Field
        csv.writeNext(new String[]{"Time","Agent No.","MQ Length"});
        //CSV a row Write
        for(List<String> plot : plotList)
            csv.writeNext(plot.toArray(new String[plot.size()]));
    }
    
    // splot script
    public static void createScriptSplot(HashMap map, OutputData out){
        //String path = map.get("current").toString();//.replace("\\", "/");
        String path = "C:/Users/悠也/Desktop/logs/user10"; //NotePC
        out.write("cd \""+path+"\";");
        
        String fname = map.get("gnuplotcsv").toString().split("/")[map.get("gnuplotcsv").toString().split("/").length-1];
        out.write("data=\""+fname+"\";");
        
        out.write("set xlabel \"Time\";");
        out.write("set ylabel \"Agent No.\";");
        out.write("set zlabel \"Length\";");
        
        //out.write("set xrange [400:600];");
        out.write("set zrange [0:1000];");
        
        out.write("set datafile separator \",\";");
        out.write("set term png size 640,480;");
        out.write("set output \"splot-png.png\";");
        
        out.write("set palette rgbformulae 22,13,-31;");
        out.write("splot data using 1:2:3 with pm3d;");
    }
    
    // multiplot script
    public static void createScriptMultiPlot(HashMap map, OutputData out) throws FileNotFoundException, IOException{
        CSVReader reader = new CSVReader(new FileReader((File) map.get("system")));
        String[] fields = reader.readNext();
        Integer layout = 0;
        for(String f : fields) if(f.contains("Length")) layout++;
        
        //Color
        HashMap<String, String> cmap = new HashMap<>();
        setColorMap(cmap);
        
        //cd replace \ - /
        //String path = map.get("current").toString();//.replace("\\", "/");
        String path = "C:/Users/悠也/Desktop/logs/user10"; //NotePC
        out.write("cd \""+path+"\";");
        String fname = map.get("system").toString().split("/")[map.get("system").toString().split("/").length-1];
        out.write("data=\""+fname+"\";");
        
        out.write("set datafile separator \",\";");
        out.write("set xdata time;");
        out.write("set yrange [0:1000];");
        out.write("set timefmt \"%Y-%m-%d %H:%M:%S\";");
        
        out.write("set ticscale 0.2;");
        
        //PNG Out
        out.write("set term png size 32000,20000;");
        out.write("set output \"multiplot-png.png\";");
        
        Integer x = (int)Math.sqrt(layout);
        Integer y = x+1;
        
        //Box
        out.write("set style fill solid;");
        
        //Multi-Plot
        out.write("set multiplot layout "+x+","+y+";");
        
        Integer cnt = 0;
        for(String f : fields){
            cnt++;
            if(f.contains("Length")){
                String agname = f.replace("Length", "");
                String ragname = searchParent(agname);
                out.write("set title \""+agname+"\";");
                out.write("plot data using 1:"+cnt+" with boxes lc rgb \""+ cmap.get(ragname) +"\" notitle;");
            }
        }
        
        out.write("unset multiplot;");
    }
    
    private static void setColorMap(HashMap map){
        map.put("R#0000", "red");
        map.put("R#0001", "green");
        map.put("R#0002", "blue");
        map.put("R#0003", "cyan");
        map.put("R#0004", "magenta");
        map.put("R#0005", "orange");
        map.put("R#0006", "purple");
        map.put("R#0007", "khaki");
        map.put("R#0008", "brown");
        map.put("R#0009", "navy");
    }
    
    private static String searchParent(String pid){
        if(agentTreeMap.get(pid) == null){
            return pid;
        }else{
            return searchParent(agentTreeMap.get(pid));
        }
    }
}
