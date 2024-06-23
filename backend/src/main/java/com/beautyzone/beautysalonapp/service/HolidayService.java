package com.beautyzone.beautysalonapp.service;

import com.beautyzone.beautysalonapp.rest.dto.HolidayRequestDto;
import com.beautyzone.beautysalonapp.rest.dto.HolidayWithEmployeeResponseDto;

import java.util.List;

public interface HolidayService {

    /**
     * Retrieves a holiday by its ID.
     *
     * @param id The ID of the holiday to retrieve.
     * @return HolidayWithEmployeeResponseDto representing the holiday.
     */
    HolidayWithEmployeeResponseDto findById(Integer id);

    /**
     * Retrieves all holidays.
     *
     * @return List of HolidayWithEmployeeResponseDto objects representing all holidays.
     */
    List<HolidayWithEmployeeResponseDto> findAll();

    /**
     * Retrieves all holidays for a specific employee.
     *
     * @param id The ID of the employee.
     * @return List of HolidayWithEmployeeResponseDto objects representing holidays for the employee.
     */
    List<HolidayWithEmployeeResponseDto> findAllByEmployeeId(Integer id);

    /**
     * Creates a new holiday.
     *
     * @param holidayRequestDto The HolidayRequestDto containing holiday details.
     * @throws Exception if there are no available timeslots for the requested period.
     */
    void createHoliday(HolidayRequestDto holidayRequestDto) throws Exception;

    /**
     * Updates an existing holiday.
     *
     * @param holidayRequestDto The HolidayRequestDto containing updated holiday details.
     * @throws Exception if there are no available timeslots for the updated period.
     */
    void updateHoliday(HolidayRequestDto holidayRequestDto) throws Exception;

    /**
     * Deletes a holiday by its ID.
     *
     * @param id The ID of the holiday to delete.
     * @return true if the holiday was successfully deleted, false otherwise.
     */
    boolean deleteHolidayById(Integer id);

}