package top.parak.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author KHighness
 * @since 2021-03-20
 */

@Builder
@Data
public class User {
    private String name;
    private int age;
}
