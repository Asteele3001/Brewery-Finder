package com.example.Breweries.Daos;

import com.example.Breweries.Exceptions.DaoException;
import com.example.Breweries.Models.Brewery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class BreweryDao {
    private JdbcTemplate jdbcTemplate;

    public BreweryDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Brewery> getBreweries() {
        List<Brewery> breweries = new ArrayList<>();
        String sql = "SELECT * from brewery;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                breweries.add(mapRowToSet(results));
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return breweries;
    }

    public Brewery getBreweryById(int id) {
        Brewery brewery = null;
        String sql = "SELECT * from brewery WHERE brewery_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                brewery = mapRowToSet(result);
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return brewery;
    }

    public List<Brewery> getBreweryByCity(String city) {
        List<Brewery> breweries = new ArrayList<>();;
        String cityLike = "%" + city + "%";
        String sql = "SELECT * from brewery WHERE city ilike ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cityLike);
            while (results.next()) {
                breweries.add(mapRowToSet(results));
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return breweries;
    }

    public List<Brewery> getBreweryByState(String stateAbbreviation) {
        List<Brewery> breweries = new ArrayList<>();
        String sql = "SELECT * from brewery WHERE state_abbreviation = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);
            while (results.next()) {
                breweries.add(mapRowToSet(results));
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return breweries;
    }

    public List<Brewery> getBreweryByZipCode(String zipCode) {
        List<Brewery> breweries = new ArrayList<>();;
        String sql = "SELECT * from brewery WHERE zip_code = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, zipCode);
            while (results.next()) {
                breweries.add(mapRowToSet(results));
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return breweries;
    }

    public Brewery createBrewery(Brewery brewery) {
        int newId;
        String sql = "INSERT into brewery (brewery_name, street_address, city, state_abbreviation, zip_code, phone_number) " +
                "VALUES (?, ?, ?, ?, ?, ?) returning brewery_id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, brewery.getBreweryName(), brewery.getStreetAddress(), brewery.getCity(),
                    brewery.getStateAbbreviation(), brewery.getZipCode(), brewery.getPhoneNumber());
        } catch (
                CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getBreweryById(newId);
    }

    public Brewery updateBrewery(Brewery brewery) {
        Brewery updatedBrewery;
        String sql = "UPDATE brewery SET brewery_name = ?, street_address = ?, city = ?, state_abbreviation = ?, " +
                "zip_code = ?, phone_number = ? WHERE brewery_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, brewery.getBreweryName(), brewery.getStreetAddress(), brewery.getCity(),
                    brewery.getStateAbbreviation(), brewery.getZipCode(), brewery.getPhoneNumber(), brewery.getId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected. At least one expected");
            } else {
                updatedBrewery = getBreweryById(brewery.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedBrewery;
    }

    public int deleteBreweryById(int id) {
        String sql = "DELETE FROM beer WHERE brewery_id = ?; " +
                "DELETE FROM brewery WHERE brewery_id = ?;";
        try {
            return jdbcTemplate.update(sql, id, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public Brewery mapRowToSet(SqlRowSet rowSet) {
        Brewery brewery = new Brewery();
        brewery.setId(rowSet.getInt("brewery_id"));
        brewery.setBreweryName(rowSet.getString("brewery_name"));
        brewery.setStreetAddress(rowSet.getString("street_address"));
        brewery.setCity(rowSet.getString("city"));
        brewery.setStateAbbreviation(rowSet.getString("state_abbreviation"));
        brewery.setZipCode(rowSet.getString("zip_code"));
        brewery.setPhoneNumber(rowSet.getString("phone_number"));
        return brewery;
    }

}
