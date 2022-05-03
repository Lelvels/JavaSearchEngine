package com.aptech.dao;

import com.aptech.models.FirstLink;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface FirstLinkDAO {
    public Optional<FirstLink> createNewSuggestLink(String link, String description);
    public Optional<FirstLink> findSuggestLinkByID(Integer ID);
    public Optional<FirstLink> findSuggestLinkByLink(String link);
    public void updateSuggestLink(FirstLink firstLink, String link, String description);
    public void deleteSuggestLink(FirstLink firstLink);
    public List<FirstLink> returnAllSuggestLink();
}
