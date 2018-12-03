package ru.drsk.progserega.inspectionsheet.activities;

public interface IProgressListener {
    void progressUpdate(int progress);
    void progressComplete();
}
