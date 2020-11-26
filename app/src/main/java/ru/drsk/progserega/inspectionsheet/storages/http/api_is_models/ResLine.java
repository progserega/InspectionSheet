package ru.drsk.progserega.inspectionsheet.storages.http.api_is_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResLine {
    /**
    id: 2,
    res_id: 1,
    line_uniq_id: 4169,
    version: 2
     */

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("res_id")
    @Expose
    private long resId;

    @SerializedName("line_uniq_id")
    @Expose
    private long lineUid;

    @SerializedName("version")
    @Expose
    private int version;

    public ResLine(long id, long resId, long lineUid, int version) {
        this.id = id;
        this.resId = resId;
        this.lineUid = lineUid;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public long getLineUid() {
        return lineUid;
    }

    public void setLineUid(long lineUid) {
        this.lineUid = lineUid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
