package es.jrq.pcc.core.model;

/**
 * Bean class modeling Episode entity
 *
 * @author JRQ
 */
public class Episode {

    /** The episode Global Unique Identifier for the episode. */
    private String episodeId;

    /** The episode Name. */
    private String episodeName;

    /**
     * The episode Global Unique Identifier for the studio that owns the
     * episode.
     */
    private String studioId;

    /**
     * Default constructor
     */
    public Episode() {
        this.episodeId = "";
        this.studioId = "";
        this.episodeName = "";
    }

    /**
     * @param episodeId Episode Identifier
     * @param episodeName Episode Name
     * @param studioId Studio Identifier
     */
    public Episode(String episodeId, String episodeName, String studioId) {
        this.episodeId = episodeId;
        this.episodeName = episodeName;
        this.studioId = studioId;
    }

    /**
     * @return the episodeId
     */
    public String getEpisodeId() {
        return episodeId;
    }

    /**
     * @param episodeId the episodeId to set
     */
    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    /**
     * @return the studioId
     */
    public String getStudioId() {
        return studioId;
    }

    /**
     * @param studioId the studioId to set
     */
    public void setStudioId(String studioId) {
        this.studioId = studioId;
    }

    /**
     * @return the episodeName
     */
    public String getEpisodeName() {
        return episodeName;
    }

    /**
     * @param episodeName the episodeName to set
     */
    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    @Override
    public String toString() {
        return "Episode [episodeId=" + episodeId + ", episodeName=" + episodeName + ", studioId=" + studioId + "]";
    }

}
