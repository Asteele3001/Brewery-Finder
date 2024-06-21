package com.example.Breweries.Controllers;

import com.example.Breweries.Daos.BreweryDao;
import com.example.Breweries.Exceptions.DaoException;
import com.example.Breweries.Models.Brewery;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/breweries")
public class BreweryController {

    private BreweryDao breweryDao;

    public BreweryController(BreweryDao breweryDao) {
        this.breweryDao = breweryDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Brewery> list() {
        return breweryDao.getBreweries();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Brewery getById(@PathVariable int id) {
        Brewery brewery = breweryDao.getBreweryById(id);
        if (brewery == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brewery not found");
        } else {
            return brewery;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/city", method = RequestMethod.GET)
    public List<Brewery> getByCity(@PathVariable @RequestParam(value = "city_like", required = false) String city) {
            return breweryDao.getBreweryByCity(city);
    }

    @RequestMapping(path = "/state", method = RequestMethod.GET)
    public List<Brewery> getByState(@PathVariable @RequestParam(value="stateAbb") String stateAbb) {
            return breweryDao.getBreweryByState(stateAbb);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/zipcode", method = RequestMethod.GET)
    public List<Brewery> getByZip(@PathVariable @RequestParam(value = "zipcode") String zipCode) {
        return breweryDao.getBreweryByZipCode(zipCode);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Brewery add(@Valid @RequestBody Brewery brewery) {
        return breweryDao.createBrewery(brewery);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Brewery update(@Valid @RequestBody Brewery brewery, @PathVariable int id) {
        brewery.setId(id);
        try {
            return breweryDao.updateBrewery(brewery);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brewery not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        breweryDao.deleteBreweryById(id);
    }
}
