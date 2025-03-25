package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.MeasurementBuilder;
import ro.tuc.ds2020.dtos.MeasurementDTO;
import ro.tuc.ds2020.entities.Measurement;
import ro.tuc.ds2020.repositories.MeasurementRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
    private final MeasurementRepository measurementRepository;
    private final DeviceService deviceService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, DeviceService deviceService) {
        this.measurementRepository = measurementRepository;
        this.deviceService = deviceService;
    }

    public List<MeasurementDTO> findAllMeasurements() {
        List<Measurement> measurementList = measurementRepository.findAll();
        return measurementList.stream()
                .map(MeasurementBuilder::toMeasurementDTO)
                .collect(Collectors.toList());
    }

    public MeasurementDTO findMeasurementById(UUID id) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if (!measurementOptional.isPresent()) {
            LOGGER.error("MeasurementMessageBroker with id {} was not found in db", id);
            return null; // Or throw an exception
        }
        return MeasurementBuilder.toMeasurementDTO(measurementOptional.get());
    }

    public void insert(MeasurementDTO measurementDTO) {
        Measurement measurement = MeasurementBuilder.toMeasurement(measurementDTO);
        measurement.setDevice(deviceService.findDeviceByIdObject(measurementDTO.getDeviceId()));
        measurementRepository.save(measurement);
    }

    // Assuming the MeasurementMessageBroker entity does not change its associated Device
    public MeasurementDTO update(UUID id, MeasurementDTO updatedMeasurementDTO) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if (!measurementOptional.isPresent()) {
            LOGGER.error("MeasurementMessageBroker with id {} was not found in the database", id);
            return null; // Or throw an exception
        }

        Measurement existingMeasurement = measurementOptional.get();
        Measurement updatedMeasurement = MeasurementBuilder.toMeasurement(updatedMeasurementDTO);

        // Update the fields of the existing measurement with the values from the updated measurement.
        existingMeasurement.setValue(updatedMeasurement.getValue());
        existingMeasurement.setTimestamp(updatedMeasurement.getTimestamp());

        existingMeasurement = measurementRepository.save(existingMeasurement);

        return MeasurementBuilder.toMeasurementDTO(existingMeasurement);
    }

    public void delete(UUID id) {
        Optional<Measurement> measurementOptional = measurementRepository.findById(id);
        if (!measurementOptional.isPresent()) {
            LOGGER.error("MeasurementMessageBroker with id {} was not found in the database", id);
            // You can throw an exception or return an appropriate response here.
            return;
        }

        measurementRepository.delete(measurementOptional.get());
        LOGGER.debug("MeasurementMessageBroker with id {} was deleted from the database", id);
    }
}
