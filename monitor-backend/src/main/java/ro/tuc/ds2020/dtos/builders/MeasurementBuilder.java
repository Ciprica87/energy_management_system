package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.entities.Measurement;
import ro.tuc.ds2020.dtos.MeasurementDTO;

public class MeasurementBuilder {

    // Convert a MeasurementMessageBroker entity to a MeasurementDTO
    public static MeasurementDTO toMeasurementDTO(Measurement measurement) {
        if (measurement == null) {
            return null;
        }
        return new MeasurementDTO(
                measurement.getId(),
                measurement.getValue(),
                measurement.getTimestamp(),
                measurement.getDevice() != null ? measurement.getDevice().getId() : null
        );
    }

    // Convert a MeasurementDTO to a MeasurementMessageBroker entity
    public static Measurement toMeasurement(MeasurementDTO measurementDTO) {
        if (measurementDTO == null) {
            return null;
        }
        Measurement measurement = new Measurement();
        measurement.setId(measurementDTO.getId());
        measurement.setValue(measurementDTO.getValue());
        measurement.setTimestamp(measurementDTO.getTimestamp());

        // The Device entity should be set outside this method, as we only have the device ID in DTO
        // Typically, you would fetch the Device entity from your database based on the device ID and set it here

        return measurement;
    }
}
