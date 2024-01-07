package com.JBS.IIAB_POC_Folder_Listen.Model;

/**
 * This Object Represents the Database Table I append to
 */
public class SwiftTransaction {
    private String swiftId;
    private String fileData;
    public SwiftTransaction() {
    }
    public SwiftTransaction(String swiftId, String fileData) {
        this.swiftId = swiftId;
        this.fileData = fileData;
    }
    public String getSwiftId() {
        return swiftId;
    }
    public void setSwiftId(String swiftId) {
        swiftId = swiftId;
    }
    public String getFileData() {
        return fileData;
    }
    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}
