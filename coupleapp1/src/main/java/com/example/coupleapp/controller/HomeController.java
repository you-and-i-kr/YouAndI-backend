package com.example.coupleapp.controller;
import com.example.coupleapp.dto.HomeDTO;
import com.example.coupleapp.service.HomeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homes")
@Api(value = "Home Controller", description = "Operations pertaining to homes")
public class HomeController {
    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a home by ID", response = HomeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved home by ID"),
            @ApiResponse(code = 404, message = "Home not found")
    })
    public HomeDTO getHome(
            @ApiParam(value = "ID of the home", required = true) @PathVariable Long id) {
        return homeService.getHomeById(id);
    }

    @PostMapping
    @ApiOperation(value = "Create a new home", response = HomeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Home created successfully"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public HomeDTO createHome(
            @ApiParam(value = "Home data", required = true) @RequestBody HomeDTO homeDTO) {
        return homeService.createHome(homeDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a home by ID", response = HomeDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated home by ID"),
            @ApiResponse(code = 404, message = "Home not found"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    public HomeDTO updateHome(
            @ApiParam(value = "ID of the home", required = true) @PathVariable Long id,
            @ApiParam(value = "Updated home data", required = true) @RequestBody HomeDTO homeDTO) {
        return homeService.updateHome(id, homeDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a home by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Home deleted successfully"),
            @ApiResponse(code = 404, message = "Home not found")
    })
    public void deleteHome(
            @ApiParam(value = "ID of the home", required = true) @PathVariable Long id) {
        homeService.deleteHome(id);
    }
}
