package org.example.services;

import org.example.db.dao.TrailerItemDao;
import org.example.db.dao.TrailerItemImpl;
import org.example.models.newTrailers.TrailerItem;

import java.util.List;

public class UnitInformService {
    private TrailerItemDao trailerItemDao = new TrailerItemImpl();

    public void publishTrailerInfo(List<TrailerItem> trailerItems) {
        trailerItemDao.insertTrailers(
               trailerItems
        );
    }
}
