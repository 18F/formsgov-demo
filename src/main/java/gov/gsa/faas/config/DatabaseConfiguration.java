package gov.gsa.faas.config;

import io.github.jhipster.config.JHipsterConstants;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableR2dbcRepositories("gov.gsa.faas.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
    // LocalDateTime seems to be the only type that is supported across all drivers atm
    // See https://github.com/r2dbc/r2dbc-h2/pull/139 https://github.com/mirromutth/r2dbc-mysql/issues/105
    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory) {
        R2dbcDialect dialect = DialectResolver.getDialect(connectionFactory);
        List<Object> converters = new ArrayList<>(dialect.getConverters());
        converters.add(new InstantWriteConverter());
        converters.add(new InstantReadConverter());
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS);
        return new R2dbcCustomConversions(
            CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder(), converters),
            Collections.emptyList());
    }

    @WritingConverter
    public static class InstantWriteConverter implements Converter<Instant, LocalDateTime> {

        public LocalDateTime convert(Instant source) {
            return LocalDateTime.ofInstant(source, ZoneOffset.UTC);
        }
    }

    @ReadingConverter
    public static class InstantReadConverter implements Converter<LocalDateTime, Instant> {

        @Override
        public Instant convert(LocalDateTime localDateTime) {
            return localDateTime.toInstant(ZoneOffset.UTC);
        }
    }
}
