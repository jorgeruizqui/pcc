package es.jrq.pcc.core.dao;

import java.util.List;

import es.jrq.pcc.core.model.Studio;

/**
 * Studio Data Access Object Interface definition
 *
 * @author JRQ
 *
 */
public interface IStudioDAO {

    /**
     *
     * @return All {@link Studio} registered in the system.
     */
    List<Studio> getAllStudios();

    /**
     *
     * @param studioId Studio Identifier
     * @return {@link Studio} if found by identifier. <code>null</code> if no studio
     *         has been found for the specified identifier.
     */
    Studio getStudioById(String studioId);

    /**
     * Persists an {@link Studio}
     * @param studio Entity to be persisted
     */
    void saveStudio(Studio studio);

   /**
    * Removes the studio with the given identifier.
     * Do nothing if the episode does not exists in the system.
    * @param studioId Studio Identifier
    */
   void removeStudioById(String studioId);

   /**
    * Reset viewing counters to zero for all studios present in the system.
    */
   void resetCounters();

   /**
    * Registers a new viewing on the system for the specified studio.
    * Do nothing if studio is not present in the system
    * @param studioId Studio Identifier
    */
   void registerViewing(String studioId);

}
