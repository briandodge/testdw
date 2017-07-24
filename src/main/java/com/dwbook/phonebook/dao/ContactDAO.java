package com.dwbook.phonebook.dao;

import com.dwbook.phonebook.dao.mappers.ContactMapper;
import com.dwbook.phonebook.representations.Contact;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface ContactDAO {

    @Mapper(ContactMapper.class)
    @SqlQuery("select * from contacts where id = :id")
    Contact getContactById(@Bind("id") int id);


    @GetGeneratedKeys
    @SqlUpdate("insert into contacts (id, firstName, lastName, phone) values (NULL, :firstName, :lastName, :phone)")
    int createContact(@Bind("firstName") String firstName, @Bind("lastName") String lastName,
                      @Bind("phone") String phone);


    @SqlUpdate("update contacts set firstName = :firstName, lastName = :lastname, phone = :phone where id = :id")
    void updateContact(@Bind("id") int id, @Bind("firstName") String firstName,
                       @Bind("lastName") String lastName, @Bind("phone") String phone);

    @SqlUpdate("delete from contacts where id = :id")
    void deleteContact(@Bind("id") int id);



}
