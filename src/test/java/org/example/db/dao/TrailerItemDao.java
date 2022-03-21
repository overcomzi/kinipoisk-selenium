package org.example.db.dao;

import org.example.models.newTrailers.TrailerItem;

import java.util.List;

public interface TrailerItemDao {
    TrailerItem getTrailer(long id);
    boolean insertTrailer(TrailerItem trailerItem);
    boolean executeTransaction(Runnable runnable);
    boolean insertTrailers(List<TrailerItem> trailers);
}
