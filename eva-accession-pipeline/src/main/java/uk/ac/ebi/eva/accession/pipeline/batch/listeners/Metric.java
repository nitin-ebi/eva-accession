package uk.ac.ebi.eva.accession.pipeline.batch.listeners;

public enum Metric {
    SUBMITTED_VARIANTS("submitted_variants", "Number of variants submitted for accessioning"),
    DISCARDED_VARIANTS("discarded_variants", "Number of variants discarded while accessioning");

    private String name;
    private String def;

    Metric(String name, String def) {
        this.name = name;
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public String getDef() {
        return def;
    }

    @Override
    public String toString() {
        return name;
    }
}
