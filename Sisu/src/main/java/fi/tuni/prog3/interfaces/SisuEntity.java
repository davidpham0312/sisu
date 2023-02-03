package fi.tuni.prog3.interfaces;

import fi.tuni.prog3.model.LanguageStringTuple;

public interface SisuEntity {
    public String getId();

    public String getName();

    public String getGroupId();

    public void setId(String id);

    public void setName(LanguageStringTuple name);

    public void setGroupId(String groupId);
}
