package itemsets;

import javafx.concurrent.Service;

public class ChampionService extends Service<Void> {
    private String leagueDir;

    public ChampionService() {

    }

    @Override
    protected ChampionTask createTask() {
        ChampionTask task = new ChampionTask();
        task.setLeagueDir(leagueDir);
        return task;
    }

    public void setLeagueDir(String leagueDir) {
        this.leagueDir = leagueDir;
    }
}
