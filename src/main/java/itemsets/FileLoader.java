package itemsets;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;

public class FileLoader extends Thread {
    private static String[] types = {"_aram_scrape.json", "_jungle_scrape.json", "_lane_scrape.json", "_support_scrape.json"};

    private ChampionTask championTask;
    private String leagueDir;

    public FileLoader(ChampionTask championTask, String leagueDir) {
        this.championTask = championTask;
        this.leagueDir = leagueDir;
    }

    @Override
    public void run() {
        String champion;
        while ((champion = championTask.getNextChampion()) != null) {
            File f = new File(leagueDir + "/Config/Champions/" + champion);
            try {
                FileUtils.deleteDirectory(f);
            } catch (IOException e1) {
            }

            new File(leagueDir + "/Config/Champions/" + champion + "/Recommended/").mkdirs();

            try {
                copyFiles(champion);
            } catch (IOException e) {
                e.printStackTrace();
            }
            championTask.champCompleted();
        }
    }

    private void copyFiles(String champion) throws IOException {
        for (String type : types) {
            File f = new File(leagueDir + "/Config/Champions/" + champion + "/Recommended/" + champion + type);
            f.createNewFile();

            URL url = new URL("http://www.lolflavor.com/champions/" + champion + "/Recommended/" + champion + type);

            try {
                FileUtils.copyURLToFile(url, f);
            } catch (FileNotFoundException e) {
            } catch (ConnectException e) {
                e.printStackTrace();
            }
        }
    }
}
