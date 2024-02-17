package com.jiho.anniehands.domain.admin.product;

import com.jiho.anniehands.domain.image.Image;
import com.jiho.anniehands.domain.productoption.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminProductJdbcRepository {
    // jpa를 사용하며 bulk insert를 구현하기 위한 NamedParameterJdbcTemplate
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void bulkInsertImages(List<Image> images) {
        String sql = "INSERT INTO image (product_no, server_name, origin_name, file_size) VALUES (:productNo, :serverName, :originName, :fileSize)";
        jdbcTemplate.batchUpdate(sql, images.stream()
                .map(image -> new MapSqlParameterSource()
                        .addValue("productNo", image.getProduct().getNo())
                        .addValue("serverName", image.getServerName())
                        .addValue("originName", image.getOriginName())
                        .addValue("fileSize", image.getFileSize()))
                .toArray(SqlParameterSource[]::new));
    }

    public void bulkInsertProductOptions(List<ProductOption> productOptions) {
        String sql = "INSERT INTO product_option (product_no, option_no) VALUES (:productNo, :optionNo)";
        jdbcTemplate.batchUpdate(sql, productOptions.stream()
                .map(option -> new MapSqlParameterSource()
                        .addValue("productNo", option.getProduct().getNo())
                        .addValue("optionNo", option.getOption().getNo())).toArray(SqlParameterSource[]::new));
    }
}
