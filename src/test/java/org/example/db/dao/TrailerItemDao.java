package org.example.db.dao;

import org.example.models.newTrailers.TrailerItem;

import java.util.List;
import java.util.Set;

public interface TrailerItemDao {
    TrailerItem getTrailer(long id);
    Set<TrailerItem> getAllTrailers();
    boolean insertTrailer(TrailerItem trailerItem);
    boolean updateTrailer();
    boolean deleteTrailer();
    boolean executeTransaction(Runnable runnable);
    boolean insertTrailers(List<TrailerItem> trailers);
}
