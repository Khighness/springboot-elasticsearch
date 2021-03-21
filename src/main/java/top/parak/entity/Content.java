package top.parak.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author KHighness
 * @since 2021-03-20
 */


@Data
@AllArgsConstructor
public class Content {

    private String name;

    private String img;

    private String price;

}
