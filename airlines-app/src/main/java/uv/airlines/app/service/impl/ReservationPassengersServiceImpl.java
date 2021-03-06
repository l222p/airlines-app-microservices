package uv.airlines.app.service.impl;

import uv.airlines.app.service.ReservationPassengersService;
import uv.airlines.app.domain.ReservationPassengers;
import uv.airlines.app.repository.ReservationPassengersRepository;
import uv.airlines.app.service.dto.MonthlyProfitsDTO;
import uv.airlines.app.service.dto.PassengersPriorityDTO;
import uv.airlines.app.service.dto.ProfitFlightsDTO;
import uv.airlines.app.service.dto.ReservationPassengersDTO;
import uv.airlines.app.service.mapper.ReservationPassengersMapper;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ReservationPassengers}.
 */
@Service
@Transactional
public class ReservationPassengersServiceImpl implements ReservationPassengersService {

    private final Logger log = LoggerFactory.getLogger(ReservationPassengersServiceImpl.class);

    private final ReservationPassengersRepository reservationPassengersRepository;

    private final ReservationPassengersMapper reservationPassengersMapper;

    public ReservationPassengersServiceImpl(ReservationPassengersRepository reservationPassengersRepository,
            ReservationPassengersMapper reservationPassengersMapper) {
        this.reservationPassengersRepository = reservationPassengersRepository;
        this.reservationPassengersMapper = reservationPassengersMapper;
    }

    /**
     * Save a reservationPassengers.
     *
     * @param reservationPassengersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReservationPassengersDTO save(ReservationPassengersDTO reservationPassengersDTO) {
        log.debug("Request to save ReservationPassengers : {}", reservationPassengersDTO);
        ReservationPassengers reservationPassengers = reservationPassengersMapper.toEntity(reservationPassengersDTO);
        reservationPassengers = reservationPassengersRepository.save(reservationPassengers);
        return reservationPassengersMapper.toDto(reservationPassengers);
    }

    /**
     * Get all the reservationPassengers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReservationPassengersDTO> findAll() {
        log.debug("Request to get all ReservationPassengers");
        return reservationPassengersRepository.findAll().stream().map(reservationPassengersMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reservationPassengers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationPassengersDTO> findOne(Long id) {
        log.debug("Request to get ReservationPassengers : {}", id);
        return reservationPassengersRepository.findById(id).map(reservationPassengersMapper::toDto);
    }

    /**
     * Delete the reservationPassengers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReservationPassengers : {}", id);
        reservationPassengersRepository.deleteById(id);
    }

	@Override
	public List<PassengersPriorityDTO> findAllPassengersWithPriority() {
		return reservationPassengersRepository.getPassengerPriority(2);
	}

	@Override
	public List<ProfitFlightsDTO> getTop10ProfitsFlights() {
		Pageable top10 = PageRequest.of(0, 10);
		Date now = new Date();
		Date monthAgo = new DateTime().minusMonths(1).toDate();
		return reservationPassengersRepository.findTop10ProfitsFlights(top10, monthAgo, now);
	}

	@Override
	public List<MonthlyProfitsDTO> getMonthlyProfits() {
		Pageable months = PageRequest.of(0, 2);
		return reservationPassengersRepository.getMonthlyProfits(months);
	}
}
