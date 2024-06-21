package com.example.Breweries.Controllers;

import com.example.Breweries.Daos.BeerDao;
import com.example.Breweries.Exceptions.DaoException;
import com.example.Breweries.Models.Beer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/beers")
public class BeerController {

    private BeerDao beerDao;

    public BeerController(BeerDao beerDao) {
        this.beerDao = beerDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Beer> list() {
        return beerDao.getBeers();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Beer getbyBeerId(@PathVariable int id) {
        Beer beer = beerDao.getBeerById(id);
        if (beer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found");
        } else {
            return beer;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/bybrewery/{breweryId}", method = RequestMethod.GET)
    public List<Beer> getByBreweryId(@PathVariable int breweryId) {
        List<Beer> beers = beerDao.getBeersByBreweryId(breweryId);
        if (beers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found");
        } else {
            return beers;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Beer add(@Valid @RequestBody Beer beer) {
        return beerDao.createBeer(beer);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Beer update(@Valid @RequestBody Beer beer, @PathVariable int id) {
        beer.setId(id);
        try {
            return beerDao.updateBeer(beer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        beerDao.deleteBeerById(id);
    }
}
