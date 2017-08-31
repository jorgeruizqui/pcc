package es.jrq.pcc.core.dao.map;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import es.jrq.pcc.core.dao.IEpisodeDAO;
import es.jrq.pcc.core.model.Episode;

/**
 * Implementation of Episode DAO based in a memory MAP, providing concurrent
 * access to Map elements.
 *
 * @author JRQ
 *
 */
public class MapEpisodeDAO implements IEpisodeDAO {

    /** Map of episode. */
    private Map<String, Episode> episodes;

    /**
     * Initialization of maps. Map is Concurrent to make sure different threads
     * can interact with the DAO service without having concurrent modification
     * exceptions.
     */
    public MapEpisodeDAO() {
        this.episodes = new ConcurrentHashMap<String, Episode>();
    }

    @Override
    public List<Episode> getAllEpisodes() {
        return episodes.values().stream().collect(Collectors.toList());
    }

    @Override
    public Episode getEpisodeById(String episodeId) {
        return episodes.get(episodeId);
    }

    @Override
    public void saveEpisode(Episode episode) {
        episodes.put(episode.getEpisodeId(), episode);
    }

    @Override
    public void removeEpisodeById(String episodeId) {
        episodes.remove(episodeId);
    }
}
