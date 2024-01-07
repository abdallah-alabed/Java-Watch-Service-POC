package com.JBS.IIAB_POC_Folder_Listen.DAO;

import com.JBS.IIAB_POC_Folder_Listen.Model.SwiftTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcImpl implements JdbcRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTransaction(SwiftTransaction swift) {
        String query = "INSERT INTO ABDALLAH.SWIFT_POC (TRANS_ID, FILEDATA) VALUES (?, ?)";
        try{
            jdbcTemplate.update(query,swift.getSwiftId(),swift.getFileData());
        }catch (DataAccessException e){
            throw new DataAccessException(e.getMessage()){};
        }
    }
}
