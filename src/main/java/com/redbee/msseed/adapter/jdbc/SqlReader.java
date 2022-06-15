package com.redbee.msseed.adapter.jdbc;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import com.redbee.msseed.adapter.jdbc.exception.SqlReaderException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Slf4j
@UtilityClass
public class SqlReader {
    /**
     * Dado un PATH, lee y convierte el contenido a String
     *
     * @param sqlPath El path se encuentra el .sql
     * @return Un string que tiene el contenido del archivo sql
     */
    public String readSql(final String sqlPath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(sqlPath);
        StringBuilder textBuilder = new StringBuilder();
        try  {
            Reader reader = new InputStreamReader(resource.getInputStream(),
                StandardCharsets.UTF_8.name());
            textBuilder.append(FileCopyUtils.copyToString(reader));
        } catch (Exception ex) {
            log.error("Error al leer el archivo sql",ex);
            throw new SqlReaderException(ex);
        }

        return textBuilder.toString().replaceAll("\n", " ");
    }
}
