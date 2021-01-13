package top.weidaboy.yiqing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * province
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvincePO implements Serializable {
    private Integer id;

    private Date date;

    private String provinceshortname;

    private Integer currentconfirmedcount;

    private Integer confirmedcount;

    private Integer suspectedcount;

    private Integer curedcount;

    private Integer deadcount;

    private static final long serialVersionUID = 1L;
}
