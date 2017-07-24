package com.dwbook.phonebook.dao.mappers;

import com.dwbook.phonebook.representations.Contact;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements ResultSetMapper<Contact> {

    @Override
    public Contact map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Contact(resultSet.getInt("id"), resultSet.getString("firstName")
                            , resultSet.getString("lastName"), resultSet.getString("phone"));
    }
}
