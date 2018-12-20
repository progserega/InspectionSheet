package ru.drsk.progserega.inspectionsheet.entities;

public class TransformerInSlot {
    private long id;
    private int slot;
    private Transformer transformer;

    public TransformerInSlot(long id, int slot, Transformer transformer) {
        this.id = id;
        this.slot = slot;
        this.transformer = transformer;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Transformer getTransformer() {
        return transformer;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

}
