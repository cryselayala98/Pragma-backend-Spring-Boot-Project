package com.pragma.photo.utilities.SequenceUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


/*
* Servicio encargado de la creacion de incrementables de facil identificacion para las fotos (photoNumber)
* */
@Service
public class SequenceService {

    @Autowired
    private MongoOperations mongo;

    public Long getNextValue(String sequenceId) {
        final Sequence sequence = mongo.findAndModify(
                query(where("_id").is(sequenceId)),
                new Update().inc("value",1),
                options().returnNew(true).upsert(true),
                Sequence.class);
        return sequence.getValue();
    }
}
