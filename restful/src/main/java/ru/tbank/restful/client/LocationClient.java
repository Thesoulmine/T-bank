package ru.tbank.restful.client;

import ru.tbank.restful.entity.Location;

import java.util.List;

public interface LocationClient {

    List<Location> getAllLocations();
}
