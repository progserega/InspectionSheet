package ru.drsk.progserega.inspectionsheet.ui.interfaces;

public interface IProgressListener {
    void progressUpdate(String progress);
    void progressComplete();
    void progressError(Exception ex);
}
