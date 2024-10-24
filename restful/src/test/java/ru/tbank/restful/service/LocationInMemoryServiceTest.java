package ru.tbank.restful.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tbank.restful.entity.Location;
import ru.tbank.restful.repository.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationInMemoryServiceTest {

    @InjectMocks
    private LocationInMemoryService locationService;

    @Mock
    private Repository<Location> locationRepository;

    @Test
    public void getAllLocations_ReturnAllLocations() {
        Location location1 = new Location();
        location1.setId(1L);
        location1.setName("qwe");

        Location location2 = new Location();
        location2.setId(2L);
        location2.setName("asd");

        Location resultLocation1 = new Location();
        resultLocation1.setId(1L);
        resultLocation1.setName("qwe");

        Location resultLocation2 = new Location();
        resultLocation2.setId(2L);
        resultLocation2.setName("asd");

        when(locationRepository.findAll()).thenReturn(List.of(location1, location2));

        List<Location> result = locationService.getAllLocations();

        verify(locationRepository).findAll();
        assertEquals(List.of(resultLocation1, resultLocation2), result);
    }

    @Test
    public void getLocationBy_ReturnLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        Location resultLocation = new Location();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(locationRepository.find(eq(location.getId()))).thenReturn(Optional.of(location));

        Location result = locationService.getLocationBy(location.getId());

        verify(locationRepository).find(idCaptor.capture());
        assertEquals(location.getId(), idCaptor.getValue());
        assertEquals(resultLocation, result);
    }

    @Test
    public void getLocationBy_ThrowsNoSuchElementException_IfElementNotExist() {
        Long id = 1L;

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(locationRepository.find(eq(id))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> locationService.getLocationBy(1L));
        verify(locationRepository).find(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
    }

    @Test
    public void saveLocation_SaveLocationAndReturnSavedLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        Location resultLocation = new Location();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location result = locationService.saveLocation(location);

        verify(locationRepository).save(locationCaptor.capture());
        assertEquals(location, locationCaptor.getValue());
        assertEquals(resultLocation, result);
    }

    @Test
    public void updateLocation_UpdateLocationAndReturnUpdatedLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        Location resultLocation = new Location();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(locationRepository.update(eq(location.getId()), any(Location.class))).thenReturn(location);

        Location result = locationService.updateLocation(location.getId(), location);

        verify(locationRepository).update(idCaptor.capture(), locationCaptor.capture());
        assertEquals(location, locationCaptor.getValue());
        assertEquals(location.getId(), idCaptor.getValue());
        assertEquals(resultLocation, result);
    }

    @Test
    public void updateLocation_ThrowsNoSuchElementException_IfElementNotExist() {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(locationRepository.update(eq(location.getId()), any(Location.class))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> locationService.updateLocation(location.getId(), location));
        verify(locationRepository).update(idCaptor.capture(), locationCaptor.capture());
        assertEquals(location.getId(), idCaptor.getValue());
        assertEquals(location, locationCaptor.getValue());
    }

    @Test
    public void deleteLocationBy_DeleteLocationAndReturnDeletedLocation() {
        Location location = new Location();
        location.setId(1L);
        location.setName("qwe");

        Location resultLocation = new Location();
        resultLocation.setId(1L);
        resultLocation.setName("qwe");

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(locationRepository.delete(eq(location.getId()))).thenReturn(location);

        Location result = locationService.deleteLocationBy(location.getId());

        verify(locationRepository).delete(idCaptor.capture());
        assertEquals(location.getId(), idCaptor.getValue());
        assertEquals(resultLocation, result);
    }

    @Test
    public void deleteLocationBy_ReturnNull_IfElementNotExist() {
        Long id = 1L;

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        when(locationRepository.delete(eq(id))).thenReturn(null);

        Location result = locationService.deleteLocationBy(id);

        verify(locationRepository).delete(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
        assertNull(result);
    }
}