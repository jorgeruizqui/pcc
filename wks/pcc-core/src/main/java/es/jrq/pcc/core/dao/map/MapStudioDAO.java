package es.jrq.pcc.core.dao.map;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import es.jrq.pcc.core.dao.IStudioDAO;
import es.jrq.pcc.core.model.Studio;

/**
 * Implementation of Studio DAO based in a memory MAP, providing concurrent
 * access to Map elements.
 *
 * @author JRQ
 *
 */
public class MapStudioDAO implements IStudioDAO {

    /** Map of studios. */
    private Map<String, Studio> studios;

    /**
     * Initialization of map. Map is Concurrent to make sure different threads
     * can interact with the DAO service without having concurrent modification
     * exceptions.
     */
    public MapStudioDAO() {
        this.studios = new ConcurrentHashMap<String, Studio>();
    }

    @Override
    public List<Studio> getAllStudios() {
        return studios.values().stream().collect(Collectors.toList());
    }

    @Override
    public Studio getStudioById(String studioId) {
        return studios.get(studioId);
    }

    @Override
    public void saveStudio(Studio studio) {
        studios.put(studio.getId(), studio);
    }

    @Override
    public void removeStudioById(String studioId) {
        studios.remove(studioId);
    }

    @Override
    public void resetCounters() {
        getAllStudios().forEach(new Consumer<Studio>() {
            @Override
            public void accept(Studio t) {
                t.setViewings(BigInteger.ZERO);
            }
        });
    }

    @Override
    public void registerViewing(String studioId) {
        Studio studio = getStudioById(studioId);
        if (studio != null) {
            studio.registerViewing();
        }
    }
}
