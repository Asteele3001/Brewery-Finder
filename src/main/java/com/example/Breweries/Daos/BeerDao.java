package com.example.Breweries.Daos;

import com.example.Breweries.Exceptions.DaoException;
import com.example.Breweries.Models.Beer;
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
public class BeerDao {
    private JdbcTemplate jdbcTemplate;

    public BeerDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Beer> getBeers() {
        List<Beer> beers = new ArrayList<>();
        String sql = "SELECT * from beer;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                beers.add(mapRowToSet(results));
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return beers;
    }

    public Beer getBeerById(int id) {
        Beer beer = null;
        String sql = "SELECT * from beer WHERE beer_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                beer = mapRowToSet(result);
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return beer;
    }

    public List<Beer> getBeersByBreweryId(int id) {
        List<Beer> beers = new ArrayList<>();
        String sql = "SELECT * from beer WHERE brewery_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                beers.add(mapRowToSet(results));
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return beers;
    }

    public Beer createBeer(Beer beer) {
        int newId;
        String sql = "INSERT into beer (beer_name, style, description, brewery_id) " +
                "VALUES (?, ?, ?, ?) returning beer_id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, beer.getName(), beer.getStyle(), beer.getDescription(),
                    beer.getBrewery_id());
        } catch (
                CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getBeerById(newId);
    }

    public Beer updateBeer(Beer beer) {
        Beer updatedBeer;
        String sql = "UPDATE beer SET beer_name = ?, style = ?, description = ?, " +
                "brewery_id = ? WHERE beer_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, beer.getName(), beer.getStyle(), beer.getDescription(),
                    beer.getBrewery_id(), beer.getId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected. At least one expected");
            } else {
                updatedBeer = getBeerById(beer.getId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (
                DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedBeer;
    }

    public int deleteBeerById(int id) {
        String sql = "DELETE FROM beer WHERE beer_id = ?;";
        try {
            return jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public Beer mapRowToSet(SqlRowSet rowSet) {
        Beer beer = new Beer();
        beer.setId(rowSet.getInt("beer_id"));
        beer.setName(rowSet.getString("beer_name"));
        beer.setStyle(rowSet.getString("style"));
        beer.setDescription(rowSet.getString("description"));
        beer.setBrewery_id(rowSet.getInt("brewery_id"));
        return beer;
    }
}

