package com.application.graphql.netflix.model.enums;

public enum BookTypeEnum {

    FICTION,
    NON_FICTION,
    SCIENCE,
    HISTORY,
    BIOGRAPHY,
    CHILDREN,
    OTHER ;

    public static BookTypeEnum getBookType(String bookType) {
        if (bookType == null || bookType.trim().isEmpty()) {
            return OTHER;
        }
        // 去除首尾空格，并转大写
        String upper = bookType.trim().toUpperCase();
        for (BookTypeEnum type : BookTypeEnum.values()) {
            if (type.name().equalsIgnoreCase(upper)) {
                return type;
            }
        }
        // 特殊处理：将 "NONFICTION" 或 "NON-FICTION" 映射到 NON_FICTION
        if (upper.replace("-", "").equals("NONFICTION")) {
            return NON_FICTION;
        }
        // 都没有匹配，返回 OTHER
        return OTHER;
    }

}
