package com.JBS.IIAB_POC_Folder_Listen.DAO;

import com.JBS.IIAB_POC_Folder_Listen.Model.SwiftTransaction;

public interface JdbcRepository {
    void addTransaction(SwiftTransaction swift);
}
