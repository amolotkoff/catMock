package com.amolotkoff.jr;

import lombok.*;
import com.amolotkoff.util.PathBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRFile implements IJR {

    @Getter
    private PathBuilder path;

    @Getter
    private List<String> imports;

    @Getter
    private JRClass clazz;
}