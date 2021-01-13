package top.weidaboy.yiqing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * city
 * @author
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityPO implements Serializable {
    private Integer id;

    private Date date;

    private String provinceshortname;

    private String cityname;

    private Integer currentconfirmedcount;

    private Integer confirmedcount;

    private Integer suspectedcount;

    private Integer curedcount;

    private Integer deadcount;

    private static final long serialVersionUID = 1L;
}
