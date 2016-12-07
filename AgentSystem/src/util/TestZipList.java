package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

public class TestZipList {

    private static String filename = ".\\logsfd\\aggregate_experiment.csv";

    public static void main(String[] args) {
        String path = ".\\logsfd";
        File file = new File(path);
        File[] zipList = file.listFiles();

        for (File f : zipList) {
            System.out.println(f.toString());
        }

        File f = new File(filename);
        try {
            f.createNewFile();
        } catch (IOException ex) {
        }

        for (int i = 1; i < zipList.length+1; i++) {
            TemporalyZip zip = new TemporalyZip(zipList[i-1].toString());
            try {
                Map fileMap = zip.decompressZip();
                for (Object key : fileMap.keySet()) {
                    if (key.toString().contains("MQLength")) {
                        File tmpFile = new File(filename + ".tmp");
                        setCSVData(i, (File) fileMap.get(key), tmpFile).close();

                        (new File(filename)).renameTo(new File(filename + "_out.tmp"));
                        csvMarge(new File(filename + "_out.tmp"), tmpFile).close();

                        tmpFile.delete();
                    }
                }
            } catch (Exception ex) {
            } finally {
                zip.close();
            }
        }
    }

    private static CSVWriter setCSVData(Integer i, File in, File out) {
        try (FileOutputStream fout = new FileOutputStream(out);
                FileInputStream fin = new FileInputStream(in)) {
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));

            csv.writeNext(new String[]{i.toString()});

            String fline[], dline[];
            while ((fline = data.readNext()) != null) {
                dline = data.readNext();
                if (!((fline.length > 3) || (dline.length > 3)) || !(fline[2].contains("Transaction"))) {
                    continue;
                }

                csv.writeNext(new String[]{dline[dline.length - 1]});
                csv.flush();
            }

            return csv;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static CSVWriter csvMarge(File in1, File in2) {
        File out = new File(filename);

        try (FileOutputStream fout = new FileOutputStream(out);
                FileInputStream fin1 = new FileInputStream(in1);
                FileInputStream fin2 = new FileInputStream(in2)) {

            CSVReader data1 = new CSVReader(new InputStreamReader(fin1));
            CSVReader data2 = new CSVReader(new InputStreamReader(fin2));

            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));

            String line1[], line2[];

            while ((line2 = data2.readNext()) != null) {
                line1 = data1.readNext();

                String data;
                if (line1 != null) {
                    data = String.join(",", line1) + "," + String.join(",", line2);
                } else {
                    data = String.join(",", line2);
                }

                csv.writeNext(data.split(","));
                csv.flush();
            }

            return csv;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            in1.delete();
        }
    }
}
