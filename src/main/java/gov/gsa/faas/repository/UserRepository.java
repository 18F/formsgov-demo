package gov.gsa.faas.repository;

import gov.gsa.faas.domain.Authority;
import gov.gsa.faas.domain.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Spring Data R2DBC repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends R2dbcRepository<User, String>, UserRepositoryInternal {

    @Query("SELECT * FROM jhi_user WHERE login = :login")
    Mono<User> findOneByLogin(String login);

    @Query("SELECT COUNT(DISTINCT id) FROM jhi_user WHERE login != :anonymousUser")
    Mono<Long> countAllByLoginNot(String anonymousUser);

    @Query("INSERT INTO jhi_user_authority VALUES(:userId, :authority)")
    Mono<Void> saveUserAuthority(String userId, String authority);

    @Query("DELETE FROM jhi_user_authority")
    Mono<Void> deleteAllUserAuthorities();
}

interface UserRepositoryInternal {

    Mono<User> findOneWithAuthoritiesByLogin(String login);

    Flux<User> findAllByLoginNot(Pageable pageable, String login);

    Mono<User> create(User user);
}

class UserRepositoryInternalImpl implements UserRepositoryInternal {
    private final DatabaseClient db;
    private final ReactiveDataAccessStrategy dataAccessStrategy;

    public UserRepositoryInternalImpl(DatabaseClient db, ReactiveDataAccessStrategy dataAccessStrategy) {
        this.db = db;
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Override
    public Mono<User> findOneWithAuthoritiesByLogin(String login) {
        return findOneWithAuthoritiesBy("login", login);
    }

    private Mono<User> findOneWithAuthoritiesBy(String fieldName, Object fieldValue) {
        return db.execute("SELECT * FROM jhi_user u LEFT JOIN jhi_user_authority ua ON u.id=ua.user_id WHERE u." + fieldName + " = :" + fieldName)
            .bind(fieldName, fieldValue)
            .map((row, metadata) ->
                Tuples.of(
                    dataAccessStrategy.getRowMapper(User.class).apply(row, metadata),
                    Optional.ofNullable(row.get("authority_name", String.class))
                )
            )
            .all()
            .collectList()
            .filter(l -> !l.isEmpty())
            .map(l -> {
                User user = l.get(0).getT1();
                user.setAuthorities(
                    l.stream()
                        .filter(t -> t.getT2().isPresent())
                        .map(t -> {
                            Authority authority = new Authority();
                            authority.setName(t.getT2().get());
                            return authority;
                        })
                        .collect(Collectors.toSet())
                );
                return user;
            });
    }

    @Override
    public Flux<User> findAllByLoginNot(Pageable pageable, String login) {
        return db.select().from(User.class)
            .matching(Criteria.where("login").not(login))
            .page(pageable)
            .as(User.class)
            .all();
    }

    @Override
    public Mono<User> create(User user) {
        return db.insert().into(User.class).using(user)
            .map(dataAccessStrategy.getConverter().populateIdIfNecessary(user))
            .first()
            .defaultIfEmpty(user);
    }

}
