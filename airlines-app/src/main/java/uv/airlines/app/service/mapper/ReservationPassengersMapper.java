package uv.airlines.app.service.mapper;

import uv.airlines.app.domain.*;
import uv.airlines.app.service.dto.ReservationPassengersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReservationPassengers} and its DTO
 * {@link ReservationPassengersDTO}.
 */
@Mapper(componentModel = "spring", uses = { ReservationsMapper.class, PassengerMapper.class })
public interface ReservationPassengersMapper extends EntityMapper<ReservationPassengersDTO, ReservationPassengers> {

    @Mapping(source = "reservationId.id", target = "reservationIdId")
    @Mapping(source = "passDni.id", target = "passDniId")
    ReservationPassengersDTO toDto(ReservationPassengers reservationPassengers);

    @Mapping(source = "reservationIdId", target = "reservationId")
    @Mapping(source = "passDniId", target = "passDni")
    ReservationPassengers toEntity(ReservationPassengersDTO reservationPassengersDTO);

    default ReservationPassengers fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReservationPassengers reservationPassengers = new ReservationPassengers();
        reservationPassengers.setId(id);
        return reservationPassengers;
    }
}
