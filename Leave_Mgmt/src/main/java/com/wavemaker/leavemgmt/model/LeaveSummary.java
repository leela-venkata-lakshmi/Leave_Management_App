package com.wavemaker.leavemgmt.model;

public class LeaveSummary {
    private int sickAllocated;
    private int sickUtilized;
    private int sickAvailable;

    public int getSickAllocated() {
        return sickAllocated;
    }

    public void setSickAllocated(int sickAllocated) {
        this.sickAllocated = sickAllocated;
    }

    public int getSickUtilized() {
        return sickUtilized;
    }

    public void setSickUtilized(int sickUtilized) {
        this.sickUtilized = sickUtilized;
    }

    public int getSickAvailable() {
        return sickAvailable;
    }

    public void setSickAvailable(int sickAvailable) {
        this.sickAvailable = sickAvailable;
    }

    public int getPtoAllocated() {
        return ptoAllocated;
    }

    public void setPtoAllocated(int ptoAllocated) {
        this.ptoAllocated = ptoAllocated;
    }

    public int getPtoUtilized() {
        return ptoUtilized;
    }

    public void setPtoUtilized(int ptoUtilized) {
        this.ptoUtilized = ptoUtilized;
    }

    public int getPtoAvailable() {
        return ptoAvailable;
    }

    public void setPtoAvailable(int ptoAvailable) {
        this.ptoAvailable = ptoAvailable;
    }

    public int getMaternityAllocated() {
        return maternityAllocated;
    }

    public void setMaternityAllocated(int maternityAllocated) {
        this.maternityAllocated = maternityAllocated;
    }

    public int getMaternityUtilized() {
        return maternityUtilized;
    }

    public void setMaternityUtilized(int maternityUtilized) {
        this.maternityUtilized = maternityUtilized;
    }

    public int getMaternityAvailable() {
        return maternityAvailable;
    }

    public void setMaternityAvailable(int maternityAvailable) {
        this.maternityAvailable = maternityAvailable;
    }

    public int getPaternityAllocated() {
        return paternityAllocated;
    }

    public void setPaternityAllocated(int paternityAllocated) {
        this.paternityAllocated = paternityAllocated;
    }

    public int getPaternityUtilized() {
        return paternityUtilized;
    }

    public void setPaternityUtilized(int paternityUtilized) {
        this.paternityUtilized = paternityUtilized;
    }

    public int getPaternityAvailable() {
        return paternityAvailable;
    }

    public void setPaternityAvailable(int paternityAvailable) {
        this.paternityAvailable = paternityAvailable;
    }

    private int ptoAllocated;
    private int ptoUtilized;
    private int ptoAvailable;
    private int maternityAllocated;
    private int maternityUtilized;
    private int maternityAvailable;
    private int paternityAllocated;
    private int paternityUtilized;
    private int paternityAvailable;
}
