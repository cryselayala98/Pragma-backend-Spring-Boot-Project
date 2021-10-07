package com.pragma.photo.utilities.SequenceUtils;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "sequences")
public class Sequence {

    @Id
    private String id;

    private Long value;
}
