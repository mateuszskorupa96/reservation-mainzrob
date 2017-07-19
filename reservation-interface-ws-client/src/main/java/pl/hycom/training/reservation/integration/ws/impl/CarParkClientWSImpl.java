package pl.hycom.training.reservation.integration.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebServiceException;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Service;

import pl.hycom.training.reservation.api.service.IReservationService;
import pl.hycom.training.reservation.exception.ParkingInvalidArgumentException;
import pl.hycom.training.reservation.exception.PlaceInvalidException;
import pl.hycom.training.reservation.exception.PlaceNotAvailableException;
import pl.hycom.training.reservation.integration.ws.api.ErrorStatus;
import pl.hycom.training.reservation.integration.ws.api.GetParkingList;
import pl.hycom.training.reservation.integration.ws.api.GetParkingListResponse;
import pl.hycom.training.reservation.integration.ws.api.ICarParkWS;
import pl.hycom.training.reservation.integration.ws.api.Parking;
import pl.hycom.training.reservation.model.LevelDTO;
import pl.hycom.training.reservation.model.ParkingDTO;

/**
 * Class provides implementation for getting data from WS and prepare proper model, which will be used in client web-application.
 * 
 * @author <a href="mailto:dominik.klys@hycom.pl">Dominik Klys, HYCOM</a>
 *
 */
@Service("wsClientService")
public class CarParkClientWSImpl implements IReservationService {

    private static final Logger LOG = LogManager.getLogger(CarParkClientWSImpl.class);

    /**
     * WS client
     */
    @Autowired
    @Qualifier("clientWS")
    private ICarParkWS client;

    /**
     * Method provides list of {@link ParkingDTO} object. Data are provided by web-service.
     * 
     * @return parkings list
     * 
     */
    public List<ParkingDTO> getAllParkings() {

        List<ParkingDTO> result = new ArrayList<ParkingDTO>();

        try {

            GetParkingList request = new GetParkingList();

            GetParkingListResponse response = client.getParkingList(request);

            if (response != null) {
                if (response.getError().getStatus() != ErrorStatus.ERROR) {
                    for (Parking p : response.getParkingList()) {
                        ParkingDTO parking = new ParkingDTO(Long.valueOf(p.getId()), p.getName(), p.getDescription());
                        result.add(parking);
                    }
                } else {
                    LOG.error(response.getError().getMessage());
                }
            } else {
                LOG.error("WS error. Response is null");
            }
        } catch (SoapFaultException | WebServiceException e) {
            LOG.error("WS error: " + e.getMessage());
        }

        return result;
    }

    public ParkingDTO findParking(int id) {
        throw new NotImplementedException("Not implemented yet");
    }

    public LevelDTO findLevel(int parkingId, int levelId) throws ParkingInvalidArgumentException {
        throw new NotImplementedException("Not implemented yet");
    }

    public void book(int parkingId, int levelId, int rowId, int placeId)
            throws PlaceNotAvailableException, PlaceInvalidException {

        throw new NotImplementedException("Not implemented yet");
    }

    public void release(int parkingId, int levelId, int rowId, int placeId) throws PlaceInvalidException {
        throw new NotImplementedException("Not implemented yet");
    }
}
