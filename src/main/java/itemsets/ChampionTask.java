package itemsets;

import constant.Region;
import dto.Static.Champion;
import dto.Static.ChampionList;
import javafx.concurrent.Task;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChampionTask extends Task<Void> {
    private RiotApi api;
    private String leagueDir;
    private ConcurrentLinkedQueue<String> champions;
    private int completed;
    private int total;

    public ChampionTask() {
        api = new RiotApi("5f6eb637-558e-4415-8917-94066b6ed087", Region.EUW);
        leagueDir = null;
        completed = 0;
        updateProgress(0, 1);
        getChampions();
    }

    private void getChampions() {
        ChampionList championList = null;
        try {
            championList = api.getDataChampionList();
        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        Map<String, Champion> map = championList.getData();
        Iterator it = map.entrySet().iterator();
        ConcurrentLinkedQueue<String> champions = new ConcurrentLinkedQueue<String>();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            champions.add((String) pair.getKey());
            it.remove();
        }

        this.champions = champions;
        this.total = this.champions.size();
    }

    public void processChampions() {
        if (leagueDir == null) return;

        for (int i = 0; i < 10; i++) {
            new FileLoader(this, leagueDir).start();
        }
    }

    public String getNextChampion() {
        synchronized (champions) {
            return champions.poll();
        }
    }

    public void champCompleted() {
        completed++;
        updateProgress(completed, total);
    }

    public void setLeagueDir(String leagueDir) {
        this.leagueDir = leagueDir;
    }

    @Override
    protected Void call() throws Exception {
        processChampions();
        return null;
    }
}
