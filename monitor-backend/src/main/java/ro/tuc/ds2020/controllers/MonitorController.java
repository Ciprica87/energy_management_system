package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MeasurementDTO;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.MeasurementService;
import ro.tuc.ds2020.services.MeasurementService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final DeviceService deviceService;
    private final MeasurementService measurementService;

    @Autowired
    public MonitorController(DeviceService deviceService, MeasurementService measurementService) {
        this.deviceService = deviceService;
        this.measurementService = measurementService;
    }

    // Get all devices
    @GetMapping("/getAllDevices")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = deviceService.findAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    // Get a device by ID
    @GetMapping("/getDevice/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable UUID id) {
        DeviceDTO deviceDTO = deviceService.findDeviceById(id);
        if (deviceDTO != null) {
            return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Create a new device
    @PostMapping("/createDevice")
    public ResponseEntity<Void> createDevice(@RequestBody DeviceDTO deviceDTO) {
        deviceService.insert(deviceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Update an existing device
    @PutMapping("/updateDevice/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO updatedDeviceDTO = deviceService.update(id, deviceDTO);
        if (updatedDeviceDTO != null) {
            return new ResponseEntity<>(updatedDeviceDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a device
    @DeleteMapping("/deleteDevice/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        deviceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all measurements
    @GetMapping("/getAllMeasurements")
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
        List<MeasurementDTO> measurements = measurementService.findAllMeasurements();
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    // Get a measurement by ID
    @GetMapping("/getMeasurements/{id}")
    public ResponseEntity<MeasurementDTO> getMeasurementById(@PathVariable UUID id) {
        MeasurementDTO measurementDTO = measurementService.findMeasurementById(id);
        if (measurementDTO != null) {
            return new ResponseEntity<>(measurementDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Create a new measurement
    @PostMapping("/createMeasurement")
    public ResponseEntity<Void> createMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        measurementService.insert(measurementDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Update an existing measurement
    @PutMapping("/updateMeasurement/{id}")
    public ResponseEntity<MeasurementDTO> updateMeasurement(@PathVariable UUID id, @RequestBody MeasurementDTO measurementDTO) {
        MeasurementDTO updatedMeasurementDTO = measurementService.update(id, measurementDTO);
        if (updatedMeasurementDTO != null) {
            return new ResponseEntity<>(updatedMeasurementDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a measurement
    @DeleteMapping("/deleteMeasurement/{id}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable UUID id) {
        measurementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
