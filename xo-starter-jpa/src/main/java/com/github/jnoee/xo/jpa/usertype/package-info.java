@TypeDefs(value = {@TypeDef(name = "Json", typeClass = JsonUserType.class),
    @TypeDef(name = "JsonList", typeClass = JsonListUserType.class),
    @TypeDef(name = "IdEntityList", typeClass = IdEntityListUserType.class),
    @TypeDef(name = "UuidEntityList", typeClass = UuidEntityListUserType.class)})
package com.github.jnoee.xo.jpa.usertype;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

