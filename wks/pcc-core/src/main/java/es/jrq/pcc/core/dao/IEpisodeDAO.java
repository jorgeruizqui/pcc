package es.jrq.pcc.core.dao;

import java.util.List;

import es.jrq.pcc.core.model.Episode;

/**
 * Episode Data Access Object Interface definition
 *
 * @author JRQ
 *
 */
public interface IEpisodeDAO {

    /**
     *
     * @return All {@link Episode} registered in the system
     */
    List<Episode> getAllEpisodes();

    /**
     *
     * @param episodeId Episode Identifier
     * @return {@link Episode} if found by identifier. <code>null</code> if no episode
     *         has been found for the specified identifier.
     */
    Episode getEpisodeById(String episodeId);

    /**
     * Persists an {@link Episode} in the system.
     * @param episode Entity to be persisted
     */
    void saveEpisode(Episode episode);

    /**
     * Removes the episode with the given identifier.
     * Do nothing if the episode does not exists in the system.
     * @param episodeId Episode Identifier
     */
    void removeEpisodeById(String episodeId);

}
